--liquibase formatted sql

--changeset DssService:4

CREATE TABLE MDSS."ScreeningModules" 
(
  "ModuleId" NUMBER  GENERATED  BY DEFAULT AS IDENTITY (START WITH 1) NOT NULL,
  "ModuleName" VARCHAR2(50),
   CONSTRAINT "ScreeningModules_PK" PRIMARY KEY ("ModuleId")
);


