--liquibase formatted sql

--changeset Policy Consumption Service:0005

ALTER TABLE  "PBM_BUSINESS_RULES"."PRESCRIPTION_METADATA" ADD ("PRESCRIPTION_VALUE" NUMBER);