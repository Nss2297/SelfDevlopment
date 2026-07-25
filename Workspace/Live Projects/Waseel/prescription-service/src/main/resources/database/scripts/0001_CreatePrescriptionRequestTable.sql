--liquibase formatted sql

--changeset Prescription:0001

CREATE TABLE "PRESCRIPTION_SERVICE"."PrescriptionRequest"
(
	"RequestID" VARCHAR2(100) NOT NULL,
    "PayerID"  VARCHAR2(20),
    "ProviderID"  VARCHAR2(20),
    "SendDateTime"  TIMESTAMP(6),
    "ReceivedDateTime"  TIMESTAMP(6),
    "StatusCode" NUMBER,
    "StatusDescription" VARCHAR2(500),
    CONSTRAINT PK_RequestID PRIMARY KEY ("RequestID")
);
