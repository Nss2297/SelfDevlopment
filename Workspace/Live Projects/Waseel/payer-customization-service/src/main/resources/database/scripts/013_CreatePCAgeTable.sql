--liquibase formatted sql

--changeset Payer-Customization-Service:013


CREATE TABLE "MDSS"."PCAge"
(
    "RuleId" NUMBER,
	"ServiceCode" VARCHAR2(250) NOT NULL,
	"PayerId" VARCHAR2(20),
	"ModuleName" VARCHAR2 (20),
	"FromAgeInDays"  NUMBER,
	"ToAgeInDays" NUMBER,
	"ServiceStatus"  VARCHAR2(50),
	"AdditionalRejectionReason" VARCHAR2(500),
	"LastUpdatedDateTime" TIMESTAMP(6),
	CONSTRAINT PK_PCAge PRIMARY KEY ("ServiceCode","PayerId","ModuleName"),
	CONSTRAINT FK_PCAge_RuleId FOREIGN KEY ("RuleId") REFERENCES "MDSS"."PayerCustomizationModules" ("Id")
);