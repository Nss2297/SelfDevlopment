--liquibase formatted sql

--changeset PbmAdminService:0001

ALTER TABLE "MDSS"."RequestInfo" 
ADD "RequestStatus" VARCHAR2(50);