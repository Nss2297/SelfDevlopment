--liquibase formatted sql

--changeset Drug Exclusion Validation Service:0007

Insert into PBM_BUSINESS_RULES.COMMON_DENIALS ("DENIAL_CODE","DENIAL_DESCRIPTION")
	Values ('BR_EXLPHYSPECDF01','<drugcode> <DrugName> cannot be prescribed for the selected speciality - this request should be manually reviewed by payer.');
COMMIT;