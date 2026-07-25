--liquibase formatted sql

--changeset DSS:22

CREATE INDEX "idx_rid_isdeletedfp_serviceinfo" ON "MDSS"."ServiceInfo" ("RequestId","IsDeletedFromProvider");
CREATE INDEX "idx_rid_sc_isdeletedfp_serviceinfo" ON "MDSS"."ServiceInfo" ("RequestId","ServiceCode","IsDeletedFromProvider");
CREATE INDEX "idx_rid_sc_serviceinfo" ON "MDSS"."ServiceInfo" ("RequestId","ServiceCode");
CREATE INDEX "idx_rid_iscancelled_isdeletedfp" ON "MDSS"."ServiceInfo" ("RequestId","IsCancelled","IsDeletedFromProvider");
CREATE INDEX "idx_rid_isoverride_isdeletedfp" ON "MDSS"."ServiceInfo" ("RequestId","IsOverriden","IsDeletedFromProvider");