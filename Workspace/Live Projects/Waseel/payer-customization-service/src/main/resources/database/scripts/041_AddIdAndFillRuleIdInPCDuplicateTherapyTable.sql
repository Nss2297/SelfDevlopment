--liquibase formatted sql

--changeset Payer-Customization-Service:041

CREATE SEQUENCE "PCDuplicateTherapy_Seq_Id"
  MINVALUE 1
  MAXVALUE 999999999999999999999999999
  START WITH 1
  INCREMENT BY 1
  CACHE 20;

ALter table "PCDuplicateTherapy"
add "Id" NUMBER default "PCDuplicateTherapy_Seq_Id".nextval not null;

Update "PCDuplicateTherapy" p1 set "RuleId" = CONCAT
(
   'PCDTRule_',
   (
      SELECT p2."Id" from "PCDuplicateTherapy" p2 where p1."Id" = p2."Id"
   )
);