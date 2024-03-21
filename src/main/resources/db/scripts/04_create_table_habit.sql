CREATE TABLE IF NOT EXISTS habit
(
    id                      BIGINT AUTO_INCREMENT PRIMARY KEY,
    habit_name              VARCHAR(255),
    habit_description       TEXT,
    frequency_type          VARCHAR(255),
    target_id               BIGINT,
    CONSTRAINT fk_target
        FOREIGN KEY (target_id)
            REFERENCES target(id)
            ON DELETE SET NULL
);