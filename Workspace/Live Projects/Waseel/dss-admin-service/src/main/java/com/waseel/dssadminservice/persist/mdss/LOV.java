package com.waseel.dssadminservice.persist.mdss;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "LOV", schema = "MDSS")
public class LOV implements Serializable {

	private static final long serialVersionUID = -3716165429681638714L;

	@Id
	@GeneratedValue(generator = "LovSeq")
	@SequenceGenerator(name = "LovSeq", sequenceName = "LOV_SEQ", allocationSize = 0, initialValue = 1)
	@Column(name = "LOV_ID", unique = true, insertable = true, updatable = false)
	private Long lovId;

	@Column(name = "KEY")
	private String key;

	@Column(name = "VALUE")
	private String value;

	@Column(name = "LABEL_EN")
	private String labelEn;

	@Column(name = "LABEL_AR")
	private String labelAr;

	public Long getLovId() {
		return lovId;
	}

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

	public String getLabelEn() {
		return labelEn;
	}

	public String getLabelAr() {
		return labelAr;
	}

	public void setLovId(Long lovId) {
		this.lovId = lovId;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setLabelEn(String labelEn) {
		this.labelEn = labelEn;
	}

	public void setLabelAr(String labelAr) {
		this.labelAr = labelAr;
	}

	public LOV() {
		super();
	}

	public LOV(Long lovId, String key, String value, String labelEn, String labelAr) {
		super();
		this.lovId = lovId;
		this.key = key;
		this.value = value;
		this.labelEn = labelEn;
		this.labelAr = labelAr;
	}

}
