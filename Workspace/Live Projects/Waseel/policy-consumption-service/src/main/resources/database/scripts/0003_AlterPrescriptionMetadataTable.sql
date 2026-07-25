--liquibase formatted sql

--changeset Policy Consumption Service:0003

ALTER TABLE  "PBM_BUSINESS_RULES"."PRESCRIPTION_METADATA" ADD ("ACTIVE_PRESCRIPTION" CHAR(1));