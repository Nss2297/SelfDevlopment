--liquibase formatted sql

--changeset DSS:17

CREATE INDEX "idx_rid_memberinfo" ON "MDSS"."MemberInfo" ("RequestId");
