--liquibase formatted sql

--changeset Drug Formulary Service:0021

Insert into PBM_BUSINESS_RULES.COMMON_DENIALS ("DENIAL_CODE","DENIAL_DESCRIPTION")
	Values ('BR_DFDNF01','<drugcode> <DrugName> is not part of the Drug formulary');
COMMIT;