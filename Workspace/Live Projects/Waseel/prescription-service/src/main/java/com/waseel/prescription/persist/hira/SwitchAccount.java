package com.waseel.prescription.persist.hira;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "SwitchAccount", schema = "HIRA")
@Transactional
public class SwitchAccount implements java.io.Serializable {

    private static final long serialVersionUID = -364486428821096329L;

    @Id
    @Column(name = "SwitchAccountId")
    private BigDecimal switchAccountId;
    @Column(name = "Name")
    private String name;
    @Column(name = "ArabicName")
    private String arabicName;
    @Column(name = "Category")
    private String category;
    @Column(name = "Code")
    private String code;
    @Column(name = "PayerCategory", precision = 0)
    private Double payerCategory;
    @Column(name = "IsEnabled", precision = 0)
    private String isEnabled;

    public BigDecimal getSwitchAccountId() {
        return switchAccountId;
    }

    public void setSwitchAccountId(BigDecimal switchAccountId) {
        this.switchAccountId = switchAccountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArabicName() {
        return arabicName;
    }

    public void setArabicName(String arabicName) {
        this.arabicName = arabicName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getPayerCategory() {
        return this.payerCategory;
    }

    public void setPayerCategory(Double payerCategory) {
        this.payerCategory = payerCategory;
    }

    public String getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(String isEnabled) {
        this.isEnabled = isEnabled;
    }
}
