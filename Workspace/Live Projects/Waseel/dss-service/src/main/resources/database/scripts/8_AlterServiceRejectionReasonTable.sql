--liquibase formatted sql

--changeset DssService:8

ALTER TABLE "MDSS"."ServiceRejectionReason" MODIFY "RejectionReason" VARCHAR2(500) ;