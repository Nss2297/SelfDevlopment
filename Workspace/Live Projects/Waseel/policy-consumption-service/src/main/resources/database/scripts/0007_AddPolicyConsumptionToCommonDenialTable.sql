--liquibase formatted sql

--changeset Policy Consumption Service:0007


insert into "PBM_BUSINESS_RULES".COMMON_DENIALS (DENIAL_CODE, DENIAL_DESCRIPTION) 
values ('BR_PCF01', 'Failed to check policy consumption for <IdNumber>.');
insert into "PBM_BUSINESS_RULES".COMMON_DENIALS (DENIAL_CODE, DENIAL_DESCRIPTION) 
values ('BR_PCRJ02', '<IdNumber> is rejected as no benefit limit is remaining for the policy.');
insert into "PBM_BUSINESS_RULES".COMMON_DENIALS (DENIAL_CODE, DENIAL_DESCRIPTION)
values ('BR_PCIV03', '<IdNumber> has an invalid request.');
insert into "PBM_BUSINESS_RULES".COMMON_DENIALS (DENIAL_CODE, DENIAL_DESCRIPTION) 
	values ('BR_PCNOB04', '<IdNumber> has no benefit details.');
insert into "PBM_BUSINESS_RULES".COMMON_DENIALS (DENIAL_CODE, DENIAL_DESCRIPTION) 
	values ('BR_PCNOBC05', '<IdNumber> has no benefit cases.');
insert into "PBM_BUSINESS_RULES".COMMON_DENIALS (DENIAL_CODE, DENIAL_DESCRIPTION) 
	values ('BR_PCNOP06', 'Policy details are unavailable for <IdNumber>.');
insert into "PBM_BUSINESS_RULES".COMMON_DENIALS (DENIAL_CODE, DENIAL_DESCRIPTION) 
	values ('BR_PCNOM07', '<IdNumber> doesnot exists.');
insert into "PBM_BUSINESS_RULES".COMMON_DENIALS (DENIAL_CODE, DENIAL_DESCRIPTION) 
	values ('BR_PCIVPRE08', '<IdNumber> has invalid prescription.');
insert into "PBM_BUSINESS_RULES".COMMON_DENIALS (DENIAL_CODE, DENIAL_DESCRIPTION) 
	values ('BR_PCIAR09', '<IdNumber> has an inactive request.');
insert into "PBM_BUSINESS_RULES".COMMON_DENIALS (DENIAL_CODE, DENIAL_DESCRIPTION) 
	values ('BR_PCNORL10', '<IdNumber> has no remaining limit left.');
insert into "PBM_BUSINESS_RULES".COMMON_DENIALS (DENIAL_CODE, DENIAL_DESCRIPTION) 
	values ('BR_PCNOLAMT11', 'Locked amount is same/exceeding remaining limit for <IdNumber>.');
insert into "PBM_BUSINESS_RULES".COMMON_DENIALS (DENIAL_CODE, DENIAL_DESCRIPTION) 
	values ('BR_PCREX12', '<IdNumber> request has expired.');
insert into "PBM_BUSINESS_RULES".COMMON_DENIALS (DENIAL_CODE, DENIAL_DESCRIPTION) 
	values ('BR_PCNOR13', '<IdNumber> request not found.');
insert into "PBM_BUSINESS_RULES".COMMON_DENIALS (DENIAL_CODE, DENIAL_DESCRIPTION) 
	values ('BR_PCIVIDNUM14', '<IdNumber> is Invalid.');
	
COMMIT;