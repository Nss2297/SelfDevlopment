--liquibase formatted sql

--changeset Payer-Customization-Service:012

Insert into MDSS."ScreeningModules" ("ModuleId", "ModuleName") Values (18, 'Payer_Customization_Gender');
COMMIT;