--liquibase formatted sql

--changeset DSS:46

Alter table MDSS."ServiceInfo" Add "ScientificCode" VARCHAR2 (64 Byte);
