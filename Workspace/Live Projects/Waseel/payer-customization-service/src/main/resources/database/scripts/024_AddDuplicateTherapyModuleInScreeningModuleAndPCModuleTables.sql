--liquibase formatted sql

--changeset Payer-Customization-Service:024

Insert into MDSS."ScreeningModules" ("ModuleId", "ModuleName") Values (21, 'Payer_Customization_DuplicateTherapy');
Insert into MDSS."PayerCustomizationModules"("Id","RuleId","Description") Values (7,'PC_CPTDE0001','Duplicate Therapy');
COMMIT;