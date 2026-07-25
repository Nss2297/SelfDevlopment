--liquibase formatted sql

--changeset PBM Scheduler Service:0005

ALTER TABLE PBM_BUSINESS_RULES.BENEFIT_CASES
MODIFY CASE_CODE VARCHAR2(50);