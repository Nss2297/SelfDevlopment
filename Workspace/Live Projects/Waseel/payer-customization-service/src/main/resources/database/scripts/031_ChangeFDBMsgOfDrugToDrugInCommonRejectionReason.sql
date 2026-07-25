--liquibase formatted sql

--changeset Payer-Customization-Service:031

UPDATE "MDSS"."CommonRejectionReason"
SET 
"RejectionReason" = 'Requested drug <DrugName> (<DrugCode>) with <InteractedDrugName> (<InteractedDrugCode>) has Severe Interactions, HIGH ALERT: MEMBER HEALTH MIGHT SEVERELY HARMED'
WHERE 
"RejectionCode" = 'FDB_CPDDI701';