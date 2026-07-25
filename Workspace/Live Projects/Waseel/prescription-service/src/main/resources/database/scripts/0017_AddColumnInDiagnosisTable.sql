--liquibase formatted sql

--changeset Prescription:0017

ALTER TABLE "PRESCRIPTION_SERVICE"."Diagnosis" ADD "IsDeleted" NUMBER DEFAULT (0);