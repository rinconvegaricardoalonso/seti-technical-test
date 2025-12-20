CREATE TABLE IF NOT EXISTS office (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    franchise_id BIGINT NOT NULL,
    CONSTRAINT fk_office_franchise
    FOREIGN KEY (franchise_id) REFERENCES franchise(id)
);