/*
*********************************************************************
Name: MySQL Database Projekt Unlimited War
*********************************************************************
Version 2.0
*********************************************************************
*/
DROP DATABASE IF EXISTS Unlimited_War;
CREATE DATABASE Unlimited_War;
USE Unlimited_War;

CREATE TABLE Player(

	Username varchar(50) NOT NULL,
    Email varchar(50) NOT NULL,
    Password varchar(100) NOT NULL,
    
    CONSTRAINT PK_Player PRIMARY KEY (Email)
);

CREATE TABLE Result(

	ID INTEGER NOT NULL,
    Outcome bit(1) NOT NULL,
    Datum date NOT NULL,
	REmail varchar(50) NOT NULL,
   
    CONSTRAINT PK_Result PRIMARY KEY (ID),
	CONSTRAINT FK_Result FOREIGN KEY (REmail)
    REFERENCES Player (Email)
);

-- Provisorisch Benutzer eingefügt:

INSERT INTO Player(Username, Email, Password) VALUES ('max01', 'max@gmail.com', '123');
INSERT INTO Player(Username, Email, Password) VALUES ('lara', 'lara@gmail.com', 'kuchen01');
INSERT INTO Player(Username, Email, Password) VALUES ('quen', 'quen@gmail.com', '2bbb3');

-- Update cascade

