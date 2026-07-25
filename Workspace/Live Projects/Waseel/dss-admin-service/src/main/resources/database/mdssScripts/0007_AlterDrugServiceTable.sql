--liquibase formatted sql

--changeset DSS Admin Service:0007

ALTER TABLE "DrugService" MODIFY ("Strength_Unit" VARCHAR2(20 BYTE) );
