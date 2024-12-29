CREATE TABLE IF NOT EXISTS organization (
    id SERIAL PRIMARY KEY,
    description VARCHAR(255),
    title VARCHAR(255) NOT NULL,
    email_address VARCHAR(255) UNIQUE,
    website VARCHAR(255) UNIQUE,
    active BOOLEAN DEFAULT TRUE,
    slogan VARCHAR(255),
    app_server VARCHAR(255),
    tax_id VARCHAR(50),
    corporate_contact VARCHAR(255),
    industry_type VARCHAR(100),
    created_by VARCHAR(255),
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS location (
    id SERIAL PRIMARY KEY,
    org_id INT NOT NULL,
    description VARCHAR(255),
    active BOOLEAN DEFAULT TRUE,
    street_address VARCHAR(255),
    city VARCHAR(100) NOT NULL,
    state VARCHAR(100),
    zip_code VARCHAR(20),
    country VARCHAR(100) NOT NULL,
    latitude DECIMAL(9,6),
    longitude DECIMAL(9,6),
    contact_info VARCHAR(255),
    left_logo VARCHAR(255),
    right_logo VARCHAR(255),
    created_by VARCHAR(255),
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    parent_location_id BIGINT,
    FOREIGN KEY (org_id) REFERENCES organization(id) ON DELETE CASCADE,
    FOREIGN KEY (parent_location_id) REFERENCES location(id)
);

CREATE TABLE employee (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255),
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    email VARCHAR(100) UNIQUE,
    password VARCHAR(255),
    active BOOLEAN default true,
    created_by VARCHAR(255),
    created_at TIMESTAMP,
    last_modified_by VARCHAR(255),
    last_modified_at TIMESTAMP
);

CREATE TABLE token (
    id SERIAL PRIMARY KEY,
    token VARCHAR(255) UNIQUE,
    token_type VARCHAR(50),
    revoked BOOLEAN,
    expired BOOLEAN,
    employee_id INTEGER REFERENCES employee(id)
);


CREATE SEQUENCE employee_seq START 1001;
CREATE SEQUENCE token_seq START 1;
CREATE SEQUENCE organization_seq START 1001;
CREATE SEQUENCE location_seq START 1001;