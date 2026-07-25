--liquibase formatted sql

--changeset Prescription:0025

CREATE TABLE "PRESCRIPTION_SERVICE"."DispensedPrescription"
(
	"ID" NUMBER NOT NULL,
    "RequestID"  VARCHAR2(100),
    "EPrescriptionReferenceNumber"  VARCHAR2(100),
    "ProviderID"  VARCHAR2(20),
    "PayerID"  VARCHAR2(20),
    "StatusCode" VARCHAR2(50),
    CONSTRAINT PK_DispensedPrescription_ID PRIMARY KEY ("ID"),
	CONSTRAINT FK_DispensedPrescription_RequestID FOREIGN KEY ("RequestID") REFERENCES "PRESCRIPTION_SERVICE"."PrescriptionRequest" ("RequestID")

);


CREATE SEQUENCE "PRESCRIPTION_SERVICE"."PS_DispensedPrescription_SEQ"
  MINVALUE 1
  START WITH 1
  INCREMENT BY 1
  NOCYCLE
  NOCACHE;