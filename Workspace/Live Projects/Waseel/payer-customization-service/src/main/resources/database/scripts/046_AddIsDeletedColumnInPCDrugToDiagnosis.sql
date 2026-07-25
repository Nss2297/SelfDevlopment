--liquibase formatted sql

--changeset Payer-Customization-Service:046


ALTER TABLE MDSS."PCDrugToDiagnosis" ADD  "IsDeleted"  CHAR(1 BYTE)     DEFAULT ('0');
