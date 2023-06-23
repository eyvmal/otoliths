CREATE SCHEMA IF NOT EXISTS otoliths;
SET search_path TO otoliths;

DROP TABLE IF EXISTS EasyResults CASCADE;
CREATE TABLE EasyResults
(
    id SERIAL PRIMARY KEY,
    username VARCHAR,
    score INT,
    date Date
);

DROP TABLE IF EXISTS HardResults CASCADE;
CREATE TABLE HardResults
(
    id SERIAL PRIMARY KEY,
    username VARCHAR,
    score INT,
    date Date
);