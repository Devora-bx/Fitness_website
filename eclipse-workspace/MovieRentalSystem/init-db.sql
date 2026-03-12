-- Initialize the Users table for MovieRentalSystem
-- Run against: jdbc:h2:C:/Users/USER/test

CREATE TABLE IF NOT EXISTS Users (
    id       INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role     VARCHAR(50)  NOT NULL
);

-- Insert default users (admin and regular user)
MERGE INTO Users (username, password, role) KEY(username)
    VALUES ('admin', 'admin123', 'admin');

MERGE INTO Users (username, password, role) KEY(username)
    VALUES ('user1', 'user123', 'user');
