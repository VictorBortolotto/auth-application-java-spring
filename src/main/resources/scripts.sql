create database web_auth_app;

use web_auth_app;

create table user(
    id int primary key auto_increment,
    name varchar(200) not null,
    email varchar(200) not null,
    password varchar(200) not null
);