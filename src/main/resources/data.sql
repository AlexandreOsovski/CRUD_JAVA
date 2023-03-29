CREATE TABLE `Usuarios`.`Usuario` (
    `id` INT(11) NOT NULL AUTO_INCREMENT AND ADD UNIQUE INDEX `id_UNIQUE` (`id` ASC),
    `nome` VARCHAR(255) NULL,
    `sobreNome` VARCHAR(255) NULL,
    `senha` VARCHAR(45) NULL,
    PRIMARY KEY (`id`)
);
