CREATE TABLE IF NOT EXISTS activities (
    habit_id BIGINT,
    activity_id BIGINT,
    PRIMARY KEY (habit_id, activity_id),
    CONSTRAINT fk_habit
        FOREIGN KEY (habit_id)
            REFERENCES habit(id)
                ON DELETE CASCADE,
    CONSTRAINT fk_activity
        FOREIGN KEY (activity_id)
            REFERENCES activity(id)
                ON DELETE CASCADE
);