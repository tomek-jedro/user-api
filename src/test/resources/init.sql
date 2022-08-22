create schema api;

create table api.user(
    id serial primary key,
    first_name text,
    last_name text,
    gender text,
    age smallint
);

insert into api.user(first_name, last_name, gender, age) values('Tom', 'Hanks', 'Male', 55);
insert into api.user(first_name, last_name, gender, age) values('Chris', 'Evans', 'Male', 41);
insert into api.user(first_name, last_name, gender, age) values('George', 'Clooney', 'Male', 61);
insert into api.user(first_name, last_name, gender, age) values('Margot', 'Roobie', 'Female', 40);
insert into api.user(first_name, last_name, gender, age) values('Will', 'Smith', 'Male', 54);