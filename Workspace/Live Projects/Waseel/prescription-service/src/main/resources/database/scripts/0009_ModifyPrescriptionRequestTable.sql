--liquibase formatted sql

--changeset Prescription:0009

ALTER TABLE "PRESCRIPTION_SERVICE"."PrescriptionRequest" MODIFY "StatusDescription" VARCHAR2(3000);