--liquibase formatted sql

--changeset Idf:14

CREATE INDEX "ServiceDecision_Index" 
ON "MDSS"."ServiceDecision"("RequestId","ServiceId");