--liquibase formatted sql

--changeset Prescription:0013

CREATE SEQUENCE "PRESCRIPTION_SERVICE"."PS_EprescriptionReferenceNumber_SEQ"
  MINVALUE 1
  START WITH 1
  INCREMENT BY 1
  NOCYCLE
  NOCACHE;
