--liquibase formatted sql

--changeset Drug Exclusion Validation Service:0039

ALTER TABLE PBM_BUSINESS_RULES.PROVIDER_EXCLUSION_ASSC
DROP CONSTRAINT UK_PEA_PROID_PID;
