--liquibase formatted sql

--changeset Payer-Customization-Service:066

ALTER TABLE "MDSS"."PCAge"
DROP CONSTRAINT PK_PCAGE;

ALTER TABLE "MDSS"."PCAge"
ADD CONSTRAINT PK_PCAGE PRIMARY KEY ("Id");

ALTER TABLE "MDSS"."PCAge"
ADD CONSTRAINT UK_PCAGE UNIQUE ("ServiceCode","PayerId","ModuleName","ScientificCode");

ALTER TABLE  "MDSS"."PCAge"
MODIFY "ServiceCode" NULL;

ALTER TABLE "PCAge"
MODIFY "PayerId" NOT NULL;

ALTER TABLE "PCAge"
MODIFY "ModuleName" NOT NULL;