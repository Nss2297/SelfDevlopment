--liquibase formatted sql

--changeset Idf:12

CREATE INDEX "IsEnabled_Index" 
ON "MDSS"."PayerModuleConfiguration"("IsEnabled");