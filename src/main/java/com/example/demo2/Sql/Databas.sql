DROP DATABASE IF EXISTS LibraryDB;
CREATE DATABASE LibraryDB;
USE LibraryDB;


CREATE TABLE UserCategory (
    categoryID INT PRIMARY KEY AUTO_INCREMENT,
    categoryName VARCHAR(255) NOT NULL,
    maxLoans INT NOT NULL,
    loanPeriodBooks INT NOT NULL,
    loanPeriodCourseLit INT NOT NULL,
    loanPeriodDVDs INT NOT NULL
);

-- Oklart
CREATE TABLE Address (
    addressID INT PRIMARY KEY AUTO_INCREMENT,
    addressLine VARCHAR(255) NOT NULL,
    zipCode VARCHAR(5) NOT NULL,
    country VARCHAR(255) NOT NULL
);

-- phone, oklar
CREATE TABLE `User` (
    userID INT PRIMARY KEY AUTO_INCREMENT,
    fullName VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phoneNr VARCHAR(255),
    password VARCHAR (50),
    categoryID INT,
    addressID INT,
    FOREIGN KEY (categoryID) REFERENCES UserCategory(categoryID),
    FOREIGN KEY (addressID) REFERENCES Address(addressID)
);

CREATE TABLE Book (
    bookID INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    category VARCHAR(255) NOT NULL,
    author VARCHAR(255),
    publisher VARCHAR(255),
    barcode VARCHAR(255) NOT NULL,
    isbn VARCHAR(13) NOT NULL,
    physicalLocation VARCHAR(255) NOT NULL,
    classification VARCHAR(255),
    isAvailable INT DEFAULT 1
);

CREATE TABLE Movie(
    movieID INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    mainCharacter VARCHAR(255) NOT NULL,
    barcode VARCHAR(255) NOT NULL,
    physicalLocation VARCHAR(255) NOT NULL,
    category VARCHAR(255) NOT NULL,
    isAvailable INT DEFAULT 1
);


CREATE TABLE Loan (
    loanID INT PRIMARY KEY AUTO_INCREMENT,
    loanDate DATETIME DEFAULT CURRENT_TIMESTAMP,
    dueDate DATETIME NOT NULL,
    status INT NOT NULL,
    userID INT,
    bookID INT NULL,
    movieID INT NULL,
    FOREIGN KEY (bookID) REFERENCES Book(bookID),
    FOREIGN KEY (movieID) REFERENCES Movie(movieID),
    FOREIGN KEY (userID) REFERENCES User(userID)
);


CREATE TABLE Reminder (
    reminderID INT PRIMARY KEY AUTO_INCREMENT,
    reminderDate DATETIME DEFAULT CURRENT_TIMESTAMP,
    loanID INT,
    FOREIGN KEY (loanID) REFERENCES Loan(loanID)
);

CREATE TABLE Staff (
    staffID    INT PRIMARY KEY AUTO_INCREMENT,
    staffFullName   VARCHAR(255) NOT NULL,
    staffEmail      VARCHAR(255) UNIQUE NOT NULL,
    staffPhoneNr    VARCHAR(50),
    staffPassword   VARCHAR(255) NOT NULL
);

-- TestData

INSERT INTO UserCategory (categoryName, maxLoans, loanPeriodBooks, loanPeriodCourseLit, loanPeriodDVDs) VALUES
('Student', 5, 21, 14, 7),
('Teacher', 10, 30, 30, 14),
('Guest', 2, 14, 7, 3);

INSERT INTO Address (addressLine, zipCode, country) VALUES
('Storgatan 1', '11111', 'Sweden'),
('Lilla Vägen 22', '22222', 'Sweden'),
('Biblioteksgatan 5', '33333', 'Sweden');

INSERT INTO `User` (fullName, email, phoneNr, password, categoryID, addressID) VALUES
('Anna Svensson', 'anna@example.com', '0701234567', 'Hejsan123', 1, 1),
('Erik Johansson', 'erik@example.com', '0702345678', 'Svejsan456', 2, 2),
('Lisa Andersson', 'lisa@example.com', '0703456789','fickintedelta789', 3, 3);

INSERT INTO Book (title, category, author, publisher, barcode, isbn, physicalLocation, classification, isAvailable) VALUES
('1984', 'Bok', 'George Orwell', 'Penguin', 'B001', '0151010269', 'Shelf A1', '823.912', 1),
('Introduction to Algorithms', 'Kurslitteratur', 'Cormen et al.', 'MIT Press', 'B002', '9780262033848', 'Shelf B2', '005.1', 2),
('Inception', 'Bok', 'Christopher Nolan', 'Warner Bros.', 'D001', '000', 'Shelf B2', '791.43', 0),
('Java How to Program - Early Objects', 'Kurslitteratur', 'Deitel & Deitel', 'Pearson', '9781292223858', '1-292-22385-5', 'Shelf B2', '90000', 8); 

INSERT INTO Movie (title, mainCharacter, barcode, physicalLocation, category, isAvailable) VALUES
('The Matrix',        'Neo',              '1234567890123', 'Shelf A1', 'DVD', 1),
('Inception',         'Dom Cobb',         '2345678901234', 'Shelf B2', 'DVD', 0),
('The Godfather',     'Michael Corleone', '3456789012345', 'Shelf C3', 'Blu-ray', 2),
('Interstellar',      'Cooper',           '4567890123456', 'Shelf D4', 'DVD', 1),
('Forrest Gump',      'Forrest Gump',     '5678901234567', 'Shelf E5', 'Blu-ray', 1);



INSERT INTO Loan (loanDate, dueDate, status, userID, bookID, movieID) VALUES
(NOW(), DATE_ADD(NOW(), INTERVAL 21 DAY), 1, 1, 1, NULL),
(NOW(), DATE_ADD(NOW(), INTERVAL 30 DAY), 1, 2, NULL, 1),
(NOW(), DATE_ADD(NOW(), INTERVAL 7 DAY), 2, 3, 2, NULL),
(NOW(), DATE_SUB(NOW(), INTERVAL 5 DAY), 1, 1, 1, NULL);
-- Status 2 = returned, t.ex.


INSERT INTO Reminder (reminderDate, loanID) VALUES
(NOW(), 1),
(NOW(), 3);

INSERT INTO Staff (staffFullName, staffEmail, staffPhoneNr, staffPassword)
VALUES
    ('Bibliotekarie Anna', 'anna.staff@example.com', '0701234567', 'hemligtLösen1'),
    ('Bibliotekarie Erik', 'erik.staff@example.com', '0702345678', 'hemligtLösen2');


