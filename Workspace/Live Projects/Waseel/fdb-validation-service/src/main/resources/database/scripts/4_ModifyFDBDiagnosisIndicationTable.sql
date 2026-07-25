--liquibase formatted sql

--changeset Fdb:4
ALTER TABLE MDSS."FDBDiagnosisIndicationConfig"
ADD
(
   "IsEnabled" CHAR(1),
   "IsDeleted" CHAR(1),
   "LastUpdatedDateTime" TIMESTAMP(6)
);