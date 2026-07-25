package com.waseel.pbm.rtsservice.persist.mdss;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * CommonRejectionReason entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "CommonRejectionReason", schema = "MDSS")
public class CommonRejectionReason implements java.io.Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 7575318728735156041L;
    @Id
    @Column(name = "RejectionCode", unique = true, nullable = false, length = 30)
    private String rejectionCode;
    @Column(name = "RejectionReason", length = 200)
    private String rejectionReason;
	public String getRejectionCode() {
		return rejectionCode;
	}
	public void setRejectionCode(String rejectionCode) {
		this.rejectionCode = rejectionCode;
	}
	public String getRejectionReason() {
		return rejectionReason;
	}
	public void setRejectionReason(String rejectionReason) {
		this.rejectionReason = rejectionReason;
	}
    
    
}