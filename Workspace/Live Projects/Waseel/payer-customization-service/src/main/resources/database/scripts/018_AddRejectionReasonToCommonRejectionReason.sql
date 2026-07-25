--liquibase formatted sql

--changeset Payer-Customization-Service:018
Insert into MDSS."CommonRejectionReason" ("RejectionCode","RejectionReason") Values ('PC_CPGNDR403', 'Gender rule violates the condition : <Condition> for drug <DrugName> (<DrugCode>)');
COMMIT;