--liquibase formatted sql

--changeset Prescription:0043

ALTER TABLE "PRESCRIPTION_SERVICE"."ServiceResponseInfo"
ADD (
	"IS_OVERRIDE_DECISION" CHAR(1) DEFAULT ('0'),
	"OVERRIDE_DESCRIPTION" VARCHAR2(3000)
);