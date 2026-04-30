CREATE TABLE customer (
    id UUID PRIMARY KEY,
    customer_code VARCHAR(30) NOT NULL UNIQUE,
    company_name VARCHAR(150) NOT NULL,
    legal_name VARCHAR(255),
    tax_number VARCHAR(50),
    industry VARCHAR(100),
    website VARCHAR(255),
    status VARCHAR(20) NOT NULL,
    segment VARCHAR(30) NOT NULL,
    owner_user_id UUID,
    address_country VARCHAR(100),
    address_city VARCHAR(100),
    address_postal_code VARCHAR(20),
    address_line1 VARCHAR(255),
    address_line2 VARCHAR(255),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE customer_tags (
    customer_id UUID NOT NULL,
    tag VARCHAR(50) NOT NULL,
    CONSTRAINT fk_customer_tags_customer FOREIGN KEY (customer_id) REFERENCES customer(id) ON DELETE CASCADE
);