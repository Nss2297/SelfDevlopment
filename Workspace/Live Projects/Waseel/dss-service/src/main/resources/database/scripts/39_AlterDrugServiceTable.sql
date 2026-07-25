--liquibase formatted sql

--changeset DssService:39


ALTER TABLE "MDSS"."DrugService"
ADD CONSTRAINT "UK_OtherCodeValue_DrugListId" UNIQUE ("Other_Codes_Value","DrugListId");

ALTER TABLE "MDSS"."DrugService"
ADD (
	"Registration_Year" VARCHAR(4),
	"Strength_Unit" VARCHAR(10)
);

ALTER TABLE "MDSS"."DrugService" ADD ("ScientificCode" VARCHAR2(64) );

ALTER TABLE "MDSS"."DrugService" ADD ("LastUpdatedDate" DATE NULL);

ALTER TABLE "MDSS"."DrugService" MODIFY ("LastUpdatedDate" DATE NOT NULL ENABLE NOVALIDATE);

CREATE SEQUENCE "Seq_WaseelDrugId"
  MINVALUE 1
  MAXVALUE 999999999999999999999999999
  START WITH 1
  INCREMENT BY 1
  CACHE 20;