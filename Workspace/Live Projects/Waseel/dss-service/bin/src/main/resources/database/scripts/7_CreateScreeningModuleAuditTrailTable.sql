--liquibase formatted sql

--changeset ScreeningModuleAuditTrail:7

--comment: Create ScreeningModuleAuditTrail table

CREATE TABLE "MDSS"."ScreeningModuleAuditTrail" 
(	
	"AuditId" NUMBER NOT NULL, 
	"RequestId" VARCHAR2(100),
	"PayerId"  VARCHAR2(20), 
	"TransactionLogId" NUMBER,
	"ModuleId" VARCHAR2(40),
	"MongoDBUniqueId" VARCHAR2(100),
	CONSTRAINT "PK_ScreeningModuleAuditTrail_AuditId" PRIMARY KEY ("AuditId")
);

CREATE SEQUENCE "MDSS"."DSS_ScreeningModuleAuditTrail_SEQ"
  MINVALUE 1
  START WITH 1
  INCREMENT BY 1
  NOCYCLE
  NOCACHE;