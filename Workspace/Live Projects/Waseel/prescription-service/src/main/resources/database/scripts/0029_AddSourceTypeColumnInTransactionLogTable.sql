--liquibase formatted sql

--changeset Prescription:0029

ALTER TABLE "PRESCRIPTION_SERVICE"."TransactionLog" ADD ("SourceType" VARCHAR2(10) NULL);
ALTER TABLE "PRESCRIPTION_SERVICE"."TransactionLog" MODIFY ("SourceType" VARCHAR2(10) NOT NULL  ENABLE NOVALIDATE);