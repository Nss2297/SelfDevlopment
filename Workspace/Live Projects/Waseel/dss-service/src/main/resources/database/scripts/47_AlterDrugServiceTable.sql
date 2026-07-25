--liquibase formatted sql

--changeset DSS:47

ALter table "MDSS"."DrugService"
add "WASEEL_DRUG_ID" NUMBER default CONCAT ('1000',"Seq_WaseelDrugId".nextval) not null;