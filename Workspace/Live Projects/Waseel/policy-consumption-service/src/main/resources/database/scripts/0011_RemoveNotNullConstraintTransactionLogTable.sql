--liquibase formatted sql

--changeset Policy Consumption Service:0011

ALTER TABLE TRANSACTION_LOG MODIFY (SENDING_RESPONSE_DATE_TIME NULL);
