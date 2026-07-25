--liquibase formatted sql

--changeset DssService:28

CREATE SEQUENCE "MDSS"."PayerValidationSequence_SEQ"
  MINVALUE 1
  START WITH 1
  INCREMENT BY 1
  NOCYCLE
  NOCACHE;