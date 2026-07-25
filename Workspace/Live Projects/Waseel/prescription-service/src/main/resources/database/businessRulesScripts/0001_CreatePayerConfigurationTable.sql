--liquibase formatted sql

--changeset Prescription:0001

CREATE TABLE "PBM_BUSINESS_RULES"."PayerConfiguration"
(
	"PayerId"  VARCHAR2(20) NOT NULL,
    "PayerName"  VARCHAR2(100),
    "IsEnabled"  char(1) DEFAULT ('1'),
    CONSTRAINT PK_PayerConfiguration_PayerId PRIMARY KEY ("PayerId")
);