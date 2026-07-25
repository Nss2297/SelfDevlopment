package com.waseel.prescription.persist.hira;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "`ICDDiagnosis`", schema = "HIRA")
public class ICDDiagnosis implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ICDDiagnosisCode", nullable = false)
    private String icdDiagnosisCode;

    @Column(name = "Description", nullable = false)
    private String description;

    public String getIcdDiagnosisCode() {
        return icdDiagnosisCode;
    }

    public void setIcdDiagnosisCode(String icdDiagnosisCode) {
        this.icdDiagnosisCode = icdDiagnosisCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
