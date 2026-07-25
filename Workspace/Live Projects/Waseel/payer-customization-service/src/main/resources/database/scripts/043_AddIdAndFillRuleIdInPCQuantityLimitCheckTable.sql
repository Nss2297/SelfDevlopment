--liquibase formatted sql

--changeset Payer-Customization-Service:043

CREATE SEQUENCE "PCQuantityLimitCheck_Seq_Id"
  MINVALUE 1
  MAXVALUE 999999999999999999999999999
  START WITH 1
  INCREMENT BY 1
  CACHE 20;

ALter table "PCQuantityLimitCheck"
add "Id" NUMBER default "PCQuantityLimitCheck_Seq_Id".nextval not null;

Update "PCQuantityLimitCheck" p1 set "RuleId" = CONCAT
(
   'PCQLCRule_',
   (
      SELECT p2."Id" from "PCQuantityLimitCheck" p2 where p1."Id" = p2."Id"
   )
);