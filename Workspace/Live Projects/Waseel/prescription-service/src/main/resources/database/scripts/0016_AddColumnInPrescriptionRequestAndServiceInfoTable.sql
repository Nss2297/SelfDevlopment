--liquibase formatted sql

--changeset Prescription:0016

ALTER TABLE "PRESCRIPTION_SERVICE"."PrescriptionRequest"
ADD "IsCancelled" char(1) DEFAULT ('0');

ALTER TABLE "PRESCRIPTION_SERVICE"."ServiceInfo"
ADD "IsDeleted" char(1) DEFAULT ('0');