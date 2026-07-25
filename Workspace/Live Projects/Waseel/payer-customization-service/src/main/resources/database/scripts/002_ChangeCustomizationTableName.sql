--liquibase formatted sql

--changeset Payer-Customization-Service:002

RENAME  "PayerDiagnosisConfiguration" TO "PayerCustomizationConfiguration";