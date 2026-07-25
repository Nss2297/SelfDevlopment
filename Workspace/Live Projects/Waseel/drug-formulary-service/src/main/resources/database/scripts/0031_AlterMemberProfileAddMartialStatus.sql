--liquibase formatted sql

--changeset Drug Formulary Service:0031


ALTER TABLE PBM_BUSINESS_RULES.MEMBER_PROFILE
ADD MARITAL_STATUS VARCHAR2(30);