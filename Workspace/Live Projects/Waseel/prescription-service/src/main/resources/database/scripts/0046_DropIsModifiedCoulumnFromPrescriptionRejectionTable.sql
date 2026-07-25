--liquibase formatted sql

--changeset Prescription:0046

ALTER TABLE PRESCRIPTION_REJECTION DROP COLUMN IS_MODIFIED_BY_PAYER;