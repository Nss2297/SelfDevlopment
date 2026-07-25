--liquibase formatted sql

--changeset Prescription:0023

CREATE TABLE "PRESCRIPTION_SERVICE"."InvalidPrescriptionRequest" (
"ID" NUMBER NOT NULL,
"RequestID" VARCHAR2(100) NULL,
"EPrescriptionReferenceNumber" VARCHAR2(100) NULL,
"SendDateTime" TIMESTAMP(6) NOT NULL,
"ReceivedDateTime" TIMESTAMP(6) NOT NULL,
"Status"	VARCHAR2(60) NULL,
"StatusDescription"	VARCHAR2(500) NULL,
"MemberID"	VARCHAR2(50) NULL,
"IDNumber"	NUMBER NULL,
"PolicyNumber"	VARCHAR2(50) NULL,
"PayerID"	VARCHAR2(20) NULL,
"ProviderID"	VARCHAR2(20) NULL,
CONSTRAINT "InvalidPrescriptionRequest_PK" PRIMARY KEY  ("ID") 
);

