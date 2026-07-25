--liquibase formatted sql

--changeset DssService:3

CREATE TABLE MDSS."PayerConfig"
(
  "PayerId"    VARCHAR2(20 BYTE),
  "PbmPayerType"    VARCHAR2(20 BYTE),
  "isEnabled"  CHAR(1 BYTE),
   CONSTRAINT "PayerConfig_PK" PRIMARY KEY ("PayerId")
  
)