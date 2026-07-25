package com.waseel.pbm.rtsservice.exceptions;

import com.waseel.pbm.rtsservice.model.RTSResponse;

public class RTSException extends Exception{

    private static final long serialVersionUID = 1L;
    private RTSResponse rtsInvalidResponse;
    
    public RTSException() {
        super();
    }

    public RTSException(RTSResponse dssInvalidResponse) {
    	this.rtsInvalidResponse = dssInvalidResponse;
	}

	public RTSException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public RTSException(String message, Throwable cause) {
        super(message, cause);
    }

    public RTSException(String message) {
        super(message);
    }

    public RTSException(Throwable cause) {
        super(cause);
    }

	public RTSResponse getRtsInvalidResponse() {
		return rtsInvalidResponse;
	}

	public void setRtsInvalidResponse(RTSResponse rtsInvalidResponse) {
		this.rtsInvalidResponse = rtsInvalidResponse;
	}

    
    
}
