--liquibase formatted sql

--changeset Prescription:0038

ALTER TABLE "PRESCRIPTION_SERVICE"."Physician" ADD ("PhysicianSpeciality" VARCHAR2(100) NULL);