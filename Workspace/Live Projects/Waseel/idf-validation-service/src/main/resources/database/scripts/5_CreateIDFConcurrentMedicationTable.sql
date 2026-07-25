--liquibase formatted sql

--changeset Idf:5

CREATE TABLE "MDSS"."IDFConcurrentMedication" 
(	
	"ServiceCode" VARCHAR2(250) NOT NULL,
	"CUServiceCode"  VARCHAR2(250), 
	"AdditionalRejectionReason"  VARCHAR2(300),
	CONSTRAINT PK_ServiceCodeCU PRIMARY KEY ("ServiceCode","CUServiceCode")
);