--liquibase formatted sql

--changeset Idf:7


ALTER TABLE "MDSS"."IDFQuantityLimitCheck" ADD "ProductPackageUnit" VARCHAR2(200);