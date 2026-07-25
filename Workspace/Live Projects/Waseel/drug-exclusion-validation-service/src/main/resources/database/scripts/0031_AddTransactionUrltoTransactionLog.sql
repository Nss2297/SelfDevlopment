--liquibase formatted sql

--changeset Drug Exclusion Validation Service:0031

ALTER TABLE PBM_BUSINESS_RULES.TRANSACTION_LOG 
ADD TRANSACTION_URL VARCHAR2(250);