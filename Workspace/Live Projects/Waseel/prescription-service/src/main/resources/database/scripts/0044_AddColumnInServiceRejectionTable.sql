--liquibase formatted sql

--changeset Prescription:0044

ALTER TABLE "PRESCRIPTION_SERVICE"."ServiceRejection"
ADD (
	"IS_MODIFIED_BY_PAYER" CHAR(1) DEFAULT ('0'));