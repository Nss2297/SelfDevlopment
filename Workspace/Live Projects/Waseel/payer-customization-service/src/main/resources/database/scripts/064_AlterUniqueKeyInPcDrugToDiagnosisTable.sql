--liquibase formatted sql

--changeset Payer-Customization-Service:064

ALTER TABLE "MDSS"."PCDrugToDiagnosis"
DROP CONSTRAINT UK_PayerDiagnosisConfiguration;

ALTER TABLE "MDSS"."PCDrugToDiagnosis"
DROP COLUMN "IsDeleted";

ALTER TABLE "MDSS"."PCDrugToDiagnosis"
ADD CONSTRAINT UK_PayerDiagnosisConfiguration UNIQUE ("ServiceCode","IcdCode","PayerId","ModuleName");