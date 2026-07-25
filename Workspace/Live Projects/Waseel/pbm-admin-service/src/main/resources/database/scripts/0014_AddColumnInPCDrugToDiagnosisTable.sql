--liquibase formatted sql

--changeset PbmAdminService:0014

ALter table "PCDrugToDiagnosis"
add "LastUpdatedBy" VARCHAR2(50) default null;