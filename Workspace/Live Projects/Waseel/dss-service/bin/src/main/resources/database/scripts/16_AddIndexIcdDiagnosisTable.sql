--liquibase formatted sql

--changeset DSS:16

CREATE INDEX "idx_rid_icddiagnosisinfo" ON "MDSS"."ICDDiagnosisInfo" ("RequestId");
