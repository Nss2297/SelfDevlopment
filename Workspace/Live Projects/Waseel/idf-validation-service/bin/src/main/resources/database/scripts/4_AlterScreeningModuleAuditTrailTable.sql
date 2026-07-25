--liquibase formatted sql

--changeset Idf:4

ALTER TABLE "MDSS"."ScreeningModuleAuditTrail" MODIFY "ModuleId" VARCHAR2(200) ;