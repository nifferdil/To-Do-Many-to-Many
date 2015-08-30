# ToDo-app

works when you create a database with these tables and columns:

CREATE DATABASE to_do;
\c to_do;

CREATE DATABASE to_do_test WITH TEMPLATE to_do;

CREATE TABLE tasks (id serial PRIMARY KEY, description varchar);
ALTER TABLE tasks ADD categoryId int;


CREATE TABLE categories (id serial PRIMARY KEY, name varchar);



