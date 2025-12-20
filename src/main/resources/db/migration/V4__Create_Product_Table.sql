CREATE TABLE IF NOT EXISTS  product (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    stock INTEGER NOT NULL,
    office_id BIGINT NOT NULL,
    CONSTRAINT fk_product_office
    FOREIGN KEY (office_id) REFERENCES office(id)
);