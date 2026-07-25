--liquibase formatted sql

--changeset Payer-Customization-Service:019

CREATE TABLE "MDSS"."PCGender"
(
    "RuleId" NUMBER,
    "PayerId" VARCHAR2(20),
	"ServiceCode" VARCHAR2(250) NOT NULL,
	"Gender"  VARCHAR2(20),
	"ServiceStatus"  VARCHAR2(50),
	"AdditionalRejectionReason" VARCHAR2(500),
    "ModuleName" VARCHAR2 (20),
	"LastUpdatedDateTime" TIMESTAMP(6),
	CONSTRAINT PK_PCGender PRIMARY KEY ("ServiceCode","PayerId","ModuleName"),
	CONSTRAINT FK_PCGender_RuleId FOREIGN KEY ("RuleId") REFERENCES "MDSS"."PayerCustomizationModules" ("Id")
);