--liquibase formatted sql

--changeset Prescription:0010

ALTER TABLE "PRESCRIPTION_SERVICE"."PrescriptionRequest" MODIFY "StatusCode" VARCHAR2(50);