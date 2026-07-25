--liquibase formatted sql

--changeset Prescription:0026

CREATE TABLE "PRESCRIPTION_SERVICE"."DispensedService"
(
	"ID" NUMBER NOT NULL,
    "ServiceID"  NUMBER NOT NULL,
    "DispensedID"  NUMBER NOT NULL,
    "DispensedQuantity"  NUMBER NOT NULL,
    "PrescribedQuantity"  NUMBER NOT NULL,
    CONSTRAINT PK_DispensedService_ID PRIMARY KEY ("ID"),
	CONSTRAINT FK_DispensedService_DispensedID FOREIGN KEY ("DispensedID") REFERENCES "PRESCRIPTION_SERVICE"."DispensedPrescription" ("ID"),
	CONSTRAINT FK_DispensedService_ServiceID FOREIGN KEY ("ServiceID") REFERENCES "PRESCRIPTION_SERVICE"."ServiceInfo" ("ID")
);

CREATE SEQUENCE "PRESCRIPTION_SERVICE"."PS_DispensedService_SEQ"
  MINVALUE 1
  START WITH 1
  INCREMENT BY 1
  NOCYCLE
  NOCACHE;