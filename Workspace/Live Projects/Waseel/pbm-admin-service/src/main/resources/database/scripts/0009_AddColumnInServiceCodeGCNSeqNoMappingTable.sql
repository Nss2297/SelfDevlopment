--liquibase formatted sql

--changeset PbmAdminService:0009

CREATE SEQUENCE "ServiceCodeGCNSeqNoMapping_Seq_Id"
  MINVALUE 1
  MAXVALUE 999999999999999999999999999
  START WITH 1
  INCREMENT BY 1
  CACHE 20;

ALter table "ServiceCodeGCNSeqNoMapping"
add "Id" NUMBER default "ServiceCodeGCNSeqNoMapping_Seq_Id".nextval not null;