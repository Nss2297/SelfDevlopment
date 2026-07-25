package com.waseel.prescription.model.common;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PhysicianModel {

    private String physicianName;
    private String physicianLicenseNumber;
    private String physicianCategory;
	private String physicianSpeciality;
	
    public String getPhysicianSpeciality() {
		return physicianSpeciality;
	}

	public void setPhysicianSpeciality(String physicianSpeciality) {
		this.physicianSpeciality = physicianSpeciality;
	}

	public String getPhysicianCategory() {
		return physicianCategory;
	}

	public void setPhysicianCategory(String physicianCategory) {
		this.physicianCategory = physicianCategory;
	}

	public String getPhysicianName() {
        return physicianName;
    }

    public void setPhysicianName(String physicianName) {
        this.physicianName = physicianName;
    }

    public String getPhysicianLicenseNumber() {
        return physicianLicenseNumber;
    }

    public void setPhysicianLicenseNumber(String physicianLicenseNumber) {
        this.physicianLicenseNumber = physicianLicenseNumber;
    }
}
