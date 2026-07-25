--liquibase formatted sql

--changeset Payer-Customization-Service:057

ALTER TABLE "MDSS"."PCDrugToDiagnosis"
DROP CONSTRAINT PK_PayerDiagnosisConfiguration;

ALTER TABLE "MDSS"."PCDrugToDiagnosis"
ADD CONSTRAINT PK_PayerDiagnosisConfiguration PRIMARY KEY ("ServiceCode","IcdCode","PayerId","ModuleName");