--liquibase formatted sql

--changeset Payer-Customization-Service:040

CREATE SEQUENCE "PCDrugToDrug_Seq_Id"
  MINVALUE 1
  MAXVALUE 999999999999999999999999999
  START WITH 1
  INCREMENT BY 1
  CACHE 20;

ALter table "PCDrugToDrug"
add "Id" NUMBER default "PCDrugToDrug_Seq_Id".nextval not null;

Update "PCDrugToDrug" p1 set "RuleId" = CONCAT
(
   'PCDTDIRule_',
   (
      SELECT p2."Id" from "PCDrugToDrug" p2 where p1."Id" = p2."Id"
   )
);