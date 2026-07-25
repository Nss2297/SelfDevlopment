--liquibase formatted sql

--changeset Payer-Customization-Service:037

ALTER TABLE "MDSS"."PCDuplicateTherapy"
ADD(
    "BatchId" NUMBER,
	CONSTRAINT FK_PCDuplicateTherapy_BatchId FOREIGN KEY ("BatchId") REFERENCES "MDSS"."CustomizationBatch" ("Id")
);
