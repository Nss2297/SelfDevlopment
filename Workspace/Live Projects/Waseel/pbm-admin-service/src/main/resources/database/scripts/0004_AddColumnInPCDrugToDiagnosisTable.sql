--liquibase formatted sql

--changeset PbmAdminService:0004

CREATE SEQUENCE "PCDrugToDiagnosis_Seq_Id"
  MINVALUE 1
  MAXVALUE 999999999999999999999999999
  START WITH 1
  INCREMENT BY 1
  CACHE 20;

ALter table "PCDrugToDiagnosis"
add "Id" NUMBER default "PCDrugToDiagnosis_Seq_Id".nextval not null;