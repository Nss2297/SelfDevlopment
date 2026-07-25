--liquibase formatted sql

--changeset Prescription:0027

ALTER TABLE "PRESCRIPTION_SERVICE"."InvalidPrescriptionRequest"  MODIFY ("PayerID" VARCHAR2(70 BYTE) );

ALTER TABLE "PRESCRIPTION_SERVICE"."InvalidPrescriptionRequest"  MODIFY ("ProviderID" VARCHAR2(70 BYTE) );