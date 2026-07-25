--liquibase formatted sql

--changeset Payer-Customization-Service:065

ALTER TABLE "MDSS"."PCGender"
DROP CONSTRAINT PK_PCGENDER;

ALTER TABLE "MDSS"."PCGender"
ADD CONSTRAINT PK_PCGENDER PRIMARY KEY ("Id");

ALTER TABLE "MDSS"."PCGender"
ADD CONSTRAINT UK_PCGENDER UNIQUE ("ServiceCode","PayerId","ModuleName","ScientificCode");

ALTER TABLE  "MDSS"."PCGender"
MODIFY "ServiceCode" NULL;