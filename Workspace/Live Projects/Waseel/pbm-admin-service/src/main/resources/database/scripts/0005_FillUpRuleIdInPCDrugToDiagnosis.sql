--liquibase formatted sql

--changeset PbmAdminService:0005

Update "PCDrugToDiagnosis" p1 set "RuleId" = CONCAT
(
   'PCDTDICRule_',
   (
      SELECT p2."Id" from "PCDrugToDiagnosis" p2 where p1."Id" = p2."Id"
   )
);