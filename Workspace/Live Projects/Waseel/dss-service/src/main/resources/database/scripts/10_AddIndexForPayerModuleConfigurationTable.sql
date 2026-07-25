--liquibase formatted sql

--changeset Idf:8

CREATE INDEX "PayerId_Index" 
ON "MDSS"."PayerModuleConfiguration"("PayerId");