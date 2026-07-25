--liquibase formatted sql

--changeset DssService:27

CREATE TABLE "MDSS"."PayerValidationConfiguration" 
(	
	"ID" NUMBER  GENERATED  BY DEFAULT AS IDENTITY (START WITH 1) NOT NULL,
	"PayerId" NUMBER NOT NULL,
	"FieldName" VARCHAR2(200) NOT NULL,
	"ToBeValidated" CHAR(1) DEFAULT ('0'),
	CONSTRAINT PK_PVC_ID PRIMARY KEY ("ID")
);

