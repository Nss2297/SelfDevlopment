--liquibase formatted sql

--changeset DSS:18

CREATE INDEX "idx_rid_isEnabled" ON "MDSS"."PayerConfig" ("PayerId","isEnabled");
