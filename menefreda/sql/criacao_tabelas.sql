create database menefreda;
use menefreda;

create table paciente (
cpf_paci char(11) primary key,
nome_paci varchar(50),
genero_paci char(1),
email_paci varchar(100),
dt_nasc_paci date,
end_paci varchar(200),
tel_paci varchar(11)
);

create table plano (
cod_plano int primary key auto_increment,
valor_plano float,
tipo_plano varchar(20)
);

create table medico (
cod_med int primary key,
nome_med varchar(50),
area_atua varchar(30),
tel_med varchar(11)
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

insert into plano (cod_plano, valor_plano, tipo_plano)
values
(1, 130.00, "Prata");

insert into paciente (cpf_paci, nome_paci, genero_paci, email_paci, dt_nasc_paci, end_paci, tel_paci) 
values
('12345678901', 'Carlos Silva', 'M', 'carlos.silva@email.com', '1985-04-15', 'Rua A, 123', '11987654321'),
('23456789012', 'Maria Oliveira', 'F', 'maria.oliveira@email.com', '1990-07-22', 'Rua B, 456', '11976543210'),
('34567890123', 'José Pereira', 'M', 'jose.pereira@email.com', '1982-03-10', 'Rua C, 789', '11965432109'),
('45678901234', 'Ana Costa', 'F', 'ana.costa@email.com', '1995-11-05', 'Rua D, 1011', '11954321098'),
('56789012345', 'Lucas Martins', 'M', 'lucas.martins@email.com', '1992-12-30', 'Rua E, 1213', '11943210987'),
('67890123456', 'Fernanda Lima', 'F', 'fernanda.lima@email.com', '1988-02-17', 'Rua F, 1415', '11932109876'),
('78901234567', 'Ricardo Souza', 'M', 'ricardo.souza@email.com', '1980-09-25', 'Rua G, 1617', '11921098765'),
('89012345678', 'Juliana Rocha', 'F', 'juliana.rocha@email.com', '2000-05-11', 'Rua H, 1819', '11910987654'),
('90123456789', 'Gustavo Almeida', 'M', 'gustavo.almeida@email.com', '1998-10-03', 'Rua I, 2021', '11898765432'),
('01234567890', 'Patrícia Ferreira', 'F', 'patricia.ferreira@email.com', '1993-06-21', 'Rua J, 2223', '11887654321');

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

insert into contrato(
    cod_cont,
    dt_inicio,
    validade,
    valor,
    cpf_paci,
    tipo_plano)
values(20, '2024-01-01', '2024-01-01', 20.00, 12312312312, 'gold');
