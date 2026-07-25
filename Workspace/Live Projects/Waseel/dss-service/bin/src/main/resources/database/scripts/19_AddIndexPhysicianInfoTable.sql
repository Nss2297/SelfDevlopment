--liquibase formatted sql

--changeset DSS:19

CREATE INDEX "idx_rid_physicianinfo" ON "MDSS"."PhysicianInfo" ("RequestId");
