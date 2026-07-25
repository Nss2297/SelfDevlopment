--liquibase formatted sql

--changeset Payer-Customization-Service:048

ALTER TABLE CUSTOMIZATION_REQUESTS  MODIFY (LAST_UPDATED_DATE DEFAULT SYSDATE );