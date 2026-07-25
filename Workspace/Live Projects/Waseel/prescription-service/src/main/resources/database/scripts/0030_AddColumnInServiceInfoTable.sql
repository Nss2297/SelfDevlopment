--liquibase formatted sql

--changeset Prescription:0030

ALTER TABLE "PRESCRIPTION_SERVICE"."ServiceInfo" ADD ("UseUnitType" VARCHAR2(20) NULL);
ALTER TABLE "PRESCRIPTION_SERVICE"."ServiceInfo" ADD ("UseUnitValue" NUMBER NULL);