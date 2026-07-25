--liquibase formatted sql

--changeset Payer-Customization-Service:027

Insert into MDSS."CommonRejectionReason" ("RejectionCode","RejectionReason") 
Values ('PC_CPINDI001','Medication <DrugName> (<DrugCode>) is not indicated with diagnosis code <ICD>');

Insert into MDSS."CommonRejectionReason" ("RejectionCode","RejectionReason") 
Values ('PC_CPINDC001','Medication  <DrugName> (<DrugCode>) has SEVERE CONTRAINDICATION with diagnosis code <ICD>, HIGH ALERT: MEMBER HEALTH MIGHT SEVERELY HARMED');

COMMIT;