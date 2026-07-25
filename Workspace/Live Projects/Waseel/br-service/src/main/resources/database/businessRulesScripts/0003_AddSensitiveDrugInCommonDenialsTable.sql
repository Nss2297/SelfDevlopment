--liquibase formatted sql

--changeset BR Service:0003

Insert into PBM_BUSINESS_RULES.COMMON_DENIALS ("DENIAL_CODE","DENIAL_DESCRIPTION")
	Values ('BR_SDNF01','<DrugCode> <DrugName> is not part of the sensitive drug list');
COMMIT;