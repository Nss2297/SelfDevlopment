package com.waseel.pbm.rtsservice.persist.mdss;

import java.sql.Timestamp;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * ServiceInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ServiceInfo", schema = "MDSS")

public class ServiceInfo implements java.io.Serializable {

    // Fields

    /**
     *
     */
    private static final long serialVersionUID = -7454920475823122526L;
    private ServiceInfoId id;
    private Timestamp serviceDate;
    private String serviceCode;
    private Double serviceQuantity;
    private Double serviceAmount;
    private Double daysOfSupply;
    private Character isDeletedFromProvider = '0';
    private Character isCancelled = '0';
    private Character isOverriden = '0';

    // Constructors

    /**
     * default constructor
     */
    public ServiceInfo() {
    }

    /**
     * minimal constructor
     */
    public ServiceInfo(ServiceInfoId id) {
        this.id = id;
    }

    /**
     * full constructor
     */
    public ServiceInfo(ServiceInfoId id, Timestamp serviceDate, String serviceCode, Double serviceQuantity,
                       Double serviceAmount, Double daysOfSupply, Character isDeletedFromProvider, Character isCancelled,
                       Character isOverriden) {
        super();
        this.id = id;
        this.serviceDate = serviceDate;
        this.serviceCode = serviceCode;
        this.serviceQuantity = serviceQuantity;
        this.serviceAmount = serviceAmount;
        this.daysOfSupply = daysOfSupply;
        this.isDeletedFromProvider = isDeletedFromProvider;
        this.isCancelled = isCancelled;
        this.isOverriden = isOverriden;
    }

    // Property accessors
    @EmbeddedId

    @AttributeOverrides({
            @AttributeOverride(name = "requestId", column = @Column(name = "RequestId", nullable = false, precision = 0)),
            @AttributeOverride(name = "serviceId", column = @Column(name = "ServiceId", nullable = false, precision = 0))})

    public ServiceInfoId getId() {
        return this.id;
    }

    public void setId(ServiceInfoId id) {
        this.id = id;
    }

    @Column(name = "ServiceDate", length = 7)

    public Timestamp getServiceDate() {
        return this.serviceDate;
    }

    public void setServiceDate(Timestamp serviceDate) {
        this.serviceDate = serviceDate;
    }

    @Column(name = "ServiceCode", length = 100)

    public String getServiceCode() {
        return this.serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    @Column(name = "ServiceQuantity", precision = 0)

    public Double getServiceQuantity() {
        return this.serviceQuantity;
    }

    public void setServiceQuantity(Double serviceQuantity) {
        this.serviceQuantity = serviceQuantity;
    }

    @Column(name = "ServiceAmount", length = 50)

    public Double getServiceAmount() {
        return this.serviceAmount;
    }

    public void setServiceAmount(Double serviceAmount) {
        this.serviceAmount = serviceAmount;
    }

    @Column(name = "DaysOfSupply", length = 100)

    public Double getDaysOfSupply() {
        return this.daysOfSupply;
    }

    public void setDaysOfSupply(Double daysOfSupply) {
        this.daysOfSupply = daysOfSupply;
    }

    @Column(name = "IsDeletedFromProvider", columnDefinition = "CHAR(1) default ('0')")
    public Character getIsDeletedFromProvider() {
        return isDeletedFromProvider;
    }

    public void setIsDeletedFromProvider(Character isDeletedFromProvider) {
        this.isDeletedFromProvider = isDeletedFromProvider;
    }

    @Column(name = "IsCancelled", columnDefinition = "CHAR(1) default ('0')")
    public Character getIsCancelled() {
        return isCancelled;
    }

    public void setIsCancelled(Character isCancelled) {
        this.isCancelled = isCancelled;
    }

    @Column(name = "IsOverriden", columnDefinition = "CHAR(1) default ('0')")
    public Character getIsOverriden() {
        return isOverriden;
    }

    public void setIsOverriden(Character isOverriden) {
        this.isOverriden = isOverriden;
    }

}