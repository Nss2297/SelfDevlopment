--liquibase formatted sql

--changeset Payer-Customization-Service:034


ALTER TABLE "MDSS"."PCAge"
ADD(
    "BatchId" NUMBER,
	CONSTRAINT FK_Age_BatchId FOREIGN KEY ("BatchId") REFERENCES "MDSS"."CustomizationBatch" ("Id")
);