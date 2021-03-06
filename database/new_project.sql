-- MySQL Script generated by MySQL Workbench
-- Qua 24 Abr 2019 23:07:57 -03
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema new_project
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema new_project
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `new_project` DEFAULT CHARACTER SET utf8mb4 ;
USE `new_project` ;

-- -----------------------------------------------------
-- Table `new_project`.`autoridade`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `new_project`.`autoridade` (
  `nome` VARCHAR(50) NOT NULL,
  `descricao` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`nome`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `new_project`.`usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `new_project`.`usuario` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `login` VARCHAR(50) NOT NULL,
  `password` VARCHAR(60) NOT NULL,
  `nome` VARCHAR(20) NOT NULL,
  `sobre_nome` VARCHAR(50) NOT NULL,
  `email` VARCHAR(100) NOT NULL,
  `ativo` TINYINT(1) NOT NULL,
  `ativado` TINYINT(1) NOT NULL,
  `chave_ativacao` VARCHAR(20) NULL,
  `chave_reset` VARCHAR(20) NULL,
  `data_criacao` DATETIME NOT NULL,
  `data_ultimo_login` DATETIME NOT NULL,
  `data_reset` DATETIME NOT NULL,
  `tentativa_login` INT(2) NOT NULL,
  `data_ultima_tentativa` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `login_UNIQUE` (`login` ASC),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `new_project`.`usuario_autoridade`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `new_project`.`usuario_autoridade` (
  `usuario_id` BIGINT(20) NOT NULL,
  `autoridade_nome` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`usuario_id`, `autoridade_nome`),
  INDEX `fk_usuario_autoridade_autoridade_idx` (`autoridade_nome` ASC),
  CONSTRAINT `fk_usuario_autoridade_usuario`
    FOREIGN KEY (`usuario_id`)
    REFERENCES `new_project`.`usuario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_usuario_autoridade_autoridade`
    FOREIGN KEY (`autoridade_nome`)
    REFERENCES `new_project`.`autoridade` (`nome`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `new_project`.`configuracao_empresa`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `new_project`.`configuracao_empresa` (
  `id` INT NOT NULL,
  `tipo_pessoa` VARCHAR(8) NOT NULL,
  `nome_razao` VARCHAR(60) NOT NULL,
  `fantasia` VARCHAR(50) NOT NULL,
  `fantasia_curta` VARCHAR(20) NOT NULL,
  `cpf_cnpj` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `new_project`.`security_token`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `new_project`.`security_token` (
  `token` VARCHAR(750) NOT NULL,
  `id_request` VARCHAR(600) NOT NULL,
  `usuario_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`token`),
  UNIQUE INDEX `security_token_request_UQ` (`id_request` ASC),
  UNIQUE INDEX `security_token_usuario_UQ` (`usuario_id` ASC),
  CONSTRAINT `fk_security_token_usuario`
    FOREIGN KEY (`usuario_id`)
    REFERENCES `new_project`.`usuario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
