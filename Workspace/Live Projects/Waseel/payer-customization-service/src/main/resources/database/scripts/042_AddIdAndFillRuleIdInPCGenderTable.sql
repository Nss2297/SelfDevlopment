--liquibase formatted sql

--changeset Payer-Customization-Service:042

CREATE SEQUENCE "PCGender_Seq_Id"
  MINVALUE 1
  MAXVALUE 999999999999999999999999999
  START WITH 1
  INCREMENT BY 1
  CACHE 20;

ALter table "PCGender"
add "Id" NUMBER default "PCGender_Seq_Id".nextval not null;

Update "PCGender" p1 set "RuleId" = CONCAT
(
   'PCDTGRule_',
   (
      SELECT p2."Id" from "PCGender" p2 where p1."Id" = p2."Id"
   )
);