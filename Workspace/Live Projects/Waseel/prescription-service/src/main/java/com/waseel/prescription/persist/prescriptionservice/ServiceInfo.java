package com.waseel.prescription.persist.prescriptionservice;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "ServiceInfo", schema = "PRESCRIPTION_SERVICE")
public class ServiceInfo implements Serializable {

	private static final long serialVersionUID = 6452445764797112307L;

	@Id
	@GeneratedValue(generator = "PsServiceInfoSeq")
	@SequenceGenerator(name = "PsServiceInfoSeq", sequenceName = "PS_ServiceInfo_Seq", allocationSize = 0, initialValue = 1)
	@Column(name = "ID", length = 10, nullable = false)
	private long id;

	@Column(name = "DrugCode", length = 50)
	private String drugCode;

	@Column(name = "UnitType", length = 30, nullable = false)
	private String unitType;

	@Column(name = "UnitPrice", precision = 0)
	private Double unitPrice;

	@Column(name = "Quantity", nullable = false)
	private BigDecimal quantity;

	@Column(name = "RequestedAmount")
	private BigDecimal requestedAmount;

	@Column(name = "OrderingClinician", length = 20)
	private String orderingClinician;

	@Column(name = "ServiceStartDate", nullable = false)
	private Date serviceStartDate;

	@Column(name = "ServiceEndDate")
	private Date serviceEndDate;

	@Column(name = "Duration")
	private long duration;

	@Column(name = "Frequency", length = 30)
	private String frequency;

	@Column(name = "FrequencyOthersDescription", length = 200)
	private String frequencyOthersDescription;

	@Column(name = "RequestID", length = 100, updatable = false)
	private String requestId;

	@Column(name = "IsDeleted", columnDefinition = "CHAR(1) default ('0')")
	private boolean isDeleted = false;

	@Column(name = "UseUnitType")
	private String useUnitType;

	@Column(name = "UseUnitValue")
	private Double useUnitValue;

	@Column(name = "SCIENTIFIC_CODE", length = 64)
	private String scientificCode;

	@Column(name = "DrugListId")
	private Long drugListId;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RequestID", referencedColumnName = "RequestID", insertable = false, updatable = false)
	@JoinColumn(name = "ID", referencedColumnName = "ServiceID", insertable = false, updatable = false)
	private ServiceResponseInfo serviceResponseInfo;

	public ServiceResponseInfo getServiceResponseInfo() {
		return serviceResponseInfo;
	}

	public void setServiceResponseInfo(ServiceResponseInfo serviceResponseInfo) {
		this.serviceResponseInfo = serviceResponseInfo;
	}

	public String getUseUnitType() {
		return useUnitType;
	}

	public void setUseUnitType(String useUnitType) {
		this.useUnitType = useUnitType;
	}

	public Double getUseUnitValue() {
		return useUnitValue;
	}

	public void setUseUnitValue(Double useUnitValue) {
		this.useUnitValue = useUnitValue;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDrugCode() {
		return drugCode;
	}

	public void setDrugCode(String drugCode) {
		this.drugCode = drugCode;
	}

	public String getUnitType() {
		return unitType;
	}

	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getRequestedAmount() {
		return requestedAmount;
	}

	public void setRequestedAmount(BigDecimal requestedAmount) {
		this.requestedAmount = requestedAmount;
	}

	public String getOrderingClinician() {
		return orderingClinician;
	}

	public void setOrderingClinician(String orderingClinician) {
		this.orderingClinician = orderingClinician;
	}

	public Date getServiceStartDate() {
		return serviceStartDate;
	}

	public void setServiceStartDate(Date serviceStartDate) {
		this.serviceStartDate = serviceStartDate;
	}

	public Date getServiceEndDate() {
		return serviceEndDate;
	}

	public void setServiceEndDate(Date serviceEndDate) {
		this.serviceEndDate = serviceEndDate;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getFrequencyOthersDescription() {
		return frequencyOthersDescription;
	}

	public void setFrequencyOthersDescription(String frequencyOthersDescription) {
		this.frequencyOthersDescription = frequencyOthersDescription;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getScientificCode() {
		return scientificCode;
	}

	public void setScientificCode(String scientificCode) {
		this.scientificCode = scientificCode;
	}

	public ServiceInfo() {
		super();
	}

	public ServiceInfo(String drugCode, String unitType, Double unitPrice, BigDecimal quantity,
			BigDecimal requestedAmount, String orderingClinician, Date serviceStartDate, Date serviceEndDate,
			long duration, String frequency, String frequencyOthersDescription, String requestId) {
		super();
		this.drugCode = drugCode;
		this.unitType = unitType;
		this.unitPrice = unitPrice;
		this.quantity = quantity;
		this.requestedAmount = requestedAmount;
		this.orderingClinician = orderingClinician;
		this.serviceStartDate = serviceStartDate;
		this.serviceEndDate = serviceEndDate;
		this.duration = duration;
		this.frequency = frequency;
		this.frequencyOthersDescription = frequencyOthersDescription;
		this.requestId = requestId;
	}

	public ServiceInfo(String drugCode, String unitType, Double unitPrice, BigDecimal quantity,
			String orderingClinician, Date serviceStartDate, Date serviceEndDate, long duration, String frequency,
			String frequencyOthersDescription, String requestId) {
		super();
		this.drugCode = drugCode;
		this.unitType = unitType;
		this.unitPrice = unitPrice;
		this.quantity = quantity;
		this.orderingClinician = orderingClinician;
		this.serviceStartDate = serviceStartDate;
		this.serviceEndDate = serviceEndDate;
		this.duration = duration;
		this.frequency = frequency;
		this.frequencyOthersDescription = frequencyOthersDescription;
		this.requestId = requestId;
	}

	public ServiceInfo(long id, String drugCode, String unitType, Double unitPrice, BigDecimal quantity,
			BigDecimal requestedAmount, String orderingClinician, Date serviceStartDate, Date serviceEndDate,
			long duration, String frequency, String frequencyOthersDescription, String requestId) {
		super();
		this.id = id;
		this.drugCode = drugCode;
		this.unitType = unitType;
		this.unitPrice = unitPrice;
		this.quantity = quantity;
		this.requestedAmount = requestedAmount;
		this.orderingClinician = orderingClinician;
		this.serviceStartDate = serviceStartDate;
		this.serviceEndDate = serviceEndDate;
		this.duration = duration;
		this.frequency = frequency;
		this.frequencyOthersDescription = frequencyOthersDescription;
		this.requestId = requestId;
	}

	public ServiceInfo(long id, String unitType, Double unitPrice, BigDecimal quantity, BigDecimal requestedAmount,
			String orderingClinician, Date serviceStartDate, Date serviceEndDate, long duration, String frequency,
			String frequencyOthersDescription, String requestId, String scientificCode) {
		super();
		this.id = id;
		this.scientificCode = scientificCode;
		this.unitType = unitType;
		this.unitPrice = unitPrice;
		this.quantity = quantity;
		this.requestedAmount = requestedAmount;
		this.orderingClinician = orderingClinician;
		this.serviceStartDate = serviceStartDate;
		this.serviceEndDate = serviceEndDate;
		this.duration = duration;
		this.frequency = frequency;
		this.frequencyOthersDescription = frequencyOthersDescription;
		this.requestId = requestId;
	}

	public ServiceInfo(Long id, String drugCode, Long duration, String frequency, String frequencyOthersDescription,
			BigDecimal quantity, Double unitPrice, String unitType, Double useUnitValue, String orderingClinician,
			Date serviceStartDate, Date serviceEndDate, String scientificCode) {
		this.id = id;
		this.drugCode = drugCode;
		this.duration = duration;
		this.frequency = frequency;
		this.frequencyOthersDescription = frequencyOthersDescription;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
		this.unitType = unitType;
		this.useUnitValue = useUnitValue;
		this.orderingClinician = orderingClinician;
		this.serviceStartDate = serviceStartDate;
		this.serviceEndDate = serviceEndDate;
		this.scientificCode = scientificCode;
	}

	public Long getDrugListId() {
		return drugListId;
	}

	public void setDrugListId(Long drugListId) {
		this.drugListId = drugListId;
	}

	public ServiceInfo(long id, String drugCode, String unitType, Double unitPrice, BigDecimal quantity,
			BigDecimal requestedAmount, String orderingClinician, Date serviceStartDate, Date serviceEndDate,
			long duration, String frequency, String frequencyOthersDescription, String requestId, boolean isDeleted,
			String useUnitType, Double useUnitValue, String scientificCode) {
		super();
		this.id = id;
		this.drugCode = drugCode;
		this.unitType = unitType;
		this.unitPrice = unitPrice;
		this.quantity = quantity;
		this.requestedAmount = requestedAmount;
		this.orderingClinician = orderingClinician;
		this.serviceStartDate = serviceStartDate;
		this.serviceEndDate = serviceEndDate;
		this.duration = duration;
		this.frequency = frequency;
		this.frequencyOthersDescription = frequencyOthersDescription;
		this.requestId = requestId;
		this.isDeleted = isDeleted;
		this.useUnitType = useUnitType;
		this.useUnitValue = useUnitValue;
		this.scientificCode = scientificCode;
	}
}
