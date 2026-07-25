--liquibase formatted sql

--changeset Drug Exclusion Validation Service:0022

Insert into PBM_BUSINESS_RULES.COMMON_DENIALS ("DENIAL_CODE","DENIAL_DESCRIPTION")
	Values ('BR_EXLNETDF04','<drugcode> <DrugName>  cannot be prescribed for any provider in your network - this request should be manually reviewed by payer.');
COMMIT;