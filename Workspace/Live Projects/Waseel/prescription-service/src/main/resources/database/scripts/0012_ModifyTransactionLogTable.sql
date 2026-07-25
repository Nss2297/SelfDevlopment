--liquibase formatted sql

--changeset Prescription:0012

ALTER TABLE "TransactionLog"  MODIFY ("Status" NULL);

ALTER TABLE "TransactionLog"  MODIFY ("HttpStatus" NULL);
