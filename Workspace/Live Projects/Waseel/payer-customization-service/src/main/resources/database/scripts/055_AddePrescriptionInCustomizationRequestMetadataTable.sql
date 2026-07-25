--liquibase formatted sql

--changeset Payer-Customization-Service:055

ALTER TABLE MDSS.CUSTOMIZATION_REQUEST_METADATA ADD E_PRESCRIPTION_REF_NO VARCHAR2(200);
