CREATE TABLE IF NOT EXISTS tbl_products (
    sku VARCHAR(10) PRIMARY KEY,
    price DECIMAL(10, 2) NOT NULL,
    description VARCHAR(255) NOT NULL,
    category VARCHAR(50) NOT NULL
);