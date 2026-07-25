--liquibase formatted sql

--changeset Prescription:0011

ALTER TABLE  "PRESCRIPTION_SERVICE"."TransactionLog"
DROP constraint FK_TransactionLog_RequestID;