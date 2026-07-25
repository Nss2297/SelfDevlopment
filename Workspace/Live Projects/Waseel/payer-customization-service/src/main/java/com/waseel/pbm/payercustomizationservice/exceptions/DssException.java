package com.waseel.pbm.payercustomizationservice.exceptions;


import com.waseel.pbm.payercustomizationservice.model.DssResponse;

public class DssException extends Exception {

    private static final long serialVersionUID = 1L;
    private DssResponse dssInvalidResponse;

    public DssException() {
        super();
    }

    public DssException(DssResponse dssInvalidResponse) {
        this.dssInvalidResponse = dssInvalidResponse;
    }

    public DssException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public DssException(String message, Throwable cause) {
        super(message, cause);
    }

    public DssException(String message) {
        super(message);
    }

    public DssException(Throwable cause) {
        super(cause);
    }

    public DssResponse getDssInvalidResponse() {
        return dssInvalidResponse;
    }

    public void setDssInvalidResponse(DssResponse dssInvalidResponse) {
        this.dssInvalidResponse = dssInvalidResponse;
    }

}
