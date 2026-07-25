--liquibase formatted sql

--changeset Eligibility Service:0003

insert into "PBM_BUSINESS_RULES".COMMON_DENIALS (DENIAL_CODE, DENIAL_DESCRIPTION) 
	values ('BR_ELGIV01', '<IdNumber> <MemberName> has an invalid request.');
insert into "PBM_BUSINESS_RULES".COMMON_DENIALS (DENIAL_CODE, DENIAL_DESCRIPTION) 
	values ('BR_ELGF02', 'Failed to check eligiblity for <IdNumber> <MemberName>.');
insert into "PBM_BUSINESS_RULES".COMMON_DENIALS (DENIAL_CODE, DENIAL_DESCRIPTION) 
	values ('BR_ELGIE03', '<IdNumber> <MemberName> is Ineligible for a prescritpion.');

COMMIT;