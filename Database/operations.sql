USE cyberguardian;
ALTER TABLE search_history
ADD COLUMN category VARCHAR(50),
ADD COLUMN risk_level VARCHAR(20);
select * from cyberguardian.browsing_history;
select * from cyberguardian.alerts;

DESCRIBE search_history;
SELECT * FROM children;
SELECT * FROM parents;
select * from cyberguardian.search_history;
select * from cyberguardian.policies;

SELECT * FROM cyberguardian.search_history
ORDER BY search_id DESC
LIMIT 5;

SELECT * FROM cyberguardian.alerts
ORDER BY alert_id DESC;
SELECT search_id, child_id, search_query, category, risk_level, searched_at
FROM cyberguardian.search_history
WHERE child_id = 2
ORDER BY search_id DESC
LIMIT 5;

INSERT INTO cyberguardian.policies
(`child_id`, `target_type`, `target_value`, `action`, `created_at`)
VALUES
(3, 'WEBSITE', 'example.com', 'BLOCK', NOW());
SELECT * FROM policies;
SHOW TABLES;

SELECT * FROM access_requests
ORDER BY request_id DESC
LIMIT 5;
DESCRIBE access_requests;
UPDATE access_requests
SET status = 'DENIED'
WHERE request_id = 5;

SELECT request_id, child_id, url, status, requested_at, responded_at
FROM access_requests
WHERE request_id = 6;


SELECT request_id, child_id, url, status
FROM access_requests
WHERE child_id = 3
AND url = 'https://example.com/';

select * from cyberguardian.access_requests;

INSERT INTO policies
(child_id, target_type, target_value, action)
VALUES
(3, 'WEBSITE', 'example.org', 'BLOCK');

INSERT INTO policies
(child_id, target_type, target_value, action)
VALUES
(3, 'WEBSITE', 'example.net', 'BLOCK');

INSERT INTO policies
(child_id, target_type, target_value, action)
VALUES
(3, 'WEBSITE', 'example.edu', 'BLOCK');


select * from cyberguardian.policies;
select * from cyberguardian.access_requests;
INSERT INTO policies
(child_id, target_type, target_value, action)
VALUES
(3, 'WEBSITE', 'example.dev', 'BLOCK');

INSERT INTO policies
(child_id, target_type, target_value, action)
VALUES
(3, 'WEBSITE', 'wikipedia.org', 'BLOCK');

CREATE TABLE parent_fcm_tokens (
    token_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    parent_id BIGINT NOT NULL,
    fcm_token VARCHAR(1000) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_parent_fcm
        FOREIGN KEY (parent_id)
        REFERENCES parents(parent_id)
        ON DELETE CASCADE
);
SELECT
    alert_id,
    child_id,
    parent_id,
    alert_type,
    message,
    is_read,
    created_at
FROM alerts
WHERE child_id = 3
ORDER BY created_at DESC;

UPDATE alerts
SET is_read = 1
WHERE alert_id = 21;

SELECT alert_id, alert_type, is_read
FROM alerts
WHERE child_id = 3
AND alert_type = 'HIGH_RISK_WEBSITE';

select * from cyberguardian.policies;
INSERT INTO policies
(child_id, target_type, target_value, action)
VALUES
(3, 'WEBSITE', 'test-access-request.com', 'BLOCK');
INSERT INTO policies
(child_id, target_type, target_value, action)
VALUES
(3, 'WEBSITE', 'cyberguardian-test123.com', 'BLOCK');

INSERT INTO policies
(child_id, target_type, target_value, action)
VALUES
(3, 'WEBSITE', 'gnu.org', 'BLOCK');

DELETE FROM policies
WHERE child_id = 3
AND target_value = 'gnu.org';

SELECT child_id, name, parent_id, connection_code
FROM children;

UPDATE children
SET parent_id = 4;

SELECT
    alert_id,
    child_id,
    parent_id,
    alert_type,
    message,
    is_read
FROM alerts
WHERE alert_type = 'TAMPER_DETECTED';

UPDATE alerts
SET is_read = 1
WHERE alert_type = 'TAMPER_DETECTED';

UPDATE alerts
SET is_read = 1
WHERE alert_id IN (8, 9, 11, 12, 13);

SELECT
    alert_id,
    child_id,
    alert_type,
    is_read
FROM alerts
WHERE alert_id IN (8, 9, 11, 12, 13);

UPDATE alerts
SET is_read = 1
WHERE alert_id IN (31, 32, 33);

SELECT
    alert_id,
    child_id,
    alert_type,
    is_read
FROM alerts
WHERE alert_id IN (31, 32, 33);

SELECT
    child_id,
    name,
    connection_code,
    extension_status,
    last_seen,
    parent_id
FROM children;

SELECT
    child_id,
    name,
    connection_code,
    extension_status,
    last_seen,
    parent_id
FROM children
ORDER BY child_id;

select * from parents;
SELECT 
    alert_id,
    child_id,
    parent_id,
    alert_type,
    created_at,
    is_read
FROM
    alerts
ORDER BY created_at DESC;

SELECT
    alert_id,
    child_id,
    parent_id,
    alert_type,
    created_at,
    is_read
FROM alerts
WHERE child_id = 3
ORDER BY created_at DESC;


SELECT
    child_id,
    name,
    parent_id,
    connection_code,
    extension_status
FROM children
WHERE child_id = 3;

INSERT INTO policies
(child_id, target_type, target_value, action)
VALUES
(3, 'WEBSITE', 'youtube.com', 'BLOCK');

SELECT COUNT(*) AS total_child_3_alerts
FROM alerts
WHERE child_id = 3;

SELECT
    child_id,
    name,
    last_seen,
    extension_status
FROM children
WHERE child_id = 3;

SELECT
    alert_id,
    child_id,
    alert_type,
    message,
    is_read,
    created_at
FROM alerts
WHERE child_id = 3
ORDER BY created_at DESC; 

SELECT *
FROM parent_fcm_tokens;
SELECT
    c.child_id,
    c.name,
    c.parent_id,
    p.name AS parent_name,
    t.fcm_token
FROM children c
JOIN parents p
    ON c.parent_id = p.parent_id
LEFT JOIN parent_fcm_tokens t
    ON t.parent_id = p.parent_id
WHERE c.child_id = 3;

SELECT *
FROM parent_fcm_tokens;

INSERT INTO policies
(child_id, target_type, target_value, action)
VALUES
(3, 'WEBSITE', 'facebook.com', 'BLOCK');

SELECT search_id, child_id, search_query, risk_level, searched_at
FROM search_history
WHERE child_id = 3
ORDER BY search_id DESC
LIMIT 10;

UPDATE search_history
SET risk_level = 'HIGH'
WHERE child_id = 3
AND search_id IN (49, 50, 51);

SELECT parent_id, email, password_hash
FROM parents
WHERE parent_id = 6;

SELECT child_id, name, parent_id
FROM children
WHERE child_id = 3;

SELECT parent_id, name, email
FROM parents
WHERE parent_id = 4;

SELECT 
    policy_id,
    child_id,
    target_type,
    target_value,
    action
FROM policies
WHERE child_id = 3;

SHOW TABLES;

SELECT *
FROM urlhaus_threats
LIMIT 5;

SELECT *
FROM openphish_threats
LIMIT 5;

