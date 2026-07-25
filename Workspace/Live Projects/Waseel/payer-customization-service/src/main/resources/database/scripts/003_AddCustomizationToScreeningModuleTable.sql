--liquibase formatted sql

--changeset Payer-Customization-Service:003


Insert into MDSS."ScreeningModules"
   ("ModuleId", "ModuleName")
 Values
   (15, 'Payer_Customization');
COMMIT;
