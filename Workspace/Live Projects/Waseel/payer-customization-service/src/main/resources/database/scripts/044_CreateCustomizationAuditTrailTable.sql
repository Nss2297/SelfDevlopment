--liquibase formatted sql

--changeset Payer-Customization-Service:044

CREATE TABLE "MDSS"."CustomizationAuditTrail"
(
    "Id" NUMBER NOT NULL,
    "RuleId" VARCHAR2(20),
	"RequestId" VARCHAR2(100),
	"SubmissionDateTime" TIMESTAMP,
	CONSTRAINT PK_CustomizationAuditTrail_Id PRIMARY KEY ("Id")
);

CREATE SEQUENCE "MDSS"."PC_CustomizationAuditTrail_Seq"
  MINVALUE 1
  START WITH 1
  INCREMENT BY 1
  NOCYCLE
  NOCACHE;