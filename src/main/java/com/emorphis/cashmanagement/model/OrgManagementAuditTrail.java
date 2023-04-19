package com.emorphis.cashmanagement.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The persistent class for the org_management_audit_trail database table.
 * 
 */
@Entity
@Table(name = "org_management_audit_trail")
@NamedQuery(name = "OrgManagementAuditTrail.findAll", query = "SELECT o FROM OrgManagementAuditTrail o")
public class OrgManagementAuditTrail implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "MySequence", sequenceName = "ORG_MANAGEMENT_AUDIT_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="MySequence")
	private Integer id;
	
	/*@ManyToOne
	@JoinColumn(name="org_management_id")
	@JsonIgnore
	private OrgManagement orgManagementId;*/
	

	@Column(name="org_management_id")
	private String orgManagementId;
	
	@Column(name = "name")
	@NotNull
	@NotBlank(message = "Required")
	private String name;

//	@Lob
	private String add1;

//	@Lob
	private String add2;

	@Column(name = "branch_control")
	private int branchControl;

	@Column(name = "centralised_control")
	private int centralisedControl;

	@Column(name = "contact_no")
	@NotEmpty(message = "Required")
	@NotNull
	private String contactNo;

	@Column(name = "created_by")
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_time")
	private Date createdTime;

	private byte deleted;

	@Column(name = "cms_approach", nullable = false)
	@NotNull
	private byte cmsApproach;

	private String detail;

	@NotNull
	@NotBlank(message = "Required")
	private String email;

	private String latitude;

	private String location;

	private String longitude;

	@Column(name = "modified_by")
	private String modifiedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_time")
	private Date modifiedTime;

	@Column(name = "org_level")
	@NotNull(message = "Required")
	@Range(min = 1, max = 10, message = "Minimum 1 and Maximum 10 level required")
	private Integer orgLevel;

	private String country;

	//private String state;

	//private String city;

	private BigInteger zip;

	@Column(name = "intra_bank")
	private boolean intraBank;

	@Column(name = "currency_chest")
	private boolean currencyChest;

	@Column(name = "max_time_intra_bank")
	private String maxTimeForIntraBank;

	@Column(name = "auto_approval_time")
	private String autoAprovalRequestTime;

	@Column(name = "vicinity")
	private BigDecimal vicinity;

	@Column(name = "start_time")
	private String start_time;

	@Column(name = "end_time")
	private String end_time;

	@Column(name = "interchange_time")
	private String interchange_time;

	@OneToMany(mappedBy = "orgManagement",cascade = CascadeType.ALL)
	@JsonIgnore
	private List<BranchManagement> branchManagements;

	@OneToMany(mappedBy = "orgManagement",cascade = CascadeType.ALL)
	@JsonIgnore
	private List<CashManagementAuditLog> cashManagementAuditLogs;

	@OneToMany(mappedBy = "orgManagement",cascade = CascadeType.ALL)
	@JsonIgnore
	//@Filter(name = "test")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<HierarchyControl> hierarchyControls;

	@ManyToOne
	@JoinColumn(name = "ot_id")
	@JsonIgnore
	private OrgType orgType;
	
	@ManyToOne
	@JoinColumn(name="city_id")
	@JsonIgnore
	private City city;
	
	@Column(name="ip")
	private String ip;
	
	@Column(name="operation")
	private String operation;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	/*public OrgManagement getOrgManagementId() {
		return orgManagementId;
	}

	public void setOrgManagementId(OrgManagement orgManagementId) {
		this.orgManagementId = orgManagementId;
	}*/

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAdd1() {
		return add1;
	}

	public void setAdd1(String add1) {
		this.add1 = add1;
	}

	public String getAdd2() {
		return add2;
	}

	public void setAdd2(String add2) {
		this.add2 = add2;
	}

	public int getBranchControl() {
		return branchControl;
	}

	public void setBranchControl(int branchControl) {
		this.branchControl = branchControl;
	}

	public int getCentralisedControl() {
		return centralisedControl;
	}

	public void setCentralisedControl(int centralisedControl) {
		this.centralisedControl = centralisedControl;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public byte getDeleted() {
		return deleted;
	}

	public void setDeleted(byte deleted) {
		this.deleted = deleted;
	}

	public byte getCmsApproach() {
		return cmsApproach;
	}

	public void setCmsApproach(byte cmsApproach) {
		this.cmsApproach = cmsApproach;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	public Integer getOrgLevel() {
		return orgLevel;
	}

	public void setOrgLevel(Integer orgLevel) {
		this.orgLevel = orgLevel;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public BigInteger getZip() {
		return zip;
	}

	public void setZip(BigInteger zip) {
		this.zip = zip;
	}

	public boolean isIntraBank() {
		return intraBank;
	}

	public void setIntraBank(boolean intraBank) {
		this.intraBank = intraBank;
	}

	public boolean isCurrencyChest() {
		return currencyChest;
	}

	public void setCurrencyChest(boolean currencyChest) {
		this.currencyChest = currencyChest;
	}

	public String getMaxTimeForIntraBank() {
		return maxTimeForIntraBank;
	}

	public void setMaxTimeForIntraBank(String maxTimeForIntraBank) {
		this.maxTimeForIntraBank = maxTimeForIntraBank;
	}

	public String getAutoAprovalRequestTime() {
		return autoAprovalRequestTime;
	}

	public void setAutoAprovalRequestTime(String autoAprovalRequestTime) {
		this.autoAprovalRequestTime = autoAprovalRequestTime;
	}

	public BigDecimal getVicinity() {
		return vicinity;
	}

	public void setVicinity(BigDecimal vicinity) {
		this.vicinity = vicinity;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public String getInterchange_time() {
		return interchange_time;
	}

	public void setInterchange_time(String interchange_time) {
		this.interchange_time = interchange_time;
	}

	public List<BranchManagement> getBranchManagements() {
		return branchManagements;
	}

	public void setBranchManagements(List<BranchManagement> branchManagements) {
		this.branchManagements = branchManagements;
	}

	public List<CashManagementAuditLog> getCashManagementAuditLogs() {
		return cashManagementAuditLogs;
	}

	public void setCashManagementAuditLogs(List<CashManagementAuditLog> cashManagementAuditLogs) {
		this.cashManagementAuditLogs = cashManagementAuditLogs;
	}

	public List<HierarchyControl> getHierarchyControls() {
		return hierarchyControls;
	}

	public void setHierarchyControls(List<HierarchyControl> hierarchyControls) {
		this.hierarchyControls = hierarchyControls;
	}

	public OrgType getOrgType() {
		return orgType;
	}

	public void setOrgType(OrgType orgType) {
		this.orgType = orgType;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getOrgManagementId() {
		return orgManagementId;
	}

	public void setOrgManagementId(String orgManagementId) {
		this.orgManagementId = orgManagementId;
	}
	
	
}
