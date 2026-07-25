--liquibase formatted sql

--changeset Prescription:0005

CREATE TABLE "PRESCRIPTION_SERVICE"."ServiceInfo"
(
	"ID" NUMBER NOT NULL,
    "DrugCode"  VARCHAR2(50) NOT NULL,
    "UnitType"  VARCHAR2(30) NOT NULL,
    "UnitPrice"  DECIMAL NOT NULL,
    "Quantity"  NUMBER NOT NULL,
    "RequestedAmount"  NUMBER,
    "OrderingClinician" VARCHAR2(20),
    "ServiceStartDate" DATE NOT NULL,
    "ServiceEndDate" DATE,
    "Duration" NUMBER,
    "Frequency" VARCHAR2(30),
    "FrequencyOthersDescription" VARCHAR2(200),
    "RequestID" VARCHAR2(100),
	CONSTRAINT PK_ServiceInfo_ID PRIMARY KEY ("ID"),
	CONSTRAINT FK_ServiceInfo_RequestID FOREIGN KEY ("RequestID") REFERENCES "PRESCRIPTION_SERVICE"."PrescriptionRequest" ("RequestID")
);

CREATE SEQUENCE "PRESCRIPTION_SERVICE"."PS_ServiceInfo_Seq"
  MINVALUE 1
  START WITH 1
  INCREMENT BY 1
  NOCYCLE
  NOCACHE;
