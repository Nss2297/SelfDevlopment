--liquibase formatted sql

--changeset Prescription:0021

ALTER TABLE "TransactionLog"  MODIFY ("EPrescriptionReferenceNumber" NULL );