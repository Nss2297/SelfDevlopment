--liquibase formatted sql

--changeset Payer-Customization-Service:061

ALTER TABLE "MDSS"."PCDrugToDiagnosis"
DROP COLUMN "IsDeleted";

ALTER TABLE "MDSS"."PCDrugToDiagnosis"
ADD "IsDeleted" CHAR(1 BYTE) DEFAULT ('0') NOT NULL;

ALTER TABLE "MDSS"."PCDrugToDiagnosis"
DROP CONSTRAINT PK_PayerDiagnosisConfiguration;

ALTER TABLE "MDSS"."PCDrugToDiagnosis"
ADD CONSTRAINT PK_PayerDiagnosisConfiguration PRIMARY KEY ("ServiceCode","IcdCode","PayerId","ModuleName","IsDeleted");