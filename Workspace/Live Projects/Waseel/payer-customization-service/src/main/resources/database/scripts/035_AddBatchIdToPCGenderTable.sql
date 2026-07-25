--liquibase formatted sql

--changeset Payer-Customization-Service:035

ALTER TABLE "MDSS"."PCGender"
ADD(
    "BatchId" NUMBER,
	CONSTRAINT FK_Gender_BatchId FOREIGN KEY ("BatchId") REFERENCES "MDSS"."CustomizationBatch" ("Id")
);