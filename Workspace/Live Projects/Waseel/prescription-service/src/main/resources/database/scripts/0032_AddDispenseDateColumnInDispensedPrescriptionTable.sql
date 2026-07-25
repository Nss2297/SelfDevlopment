--liquibase formatted sql

--changeset Prescription:0032

ALTER TABLE "PRESCRIPTION_SERVICE"."DispensedPrescription" ADD ("DispenseDate" TIMESTAMP(6));