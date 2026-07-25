--liquibase formatted sql

--changeset Payer-Customization-Service:022

Insert into MDSS."CommonRejectionReason"
("RejectionCode","RejectionReason")
Values
('PC_CPDDI701','Requested drug <DrugName> (<DrugCode>) with <InteractedDrugName> (<InteractedDrugCode>) has Severe Interactions, HIGH ALERT: MEMBER HEALTH MIGHT SEVERELY HARMED');
COMMIT;