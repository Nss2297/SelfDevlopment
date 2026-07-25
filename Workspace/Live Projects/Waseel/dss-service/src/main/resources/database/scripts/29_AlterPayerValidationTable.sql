--liquibase formatted sql

--changeset DssService:29


ALTER TABLE "MDSS"."PayerValidationConfiguration" MODIFY "PayerId" VARCHAR2(200);

