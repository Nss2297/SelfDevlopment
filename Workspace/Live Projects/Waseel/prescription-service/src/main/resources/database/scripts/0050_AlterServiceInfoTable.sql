--liquibase formatted sql

--changeset Prescription:0050

ALTER TABLE "PRESCRIPTION_SERVICE"."ServiceInfo" 
ADD "SCIENTIFIC_CODE" VARCHAR2(64);