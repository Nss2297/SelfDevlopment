--liquibase formatted sql

--changeset Payer-Customization-Service:039

CREATE SEQUENCE "PCAge_Seq_Id"
  MINVALUE 1
  MAXVALUE 999999999999999999999999999
  START WITH 1
  INCREMENT BY 1
  CACHE 20;

ALter table "PCAge"
add "Id" NUMBER default "PCAge_Seq_Id".nextval not null;

Update "PCAge" p1 set "RuleId" = CONCAT
(
   'PCDTARule_',
   (
      SELECT p2."Id" from "PCAge" p2 where p1."Id" = p2."Id"
   )
);