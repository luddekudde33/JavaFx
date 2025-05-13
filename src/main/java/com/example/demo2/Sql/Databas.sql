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

-- ISBN lägg till beroende på hur vi gör
CREATE TABLE Item (
    itemID INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    category VARCHAR(255) NOT NULL,
    authorOrArtist VARCHAR(255),
    publisher VARCHAR(255),
    barcode VARCHAR(255) NOT NULL,
    isbn VARCHAR(13) NOT NULL,
    physicalLocation VARCHAR(255) NOT NULL,
    classification VARCHAR(255)
);


CREATE TABLE ItemCopy (
    copyID INT PRIMARY KEY AUTO_INCREMENT,
    newBarcode VARCHAR(255) UNIQUE NOT NULL,
    isAvailable BOOLEAN DEFAULT TRUE,
    physicalLocation VARCHAR(255) NOT NULL,
    itemID INT,
    FOREIGN KEY (itemID) REFERENCES Item(itemID)
);


CREATE TABLE Loan (
    loanID INT PRIMARY KEY AUTO_INCREMENT,
    loanDate DATETIME DEFAULT CURRENT_TIMESTAMP,
    dueDate DATETIME NOT NULL,
    status INT NOT NULL,
    userID INT,
    copyID INT,
    FOREIGN KEY (userID) REFERENCES User(userID),
    FOREIGN KEY (copyID) REFERENCES ItemCopy(copyID)
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

INSERT INTO Item (title, category, authorOrArtist, publisher, barcode, isbn, physicalLocation, classification) VALUES
('1984', 'Book', 'George Orwell', 'Penguin', 'B001', '0151010269', 'Shelf A1', '823.912'),
('Introduction to Algorithms', 'CourseLiterature', 'Cormen et al.', 'MIT Press', 'B002', '9780262033848', 'Shelf B2', '005.1'),
('Inception', 'DVD', 'Christopher Nolan', 'Warner Bros.', 'D001', '000', 'DVD Shelf', '791.43');


INSERT INTO ItemCopy (newBarcode, isAvailable, physicalLocation, itemID) VALUES
('B001-1', TRUE, 'Shelf A1', 1),
('B002-1', TRUE, 'Shelf B2', 2),
('D001-1', FALSE, 'DVD Shelf', 3);


INSERT INTO Loan (loanDate, dueDate, status, userID, copyID) VALUES
(NOW(), DATE_ADD(NOW(), INTERVAL 21 DAY), 1, 1, 1),
(NOW(), DATE_ADD(NOW(), INTERVAL 30 DAY), 1, 2, 2),
(NOW(), DATE_ADD(NOW(), INTERVAL 7 DAY), 2, 3, 3); -- Status 2 = returned, t.ex.


INSERT INTO Reminder (reminderDate, loanID) VALUES
(NOW(), 1),
(NOW(), 3);

INSERT INTO Staff (staffFullName, staffEmail, staffPhoneNr, staffPassword)
VALUES
    ('Bibliotekarie Anna', 'anna.staff@example.com', '0701234567', 'hemligtLösen1'),
    ('Bibliotekarie Erik', 'erik.staff@example.com', '0702345678', 'hemligtLösen2');


