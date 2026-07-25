--liquibase formatted sql

--changeset Payer-Customization-Service:067

ALTER TABLE  "MDSS"."PCGender"
MODIFY "PayerId" NOT NULL;

ALTER TABLE  "MDSS"."PCGender"
MODIFY "ModuleName" NOT NULL;