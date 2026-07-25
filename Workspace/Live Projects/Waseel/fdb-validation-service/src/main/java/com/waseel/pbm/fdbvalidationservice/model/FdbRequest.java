package com.waseel.pbm.fdbvalidationservice.model;

import com.fdb.mkfi.screening.FDBProfile;

import java.util.List;

public class FdbRequest {

    private String requestId;
    private PatientProfile patientProfile;
    private List<FdbDrugList> drugList;
    private List<String> diagnosisCodes;
    private FDBProfile precautionProfile;
    private FDBProfile nonPrecautionProfile;

    public FdbRequest() {
        super();
    }

    public FdbRequest(String requestId, PatientProfile patientProfile, List<FdbDrugList> drugList,
                      List<String> diagnosisCodes) {
        super();
        this.requestId = requestId;
        this.patientProfile = patientProfile;
        this.drugList = drugList;
        this.diagnosisCodes = diagnosisCodes;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public PatientProfile getPatientProfile() {
        return patientProfile;
    }

    public void setPatientProfile(PatientProfile patientProfile) {
        this.patientProfile = patientProfile;
    }

    public List<FdbDrugList> getDrugList() {
        return drugList;
    }

    public void setDrugList(List<FdbDrugList> drugList) {
        this.drugList = drugList;
    }

    public List<String> getDiagnosisCodes() {
        return diagnosisCodes;
    }

    public void setDiagnosisCodes(List<String> diagnosisCodes) {
        this.diagnosisCodes = diagnosisCodes;
    }

    public FDBProfile getPrecautionProfile() {
        return precautionProfile;
    }

    public void setPrecautionProfile(FDBProfile precautionProfile) {
        this.precautionProfile = precautionProfile;
    }

    public FDBProfile getNonPrecautionProfile() {
        return nonPrecautionProfile;
    }

    public void setNonPrecautionProfile(FDBProfile nonPrecautionProfile) {
        this.nonPrecautionProfile = nonPrecautionProfile;
    }
}
