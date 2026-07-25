--liquibase formatted sql

--changeset Payer-Customization-Service:011


CREATE TABLE "MDSS"."PCQuantityLimitCheck"
(
    "RuleId" NUMBER,
	"ServiceCode" VARCHAR2(250) NOT NULL,
	"IcdCode"  VARCHAR2(20),
	"PayerId" VARCHAR2(20),
	"ModuleName" VARCHAR2 (20),
	"FromAgeInDays"  NUMBER,
	"ToAgeInDays" NUMBER,
	"DrugType"  VARCHAR2(20),
	"MaxValuePerDay"  NUMBER,
	"UnitType"  VARCHAR2(10),
	"PayerSourceOfCustomization" VARCHAR2(100),
	"ProductPackageSize" NUMBER,
	"LastUpdatedDateTime" TIMESTAMP(6),
	"AdditionalRejectionReason" VARCHAR2(500),
	CONSTRAINT PK_PCQuantityLimitCheck PRIMARY KEY ("ServiceCode","IcdCode","PayerId","ModuleName"),
	CONSTRAINT FK_PCQLC_RuleId FOREIGN KEY ("RuleId") REFERENCES "MDSS"."PayerCustomizationModules" ("Id")
);