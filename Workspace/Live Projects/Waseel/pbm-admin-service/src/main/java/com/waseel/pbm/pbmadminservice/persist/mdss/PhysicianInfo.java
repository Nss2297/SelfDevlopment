package com.waseel.pbm.pbmadminservice.persist.mdss;

import javax.persistence.*;
import java.io.Serializable;

/**
 * PhyscisionInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "PhysicianInfo", schema = "MDSS")
public class PhysicianInfo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 9086210598710077907L;
    // Fields
    private PhysicianInfoId id;
    // Constructors

    /**
     * default constructor
     */
    public PhysicianInfo() {
    }

    /**
     * minimal constructor
     */
    public PhysicianInfo(PhysicianInfoId id) {
        this.id = id;
    }

    /**
     * full constructor
     */
    // Property accessors
    @EmbeddedId
    @AttributeOverride(name = "requestId", column = @Column(name = "RequestId", precision = 0))
    @AttributeOverride(name = "physicianId", column = @Column(name = "PhysicianId", length = 20, updatable = true, insertable = true))
    public PhysicianInfoId getId() {
        return this.id;
    }

    public void setId(PhysicianInfoId id) {
        this.id = id;
    }

}