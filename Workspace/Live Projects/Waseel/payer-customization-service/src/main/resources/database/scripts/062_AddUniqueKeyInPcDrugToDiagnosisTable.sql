--liquibase formatted sql

--changeset Payer-Customization-Service:062

ALTER TABLE "MDSS"."PCDrugToDiagnosis"
DROP CONSTRAINT PK_PayerDiagnosisConfiguration;

ALTER TABLE "MDSS"."PCDrugToDiagnosis"
ADD CONSTRAINT PK_PayerDiagnosisConfiguration PRIMARY KEY ("ServiceCode","IcdCode","PayerId","ModuleName");

ALTER TABLE "MDSS"."PCDrugToDiagnosis"
ADD CONSTRAINT UK_PayerDiagnosisConfiguration UNIQUE ("ServiceCode","IcdCode","PayerId","ModuleName","IsDeleted");