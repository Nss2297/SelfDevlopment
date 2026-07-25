--liquibase formatted sql

--changeset Prescription:0002

CREATE TABLE "PBM_BUSINESS_RULES"."PhysicianCategory"
(
	"PhysicianCategoryName" VARCHAR2(50) NOT NULL,
	"CategoryDescription"  VARCHAR2(150) NOT NULL,
    CONSTRAINT PK_PhysicianCategory_PhysicianCategoryName PRIMARY KEY ("PhysicianCategoryName")
);