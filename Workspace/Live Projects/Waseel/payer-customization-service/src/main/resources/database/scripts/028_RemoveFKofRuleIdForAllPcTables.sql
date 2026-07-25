--liquibase formatted sql

--changeset Payer-Customization-Service:028

ALTER TABLE "MDSS"."PCDrugToDiagnosis" 
DROP CONSTRAINT FK_RuleId;

ALTER TABLE "MDSS"."PCQuantityLimitCheck"
DROP CONSTRAINT FK_PCQLC_RuleId;

ALTER TABLE "MDSS"."PCAge" 
DROP CONSTRAINT FK_PCAge_RuleId;

ALTER TABLE "MDSS"."PCGender" 
DROP CONSTRAINT FK_PCGender_RuleId;

ALTER TABLE "MDSS"."PCDrugToDrug" 
DROP CONSTRAINT FK_PCDrugToDrug_RuleId;

ALTER TABLE "MDSS"."PCDuplicateTherapy" 
DROP CONSTRAINT FK_PCDuplicateTherapy_RuleId;