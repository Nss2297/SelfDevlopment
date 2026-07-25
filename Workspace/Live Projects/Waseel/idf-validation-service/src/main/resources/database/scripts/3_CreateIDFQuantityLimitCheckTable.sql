--liquibase formatted sql

--changeset Idf:3

CREATE TABLE "MDSS"."IDFQuantityLimitCheck" 
(	
	"ServiceCode" VARCHAR2(250) NOT NULL,
	"FromAgeDuration-[InDays]"  NUMBER,
	"ToAgeDuration-[InDays]"  NUMBER,
	"MaxQuantityLimitInDays"  NUMBER,
	"MaxDurationInDays"  NUMBER
);