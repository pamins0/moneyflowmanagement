package com.emorphis.cashmanagement.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The persistent class for the branch_management_audit_trail database table.
 * 
 */
@Entity
@Table(name = "branch_management_audit_trail")
@NamedQuery(name = "BranchManagementAuditTrail.findAll", query = "SELECT b FROM BranchManagementAuditTrail b")
public class BranchManagementAuditTrail implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "MySequence", sequenceName = "BRANCH_MNG_AUDIT_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="MySequence")
	private Integer id;
	
	/*@ManyToOne
	@JoinColumn(name="branch_management_id")
	@JsonIgnore
	private BranchManagement branchManagementId;
	*/

	@Column(name="branch_management_id")
	private String branchManagementId;
	
	@Column(name = "chest")
	private byte chest;

	@Column(name = "abbreviation")
	@NotBlank(message = "Required")
	@NotNull
	private String abbreviation;

//	@Lob
	@Column(name = "branch_add1")
	private String branchAdd1;

//	@Lob we remove this @Lob because it creates error in oracle database.......
	@Column(name = "branch_add2")
	private String branchAdd2;

	@Column(name = "branch_cashlimit")
	@NotNull(message = "Required")
	@DecimalMax("999999999999999.0")
	@DecimalMin("0.0")
	private Double branchCashlimit;

	@Column(name = "percentage")
	@Range(max = 99)
	private Double percentage;

	@Column(name = "min_threshold_amount")
	private Double minThresholdAmount;

	@Column(name = "max_threshold_amount")
	private Double maxThresholdAmount;

	@Column(name = "branch_zip")
	@NotNull(message = "Required")
	private Integer branchZip;

	@Column(name = "branch_status")
	private byte branchStatus;

	@Column(name = "branch_code")
	@NotBlank(message = "Required")
	@NotNull
	private String branchCode;

	@Column(name = "branch_contact_no")
	@NotBlank(message = "Required")
	@NotNull
	private String branchContactNo;
	
	@Column(name = "branch_detail")
	private String branchDetail;

	@Column(name = "branch_email")
	@NotNull
	@NotBlank(message = "Required")
	private String branchEmail;

	@Column(name = "branch_latitude")
	private String branchLatitude;

	@Column(name = "branch_location")
	private String branchLocation;

	@Column(name = "branch_longitude")
	private String branchLongitude;

	@Column(name = "branch_name")
	@NotBlank(message = "Required")
	@NotNull
	private String branchName;

	@Column(name = "created_by")
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_time")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date createdTime;

	private byte deleted;

	private byte isgroup;

	@Column(name = "modified_by")
	private String modifiedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_time")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date modifiedTime;

	@Column(name = "branch_type")
	private byte branchType;

	@Column(name = "request_approval")
	private byte requestApprovalType;

	@Column(name = "branch_approvers")
	private int branchApprovers;

	@Column(name = "vicinity")
	private BigDecimal vicinity;

	@Column(name = "branch_ifsc_code")
	@NotBlank(message = "Required")
	@NotNull
	private String branchIfscCode;

	
	// bi-directional many-to-one association to OrgManagement
	@ManyToOne
	@JoinColumn(name = "om_id")
	@JsonIgnore
	private OrgManagement orgManagement;

	// bi-directional many-to-one association to HierarchyControl
	@ManyToOne
	@JoinColumn(name = "hcid")
	@JsonIgnore
	private HierarchyControl hierarchyControl;

	// bi-directional many-to-one association to Place_cash_Request
	@OneToMany(mappedBy = "branchManagementRequestedFrom",cascade = CascadeType.ALL)
	@JsonIgnore
	private List<PlaceCashRequest> placeCashRequestsFrom;

	// bi-directional many-to-one association to Place_cash_Request
	@OneToMany(mappedBy = "branchManagementRequestedTo",cascade = CascadeType.ALL)
	@JsonIgnore
	private List<PlaceCashRequest> placeCashRequestsTo;

	@OneToMany(mappedBy = "branchManagementRequestedFrom",cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<PlaceCashRequestAuditTrial> placeCashRequestAuditTrialsFrom;

	// bi-directional many-to-one association to Place_cash_Request_audit_trial
	@OneToMany(mappedBy = "branchManagementRequestedTo",cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<PlaceCashRequestAuditTrial> placeCashRequestAuditTrialsTo;

	@OneToMany(mappedBy = "branchManagementRequestedFrom",cascade = CascadeType.ALL)
	@JsonIgnore
	private List<PlaceCashRequestSwap> placeCashRequestsSwapFrom;

	// bi-directional many-to-one association to Place_cash_Request
	@OneToMany(mappedBy = "branchManagementRequestedTo",cascade = CascadeType.ALL)
	@JsonIgnore
	private List<PlaceCashRequestSwap> placeCashRequestsSwapTo;

	@OneToMany(mappedBy = "branchManagementRequestedFrom",cascade = CascadeType.ALL)
	@JsonIgnore
	private List<PlaceCashRequestSwapAuditTrial> placeCashRequestSwapAuditTrialsFrom;

	// bi-directional many-to-one association to
	// Place_cash_Request_swap_audit_trial
	@OneToMany(mappedBy = "branchManagementRequestedTo",cascade = CascadeType.ALL)
	@JsonIgnore
	private List<PlaceCashRequestSwapAuditTrial> placeCashRequestSwapAuditTrialsTo;

	/*
	 * @OneToMany(mappedBy = "branchManagement")
	 * 
	 * @JsonIgnore private List<BranchManagementAuditTrail>
	 * branchManagementAuditTrails;
	 */

	// bi-directional many-to-one association to BranchPropertyField
	@OneToMany(mappedBy = "branchManagement",cascade = CascadeType.ALL)
	@JsonIgnore
	private List<BranchPropertyField> branchPropertyFields;

	// bi-directional many-to-one association to User
	@OneToMany(mappedBy = "branchManagement",cascade = CascadeType.ALL)
	@Filter(name = "test")
	@JsonIgnore
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<User> users;

	// bi-directional many-to-one association to UserBranch
	@OneToMany(mappedBy = "branchManagement",cascade = CascadeType.ALL)
	@JsonIgnore
	private List<UserBranch> userBranches;

	// bi-directional many-to-one association to BranchParameterStatus
	@OneToMany(mappedBy = "branchManagement", cascade = CascadeType.ALL)
	@JsonIgnore
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<BranchParameterStatus> branchParameterStatuses;

	// bi-directional many-to-one association to BranchClosedGroup
	@OneToMany(mappedBy = "parentBranch", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<BranchClosedGroup> branchClosedGroups;

	// bi-directional many-to-one association to BranchClosedGroup
	@OneToMany(mappedBy = "closedGroupBranch", cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<BranchClosedGroup> branchClosedGroups2;

	@OneToMany(mappedBy = "branchManagement", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<DashboardFinalBid> dashboardFinalBids;

	@OneToMany(mappedBy = "branchManagement",cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<BranchGroup> branchGroups;
	
	@ManyToOne
	@JoinColumn(name="city_id")
	@JsonIgnore
	private City city;
	
	@Column(name="ip")
	private String ip;
	
	@Column(name="operation")
	private String operation;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "branch_control")
	@JsonIgnore
	@Filter(name = "test")
	private Set<BranchManagement> branchControlalias = new HashSet<BranchManagement>();

	// @Transient
	@Column(name = "branch_control", insertable = true, updatable = true)
	private String branchControl;


	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	/*public BranchManagement getBranchManagementId() {
		return branchManagementId;
	}

	public void setBranchManagementId(BranchManagement branchManagementId) {
		this.branchManagementId = branchManagementId;
	}*/

	public byte getChest() {
		return chest;
	}

	public void setChest(byte chest) {
		this.chest = chest;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public String getBranchAdd1() {
		return branchAdd1;
	}

	public void setBranchAdd1(String branchAdd1) {
		this.branchAdd1 = branchAdd1;
	}

	public String getBranchAdd2() {
		return branchAdd2;
	}

	public void setBranchAdd2(String branchAdd2) {
		this.branchAdd2 = branchAdd2;
	}

	public Double getBranchCashlimit() {
		return branchCashlimit;
	}

	public void setBranchCashlimit(Double branchCashlimit) {
		this.branchCashlimit = branchCashlimit;
	}

	public Double getPercentage() {
		return percentage;
	}

	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}

	public Double getMinThresholdAmount() {
		return minThresholdAmount;
	}

	public void setMinThresholdAmount(Double minThresholdAmount) {
		this.minThresholdAmount = minThresholdAmount;
	}

	public Double getMaxThresholdAmount() {
		return maxThresholdAmount;
	}

	public void setMaxThresholdAmount(Double maxThresholdAmount) {
		this.maxThresholdAmount = maxThresholdAmount;
	}

	public Integer getBranchZip() {
		return branchZip;
	}

	public void setBranchZip(Integer branchZip) {
		this.branchZip = branchZip;
	}

	public byte getBranchStatus() {
		return branchStatus;
	}

	public void setBranchStatus(byte branchStatus) {
		this.branchStatus = branchStatus;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getBranchContactNo() {
		return branchContactNo;
	}

	public void setBranchContactNo(String branchContactNo) {
		this.branchContactNo = branchContactNo;
	}

	public String getBranchDetail() {
		return branchDetail;
	}

	public void setBranchDetail(String branchDetail) {
		this.branchDetail = branchDetail;
	}

	public String getBranchEmail() {
		return branchEmail;
	}

	public void setBranchEmail(String branchEmail) {
		this.branchEmail = branchEmail;
	}

	public String getBranchLatitude() {
		return branchLatitude;
	}

	public void setBranchLatitude(String branchLatitude) {
		this.branchLatitude = branchLatitude;
	}

	public String getBranchLocation() {
		return branchLocation;
	}

	public void setBranchLocation(String branchLocation) {
		this.branchLocation = branchLocation;
	}

	public String getBranchLongitude() {
		return branchLongitude;
	}

	public void setBranchLongitude(String branchLongitude) {
		this.branchLongitude = branchLongitude;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
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

	public byte getIsgroup() {
		return isgroup;
	}

	public void setIsgroup(byte isgroup) {
		this.isgroup = isgroup;
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

	public byte getBranchType() {
		return branchType;
	}

	public void setBranchType(byte branchType) {
		this.branchType = branchType;
	}

	public byte getRequestApprovalType() {
		return requestApprovalType;
	}

	public void setRequestApprovalType(byte requestApprovalType) {
		this.requestApprovalType = requestApprovalType;
	}

	public int getBranchApprovers() {
		return branchApprovers;
	}

	public void setBranchApprovers(int branchApprovers) {
		this.branchApprovers = branchApprovers;
	}

	public BigDecimal getVicinity() {
		return vicinity;
	}

	public void setVicinity(BigDecimal vicinity) {
		this.vicinity = vicinity;
	}

	public String getBranchIfscCode() {
		return branchIfscCode;
	}

	public void setBranchIfscCode(String branchIfscCode) {
		this.branchIfscCode = branchIfscCode;
	}


	public OrgManagement getOrgManagement() {
		return orgManagement;
	}

	public void setOrgManagement(OrgManagement orgManagement) {
		this.orgManagement = orgManagement;
	}

	public HierarchyControl getHierarchyControl() {
		return hierarchyControl;
	}

	public void setHierarchyControl(HierarchyControl hierarchyControl) {
		this.hierarchyControl = hierarchyControl;
	}

	public List<PlaceCashRequest> getPlaceCashRequestsFrom() {
		return placeCashRequestsFrom;
	}

	public void setPlaceCashRequestsFrom(List<PlaceCashRequest> placeCashRequestsFrom) {
		this.placeCashRequestsFrom = placeCashRequestsFrom;
	}

	public List<PlaceCashRequest> getPlaceCashRequestsTo() {
		return placeCashRequestsTo;
	}

	public void setPlaceCashRequestsTo(List<PlaceCashRequest> placeCashRequestsTo) {
		this.placeCashRequestsTo = placeCashRequestsTo;
	}

	public Set<PlaceCashRequestAuditTrial> getPlaceCashRequestAuditTrialsFrom() {
		return placeCashRequestAuditTrialsFrom;
	}

	public void setPlaceCashRequestAuditTrialsFrom(Set<PlaceCashRequestAuditTrial> placeCashRequestAuditTrialsFrom) {
		this.placeCashRequestAuditTrialsFrom = placeCashRequestAuditTrialsFrom;
	}

	public Set<PlaceCashRequestAuditTrial> getPlaceCashRequestAuditTrialsTo() {
		return placeCashRequestAuditTrialsTo;
	}

	public void setPlaceCashRequestAuditTrialsTo(Set<PlaceCashRequestAuditTrial> placeCashRequestAuditTrialsTo) {
		this.placeCashRequestAuditTrialsTo = placeCashRequestAuditTrialsTo;
	}

	public List<PlaceCashRequestSwap> getPlaceCashRequestsSwapFrom() {
		return placeCashRequestsSwapFrom;
	}

	public void setPlaceCashRequestsSwapFrom(List<PlaceCashRequestSwap> placeCashRequestsSwapFrom) {
		this.placeCashRequestsSwapFrom = placeCashRequestsSwapFrom;
	}

	public List<PlaceCashRequestSwap> getPlaceCashRequestsSwapTo() {
		return placeCashRequestsSwapTo;
	}

	public void setPlaceCashRequestsSwapTo(List<PlaceCashRequestSwap> placeCashRequestsSwapTo) {
		this.placeCashRequestsSwapTo = placeCashRequestsSwapTo;
	}

	public List<PlaceCashRequestSwapAuditTrial> getPlaceCashRequestSwapAuditTrialsFrom() {
		return placeCashRequestSwapAuditTrialsFrom;
	}

	public void setPlaceCashRequestSwapAuditTrialsFrom(
			List<PlaceCashRequestSwapAuditTrial> placeCashRequestSwapAuditTrialsFrom) {
		this.placeCashRequestSwapAuditTrialsFrom = placeCashRequestSwapAuditTrialsFrom;
	}

	public List<PlaceCashRequestSwapAuditTrial> getPlaceCashRequestSwapAuditTrialsTo() {
		return placeCashRequestSwapAuditTrialsTo;
	}

	public void setPlaceCashRequestSwapAuditTrialsTo(
			List<PlaceCashRequestSwapAuditTrial> placeCashRequestSwapAuditTrialsTo) {
		this.placeCashRequestSwapAuditTrialsTo = placeCashRequestSwapAuditTrialsTo;
	}

	public List<BranchPropertyField> getBranchPropertyFields() {
		return branchPropertyFields;
	}

	public void setBranchPropertyFields(List<BranchPropertyField> branchPropertyFields) {
		this.branchPropertyFields = branchPropertyFields;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<UserBranch> getUserBranches() {
		return userBranches;
	}

	public void setUserBranches(List<UserBranch> userBranches) {
		this.userBranches = userBranches;
	}
	
	public List<BranchParameterStatus> getBranchParameterStatuses() {
		return branchParameterStatuses;
	}

	public void setBranchParameterStatuses(List<BranchParameterStatus> branchParameterStatuses) {
		this.branchParameterStatuses = branchParameterStatuses;
	}

	public Set<BranchClosedGroup> getBranchClosedGroups() {
		return branchClosedGroups;
	}

	public void setBranchClosedGroups(Set<BranchClosedGroup> branchClosedGroups) {
		this.branchClosedGroups = branchClosedGroups;
	}

	public Set<BranchClosedGroup> getBranchClosedGroups2() {
		return branchClosedGroups2;
	}

	public void setBranchClosedGroups2(Set<BranchClosedGroup> branchClosedGroups2) {
		this.branchClosedGroups2 = branchClosedGroups2;
	}

	public Set<DashboardFinalBid> getDashboardFinalBids() {
		return dashboardFinalBids;
	}

	public void setDashboardFinalBids(Set<DashboardFinalBid> dashboardFinalBids) {
		this.dashboardFinalBids = dashboardFinalBids;
	}

	public Set<BranchGroup> getBranchGroups() {
		return branchGroups;
	}

	public void setBranchGroups(Set<BranchGroup> branchGroups) {
		this.branchGroups = branchGroups;
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

	public Set<BranchManagement> getBranchControlalias() {
		return branchControlalias;
	}

	public void setBranchControlalias(Set<BranchManagement> branchControlalias) {
		this.branchControlalias = branchControlalias;
	}

	public String getBranchControl() {
		return branchControl;
	}

	public void setBranchControl(String branchControl) {
		this.branchControl = branchControl;
	}

	public String getBranchManagementId() {
		return branchManagementId;
	}

	public void setBranchManagementId(String branchManagementId) {
		this.branchManagementId = branchManagementId;
	}
	
	
}
