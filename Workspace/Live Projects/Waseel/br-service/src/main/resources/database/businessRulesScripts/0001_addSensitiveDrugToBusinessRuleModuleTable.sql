--liquibase formatted sql

--changeset BR Service:0001

INSERT INTO PBM_BUSINESS_RULES.BUSINESS_RULE_MODULE (MODULE_ID, MODULE_NAME) VALUES  (7, 'Sensitive Drug Validation');
COMMIT;