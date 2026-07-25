--liquibase formatted sql

--changeset Prescription:0051

ALTER TABLE "PRESCRIPTION_SERVICE"."ServiceRejection" 
ADD "SCIENTIFIC_CODE" VARCHAR2(64);