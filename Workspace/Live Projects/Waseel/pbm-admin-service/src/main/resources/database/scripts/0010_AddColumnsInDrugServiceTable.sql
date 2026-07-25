--liquibase formatted sql

--changeset PbmAdminService:0010
ALTER TABLE "MDSS"."DrugService" ADD ("ScientificCode" VARCHAR2(64) );

ALTER TABLE "MDSS"."DrugService" ADD ("ScientificName" VARCHAR2(256) );

ALTER TABLE "MDSS"."DrugService" ADD ("LastUpdatedDate" DATE NULL);

ALTER TABLE "MDSS"."DrugService" MODIFY ("LastUpdatedDate" DATE NOT NULL ENABLE NOVALIDATE);
