--liquibase formatted sql

--changeset Prescription:0042

ALTER TABLE "PRESCRIPTION_SERVICE"."PrescriptionRequest"
ADD "LAST_UPDATE_DATE" DATE DEFAULT NULL;