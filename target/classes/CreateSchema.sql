create schema veterinary;

ALTER SCHEMA `veterinary` DEFAULT COLLATE utf8_unicode_ci;

create table veterinary.users (
	id bigint auto_increment,
	firstName text not null,
	lastName text not null,
	primary key (id)
) default charset=utf8;

create table veterinary.authData (
	id bigint auto_increment,
	username text not null,
	password text not null,
	primary key(id),
    unique (username(30))
) default charset=utf8;

create table veterinary.accounts (
	id bigint auto_increment,
    userId bigint not null,
    authDataId bigint not null,
    primary key (id),
    foreign key (userId) references veterinary.users (id),
    foreign key (authDataId) references veterinary.authData (id),
    unique (userId, authDataId)
) default charset=utf8;

INSERT INTO veterinary.users (firstName,lastName)VALUES("Andrew","Gordon");
INSERT INTO veterinary.authdata (username,`password`)VALUES("Gordon","password");
INSERT INTO veterinary.accounts (userId, authDataId) VALUES(1, 1);

create table veterinary.patients (
	id bigint auto_increment,
    ownerName text not null,
    petName text not null,
    petSpecies text not null,
    primary key (id)
) default charset=utf8;

INSERT INTO veterinary.patients(ownerName, petName, petSpecies) values("Вася Попов", "Коля", "Лошадь");
INSERT INTO veterinary.patients(ownerName, petName, petSpecies) values("Петя Лапочкин", "Перец", "Рыба");
INSERT INTO veterinary.patients(ownerName, petName, petSpecies) values("Иван Воробьев", "Армагеддон", "Петух");
INSERT INTO veterinary.patients(ownerName, petName, petSpecies) values("Андрей Борщ", "Маруся", "Мышь");
INSERT INTO veterinary.patients(ownerName, petName, petSpecies) values("Вася Солодов", "Петька", "Медведь");
INSERT INTO veterinary.patients(ownerName, petName, petSpecies) values("Максимилиан Кукушкин", "Анатолий", "Черепаха");
INSERT INTO veterinary.patients(ownerName, petName, petSpecies) values("Ксения Шапокляк", "Лариска", "Крыса");
INSERT INTO veterinary.patients(ownerName, petName, petSpecies) values("Николай Васильев", "Фунтик", "Собака");
INSERT INTO veterinary.patients(ownerName, petName, petSpecies) values("Анна Тортикова", "Евгений", "Хомяк");
INSERT INTO veterinary.patients(ownerName, petName, petSpecies) values("Арчибальд Феечкин", "Володя", "Верблюд");
INSERT INTO veterinary.patients(ownerName, petName, petSpecies) values("Беляш Котиков", "Беляшик", "Кот");
INSERT INTO veterinary.patients(ownerName, petName, petSpecies) values("Василиса Прекрасная", "Колбаска", "Хорек");
INSERT INTO veterinary.patients(ownerName, petName, petSpecies) values("Кирилл Кощеев", "Глазунья", "Сова");
INSERT INTO veterinary.patients(ownerName, petName, petSpecies) values("Андрей Головин", "Попка", "Попугай");
INSERT INTO veterinary.patients(ownerName, petName, petSpecies) values("Анфиса Леопольдова", "Бум", "Бегемот");

create table veterinary.doctors (
	id bigint auto_increment,
	firstName text not null,
	lastName text not null,
    specialization text not null,
	primary key (id)
) default charset=utf8;

INSERT INTO veterinary.doctors(firstName,lastName,specialization)VALUES("Бет", "Санчез", "Лошадиный хирург");
INSERT INTO veterinary.doctors(firstName,lastName,specialization)VALUES("Василий", "Хамелеонович", "Психотерапевт");
INSERT INTO veterinary.doctors(firstName,lastName,specialization)VALUES("Екатерина", "Лисичкова", "Медсестра");
INSERT INTO veterinary.doctors(firstName,lastName,specialization)VALUES("Петр", "Зоркий", "Окулист");
INSERT INTO veterinary.doctors(firstName,lastName,specialization)VALUES("Егор", "Ножичков", "Хирург");
INSERT INTO veterinary.doctors(firstName,lastName,specialization)VALUES("Лев", "Толстой", "Диетолог");
INSERT INTO veterinary.doctors(firstName,lastName,specialization)VALUES("Надежда", "Ушастикова", "ЛОР");

create table veterinary.clinicalRecords (
	id bigint auto_increment,
    doctorId bigint not null,
    patientId bigint not null,
    prescription text not null,
    primary key (id),
    foreign key (doctorId) references veterinary.doctors (id),
    foreign key (patientId) references veterinary.patients (id)
) default charset=utf8;

INSERT INTO veterinary.clinicalrecords(doctorId,patientId,prescription)VALUES(2,1,"Операция на сердце (выписал(а): Екатерина Лисичкова)");
INSERT INTO veterinary.clinicalrecords(doctorId,patientId,prescription)VALUES(3,5,"Маниакальное желание сгореть в машине (выписал(а): Екатерина Лисичкова)");
INSERT INTO veterinary.clinicalrecords(doctorId,patientId,prescription)VALUES(8,3,"Инородный предмет в горле (выписал(а): Екатерина Лисичкова)");
INSERT INTO veterinary.clinicalrecords(doctorId,patientId,prescription)VALUES(6,2,"Извлечение рыболовного крючка из губы (выписал(а): Екатерина Лисичкова)");
INSERT INTO veterinary.clinicalrecords(doctorId,patientId,prescription)VALUES(5,10,"Подбор очков (выписал(а): Екатерина Лисичкова)");
INSERT INTO veterinary.clinicalrecords(doctorId,patientId,prescription)VALUES(7,11,"Подобрать строгую диету (выписал(а):  Екатерина Лисичкова)");
INSERT INTO veterinary.clinicalrecords(doctorId,patientId,prescription)VALUES(4,6,"Поставить клизму (выписал(а): Медсестра - Екатерина Лисичкова)");
INSERT INTO veterinary.clinicalrecords(doctorId,patientId,prescription)VALUES(3,9,"Разговор о принятии скорой смерти (выписал(а): Екатерина Лисичкова)");
INSERT INTO veterinary.clinicalrecords(doctorId,patientId,prescription)VALUES(7,14,"Составить план перехода с человечины на зернышки (выписал(а): Екатерина Лисичкова)");
INSERT INTO veterinary.clinicalrecords(doctorId,patientId,prescription)VALUES(6,8,"Вытаскивание иголок дикобраза отовсюду (выписал(а): Екатерина Лисичкова)");
INSERT INTO veterinary.clinicalrecords(doctorId,patientId,prescription)VALUES(4,12,"Профилактическое почесывание (выписал(а): Екатерина Лисичкова)");

SELECT concat(D.firstName, " ", D.lastName) as fullName, D.specialization,
P.petSpecies, P.petName, P.ownerName, 
CR.prescription FROM
veterinary.doctors as D, veterinary.patients as P, veterinary.clinicalrecords as CR
WHERE CR.doctorId=D.id and CR.patientId=P.id

