--liquibase formatted sql

--changeset Payer-Customization-Service:060

ALTER TABLE "MDSS"."CUSTOMIZATION_REQUEST_DETAILS"
DROP COLUMN IS_CUSTOMIZABLE;
