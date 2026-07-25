--liquibase formatted sql

--changeset Eligibility Service:0004

insert into "PBM_BUSINESS_RULES".COMMON_DENIALS (DENIAL_CODE, DENIAL_DESCRIPTION) 
	values ('BR_ELGIVMP04', 'Policy or Member does not exist for <IdNumber> <MemberName>.');