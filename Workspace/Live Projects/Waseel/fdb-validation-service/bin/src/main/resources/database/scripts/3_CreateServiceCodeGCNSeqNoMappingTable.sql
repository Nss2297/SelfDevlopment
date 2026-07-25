--liquibase formatted sql
--changeset Fdb:3


Drop table MDSS."ServiceCodeGCNSeqNoMapping";


CREATE TABLE "MDSS"."ServiceCodeGCNSeqNoMapping"
(
   "ServiceCode" VARCHAR2 (50) NOT NULL,
   "GcnSeqNo" NUMBER NOT NULL ,
   "ProductPackageUnit" VARCHAR2 (1 Byte)  ,
   "ProductPackageSize" NUMBER ,
   CONSTRAINT PK_ServiceCodeGCNSeqNoMapping PRIMARY KEY ("ServiceCode")
);