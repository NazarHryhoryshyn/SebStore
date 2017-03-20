-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema web_store
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema web_store
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `web_store` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `web_store` ;

-- -----------------------------------------------------
-- Table `web_store`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `web_store`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '',
  `first_name` VARCHAR(45) NULL COMMENT '',
  `last_name` VARCHAR(45) NULL COMMENT '',
  `email` VARCHAR(45) NOT NULL COMMENT '',
  `telephone` VARCHAR(15) NULL COMMENT '',
  `sex` ENUM('male', 'female') NOT NULL COMMENT '',
  `password` VARCHAR(100) NOT NULL COMMENT '',
  `login` VARCHAR(45) NOT NULL COMMENT '',
  PRIMARY KEY (`id`)  COMMENT '')
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `web_store`.`role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `web_store`.`role` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '',
  `name` VARCHAR(45) NOT NULL COMMENT '',
  PRIMARY KEY (`id`)  COMMENT '')
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `web_store`.`user_roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `web_store`.`user_roles` (
  `user_id` INT NOT NULL COMMENT '',
  `role_id` INT NOT NULL COMMENT '',
  PRIMARY KEY (`user_id`, `role_id`)  COMMENT '',
  INDEX `fk_user_has_role_role1_idx` (`role_id` ASC)  COMMENT '',
  INDEX `fk_user_has_role_user_idx` (`user_id` ASC)  COMMENT '',
  CONSTRAINT `fk_user_has_role_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `web_store`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_has_role_role1`
    FOREIGN KEY (`role_id`)
    REFERENCES `web_store`.`role` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `web_store`.`category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `web_store`.`category` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '',
  `name` VARCHAR(45) NOT NULL COMMENT '',
  `is_sub` BIT NOT NULL COMMENT '',
  `main_category_id` INT NULL COMMENT '',
  PRIMARY KEY (`id`)  COMMENT '',
  UNIQUE INDEX `name_UNIQUE` (`name` ASC)  COMMENT '')
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `web_store`.`product`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `web_store`.`product` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '',
  `name` VARCHAR(45) NOT NULL COMMENT '',
  `price` DECIMAL NOT NULL DEFAULT 0 COMMENT '',
  `category_id` INT NOT NULL COMMENT '',
  `producer` VARCHAR(45) NULL COMMENT '',
  `country` VARCHAR(45) NULL COMMENT '',
  `amount_on_warehouse` INT NOT NULL DEFAULT 0 COMMENT '',
  `weight` INT NULL COMMENT '',
  PRIMARY KEY (`id`)  COMMENT '',
  INDEX `fk_product_category1_idx` (`category_id` ASC)  COMMENT '',
  UNIQUE INDEX `name_UNIQUE` (`name` ASC)  COMMENT '',
  CONSTRAINT `fk_product_category1`
    FOREIGN KEY (`category_id`)
    REFERENCES `web_store`.`category` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `web_store`.`cart`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `web_store`.`cart` (
  `user_id` INT NOT NULL COMMENT '',
  `product_id` INT NOT NULL COMMENT '',
  INDEX `fk_cart_user1_idx` (`user_id` ASC)  COMMENT '',
  INDEX `fk_cart_product1_idx` (`product_id` ASC)  COMMENT '',
  CONSTRAINT `fk_cart_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `web_store`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_cart_product1`
    FOREIGN KEY (`product_id`)
    REFERENCES `web_store`.`product` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `web_store`.`feature`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `web_store`.`feature` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '',
  `name` VARCHAR(250) NOT NULL COMMENT '',
  PRIMARY KEY (`id`)  COMMENT '',
  UNIQUE INDEX `name_UNIQUE` (`name` ASC)  COMMENT '')
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `web_store`.`additional_features`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `web_store`.`additional_features` (
  `category_id` INT NOT NULL COMMENT '',
  `feature_id` INT NOT NULL COMMENT '',
  PRIMARY KEY (`category_id`, `feature_id`)  COMMENT '',
  INDEX `fk_category_has_feature_feature1_idx` (`feature_id` ASC)  COMMENT '',
  INDEX `fk_category_has_feature_category1_idx` (`category_id` ASC)  COMMENT '',
  CONSTRAINT `fk_category_has_feature_category1`
    FOREIGN KEY (`category_id`)
    REFERENCES `web_store`.`category` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_category_has_feature_feature1`
    FOREIGN KEY (`feature_id`)
    REFERENCES `web_store`.`feature` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `web_store`.`orders`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `web_store`.`orders` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '',
  `status` ENUM('in processing', 'preparation for sending', 'sent', 'arrived') NOT NULL COMMENT '',
  `delivery_type` ENUM('courier', 'self sending') NOT NULL COMMENT '',
  `date` DATETIME NOT NULL DEFAULT now() COMMENT '',
  `payment_type` ENUM('cash', 'credit card') NOT NULL COMMENT '',
  `receiver` VARCHAR(100) NOT NULL COMMENT '',
  `phone` VARCHAR(15) NULL COMMENT '',
  `email` VARCHAR(45) NULL COMMENT '',
  `delivery_price` DECIMAL NOT NULL DEFAULT 0 COMMENT '',
  `address` VARCHAR(250) NULL COMMENT '',
  `card_number` VARCHAR(16) NULL COMMENT '',
  `card_term_of` VARCHAR(5) NULL COMMENT '',
  `card_three_numbers` INT NULL COMMENT '',
  `user_id` INT NOT NULL COMMENT '',
  PRIMARY KEY (`id`)  COMMENT '',
  INDEX `fk_order_user1_idx` (`user_id` ASC)  COMMENT '',
  CONSTRAINT `fk_order_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `web_store`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `web_store`.`photo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `web_store`.`photo` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '',
  `data` LONGBLOB NOT NULL COMMENT '',
  `product_id` INT NOT NULL COMMENT '',
  PRIMARY KEY (`id`)  COMMENT '',
  CONSTRAINT `fk_photo_product1`
    FOREIGN KEY (`product_id`)
    REFERENCES `web_store`.`product` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `web_store`.`product_extra_features`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `web_store`.`product_extra_features` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '',
  `name` VARCHAR(250) NOT NULL DEFAULT '-' COMMENT '',
  `value` VARCHAR(250) NOT NULL DEFAULT '-' COMMENT '',
  `product_id` INT NOT NULL COMMENT '',
  PRIMARY KEY (`id`)  COMMENT '',
  INDEX `fk_product_extra_features_product1_idx` (`product_id` ASC)  COMMENT '',
  UNIQUE INDEX `name_UNIQUE` (`name` ASC)  COMMENT '',
  CONSTRAINT `fk_product_extra_features_product1`
    FOREIGN KEY (`product_id`)
    REFERENCES `web_store`.`product` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `web_store`.`orders_products`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `web_store`.`orders_products` (
  `orders_id` INT NOT NULL COMMENT '',
  `product_id` INT NOT NULL COMMENT '',
  PRIMARY KEY (`orders_id`, `product_id`)  COMMENT '',
  INDEX `fk_orders_has_product_product1_idx` (`product_id` ASC)  COMMENT '',
  INDEX `fk_orders_has_product_orders1_idx` (`orders_id` ASC)  COMMENT '',
  CONSTRAINT `fk_orders_has_product_orders1`
    FOREIGN KEY (`orders_id`)
    REFERENCES `web_store`.`orders` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_orders_has_product_product1`
    FOREIGN KEY (`product_id`)
    REFERENCES `web_store`.`product` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
