--liquibase formatted sql

--changeset Idf:13

CREATE INDEX "ServiceRejection_Index" 
ON "MDSS"."ServiceRejectionReason"("RejectionCode","RejectionReason","RequestId","ServiceId");