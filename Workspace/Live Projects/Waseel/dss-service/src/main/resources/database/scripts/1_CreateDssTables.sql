--liquibase formatted sql

--changeset DssService:1

CREATE TABLE "MDSS"."RequestInfo" 
(	
	"RequestId" VARCHAR2(100) NOT NULL,
	"PayerId"  VARCHAR2(20), 
	"ProviderId" VARCHAR2(20),
	"IsDeletedFromProvider" CHAR(1) DEFAULT ('0'),
	"IsCancelled" CHAR(1) DEFAULT ('0'),
	"IsOverriden" CHAR(1) DEFAULT ('0'),
	CONSTRAINT PK_RequestId PRIMARY KEY ("RequestId")
);
 
CREATE TABLE "MDSS"."PhysicianInfo" 
(	
	"RequestId" VARCHAR2(100), 
	"PhysicianId" VARCHAR2(20),
	 CONSTRAINT FK_PhyscisionInfo FOREIGN KEY ("RequestId") REFERENCES "MDSS"."RequestInfo"("RequestId") ON DELETE CASCADE ENABLE VALIDATE
);

CREATE TABLE "MDSS"."MemberInfo" 
(
	"RequestId" VARCHAR2(100), 
	"MemberId" VARCHAR2(30), 
	"MemberGender" VARCHAR2(10), 
	"MemberWeight" VARCHAR2(30), 
	"MemberHeight" VARCHAR2(30), 
	"DateOfBirth" VARCHAR2(10),
	CONSTRAINT FK_MemberInfo FOREIGN KEY ("RequestId") REFERENCES "MDSS"."RequestInfo"("RequestId") ON DELETE CASCADE ENABLE VALIDATE
);

CREATE TABLE "MDSS"."ICDDiagnosisInfo" 
(	
	"RequestId" VARCHAR2(100), 
	"IcdDiagnosisCode" VARCHAR2(10),
	CONSTRAINT FK_ICD10Info FOREIGN KEY ("RequestId") REFERENCES "MDSS"."RequestInfo"("RequestId") ON DELETE CASCADE ENABLE VALIDATE
); 

CREATE TABLE "MDSS"."CommonRejectionReason" 
(	
	"RejectionCode" VARCHAR2(30) NOT NULL, 
	"RejectionReason" VARCHAR2(200),
	CONSTRAINT "PK_CommonRejectionReason" PRIMARY KEY ("RejectionCode")
);

CREATE TABLE "MDSS"."ServiceInfo" 
(
	"RequestId" VARCHAR2(100) NOT NULL, 
	"ServiceId" NUMBER  GENERATED  BY DEFAULT AS IDENTITY (START WITH 1) NOT NULL, 
	"ServiceDate" DATE, 
	"ServiceCode" VARCHAR2(50), 
	"ServiceQuantity" NUMBER, 
	"ServiceAmount" NUMBER, 
	"DaysOfSupply" NUMBER,
	"IsDeletedFromProvider" CHAR(1) DEFAULT ('0'),
	"IsCancelled" CHAR(1) DEFAULT ('0'),
	"IsOverriden" CHAR(1) DEFAULT ('0'),
	CONSTRAINT "PK_ServiceInfo" PRIMARY KEY ("RequestId","ServiceId"),
	CONSTRAINT FK_ServiceInfo FOREIGN KEY ("RequestId") REFERENCES "MDSS"."RequestInfo"("RequestId") ON DELETE CASCADE ENABLE VALIDATE
); 

CREATE TABLE "MDSS"."ServiceDecision" 
(	
	"RequestId"  VARCHAR2(100), 
	"ServiceId" NUMBER, 
	"Status" VARCHAR2(50),
	CONSTRAINT "FK_ServiceDecision_RequestId" FOREIGN KEY ("RequestId","ServiceId") REFERENCES "MDSS"."ServiceInfo"("RequestId","ServiceId") ON DELETE CASCADE ENABLE VALIDATE
);

CREATE TABLE "MDSS"."ServiceRejectionReason" 
( 
	"RequestId" VARCHAR2(100), 
	"ServiceId" NUMBER, 
	"RejectionCode" VARCHAR2(30), 
	"RejectionReason" VARCHAR2(200),
	CONSTRAINT "FK_ServiceRejectionReason_RequestId"  FOREIGN KEY ("RequestId","ServiceId") REFERENCES "MDSS"."ServiceInfo"("RequestId","ServiceId") ON DELETE CASCADE ENABLE VALIDATE,
	CONSTRAINT "FK_ServiceRejectionReason_RejectionCode" FOREIGN KEY ("RejectionCode") REFERENCES "MDSS"."CommonRejectionReason"("RejectionCode") ON DELETE CASCADE ENABLE VALIDATE
);
