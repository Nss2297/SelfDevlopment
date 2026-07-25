--liquibase formatted sql

--changeset DssService:10

ALTER TABLE "MDSS"."ServiceRejectionReason" MODIFY "RejectionReason" VARCHAR2(2500) ;