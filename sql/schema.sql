create table roles (
	id BIGSERIAL PRIMARY KEY,
    name VARCHAR(64)
);

create table accounts (
	id BIGSERIAL PRIMARY KEY,
    UID VARCHAR(50),
    avatar VARCHAR(1000),
    username VARCHAR(64),
    password VARCHAR(20),
    full_name VARCHAR(64),
    gender int,
    email VARCHAR(320),
    dob DATE,
    mobile VARCHAR(11),
    status int,
    roleId int,
    createdAt DATE,
	updatedAt DATE,
    FOREIGN KEY (roleId) REFERENCES roles(id)
);

create table categories (
	id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    createdAt DATE,
	updatedAt DATE
);

create table authors (
	id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    dob DATE,
    sign_name VARCHAR(64),
    createdAt DATE,
	updatedAt DATE
);

create table publishing (
	id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    address VARCHAR(320),
    co_year DATE,
    createdAt DATE,
	updatedAt DATE
);

create table books (
	id BIGSERIAL PRIMARY KEY,
    name VARCHAR(320),
    image VARCHAR(1000),
    co_year DATE,
    price FLOAT,
    quantity int,
    description VARCHAR(255),
    categoryId int,
    authorId int,
    publishId int,
    FOREIGN KEY (categoryId) REFERENCES categories(id),
    FOREIGN KEY (authorId) REFERENCES authors(id),
    FOREIGN KEY (publishId) REFERENCES publishing(id)
);

create table status_manage (
	id BIGSERIAL PRIMARY KEY,
    name VARCHAR(64)
);

create table status_borrow (
	id BIGSERIAL PRIMARY KEY,
    name VARCHAR(64)
);

create table manage_book (
	id BIGSERIAL PRIMARY KEY,
    price_per_book FLOAT,
	accountId int,
    bookId int,
    statusId int,
    createdAt DATE,
	updatedAt DATE,
    FOREIGN KEY (accountId) REFERENCES accounts(id),
    FOREIGN KEY (bookId) REFERENCES books(id),
    FOREIGN KEY (statusId) REFERENCES status_manage(id)
);

create table borrow (
	id BIGSERIAL PRIMARY KEY,
	borrowAt date,
    refundAt date,
    time_out int,
    amount_of_pay float,
    manageId int,
    statusId int,
    FOREIGN KEY (manageId) REFERENCES manage_book(id),
    FOREIGN KEY (statusId) REFERENCES status_borrow(id)
);

INSERT INTO roles (name) VALUES ('Admin');
INSERT INTO roles (name) VALUES ('Librarian');
INSERT INTO roles (name) VALUES ('Reader');

INSERT INTO accounts
    (UID, avatar, username, password, full_name, gender, email, dob, mobile, status, roleId, createdAt, updatedAt)
VALUES
    ('82e3c116-29d5-4e87-a5c7-716ab8cfebc1', 'https://example.com/avatar.png', 'admin', 'password123', 'Admin', 1, 'admin@example.com', '1990-05-15', '0123456789', 1, 1, '2024-11-22', '2024-11-22');

INSERT INTO status_manage (name) VALUES ('Importing');
INSERT INTO status_manage (name) VALUES ('Imported');
INSERT INTO status_manage (name) VALUES ('Out Of Stock');
INSERT INTO status_manage (name) VALUES ('Deleted');
INSERT INTO status_manage (name) VALUES ('Borrowed');

INSERT INTO status_borrow (name) VALUES ('Borrowing');
INSERT INTO status_borrow (name) VALUES ('Refunded');
INSERT INTO status_borrow (name) VALUES ('Out Of Time');