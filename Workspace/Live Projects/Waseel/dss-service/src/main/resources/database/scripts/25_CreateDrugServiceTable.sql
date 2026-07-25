--liquibase formatted sql

--changeset DssService:25

CREATE TABLE "MDSS"."DrugService" 
(	
	"Code"  VARCHAR2(100),
	"DrugListId" NUMBER  NOT NULL,
	"Category" VARCHAR2(100),
	"Display" VARCHAR2(100),
	"Discontinue_Date" VARCHAR2(100),
	"Price" VARCHAR2(100),
	"Granular_Unit" VARCHAR2(100),
	"Unit_Type" VARCHAR2(100) ,
	"Manufacturer"  VARCHAR2(300), 
	"Reg_Owner" VARCHAR2(300),
	"Dosage_Form" VARCHAR2(100),
	"ROA_Suggested" VARCHAR2(100), 
	"Package_Type" VARCHAR2(100),
	"Package_Size" VARCHAR2(100), 
	"Ingredients" VARCHAR2(100),
	"Strength" VARCHAR2(100), 
	"Release_Date" TIMESTAMP,
	"Received_Date" DATE,
	"Other_Codes_Type" VARCHAR2(100), 
	"Other_Codes_Value" VARCHAR2(150),
	CONSTRAINT FK_DrugListId FOREIGN KEY ("DrugListId") REFERENCES "MDSS"."DrugServiceMetaData"("DrugListId") ON DELETE CASCADE ENABLE VALIDATE,
	CONSTRAINT PK_Code PRIMARY KEY ("Code")
);

