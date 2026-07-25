--liquibase formatted sql

--changeset Payer-Customization-Service:057

ALTER TABLE "MDSS"."CUSTOMIZATION_REQUEST_METADATA"
DROP COLUMN IS_CUSTOMIZABLE;