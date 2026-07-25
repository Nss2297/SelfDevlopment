--liquibase formatted sql

--changeset Payer-Customization-Service:030

DELETE FROM "MDSS"."PayerCustomizationModules" ;

Insert ALL 
	into "MDSS"."PayerCustomizationModules" ("Id","RuleId", "Description")
		Values (1,'PCDTDICRule', 'Drug To Diagnosis Indication and ContraIndication')
	into "MDSS"."PayerCustomizationModules" ("Id","RuleId", "Description") 
		Values (2,'PCDTDIRule', 'Drug To Drug Interaction')
	into "MDSS"."PayerCustomizationModules" ("Id","RuleId", "Description")
		Values (3,'PCQLCRule', 'Quantity Limit Check')
	into "MDSS"."PayerCustomizationModules" ("Id","RuleId", "Description")
		Values (4,'PCDTGRule', 'Drug To Gender ')
	into "MDSS"."PayerCustomizationModules" ("Id","RuleId", "Description") 
		Values (5,'PCDTARule', 'Drug To Age')
	into "MDSS"."PayerCustomizationModules" ("Id","RuleId", "Description")
		Values (6,'PCDTRule', 'Duplicate Therapy')
	
	SELECT * FROM dual;  
COMMIT;
