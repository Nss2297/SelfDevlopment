--liquibase formatted sql

--changeset Prescription:0004

CREATE TABLE "PBM_BUSINESS_RULES"."PayerMemberInfo"
(
	"ID" NUMBER NOT NULL,
	"PayerId" VARCHAR2(20) NOT NULL,
	"MemberName" VARCHAR2(200) NOT NULL,
	"IDNumber" NUMBER NOT NULL,
    "MemberID" VARCHAR2(50),
    "DOB" DATE NOT NULL,
    "Gender" VARCHAR2(10) NOT NULL,
    "Nationality" VARCHAR2(56),
    "MobileNumber" VARCHAR2(15),
    "Email" VARCHAR2(64),
    "Status" VARCHAR2(10) NOT NULL,
    "IssueDate" DATE,
    "IsCancelled" CHAR(1) DEFAULT ('0'),
    "CancelledDate" DATE,
	CONSTRAINT PK_PayerMemberInfo_ID PRIMARY KEY ("ID")
);

CREATE SEQUENCE "PBM_BUSINESS_RULES"."Ps_PayerMemberInfo_Seq"
  MINVALUE 1
  START WITH 1
  INCREMENT BY 1
  NOCYCLE
  NOCACHE;
