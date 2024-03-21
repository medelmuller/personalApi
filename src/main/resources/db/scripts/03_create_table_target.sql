CREATE TABLE IF NOT EXISTS target (
    id                      BIGINT AUTO_INCREMENT PRIMARY KEY,
    target_name             VARCHAR(255) NOT NULL,
    description             TEXT,
    target_begins           TIMESTAMP,
    target_ends             TIMESTAMP,
    category                VARCHAR(255), -- Adjust based on how you represent the TargetCategory enum
    user_id                 BIGINT,
    CONSTRAINT fk_target_user FOREIGN KEY (user_id) REFERENCES users(id)
);