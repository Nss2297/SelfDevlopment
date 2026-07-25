--liquibase formatted sql

--changeset Payer-Customization-Service:009


Insert ALL 
	into MDSS."PayerCustomizationModules" ("Id","RuleId", "Description") Values (1,'PC_CPINDI001', 'Drug To Diagnosis Indication')
	into MDSS."PayerCustomizationModules" ("Id","RuleId", "Description") Values (2,'PC_CPINDC001', 'Drug To Diagnosis ContraIndication')
	into MDSS."PayerCustomizationModules" ("Id","RuleId", "Description") Values (3,'PC_CPQTL912', 'Quantity Limit Check')
	
	SELECT * FROM dual;  
COMMIT;
