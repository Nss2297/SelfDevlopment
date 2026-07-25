--liquibase formatted sql

--changeset Payer-Customization-Service:025

Insert into MDSS."CommonRejectionReason"
("RejectionCode","RejectionReason")
Values
('PC_CPTDE0001','Therapeutic Duplication : between drug <DrugName> (<DrugCode>) and drug <DrugName> (<ConcurrentDrugCode>)');
COMMIT;
