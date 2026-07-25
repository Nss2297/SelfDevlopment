--liquibase formatted sql

--changeset Payer-Customization-Service:038

ALTER TABLE "MDSS"."PCDrugToDiagnosis"
ADD(
    "BatchId" NUMBER,
	CONSTRAINT FK_PCPCDrugToDiagnosis_BatchId FOREIGN KEY ("BatchId") REFERENCES "MDSS"."CustomizationBatch" ("Id")
);
