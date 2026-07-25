--liquibase formatted sql

--changeset Payer-Customization-Service:008

ALTER TABLE "MDSS"."PCDrugToDiagnosis" 
ADD CONSTRAINT PK_PayerDiagnosisConfiguration PRIMARY KEY ("ServiceCode","IcdCode","PayerId","ModuleName");