--liquibase formatted sql

--changeset Policy Consumption Service:0010

INSERT INTO PBM_BUSINESS_RULES.BUSINESS_RULE_MODULE (MODULE_ID, MODULE_NAME) VALUES  (6, 'Brand Replicability check');
