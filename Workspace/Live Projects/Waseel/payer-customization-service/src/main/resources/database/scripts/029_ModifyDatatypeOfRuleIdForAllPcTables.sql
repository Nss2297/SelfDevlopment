--liquibase formatted sql

--changeset Payer-Customization-Service:029

ALTER TABLE "MDSS"."PCDrugToDiagnosis"  MODIFY ("RuleId"  varchar2(25));
ALTER TABLE "MDSS"."PCQuantityLimitCheck"  MODIFY ("RuleId"  varchar2(25));
ALTER TABLE "MDSS"."PCAge"  MODIFY ("RuleId"  varchar2(25));
ALTER TABLE "MDSS"."PCGender"  MODIFY ("RuleId"  varchar2(25));
ALTER TABLE "MDSS"."PCDrugToDrug"  MODIFY ("RuleId"  varchar2(25));
ALTER TABLE "MDSS"."PCDuplicateTherapy"  MODIFY ("RuleId"  varchar2(25));