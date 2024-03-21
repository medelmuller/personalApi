CREATE TABLE IF NOT EXISTS target
(
    id                  BIGINT PRIMARY KEY,
    target_name         VARCHAR(128),
    description         VARCHAR(10000),
    target_begins       TIMESTAMP,
    target_ends         TIMESTAMP,
    category            VARCHAR(128)
    user_id             BIGINT,
    FOREIGN KEY user_id REFERENCES user(id)
)