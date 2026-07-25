--liquibase formatted sql

--changeset Payer-Customization-Service:010

Insert into MDSS."ScreeningModules" ("ModuleId", "ModuleName") Values (16, 'Payer_Customization_DrugToDiagnosisInteraction');
Insert into MDSS."ScreeningModules" ("ModuleId", "ModuleName") Values (17, 'Payer_Customization_QuantityLimitCheck');
COMMIT;

