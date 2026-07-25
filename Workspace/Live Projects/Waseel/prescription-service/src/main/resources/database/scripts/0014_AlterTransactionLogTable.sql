--liquibase formatted sql

--changeset Prescription:0014

ALTER TABLE "TransactionLog"  MODIFY ("PayerID" NULL);

ALTER TABLE "TransactionLog"  MODIFY ("ProviderID" NULL);

