--liquibase formatted sql

--changeset Fdb:5

CREATE SEQUENCE "FDBPediatricAgeSeverityLevel_Seq_Id"
  MINVALUE 1
  MAXVALUE 999999999999999999999999999
  START WITH 1
  INCREMENT BY 1
  CACHE 20;

CREATE TABLE "MDSS"."FDBPediatricAgeSeverityLevel"
(
    "Id" NUMBER default "FDBPediatricAgeSeverityLevel_Seq_Id".nextval not null,
	"PayerId" VARCHAR2(20) NOT NULL,
	"ServiceCode"  VARCHAR2(250),
	"PediatricAgeSeverityLevel"  VARCHAR2(50),
	"IsDeleted" CHAR(1) DEFAULT ('0'),
    "LastUpdatedDateTime" TIMESTAMP(6),
	CONSTRAINT PK_ServiceCode_PayerId PRIMARY KEY ("ServiceCode","PayerId")
);