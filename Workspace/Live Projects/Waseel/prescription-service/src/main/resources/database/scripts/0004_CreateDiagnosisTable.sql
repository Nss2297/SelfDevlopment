--liquibase formatted sql

--changeset Prescription:0004

CREATE TABLE "PRESCRIPTION_SERVICE"."Diagnosis"
(
	"DiagnosisCode"  VARCHAR2(10) NOT NULL,
    "DiagnosisType"  VARCHAR2(30) NOT NULL,
    "RequestID" VARCHAR2(100) NOT NULL,
	CONSTRAINT FK_Diagnosis_RequestID FOREIGN KEY ("RequestID") REFERENCES "PRESCRIPTION_SERVICE"."PrescriptionRequest" ("RequestID")
);
