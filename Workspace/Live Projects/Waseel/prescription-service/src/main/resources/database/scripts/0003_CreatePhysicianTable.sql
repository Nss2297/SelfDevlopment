--liquibase formatted sql

--changeset Prescription:0003

CREATE TABLE "PRESCRIPTION_SERVICE"."Physician"
(
	"ID" NUMBER NOT NULL,
    "PhysicianLicenseNumber"  VARCHAR2(20),
    "PhysicianName"  VARCHAR2(200),
    "PhysicianCategory"  VARCHAR2(100),
    "RequestID" VARCHAR2(100) NOT NULL,
	CONSTRAINT PK_Physician_ID PRIMARY KEY ("ID"),
	CONSTRAINT FK_Physician_RequestID FOREIGN KEY ("RequestID") REFERENCES "PRESCRIPTION_SERVICE"."PrescriptionRequest" ("RequestID")
);

CREATE SEQUENCE "PRESCRIPTION_SERVICE"."PS_Physician_Seq"
  MINVALUE 1
  START WITH 1
  INCREMENT BY 1
  NOCYCLE
  NOCACHE;
