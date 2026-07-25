--liquibase formatted sql

--changeset Prescription:0009

INSERT INTO PBM_BUSINESS_RULES.COMMON_DENIALS  (
    DENIAL_CODE,
    DENIAL_DESCRIPTION
) VALUES (
    'PYR_APPR_REQ',
    'DrugCode <DrugCode> required an approval'
);

COMMIT;


