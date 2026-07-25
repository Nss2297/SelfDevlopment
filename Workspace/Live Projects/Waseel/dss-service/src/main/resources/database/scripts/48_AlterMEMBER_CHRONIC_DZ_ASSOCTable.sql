--liquibase formatted sql

--changeset DSS:48

ALTER TABLE MDSS.MEMBER_CHRONIC_DZ_ASSOC
 ADD (PAYER_ID  VARCHAR2(20));