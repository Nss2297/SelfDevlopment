--liquibase formatted sql

--changeset Idf:2

CREATE TABLE "MDSS"."IDFDrugToAge" 
(	
	"ServiceCode" VARCHAR2(250) NOT NULL,
	" FromAgeInDays"  VARCHAR2(20), 
	"ToAgeInDays"  VARCHAR2(20),
	CONSTRAINT PK_ServiceCodeAge PRIMARY KEY ("ServiceCode")
);