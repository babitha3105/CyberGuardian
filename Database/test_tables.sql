use cyberguardian;
INSERT INTO parents (name, email, password_hash)
VALUES ('Test Parent', 'testparent@example.com', 'dummy_hash');
SELECT * FROM parents;
INSERT INTO children
    (parent_id, name, connection_code, extension_status)
VALUES
    (1, 'Test Child', 'TEST-CODE-001', 'DISCONNECTED');
    SELECT * FROM children;
INSERT INTO policies
    (child_id, target_type, target_value, action)
VALUES
    (1, 'WEBSITE', 'example.com', 'BLOCK');
    SELECT * FROM policies;
    
    INSERT INTO browsing_history
    (child_id, url, category, risk_level, action_taken)
VALUES
    (1, 'https://example.com', 'Education', 'LOW', 'ALLOWED');
    SELECT * FROM browsing_history;
    
    INSERT INTO search_history
    (child_id, search_query)
VALUES
    (1, 'how to learn Java');
    SELECT * FROM search_history;
    
    INSERT INTO access_requests
    (child_id, url, status)
VALUES
    (1, 'https://example.com', 'PENDING');
    SELECT * FROM access_requests;
    
    INSERT INTO alerts
    (parent_id, child_id, alert_type, message)
VALUES
    (1, 1, 'RISK_DETECTED', 'Test risky website alert');
    SELECT * FROM alerts;
    DESCRIBE cyberguardian.alerts;
    SELECT
    p.parent_id,
    p.name AS parent_name,
    c.child_id,
    c.name AS child_name
FROM parents p
JOIN children c
    ON p.parent_id = c.parent_id;