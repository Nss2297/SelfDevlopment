--liquibase formatted sql

--changeset DSS:23

CREATE INDEX "IsEnabled&PayerId_Index" 
ON "MDSS"."PayerModuleConfiguration"("PayerId","IsEnabled");

