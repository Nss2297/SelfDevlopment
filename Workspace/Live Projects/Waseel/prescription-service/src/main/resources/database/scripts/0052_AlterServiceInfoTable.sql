--liquibase formatted sql

--changeset Prescription:0052

ALTER TABLE "PRESCRIPTION_SERVICE"."ServiceInfo" 
MODIFY "DrugCode" VARCHAR2(50) NULL;
