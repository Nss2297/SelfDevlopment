--liquibase formatted sql

--changeset DssService:40

ALter table "MDSS"."DrugService"
add "WASEEL_DRUG_ID" NUMBER default CONCAT ('1000',"Seq_WaseelDrugId".nextval);