--liquibase formatted sql

--changeset Payer-Customization-Service:016

Insert into MDSS."CommonRejectionReason"
("RejectionCode","RejectionReason")
Values
('PC_CPAGE902','Drug <DrugName> (<DrugCode>) is inconsistent with the patient''s age');
COMMIT;