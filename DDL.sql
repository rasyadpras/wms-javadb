CREATE TABLE branches (
    branch_no VARCHAR(10) PRIMARY KEY,
    branch_name VARCHAR(50) NOT NULL
);

CREATE TABLE products (
    id VARCHAR(10) PRIMARY KEY,
    product VARCHAR(50) NOT NULL,
    price DECIMAL(10) NOT NULL
);

CREATE TABLE transactions (
    bill_no VARCHAR(20) PRIMARY KEY,
    branch_code VARCHAR(10) NOT NULL,
    transaction_date DATE NOT NULL,
    type ENUM('EAT_IN', 'TAKE_AWAY', 'ONLINE') NOT NULL,
    FOREIGN KEY (branch_code) REFERENCES branches(branch_no)
);

CREATE TABLE detail_transactions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    bill_no VARCHAR(20) NOT NULL,
    product_no VARCHAR(10) NOT NULL,
    quantity INT NOT NULL,
    total_price DECIMAL(20) NOT NULL,
    FOREIGN KEY (bill_no) REFERENCES transactions(bill_no),
    FOREIGN KEY (product_no) REFERENCES products(id)
);