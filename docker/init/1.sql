create schema api;

create table api.user(
    id serial primary key,
    first_name text,
    last_name text,
    gender text,
    age smallint
);