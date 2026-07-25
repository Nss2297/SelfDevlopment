--liquibase formatted sql

--changeset Payer-Customization-Service:001


CREATE TABLE "MDSS"."PayerDiagnosisConfiguration" 
(	
	"ServiceCode" VARCHAR2(250) NOT NULL,
	"IcdCode"  VARCHAR2(20), 
	"PayerId" VARCHAR2(20),
	"CategoryOfApproval" VARCHAR2(100),
	"RejectionCategory" VARCHAR2(100),
	"ServiceStatus"  VARCHAR2(50),
	"AdditionalRejectionReason" VARCHAR2(500),
	CONSTRAINT PK_PayerDiagnosisConfiguration PRIMARY KEY ("ServiceCode","IcdCode","PayerId")
);