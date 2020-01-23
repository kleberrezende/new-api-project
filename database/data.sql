INSERT INTO `new_project`.`usuario` (`id`, `login`, `password`, `nome`, `sobre_nome`, `email`, `ativo`, `ativado`, `chave_ativacao`, `chave_reset`, `data_criacao`, `data_ultimo_login`, `data_reset`, `tentativa_login`, `data_ultima_tentativa`) 
VALUES ('1', 'admin', '$2a$10$Dg36RZH2asj.sr.37zS/Cecmuwdslekj1ijCETB2j6eaaoeq6Alfm', 'Admin', 'Sistema', 'admin@sistema.com', '1', '1', null, null, sysdate(), sysdate(), sysdate(), 0, sysdate());

INSERT INTO `new_project`.`autoridade` (`nome`, `descricao`) 
VALUES ('ROLE_ADMIN', 'Administrador'), ('ROLE_USER', 'Usuário');

INSERT INTO `new_project`.`usuario_autoridade` (`usuario_id`, `autoridade_nome`) 
VALUES ('1', 'ROLE_ADMIN'), ('1', 'ROLE_USER');

