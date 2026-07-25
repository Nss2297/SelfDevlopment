package com.waseel.pbm.rtsservice.persist.mdss;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * MemberInfoId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class MemberInfoId implements java.io.Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 3380560839861842188L;
    @Column(name = "RequestId", precision = 0)
    private String requestId;
    // Constructors

//    /**
//     * default constructor
//     */
//    public MemberInfoId() {
//    }

//    public MemberInfoId(String requestId) {
//        this.requestId = requestId;
//    }
    // Property accessors

    public String getRequestId() {
  		return requestId;
  	}

  	public void setRequestId(String requestId) {
  		this.requestId = requestId;
  	}


    public boolean equals(Object other) {
        if ((this == other))
            return true;
        if ((other == null))
            return false;
        if (!(other instanceof MemberInfoId))
            return false;
        MemberInfoId castOther = (MemberInfoId) other;

        return ((this.getRequestId() == castOther.getRequestId()) || (this.getRequestId() != null
                && castOther.getRequestId() != null && this.getRequestId().equals(castOther.getRequestId())));
    }
  

	public int hashCode() {
        int result = 17;
        result = 37 * result + (getRequestId() == null ? 0 : this.getRequestId().hashCode());
        return result;
    }

}