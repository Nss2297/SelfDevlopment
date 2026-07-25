--liquibase formatted sql

--changeset Prescription:0020

ALTER TABLE "TransactionLog"  MODIFY ("RequestID" NULL );