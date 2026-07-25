--liquibase formatted sql

--changeset DssService:5

CREATE TABLE MDSS."PayerModuleConfiguration"
(
  "PayerId"    VARCHAR2(20 BYTE),
  "ModuleId"   NUMBER,
  "IsEnabled"  CHAR(1 BYTE)
);

ALTER TABLE MDSS."PayerModuleConfiguration" ADD (
  CONSTRAINT "PayerModuleConfiguration_PK"
  PRIMARY KEY
  ("ModuleId", "PayerId")
 ) ;

ALTER TABLE MDSS."PayerModuleConfiguration" ADD (
  CONSTRAINT "PayerModuleConfiguration_FK" 
  FOREIGN KEY ("ModuleId") 
  REFERENCES MDSS."ScreeningModules" ("ModuleId")
 );
