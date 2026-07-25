--liquibase formatted sql

--changeset DSS:20

CREATE INDEX "idx_rid_isdeletedfp" ON "MDSS"."RequestInfo" ("RequestId","IsDeletedFromProvider");
