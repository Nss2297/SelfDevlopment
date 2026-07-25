--liquibase formatted sql

--changeset DSS:21

CREATE INDEX "idx_rid_transactionlog" ON "MDSS"."TransactionLog" ("RequestId");
