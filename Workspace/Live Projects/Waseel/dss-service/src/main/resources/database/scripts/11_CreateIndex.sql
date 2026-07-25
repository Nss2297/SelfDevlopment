--liquibase formatted sql

--changeset DSS:10

CREATE INDEX "idx_idfp_rid_sid_sc" ON "MDSS"."ServiceInfo" ("IsDeletedFromProvider","RequestId","ServiceId","ServiceCode");
CREATE INDEX "idx_rid_sid_status" ON "MDSS"."ServiceDecision" ("RequestId","ServiceId","Status");
CREATE INDEX "idx_rid_mid" ON "MDSS"."MemberInfo" ("RequestId","MemberId");
