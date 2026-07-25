--liquibase formatted sql
--changeset Payer-Customization-Service:006

ALTER TABLE "MDSS"."PCDrugToDiagnosis" 
ADD
(
   "RuleId" NUMBER,
   "LastUpdatedDateTime" TIMESTAMP(6),
   "ModuleName" VARCHAR2 (20)
);

ALTER TABLE "MDSS"."PCDrugToDiagnosis" 
ADD CONSTRAINT FK_RuleId FOREIGN KEY ("RuleId") REFERENCES "MDSS"."PayerCustomizationModules" ("Id");