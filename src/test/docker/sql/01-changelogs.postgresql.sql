--liquibase formatted sql

--changeset vnsk:1569145769245-1
CREATE TABLE IF NOT EXISTS dual AS ( VALUES (true) );
--liquibase formatted sql

--changeset vnsk:1569145769245-2
CREATE SCHEMA IF NOT EXISTS db;
CREATE SCHEMA IF NOT EXISTS dictionary;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp" SCHEMA pg_catalog version "1.1"; 
