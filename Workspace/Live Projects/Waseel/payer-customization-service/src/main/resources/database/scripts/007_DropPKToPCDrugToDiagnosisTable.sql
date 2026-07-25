--liquibase formatted sql

--changeset Payer-Customization-Service:007

ALTER TABLE "MDSS"."PCDrugToDiagnosis" 
DROP CONSTRAINT PK_PayerDiagnosisConfiguration;

