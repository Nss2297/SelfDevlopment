--liquibase formatted sql

--changeset Prescription:0006

CREATE TABLE "PRESCRIPTION_SERVICE"."TransactionLog"
(
	"TransactionLogID" NUMBER NOT NULL,
	"RequestID" VARCHAR2(100) NOT NULL,
	"TransactionID" NUMBER NOT NULL,
	"TransactionType" VARCHAR2(20) NOT NULL,
	"PayerID" VARCHAR2(20) NOT NULL,
	"ProviderID" VARCHAR2(20) NOT NULL,
	"TransactionStatus" VARCHAR2(40) NOT NULL,
	"EPrescriptionReferenceNumber" VARCHAR2(100) NOT NULL,
	"Status" VARCHAR2(60) NOT NULL,
	"StatusDescription" VARCHAR2(500),
	"ReceivingRequestDateTime" TIMESTAMP(6) NOT NULL,
	"SendingResponseDateTime" TIMESTAMP(6) NOT NULL,
	"UserID" VARCHAR2(100),
	"HttpStatus" VARCHAR2(30) NOT NULL,
	"HttpStatusDescription" VARCHAR2(5000),
	CONSTRAINT PK_TransactionLogId PRIMARY KEY ("TransactionLogID"),
	CONSTRAINT FK_TransactionLog_RequestID FOREIGN KEY ("RequestID") REFERENCES "PRESCRIPTION_SERVICE"."PrescriptionRequest" ("RequestID")
);

CREATE SEQUENCE "PRESCRIPTION_SERVICE"."PS_TransactionLog_SEQ"
  MINVALUE 1
  START WITH 1
  INCREMENT BY 1
  NOCYCLE
  NOCACHE;