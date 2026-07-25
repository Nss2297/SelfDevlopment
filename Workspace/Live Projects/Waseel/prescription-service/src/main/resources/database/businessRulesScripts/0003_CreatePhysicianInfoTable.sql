--liquibase formatted sql

--changeset Prescription:0003

CREATE TABLE "PBM_BUSINESS_RULES"."PhysicianInfo"
(
	"ProviderId" NUMBER NOT NULL,
	"RegistrationNumber" VARCHAR2(20) NOT NULL,
    "Name" VARCHAR2(50) NOT NULL,
    "Category" VARCHAR2(50) NOT NULL,
    CONSTRAINT PK_Physician_ProviderId_RegistrationNumber PRIMARY KEY ("ProviderId","RegistrationNumber"),
    CONSTRAINT FK_Physician_Category FOREIGN KEY ("Category") REFERENCES "PBM_BUSINESS_RULES"."PhysicianCategory" ("PhysicianCategoryName")
);