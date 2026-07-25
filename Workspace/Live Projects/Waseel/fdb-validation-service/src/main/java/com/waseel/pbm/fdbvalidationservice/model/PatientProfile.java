package com.waseel.pbm.fdbvalidationservice.model;

import com.fdb.mkfi.screening.FDBProfile;

public class PatientProfile {

	private FDBProfile fdbProfile;
	private String patientGender;

	public PatientProfile(FDBProfile fdbProfile, String patientGender) {
		super();
		this.fdbProfile = fdbProfile;
		this.patientGender = patientGender;
	}

	public FDBProfile getFdbProfile() {
		return fdbProfile;
	}

	public void setFdbProfile(FDBProfile fdbProfile) {
		this.fdbProfile = fdbProfile;
	}

	public String getPatientGender() {
		return patientGender;
	}

	public void setPatientGender(String patientGender) {
		this.patientGender = patientGender;
	}

}
