--liquibase formatted sql

--changeset Prescription:0054

ALTER TABLE "PRESCRIPTION_SERVICE"."ServiceInfo" ADD "DrugListId" NUMBER;
ALTER TABLE "PRESCRIPTION_SERVICE"."ServiceInfo" MODIFY ("DrugListId" NUMBER NOT NULL  ENABLE NOVALIDATE);