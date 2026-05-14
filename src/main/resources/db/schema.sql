-- UC16 Database Schema
-- Default: H2 in-memory DB
-- Future: MySQL/PostgreSQL supported (same schema)

CREATE TABLE IF NOT EXISTS quantity_measurement_entity (
                                                           id IDENTITY PRIMARY KEY,        -- Auto-increment ID (H2)
                                                           type VARCHAR(50) NOT NULL,      -- Measurement type (LENGTH, WEIGHT, VOLUME, TEMPERATURE)
    operation VARCHAR(50) NOT NULL, -- Operation performed (ADD, COMPARE, CONVERT)
    operand VARCHAR(255) NOT NULL,  -- Input values (e.g., "1ft+12in")
    result VARCHAR(255) NOT NULL,   -- Result (e.g., "2ft")
    ts TIMESTAMP DEFAULT CURRENT_TIMESTAMP -- Timestamp of operation
    );

-- === MySQL/PostgreSQL (future use) ===
-- Uncomment and adjust if migrating to production DB
/*
CREATE TABLE quantity_measurement_entity (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(50) NOT NULL,
    operation VARCHAR(50) NOT NULL,
    operand VARCHAR(255) NOT NULL,
    result VARCHAR(255) NOT NULL,
    ts TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
*/
