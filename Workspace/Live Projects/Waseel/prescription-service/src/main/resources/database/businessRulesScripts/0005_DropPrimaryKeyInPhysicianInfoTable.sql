--liquibase formatted sql

--changeset Prescription:0005

ALTER TABLE "PBM_BUSINESS_RULES"."PhysicianInfo"
DROP CONSTRAINT PK_Physician_ProviderId_RegistrationNumber;
