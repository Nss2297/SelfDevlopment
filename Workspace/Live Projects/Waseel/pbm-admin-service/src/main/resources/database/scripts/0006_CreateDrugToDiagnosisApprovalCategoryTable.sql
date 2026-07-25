--liquibase formatted sql

--changeset PbmAdminService:0006

CREATE TABLE "MDSS"."DrugToDiagnosisApprovalCategory" 
(	
	"Id" NUMBER NOT NULL, 
	"Name"  VARCHAR2(100), 
	"IsEnabled" CHAR(1) DEFAULT ('0'),
	CONSTRAINT "PK_DrugToDiagnosisApprovalCategory" PRIMARY KEY ("Id")
);

CREATE SEQUENCE "MDSS"."DrugToDiagnosisApprovalCategory_SEQ"
  MINVALUE 1
  START WITH 1
  INCREMENT BY 1
  NOCYCLE
  NOCACHE;