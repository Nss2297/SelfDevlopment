--liquibase formatted sql

--changeset Fdb:2

ALTER TABLE "MDSS"."ServiceCodeGCNSeqNoMapping"
ADD
(
   "IsDeleted" CHAR(1) DEFAULT ('0'),
   "LastUpdatedDateTime" TIMESTAMP(6)
);