--liquibase formatted sql

--changeset DssService:24

CREATE TABLE "MDSS"."DrugServiceMetaData" 
(	
	"DrugListId" NUMBER  GENERATED  BY DEFAULT AS IDENTITY (START WITH 1) NOT NULL,
	"File_Name" VARCHAR2(100),
	"SFDA_Version" VARCHAR2(100), 
	"Owner_Name" VARCHAR2(100),
	"SFDA_Update_Date" DATE,
	"Effective_Date" DATE,
	"Upload_Date_Time" TIMESTAMP,
	CONSTRAINT PK_DrugListId PRIMARY KEY ("DrugListId")
);

