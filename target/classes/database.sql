 
DROP TABLE IF EXISTS CreditAccount;

CREATE TABLE CreditAccount (id int NOT NULL AUTO_INCREMENT,
 userName VARCHAR(200) NOT NULL,
 cardNumber VARCHAR(20) NOT NULL,
 cardLimit DECIMAL(19,2),
 balance DECIMAL(19,2) default 0);

CREATE UNIQUE INDEX index_user on CreditAccount(userName,cardNumber);

DROP TABLE IF EXISTS User;

CREATE TABLE User (id int NOT NULL AUTO_INCREMENT,
 userName VARCHAR(30) NOT NULL);