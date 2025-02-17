drop database menefreda;
create database menefreda;
use menefreda;

create table plano (
	cod_plano int primary key auto_increment,
    tipo_plano varchar(255),
    valor_plano numeric(10,2)
);

create table paciente (
	cod_paci int unique auto_increment,
	cpf_paci char(11) primary key,
    genero_paci char(1),
    email_paci varchar(255),
    nome_paci varchar(255),
    dt_nasc_paci date,
    end_paci varchar(255),
    tel_paci varchar(255),
    cod_plano int,
    foreign key(cod_plano) references plano(cod_plano)
);

create table dependente (
	cod_dep int unique auto_increment,
	cpf_dep char(11) primary key,
    cpf_paci char(11),
    tel_emergencia varchar(255),
    genero_dep char(1),
    nm_dep varchar(255),
    dt_nasc_dep date,
    parentesco varchar(255),
    foreign key(cpf_paci) references paciente(cpf_paci)
);

create table medico (
	cod_medico int primary key auto_increment,
    nome_medico varchar (255),
    area_atuacao varchar(255),
    tel_medico varchar (255)
);

create table contrato (
	cod_cont int primary key auto_increment,
	cpf_paci char(11),
	cod_plano int,
	dt_inicio date,
	validade date,
	valor float,
	foreign key(cpf_paci) references paciente(cpf_paci),
	foreign key(cod_plano) references plano(cod_plano)
);

create table hospital (
	cod_hosp int primary key auto_increment,
    tipo_hosp varchar(255),
    nm_hosp varchar(255),
    end_hosp varchar(255),
    tel_hosp varchar(255),
    especialidade varchar(255)
);

create table exame (
	cod_exame int primary key auto_increment,
    nm_exame varchar(255),
    observacaos varchar(255),
    tipo_exame varchar(255),
    carencia varchar(255)
);

create table procedimento (
	cod_proc int primary key auto_increment,
    tipo_proc varchar(255),
    desc_proc varchar(255)
);

create table planoExame (
	cod_exame int,
    cod_plano int,
    status_exame varchar(255),
    dt_exame date,
    locla_exame varchar(255)
);

create table planoProcedimento (
	cod_proc int,
    cod_plano int,
    local_proc varchar(255),
    status_proc varchar(255)
); 

INSERT INTO plano (tipo_plano, valor_plano) VALUES
('Bronze', 90.00),
('Prata', 140.00),
('Gold', 180.00),
('Diamante', 200.00);

insert into paciente (cpf_paci, nome_paci, genero_paci, email_paci, dt_nasc_paci, end_paci, tel_paci, cod_plano) 
values
('12345678901', 'Carlos Silva', 'M', 'carlos.silva@email.com', '1985-04-15', 'Rua A, 123', '11987654321', 1),
('23456789012', 'Maria Oliveira', 'F', 'maria.oliveira@email.com', '1990-07-22', 'Rua B, 456', '12976543210', 2),
('34567890123', 'José Pereira', 'M', 'jose.pereira@email.com', '1982-03-10', 'Rua C, 789', '34965432109', 3),
('45678901234', 'Ana Costa', 'F', 'ana.costa@email.com', '1995-11-05', 'Rua D, 1011', '34954321098', 4),
('56789012345', 'Lucas Martins', 'M', 'lucas.martins@email.com', '1992-12-30', 'Rua E, 1213', '33943210987', 5),
('67890123456', 'Fernanda Lima', 'F', 'fernanda.lima@email.com', '1988-02-17', 'Rua F, 1415', '32932109876', 1),
('78901234567', 'Ricardo Souza', 'M', 'ricardo.souza@email.com', '1980-09-25', 'Rua G, 1617', '30921098765', 2),
('89012345678', 'Juliana Rocha', 'F', 'juliana.rocha@email.com', '2000-05-11', 'Rua H, 1819', '10910987654', 3),
('90123456789', 'Gustavo Almeida', 'M', 'gustavo.almeida@email.com', '1998-10-03', 'Rua I, 2021', '11898765432', 4),
('01234567890', 'Patrícia Ferreira', 'F', 'patricia.ferreira@email.com', '1993-06-21', 'Rua J, 2223', '11887654321', 5);

INSERT INTO dependente (cpf_dep, cpf_paci, tel_emergencia, genero_dep, nm_dep, dt_nasc_dep, parentesco) VALUES
('11223344551', '12345678901', '1199999-1111', 'F', 'Sophia Silva', '2015-03-15', 'Filha'),
('22334455662', '23456789012', '1198888-2222', 'M', 'Lucas Pereira', '2018-07-20', 'Filho'),
('33445566773', '34567890123', '1197777-3333', 'F', 'Laura Costa', '2020-01-10', 'Filha'),
('44556677884', '45678901234', '1196666-4444', 'M', 'Enzo Santos', '2012-12-05', 'Filho'),
('55667788995', '56789012345', '1195555-5555', 'F', 'Valentina Oliveira', '2022-05-18', 'Filha');

insert into contrato (cod_cont, cpf_paci, cod_plano, dt_inicio, validade, valor)
values
(1, '12345678901', 2, '2024-01-01', '2025-01-01', 350.00),  -- Carlos Silva contrata Plano Premium
(2, '23456789012', 1, '2024-02-15', '2025-02-15', 150.00),  -- Maria Oliveira contrata Plano Básico
(3, '34567890123', 3, '2024-03-10', '2025-03-10', 250.00),  -- José Pereira contrata Plano Família
(4, '45678901234', 3, '2024-04-01', '2025-04-01', 180.00),  -- Ana Costa contrata Plano Infantil
(5, '56789012345', 2, '2024-05-20', '2025-05-20', 370.00),  -- Lucas Martins contrata Plano Premium
(6, '67890123456', 4, '2024-06-10', '2025-06-10', 90.00),   -- Fernanda Lima contrata Plano Odontológico
(7, '78901234567', 1, '2024-07-05', '2025-07-05', 160.00),   -- Ricardo Souza contrata Plano Básico
(8, '89012345678', 3, '2024-08-12', '2025-08-12', 250.00),   -- Juliana Rocha contrata Plano Família
(9, '90123456789', 4, '2024-09-15', '2025-09-15', 190.00),   -- Gustavo Almeida contrata Plano Infantil
(10, '01234567890', 3, '2024-10-01', '2025-10-01', 100.00);  -- Patrícia Ferreira contrata Plano Odontológico

INSERT INTO medico (nome_medico, area_atuacao, tel_medico) VALUES
('Dra. Ana Claudia', 'Cardiologia', '112111-1111'),
('Dr. Roberto Almeida', 'Ortopedia', '112222-2222'),
('Dra. Carla Mendes', 'Pediatria', '112333-3333'),
('Dr. Marcelo Costa', 'Dermatologia', '112444-4444'),
('Dra. Fernanda Lima', 'Ginecologia', '(12555-5555'),
('Dr. Paulo Henrique', 'Neurologia', '(12666-6666'),
('Dra. Beatriz Rocha', 'Oftalmologia', '112777-7777'),
('Dr. Gustavo Santos', 'Oncologia', '112888-8888'),
('Dra. Lúcia Pereira', 'Psiquiatria', '112999-9999'),
('Dr. André Oliveira', 'Cirurgia Geral', '112100-0000');

INSERT INTO hospital (tipo_hosp, nm_hosp, end_hosp, tel_hosp, especialidade) VALUES
('Privado', 'Hospital Vida', 'Av. das Nações, 1000', '113111-1111', 'Cardiologia'),
('Público', 'Santa Casa', 'Rua da Caridade, 500', '113222-2222', 'Atendimento Geral'),
('Filantrópico', 'Hospital Esperança', 'Rua da Solidariedade, 200', '113333-3333', 'Oncologia'),
('Privado', 'Hospital Cura', 'Av. Medical, 300', '113444-4444', 'Ortopedia'),
('Público', 'Hospital Municipal', 'Rua Central, 150', '113555-5555', 'Emergências'),
('Privado', 'Hospital da Visão', 'Av. Ótica, 400', '113666-6666', 'Oftalmologia'),
('Filantrópico', 'Hospital da Criança', 'Rua Infantil, 600', '113777-7777', 'Pediatria'),
('Privado', 'Hospital da Mulher', 'Av. Feminina, 700', '113888-8888', 'Ginecologia'),
('Público', 'Hospital Psiquiátrico', 'Rua Mental, 800', '113999-9999', 'Psiquiatria'),
('Privado', 'Hospital Diagnóstico', 'Av. dos Exames, 900', '113100-0000', 'Diagnóstico');

INSERT INTO exame (nm_exame, observacaos, tipo_exame, carencia) VALUES
('Hemograma completo', 'Jejum de 8 horas', 'Laboratorial', '30 dias'),
('Ressonância magnética', 'Sem metal no corpo', 'Imagem', '6 meses'),
('Colonoscopia', 'Dieta especial prévia', 'Endoscopia', '1 ano'),
('Ultrassom abdominal', 'Beber 2L de água', 'Imagem', '3 meses'),
('Eletrocardiograma', 'Evitar cremes na pele', 'Cardiológico', '1 mês');

INSERT INTO procedimento (tipo_proc, desc_proc) VALUES
('Cirurgia', 'Apêndice laparoscópica'),
('Consulta', 'Check-up anual'),
('Terapia', 'Fisioterapia pós-operatória'),
('Exame', 'Biopsia de pele'),
('Emergência', 'Sutura de ferimento');

INSERT INTO planoExame (cod_exame, cod_plano, status_exame, dt_exame, locla_exame) VALUES
(1, 1, 'Aprovado', '2024-01-10', 'Hospital Vida'),
(2, 2, 'Pendente', '2024-02-15', 'Santa Casa'),
(3, 3, 'Negado', '2024-03-20', 'Hospital Esperança'),
(4, 4, 'Aprovado', '2024-04-05', 'Hospital Cura'),
(5, 5, 'Pendente', '2024-05-12', 'Hospital Municipal');

INSERT INTO planoProcedimento (cod_proc, cod_plano, local_proc, status_proc) VALUES
(1, 1, 'Hospital Vida', 'Realizado'),
(2, 2, 'Santa Casa', 'Pendente'),
(3, 3, 'Hospital Esperança', 'Cancelado'),
(4, 4, 'Hospital Cura', 'Agendado'),
(5, 5, 'Hospital Municipal', 'Realizado');