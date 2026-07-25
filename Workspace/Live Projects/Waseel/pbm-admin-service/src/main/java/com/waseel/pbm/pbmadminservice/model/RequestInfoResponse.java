package com.waseel.pbm.pbmadminservice.model;

public class RequestInfoResponse {

    private DssResponse dssResponse;
    private DssRequest dssRequest;

    public DssResponse getDssResponse() {
        return dssResponse;
    }

    public void setDssResponse(DssResponse dssResponse) {
        this.dssResponse = dssResponse;
    }

    public DssRequest getDssRequest() {
        return dssRequest;
    }

    public void setDssRequest(DssRequest dssRequest) {
        this.dssRequest = dssRequest;
    }
}
