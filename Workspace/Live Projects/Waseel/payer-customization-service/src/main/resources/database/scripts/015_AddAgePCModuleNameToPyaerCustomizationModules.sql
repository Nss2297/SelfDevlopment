--liquibase formatted sql

--changeset Payer-Customization-Service:015

Insert into MDSS."PayerCustomizationModules"
("Id","RuleId","Description")
Values
(5,'PC_CPAGE902','Drug To Age');
COMMIT;