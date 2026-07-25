--liquibase formatted sql

--changeset Payer-Customization-Service:021

Insert into MDSS."ScreeningModules" ("ModuleId", "ModuleName") Values (20, 'Payer_Customization_DrugToDrugInteraction');
Insert into MDSS."PayerCustomizationModules"("Id","RuleId","Description") Values (6,'PC_CPDDI701','Drug To Drug Interaction');
COMMIT;