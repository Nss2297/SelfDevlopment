--liquibase formatted sql

--changeset DssService:2

--comment: Created tables for idf-validation-service

CREATE TABLE "MDSS"."IDFDrugToDiagnosisIndications" 
(	
	"ICDDiagnosisCode" VARCHAR2(10),
	"OldServiceCode" VARCHAR2(100),
	"ServiceCode" VARCHAR2(100)
);
