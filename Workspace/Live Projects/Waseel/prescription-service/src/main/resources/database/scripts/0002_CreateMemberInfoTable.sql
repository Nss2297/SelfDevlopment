--liquibase formatted sql

--changeset Prescription:0002

CREATE TABLE "PRESCRIPTION_SERVICE"."MemberInfo"
(
	"ID" NUMBER NOT NULL,
    "MemberID"  VARCHAR2(50),
    "IDNumber"  NUMBER,
    "PolicyNumber"  VARCHAR2(50),
    "DOB"  DATE,
    "Weight"  DECIMAL,
    "Height" DECIMAL,
    "Gender" VARCHAR2(10),
    "RequestID" VARCHAR2(100),
	CONSTRAINT PK_ID PRIMARY KEY ("ID"),
	CONSTRAINT FK_RequestID FOREIGN KEY ("RequestID") REFERENCES "PRESCRIPTION_SERVICE"."PrescriptionRequest" ("RequestID")
);

CREATE SEQUENCE "PRESCRIPTION_SERVICE"."PS_MemberInfo_Seq"
  MINVALUE 1
  START WITH 1
  INCREMENT BY 1
  NOCYCLE
  NOCACHE;
