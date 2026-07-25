--liquibase formatted sql

--changeset Prescription:0034

ALTER TABLE "PRESCRIPTION_SERVICE"."ServiceRejection" ADD ("EligibilityReferenceNumber" VARCHAR2(40) NULL);