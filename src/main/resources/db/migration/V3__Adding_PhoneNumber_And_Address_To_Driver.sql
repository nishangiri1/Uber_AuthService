ALTER TABLE driver
    ADD address VARCHAR(255) NULL;

ALTER TABLE driver
    ADD phone_number BIGINT NULL;

ALTER TABLE driver
    MODIFY address VARCHAR (255) NOT NULL;

ALTER TABLE driver
    MODIFY phone_number BIGINT NOT NULL;