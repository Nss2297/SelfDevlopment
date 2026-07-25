--liquibase formatted sql

--changeset Prescription:0037

ALTER TABLE "PRESCRIPTION_SERVICE"."PrescriptionRequest" ADD ("CaseType" VARCHAR(50));
