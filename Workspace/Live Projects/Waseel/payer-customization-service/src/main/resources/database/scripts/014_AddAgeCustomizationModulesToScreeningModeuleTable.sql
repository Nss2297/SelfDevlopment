--liquibase formatted sql

--changeset Payer-Customization-Service:014

Insert into MDSS."ScreeningModules" ("ModuleId", "ModuleName") Values (19, 'Payer_Customization_Age');
COMMIT;

