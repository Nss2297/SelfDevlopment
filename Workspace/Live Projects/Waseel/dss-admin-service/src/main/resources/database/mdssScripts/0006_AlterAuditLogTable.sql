--liquibase formatted sql

--changeset DSS Admin Service:0006

ALTER TABLE "MDSS"."AUDIT_LOG"
RENAME COLUMN "ENTITIY_DATA" TO "ENTITY_DATA";