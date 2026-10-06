package com.example.cyberguardian.service;
import com.example.cyberguardian.dto.LoginRequest;
import com.example.cyberguardian.entity.Parent;
import com.example.cyberguardian.repository.ParentRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.cyberguardian.service.JwtService;
import java.util.Optional;

@Service
public class ParentService {

    private final ParentRepository parentRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public ParentService(ParentRepository parentRepository,
                         PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.parentRepository = parentRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public Parent saveParent(Parent parent) {

        String hashedPassword =
                passwordEncoder.encode(parent.getPasswordHash());

        parent.setPasswordHash(hashedPassword);

        return parentRepository.save(parent);
    }
    public Optional<Parent> getParentById(Long parentId) {
        return parentRepository.findById(parentId);
    }

    public Optional<Parent> getParentByEmail(String email) {
        return parentRepository.findByEmail(email);
    }

    public String login(LoginRequest request) {

        Optional<Parent> parentOptional =
                parentRepository.findByEmail(request.getEmail());

        if (parentOptional.isEmpty()) {
            return null;
        }

        Parent parent = parentOptional.get();

        boolean passwordCorrect = passwordEncoder.matches(
                request.getPassword(),
                parent.getPasswordHash()
        );

        if (!passwordCorrect) {
            return null;
        }

        return jwtService.generateToken(parent.getEmail());
    }
}