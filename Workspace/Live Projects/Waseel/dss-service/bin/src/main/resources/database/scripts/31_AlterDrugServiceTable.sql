--liquibase formatted sql

--changeset DssService:31

ALTER TABLE "MDSS"."DrugService"
DROP CONSTRAINT "PK_CODE";

