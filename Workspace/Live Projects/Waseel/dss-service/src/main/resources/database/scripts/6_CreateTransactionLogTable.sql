--liquibase formatted sql

--changeset TRansactionLog:6

--comment: Create TransactionLog table

CREATE TABLE "MDSS"."TransactionLog" 
(	
	"TransactionLogId" NUMBER NOT NULL, 
	"RequestId" VARCHAR2(100), 
	"TransactionType" VARCHAR2(20), 
	"TransactionId" NUMBER, 
	"PayerId" VARCHAR2(20), 
	"ProviderId" VARCHAR2(20), 
	"TransactionStatus" VARCHAR2(40), 
	"PBMStatus" VARCHAR2(40), 
	"HttpStatus" VARCHAR2(30), 
	"HttpStatusDescription" VARCHAR2(5000), 
	"ReceivingRequestDateTime" TIMESTAMP(6), 
	"SendingResponseDateTime" TIMESTAMP(6),
	"ValidationRequester" VARCHAR2(25) ,
	CONSTRAINT "PK_TransactionLogId" PRIMARY KEY ("TransactionLogId")
); 

CREATE SEQUENCE "MDSS"."DSSTransactionLog_SEQ"
  MINVALUE 1
  START WITH 1
  INCREMENT BY 1
  NOCYCLE
  NOCACHE;
  
ALTER TABLE "MDSS"."ICDDiagnosisInfo"  
ADD "IsDeletedFromProvider" CHAR(1) DEFAULT ('0');