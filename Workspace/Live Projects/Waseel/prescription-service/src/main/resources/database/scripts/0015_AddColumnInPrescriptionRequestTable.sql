--liquibase formatted sql

--changeset Prescription:0015

ALTER TABLE "PRESCRIPTION_SERVICE"."PrescriptionRequest"
ADD (
	"canCancel" char(1) DEFAULT ('1'),
	"canFollowUp" char(1) DEFAULT ('1')
);