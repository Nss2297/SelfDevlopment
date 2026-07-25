--liquibase formatted sql

--changeset Idf:3

ALTER TABLE "MDSS"."IDFDrugToAge" DROP COLUMN " FromAgeInDays";  
ALTER TABLE  "MDSS"."IDFDrugToAge" ADD ("FromAgeInDays" varchar2(20));

