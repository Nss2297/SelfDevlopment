--liquibase formatted sql

--changeset Fdb:2

ALTER TABLE MDSS."FDBDiagnosisIndication"
 DROP COLUMN "IsSupportedByFDB";
 
ALTER TABLE 
   MDSS."FDBDiagnosisIndication"
RENAME COLUMN
   "IsSubChapterAvailable"
TO
   "ValidateSubChapters";
   
RENAME  "FDBDiagnosisIndication" TO "FDBDiagnosisIndicationConfig";