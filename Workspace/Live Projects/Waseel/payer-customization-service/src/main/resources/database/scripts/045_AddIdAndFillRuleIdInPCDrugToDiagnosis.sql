--liquibase formatted sql

--changeset Payer-Customization-Service:045

CREATE SEQUENCE "PCDrugToDiagnosis_Seq_Id"
  MINVALUE 1
  MAXVALUE 999999999999999999999999999
  START WITH 1
  INCREMENT BY 1
  CACHE 20;

ALter table "PCDrugToDiagnosis"
add "Id" NUMBER default "PCDrugToDiagnosis_Seq_Id".nextval not null;

Update "PCDrugToDiagnosis" p1 set "RuleId" = CONCAT
(
   'PCDTDICRule_',
   (
      SELECT p2."Id" from "PCDrugToDiagnosis" p2 where p1."Id" = p2."Id"
   )
);