CREATE TABLE IF NOT EXISTS activity
(
    id                      BIGINT PRIMARY KEY,
    activity_name           VARCHAR(128),
    activity_description    VARCHAR(10000)
)