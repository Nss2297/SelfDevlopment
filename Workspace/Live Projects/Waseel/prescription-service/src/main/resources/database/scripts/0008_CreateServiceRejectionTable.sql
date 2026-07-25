--liquibase formatted sql

--changeset Prescription:0008

CREATE TABLE "PRESCRIPTION_SERVICE"."ServiceRejection"
(
	"ID" NUMBER NOT NULL,
	"DrugCode" VARCHAR2(50),
	"DenialCode" VARCHAR2(30),
	"RejectionReason" VARCHAR2(2500),
	"RequestID" VARCHAR2(100) NOT NULL,
	"ServiceResponseID" NUMBER NOT NULL,
	CONSTRAINT PK_ServiceRejection_ID PRIMARY KEY ("ID"),
	CONSTRAINT FK_ServiceRejection_RequestID FOREIGN KEY ("RequestID") REFERENCES "PRESCRIPTION_SERVICE"."PrescriptionRequest" ("RequestID"),
	CONSTRAINT FK_ServiceRejection_ServiceResponseID FOREIGN KEY ("ServiceResponseID") REFERENCES "PRESCRIPTION_SERVICE"."ServiceResponseInfo" ("ID")
);

CREATE SEQUENCE "PRESCRIPTION_SERVICE"."PS_ServiceRejection_SEQ"
  MINVALUE 1
  START WITH 1
  INCREMENT BY 1
  NOCYCLE
  NOCACHE;