CREATE TABLE account_info (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    profile_picture_url VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_login TIMESTAMP NULL
);

CREATE TABLE account_security (
    id INT AUTO_INCREMENT PRIMARY KEY,
    password_hash VARCHAR(255) NOT NULL
);

CREATE TABLE contact_info (
    id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    nif VARCHAR(50) UNIQUE,
    email VARCHAR(150) UNIQUE NOT NULL,
    phone_number VARCHAR(20) UNIQUE NOT NULL,
    birth_date DATE NOT NULL,
    address TEXT,
    city VARCHAR(100),
    postal_code VARCHAR(20),
    country VARCHAR(100)
);


CREATE TABLE courts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    description TEXT,
    picture_url VARCHAR(255),
    court_zone ENUM('INDOOR', 'OUTDOOR') NOT NULL,
    court_type ENUM('ARTIFICIAL_GRASS', 'HARD_COURT') NOT NULL,
    price_per_match DECIMAL(10,2) NOT NULL
);


CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    level ENUM('BEGINNER', 'INTERMEDIATE', 'ADVANCED', 'EXPERT') NOT NULL,
    account_info_id INT UNIQUE NOT NULL,
    contact_info_id INT UNIQUE NOT NULL,
    account_security_id INT UNIQUE NOT NULL,
    FOREIGN KEY (account_info_id) REFERENCES account_info(id),
    FOREIGN KEY (contact_info_id) REFERENCES contact_info(id),
    FOREIGN KEY (account_security_id) REFERENCES account_security(id)
);


CREATE TABLE reservations (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    court_id INT NOT NULL,
    date DATE NOT NULL,
    is_match BOOLEAN DEFAULT FALSE,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    duration INT NOT NULL,
    status ENUM('CONFIRMED', 'CANCELLED', 'COMPLETED') NOT NULL DEFAULT 'CONFIRMED',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_paid BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (court_id) REFERENCES courts(id)
);


CREATE TABLE matches (
    id INT AUTO_INCREMENT PRIMARY KEY,
    reservation_id INT UNIQUE NOT NULL,
    owner_id INT NOT NULL,
    player_1_id INT,
    player_2_id INT,
    player_3_id INT,
    status ENUM('OPEN', 'CLOSED') NOT NULL DEFAULT 'OPEN',
    level ENUM('BEGINNER', 'INTERMEDIATE', 'ADVANCED', 'EXPERT') NOT NULL,
    FOREIGN KEY (reservation_id) REFERENCES reservations(id),
    FOREIGN KEY (owner_id) REFERENCES users(id),
    FOREIGN KEY (player_1_id) REFERENCES users(id),
    FOREIGN KEY (player_2_id) REFERENCES users(id),
    FOREIGN KEY (player_3_id) REFERENCES users(id)
);


CREATE TABLE payments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    reservation_id INT NOT NULL,
    user_id INT NOT NULL,
    amount DECIMAL(10,2),
    method ENUM('CARD', 'APPLE_PAY', 'CASH'),
    status ENUM('PENDING', 'COMPLETED', 'REFUNDED', 'CANCELLED') NOT NULL DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (reservation_id) REFERENCES reservations(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);


INSERT INTO courts (name, description, picture_url, court_zone, court_type, price_per_match)
VALUES 
('Pista 1', 'Pista cubierta de pádel con superficie dura, ideal para todo clima.', 'court1', 'INDOOR', 'HARD_COURT', 20.00),
('Pista 2', 'Pista cubierta de pádel con superficie dura, ideal para todo clima.', 'court1','INDOOR', 'HARD_COURT', 20.00),
('Pista 3', 'Pista cubierta de pádel con césped artificial, cómoda y protegida del clima.', 'court2','INDOOR', 'ARTIFICIAL_GRASS', 22.00),
('Pista 4', 'Pista cubierta de pádel con césped artificial, cómoda y protegida del clima.', 'court2','INDOOR', 'ARTIFICIAL_GRASS', 22.00),
('Pista 5', 'Pista exterior de pádel con superficie dura, perfecta para días soleados.', 'court3','OUTDOOR', 'HARD_COURT', 16.00),
('Pista 6', 'Pista exterior de pádel con superficie dura, perfecta para días soleados.', 'court3', 'OUTDOOR', 'HARD_COURT', 16.00),
('Pista 7', 'Pista exterior de pádel con césped artificial, ideal para jugar al aire libre.', 'court4','OUTDOOR', 'ARTIFICIAL_GRASS', 18.00),
('Pista 8', 'Pista exterior de pádel con césped artificial, ideal para jugar al aire libre.', 'court4','OUTDOOR', 'ARTIFICIAL_GRASS', 18.00);