--liquibase formatted sql

--changeset Fdb:1

CREATE TABLE "MDSS"."FDBDiagnosisIndication"
(	
	"ICDCode" VARCHAR2(20) NOT NULL,
	"IsSupportedByFDB"  CHAR(1),
	"IsSubChapterAvailable"  CHAR(1),
	CONSTRAINT PK_ICDCode PRIMARY KEY ("ICDCode")
);
 