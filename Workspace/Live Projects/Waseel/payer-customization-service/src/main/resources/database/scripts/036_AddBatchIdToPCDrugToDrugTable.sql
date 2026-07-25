--liquibase formatted sql

--changeset Payer-Customization-Service:036

ALTER TABLE "MDSS"."PCDrugToDrug"
ADD(
    "BatchId" NUMBER,
	CONSTRAINT FK_PCDrugToDrug_BatchId FOREIGN KEY ("BatchId") REFERENCES "MDSS"."CustomizationBatch" ("Id")
);