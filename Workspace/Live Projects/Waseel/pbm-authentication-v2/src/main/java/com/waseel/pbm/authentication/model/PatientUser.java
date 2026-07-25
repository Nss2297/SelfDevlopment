package com.waseel.pbm.authentication.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public class PatientUser extends org.springframework.security.core.userdetails.User {

    private String patientId;

    public PatientUser(String username, Collection<? extends GrantedAuthority> authorities) {
        super(username, "", authorities);
        this.patientId = username;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public Boolean getActive() {
		return true;
	}

}
