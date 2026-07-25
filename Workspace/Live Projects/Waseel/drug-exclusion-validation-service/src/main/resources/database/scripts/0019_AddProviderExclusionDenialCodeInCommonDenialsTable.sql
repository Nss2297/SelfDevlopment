--liquibase formatted sql

--changeset Drug Exclusion Validation Service:0019

Insert into PBM_BUSINESS_RULES.COMMON_DENIALS ("DENIAL_CODE","DENIAL_DESCRIPTION")
Values ('BR_EXLPRODF02','<drugcode> <DrugName>  cannot be prescribed by this provider - this request should be manually reviewed by payer.');