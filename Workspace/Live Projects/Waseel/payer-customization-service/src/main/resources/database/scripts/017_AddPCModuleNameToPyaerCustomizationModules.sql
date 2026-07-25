--liquibase formatted sql

--changeset Payer-Customization-Service:017
Insert into MDSS."PayerCustomizationModules" ("Id","RuleId", "Description") Values (4,'PC_CPGNDR403', 'Gender Customization');
COMMIT;