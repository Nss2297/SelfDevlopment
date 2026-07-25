--liquibase formatted sql

--changeset Prescription:0019

ALTER TABLE "MemberInfo"  MODIFY ("Height" DECIMAL(5,2) );
ALTER TABLE "MemberInfo"  MODIFY ("Weight" DECIMAL(5,2) );