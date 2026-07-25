--liquibase formatted sql

--changeset Payer-Customization-Service:033


ALTER TABLE "MDSS"."PCQuantityLimitCheck"
ADD(
    "BatchId" NUMBER,
	CONSTRAINT FK_PCQLC_BatchId FOREIGN KEY ("BatchId") REFERENCES "MDSS"."CustomizationBatch" ("Id")
);