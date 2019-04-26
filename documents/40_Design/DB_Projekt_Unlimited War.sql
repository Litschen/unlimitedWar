/*
*********************************************************************
Name: MySQL Database Project Unlimited War
*********************************************************************
Version 4.0

- remove E-Mail change
- Set new primarykey on Result: ID, REmail
- Set AUTO_INCREMENT for ID
*********************************************************************
*/
DROP DATABASE IF EXISTS Unlimited_War;
CREATE DATABASE Unlimited_War;
USE Unlimited_War;

CREATE TABLE Player
(

    Username varchar(50)  NOT NULL,
    Email    varchar(50)  NOT NULL,
    Password blob NOT NULL,

    CONSTRAINT PK_Player PRIMARY KEY (Email)
);

CREATE TABLE Result
(

    ID      INTEGER     NOT NULL AUTO_INCREMENT,
    Outcome bit(1)      NOT NULL,
    Date    date        NOT NULL,
    REmail  varchar(50) NOT NULL,

    CONSTRAINT PK_Result PRIMARY KEY (ID, REmail),
    CONSTRAINT FK_Result FOREIGN KEY (REmail)
        REFERENCES Player (Email)
);

-- Provisionally inserted user:

INSERT INTO Player(Username, Email, Password)
VALUES ('max01', 'max@gmail.com', '123');
INSERT INTO Player(Username, Email, Password)
VALUES ('lara', 'lara@gmail.com', 'kuchen01');
INSERT INTO Player(Username, Email, Password)
VALUES ('quen', 'quen@gmail.com', '2bbb3');

-- Provisional displaying the results

INSERT INTO Result(Outcome, Date, REmail)
VALUES (1, '2019-02-12', 'max@gmail.com');
INSERT INTO Result(Outcome, Date, REmail)
VALUES (0, '2019-02-14', 'lara@gmail.com');
INSERT INTO Result(Outcome, Date, REmail)
VALUES (1, '2019-02-20', 'quen@gmail.com');
INSERT INTO Result(Outcome, Date, REmail)
VALUES (0, '2019-02-20', 'max@gmail.com');
