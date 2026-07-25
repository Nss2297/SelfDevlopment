--liquibase formatted sql

--changeset Payer-Customization-Service:026

CREATE TABLE "MDSS"."PCDuplicateTherapy"
(
    "RuleId" NUMBER,
    "PayerId" VARCHAR2(20),
	"ServiceCode" VARCHAR2(250),
	"InteractedServiceCode" VARCHAR2(250),
	"ServiceStatus"  VARCHAR2(50),
	"AdditionalRejectionReason" VARCHAR2(500),
	"ModuleName" VARCHAR2 (20),
	"LastUpdatedDateTime" TIMESTAMP(6),
	CONSTRAINT PK_PCDuplicateTherapy PRIMARY KEY ("ServiceCode","InteractedServiceCode","PayerId","ModuleName"),
	CONSTRAINT FK_PCDuplicateTherapy_RuleId FOREIGN KEY ("RuleId") REFERENCES "MDSS"."PayerCustomizationModules" ("Id")
);