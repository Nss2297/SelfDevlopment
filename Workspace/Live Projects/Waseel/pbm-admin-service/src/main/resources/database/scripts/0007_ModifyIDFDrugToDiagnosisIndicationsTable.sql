--liquibase formatted sql

--changeset PbmAdminService:0007

CREATE SEQUENCE "MDSS"."IDFDrugToDiagnosisIndications_SEQ"
  MINVALUE 1
  START WITH 1
  INCREMENT BY 1
  NOCYCLE
  NOCACHE
  ORDER;
  
ALTER TABLE "MDSS"."IDFDrugToDiagnosisIndications" 
ADD
(
   "Id" NUMBER default "IDFDrugToDiagnosisIndications_SEQ".nextval NOT NULL,
   "IsDeleted" CHAR(1) DEFAULT ('0'),
   "LastUpdatedDateTime" TIMESTAMP(6)
);

ALTER TABLE "MDSS"."IDFDrugToDiagnosisIndications" 
ADD CONSTRAINT "PK_IDFDrugToDiagnosisIndications" PRIMARY KEY ("ICDDiagnosisCode","ServiceCode");
