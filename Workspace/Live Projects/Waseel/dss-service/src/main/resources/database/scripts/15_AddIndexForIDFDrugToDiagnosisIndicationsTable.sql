--liquibase formatted sql

--changeset Idf:15

CREATE INDEX "IDFDrugToDiagnosis_Index" 
ON "MDSS"."IDFDrugToDiagnosisIndications"("ServiceCode");