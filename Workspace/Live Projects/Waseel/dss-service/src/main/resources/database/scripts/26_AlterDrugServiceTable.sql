--liquibase formatted sql

--changeset DssService:26


ALTER TABLE "MDSS"."DrugService" MODIFY "Ingredients" VARCHAR2(2000);
ALTER TABLE "MDSS"."DrugService" MODIFY "Strength" VARCHAR2(2000);

