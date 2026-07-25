--liquibase formatted sql

--changeset PbmAdminService:0003

ALTER TABLE "MDSS"."PCDrugToDiagnosis" 
ADD ("IsDeleted" CHAR(1) DEFAULT ('0'));

