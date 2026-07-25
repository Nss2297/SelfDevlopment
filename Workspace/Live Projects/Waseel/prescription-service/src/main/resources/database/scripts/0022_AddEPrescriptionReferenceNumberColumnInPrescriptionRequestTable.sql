--liquibase formatted sql

--changeset Prescription:0022

ALTER TABLE "PRESCRIPTION_SERVICE"."PrescriptionRequest" ADD ("EPrescriptionReferenceNumber" VARCHAR2(100) NULL);
ALTER TABLE "PRESCRIPTION_SERVICE"."PrescriptionRequest" MODIFY ("EPrescriptionReferenceNumber" VARCHAR2(100) NOT NULL  ENABLE NOVALIDATE);
