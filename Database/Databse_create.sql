SELECT version();
CREATE DATABASE cyberguardian;
use cyberguardian;
show databases;
CREATE TABLE parents (
    parent_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
DESCRIBE parents;

CREATE TABLE children (
    child_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    parent_id BIGINT NOT NULL,
    name VARCHAR(100) NOT NULL,
    connection_code VARCHAR(100) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (parent_id)
        REFERENCES parents(parent_id)
);
DESCRIBE children;

CREATE TABLE policies (
    policy_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    child_id BIGINT NOT NULL,
    target_type VARCHAR(30) NOT NULL,
    target_value VARCHAR(255) NOT NULL,
    action VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (child_id)
        REFERENCES children(child_id)
);

DESCRIBE policies;
CREATE TABLE browsing_history (
    history_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    child_id BIGINT NOT NULL,
    url VARCHAR(2048) NOT NULL,
    category VARCHAR(50),
    visited_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    risk_level VARCHAR(20),
    action_taken VARCHAR(20),

    FOREIGN KEY (child_id)
        REFERENCES children(child_id)
);
DESCRIBE browsing_history;

CREATE TABLE search_history (
    search_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    child_id BIGINT NOT NULL,
    search_query VARCHAR(1000) NOT NULL,
    searched_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (child_id)
        REFERENCES children(child_id)
);

DESCRIBE search_history;

CREATE TABLE access_requests (
    request_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    child_id BIGINT NOT NULL,
    url VARCHAR(2048) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    requested_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    responded_at TIMESTAMP NULL,

    FOREIGN KEY (child_id)
        REFERENCES children(child_id)
);

CREATE TABLE alerts (
    alert_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    parent_id BIGINT NOT NULL,
    child_id BIGINT NOT NULL,
    alert_type VARCHAR(50) NOT NULL,
    message VARCHAR(500) NOT NULL,
    is_read BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (parent_id)
        REFERENCES parents(parent_id),

    FOREIGN KEY (child_id)
        REFERENCES children(child_id)
);

DESCRIBE alerts;
ALTER TABLE children
ADD COLUMN extension_status VARCHAR(20) NOT NULL DEFAULT 'DISCONNECTED',
ADD COLUMN last_seen TIMESTAMP NULL;

DESCRIBE children;
SHOW TABLES;