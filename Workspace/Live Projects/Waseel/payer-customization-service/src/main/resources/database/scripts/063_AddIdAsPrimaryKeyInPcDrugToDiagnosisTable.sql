--liquibase formatted sql

--changeset Payer-Customization-Service:062

ALTER TABLE "MDSS"."PCDrugToDiagnosis"
DROP CONSTRAINT PK_PayerDiagnosisConfiguration;

ALTER TABLE "MDSS"."PCDrugToDiagnosis"
ADD CONSTRAINT PK_PayerDiagnosisConfiguration PRIMARY KEY ("Id");