CREATE DATABASE foodweb;

USE foodweb;
CREATE TABLE users (

id BIGINT AUTO_INCREMENT PRIMARY KEY,

username VARCHAR(100) UNIQUE NOT NULL,

password VARCHAR(255) NOT NULL,

email VARCHAR(150),

role VARCHAR(50),

created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP

);CREATE TABLE categories (

id BIGINT AUTO_INCREMENT PRIMARY KEY,

name VARCHAR(100) NOT NULL

);INSERT INTO categories(name)

VALUES
('Pizza'),
('Burger'),
('Drink'),
('Fast Food'),
('Dessert');CREATE TABLE products (

id BIGINT AUTO_INCREMENT PRIMARY KEY,

name VARCHAR(200) NOT NULL,

price DOUBLE NOT NULL,

image VARCHAR(500),

description TEXT,

category_id BIGINT,

created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

FOREIGN KEY (category_id) REFERENCES categories(id)

);INSERT INTO products(name,price,image,description,category_id)

VALUES

('Pizza Hải Sản',120000,'pizza.jpg','Pizza topping hải sản',1),

('Burger Bò Phô Mai',85000,'burger.jpg','Burger bò với phô mai',2),

('Trà Sữa Trân Châu',35000,'trasua.jpg','Trà sữa truyền thống',3),

('Gà Rán Giòn',90000,'garan.jpg','Gà rán giòn kiểu KFC',4),

('Kem Vanilla',25000,'kem.jpg','Kem vị vanilla',5);

CREATE TABLE cart (

id BIGINT AUTO_INCREMENT PRIMARY KEY,

user_id BIGINT,

product_id BIGINT,

quantity INT DEFAULT 1,

FOREIGN KEY (user_id) REFERENCES users(id),

FOREIGN KEY (product_id) REFERENCES products(id)

);

CREATE TABLE orders (

id BIGINT AUTO_INCREMENT PRIMARY KEY,

user_id BIGINT,

total DOUBLE,

status VARCHAR(50),

created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

FOREIGN KEY (user_id) REFERENCES users(id)

);

CREATE TABLE order_items (

id BIGINT AUTO_INCREMENT PRIMARY KEY,

order_id BIGINT,

product_id BIGINT,

price DOUBLE,

quantity INT,

FOREIGN KEY (order_id) REFERENCES orders(id),

FOREIGN KEY (product_id) REFERENCES products(id)

);

CREATE TABLE reviews (

id BIGINT AUTO_INCREMENT PRIMARY KEY,

user_id BIGINT,

product_id BIGINT,

rating INT,

comment TEXT,

created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

FOREIGN KEY (user_id) REFERENCES users(id),

FOREIGN KEY (product_id) REFERENCES products(id)

);