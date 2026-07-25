--liquibase formatted sql

--changeset Prescription:0007

CREATE TABLE "PRESCRIPTION_SERVICE"."ServiceResponseInfo"
(
	"ID" NUMBER NOT NULL,
	"RequestID" VARCHAR2(100) NOT NULL,
	"RequestedAmount" NUMBER,
	"ApprovedAmount" NUMBER,
	"Discount" NUMBER,
	"PatientShare" NUMBER,
	"Net" NUMBER,
	"Status" VARCHAR2(60),
	"StatusDescription" VARCHAR2(500),
	"ServiceID" NUMBER NOT NULL,
	CONSTRAINT PK_ServiceResponseInfo_ID PRIMARY KEY ("ID"),
	CONSTRAINT FK_ServiceResponseInfo_RequestID FOREIGN KEY ("RequestID") REFERENCES "PRESCRIPTION_SERVICE"."PrescriptionRequest" ("RequestID"),
	CONSTRAINT FK_ServiceResponseInfo_ServiceInfoID FOREIGN KEY ("ServiceID") REFERENCES "PRESCRIPTION_SERVICE"."ServiceInfo" ("ID")
);

CREATE SEQUENCE "PRESCRIPTION_SERVICE"."PS_ServiceResponseInfo_SEQ"
  MINVALUE 1
  START WITH 1
  INCREMENT BY 1
  NOCYCLE
  NOCACHE;