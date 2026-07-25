--liquibase formatted sql

--changeset Idf:6


ALTER TABLE "MDSS"."IDFConcurrentMedication" ADD "Severity" VARCHAR2(200);