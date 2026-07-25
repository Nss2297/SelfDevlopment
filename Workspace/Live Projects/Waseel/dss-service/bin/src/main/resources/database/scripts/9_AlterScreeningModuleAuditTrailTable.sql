--liquibase formatted sql

--changeset DSS:9

ALTER TABLE  "MDSS"."ScreeningModuleAuditTrail" ADD ("ModuleType" varchar2(20));
