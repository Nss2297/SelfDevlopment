--liquibase formatted sql

--changeset Idf:1

CREATE TABLE "MDSS"."IDFDrugToGenderInteraction" 
(	
	"ServiceCode" VARCHAR2(250) NOT NULL,
	"Gender"  VARCHAR2(20), 
	CONSTRAINT PK_ServiceCode PRIMARY KEY ("ServiceCode")
);
 