--liquibase formatted sql

--changeset Prescription:0038

ALTER TABLE "PRESCRIPTION_SERVICE"."PrescriptionRequest" ADD ("PatientShare" NUMBER NULL);
ALTER TABLE "PRESCRIPTION_SERVICE"."PrescriptionRequest" MODIFY ("PatientShare" NUMBER NOT NULL  ENABLE NOVALIDATE);
ALTER TABLE "PRESCRIPTION_SERVICE"."PrescriptionRequest" ADD ("PayerShare" NUMBER NULL);
ALTER TABLE "PRESCRIPTION_SERVICE"."PrescriptionRequest" MODIFY ("PayerShare" NUMBER NOT NULL  ENABLE NOVALIDATE);