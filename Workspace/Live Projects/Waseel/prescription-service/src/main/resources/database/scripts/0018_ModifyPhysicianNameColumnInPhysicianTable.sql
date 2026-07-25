--liquibase formatted sql

--changeset Prescription:0018

ALTER TABLE "Physician"  MODIFY ("PhysicianName" VARCHAR2(250 BYTE) );