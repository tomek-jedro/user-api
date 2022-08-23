create schema api;

create table api.user(
    id serial primary key,
    first_name text,
    last_name text,
    gender text,
    age smallint
);

insert into api.user(first_name, last_name, gender, age) values('Tom', 'Hanks', 'MALE', 55);
insert into api.user(first_name, last_name, gender, age) values('Chris', 'Evans', 'MALE', 41);
insert into api.user(first_name, last_name, gender, age) values('George', 'Clooney', 'MALE', 61);
insert into api.user(first_name, last_name, gender, age) values('Margot', 'Roobie', 'FEMALE', 40);
insert into api.user(first_name, last_name, gender, age) values('Will', 'Smith', 'MALE', 54);