CREATE TABLE IF NOT EXISTS room_pricing (
    id SERIAL PRIMARY KEY,
    room_type VARCHAR(100) NOT NULL,
    base_price NUMERIC(10, 2) NOT NULL,
    seasonal_multiplier NUMERIC(5, 2) DEFAULT 1.0,
    demand_multiplier NUMERIC(5, 2) DEFAULT 1.0,
    start_date DATE, -- Defines when the seasonal multiplier starts
    end_date DATE,   -- Defines when the seasonal multiplier ends
    active BOOLEAN DEFAULT TRUE,
    created_by VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified_by VARCHAR(255),
    last_modified_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS promotions (
    id SERIAL PRIMARY KEY,
    promotion_code VARCHAR(100) UNIQUE NOT NULL,
    discount_percentage NUMERIC(5, 2) NOT NULL,
    valid_from DATE NOT NULL,
    valid_to DATE NOT NULL,
    applicable_room_types TEXT, -- Can store room types as JSON
    max_usage_limit INT DEFAULT NULL,
    created_by VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified_by VARCHAR(255),
    last_modified_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS loyalty_program (
    id SERIAL PRIMARY KEY,
    guest_id INT NOT NULL REFERENCES guests(id) ON DELETE CASCADE,
    points INT NOT NULL DEFAULT 0,
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified_by VARCHAR(255),
    last_modified_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE SEQUENCE room_pricing_seq START 1001;
CREATE SEQUENCE promotions_seq START 1001;
CREATE SEQUENCE loyalty_program_seq START 1001;
