--liquibase formatted sql

--changeset DssService:41

ALter table "MDSS"."DrugService"
ADD CONSTRAINT "PK_WaseelDrugID_DrugService" PRIMARY KEY ("WASEEL_DRUG_ID");