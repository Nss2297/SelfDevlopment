package com.waseel.pbm.authentication.model;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class SystemUser extends User {

	private static final long serialVersionUID = 3272447190168005281L;
	
	private String nameEn;
    private String nameAr;
    private Boolean active;

    public SystemUser(String payerId, String nameEn, String nameAr) {
        super(payerId, "", List.of(new SimpleGrantedAuthority(payerId)));

        this.nameEn = nameEn;
        this.nameAr = nameAr;
        this.active = true;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getNameAr() {
        return nameAr;
    }

    public void setNameAr(String nameAr) {
        this.nameAr = nameAr;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

}
