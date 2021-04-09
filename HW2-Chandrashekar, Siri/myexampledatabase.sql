create database exampledatabase;
use exampledatabase;
CREATE TABLE `productdetails` (
  `ProductType` VARCHAR(40) NULL,
  `id` VARCHAR(11) NULL,
  `productName` VARCHAR(45) NULL,
  `productPrice` DOUBLE NULL,
  `productImage` VARCHAR(45) NULL,
  `productCondition` VARCHAR(45) NULL,
  `productDiscount` DOUBLE NULL,
  `inventory` INT NOT NULL DEFAULT '5');

CREATE TABLE `orders` (
  `OrderId` INT NOT NULL,
  `userName` VARCHAR(45) NOT NULL,
  `orderName` VARCHAR(45) NOT NULL,
  `orderPrice` VARCHAR(45) NULL,
  `userAddress` VARCHAR(45) NULL,
  `creditCardNo` VARCHAR(45) NULL,
  `orderTime` DATE NULL);

CREATE TABLE `user` (
  `username` VARCHAR(45) NULL,
  `password` VARCHAR(45) NULL,
  `repassword` VARCHAR(45) NULL,
  `usertype` VARCHAR(45) NULL);


