--liquibase formatted sql

--changeset Prescription:0058

ALTER TABLE "PRESCRIPTION_SERVICE"."MemberInfo" ADD ("Nationality" VARCHAR2(56));
ALTER TABLE "PRESCRIPTION_SERVICE"."MemberInfo" MODIFY ("Nationality" VARCHAR2(56) NOT NULL  ENABLE NOVALIDATE);
  