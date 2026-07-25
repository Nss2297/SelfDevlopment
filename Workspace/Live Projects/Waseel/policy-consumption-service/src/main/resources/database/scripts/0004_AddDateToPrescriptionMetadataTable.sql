--liquibase formatted sql

--changeset Policy Consumption Service:0004

ALTER TABLE  "PBM_BUSINESS_RULES"."PRESCRIPTION_METADATA" ADD ("UPDATE_DATE" TIMESTAMP(6));