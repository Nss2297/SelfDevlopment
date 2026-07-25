--liquibase formatted sql

--changeset Prescription:0031

ALTER TABLE "TransactionLog"  MODIFY ("SourceType" VARCHAR2(20));