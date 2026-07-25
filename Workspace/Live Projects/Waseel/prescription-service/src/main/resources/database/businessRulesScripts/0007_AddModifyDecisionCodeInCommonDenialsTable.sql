--liquibase formatted sql

--changeset Prescription:0007

Insert into PBM_BUSINESS_RULES.COMMON_DENIALS ("DENIAL_CODE","DENIAL_DESCRIPTION")
	Values ('PYR102_OVERRIDE ','Modified decision by Payer');
COMMIT;