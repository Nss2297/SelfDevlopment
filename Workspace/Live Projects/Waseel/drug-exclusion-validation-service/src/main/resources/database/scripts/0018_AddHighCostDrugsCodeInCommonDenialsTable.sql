--liquibase formatted sql

--changeset Drug Exclusion Validation Service:0018

Insert into PBM_BUSINESS_RULES.COMMON_DENIALS ("DENIAL_CODE","DENIAL_DESCRIPTION")
	Values ('BR_EXLHCDF03','<drugcode> <DrugName> is an expensive drug - this request requires a manual review from the payer.');
COMMIT;