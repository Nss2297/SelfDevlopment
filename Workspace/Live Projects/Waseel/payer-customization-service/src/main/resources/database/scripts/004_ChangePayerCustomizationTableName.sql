--liquibase formatted sql

--changeset Payer-Customization-Service:004

RENAME  "PayerCustomizationConfiguration" TO "PCDrugToDiagnosis";

