package com.emorphis.cashmanagement.model;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.FilterDef;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The persistent class for the user_management database table.
 * 
 */
@Entity
@FilterDef(name = "test", defaultCondition = "deleted=0")
@Table(name = "user_management")
@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
public class User extends CommonModel {

	@Id
	private String id;

	/*@OneToMany(mappedBy="userManagementId",cascade = CascadeType.ALL)
	@JsonIgnore
	private List<UserAuditTrail> userAuditTrails;*/
	
	@Column(name = "gender")
	private byte gender;

	@Column(name = "contact_no")
	@NotBlank(message = "Required")
	private String contactNo;

	@Column(name = "created_by")
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_time")
	private Date createdTime;

	private byte deleted;

	@ManyToOne
	@JoinColumn(name = "designation")
	private Designation designation;
	
	@ManyToOne
	@JoinColumn(name = "department")
	private Department department;
	
	@NotBlank(message = "Required")
	private String email;

	@Column(name = "modified_by")
	private String modifiedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_time")
	private Date modifiedTime;

	@DateTimeFormat(pattern = "dd-MM-yyyy")
	@Column(name = "date_of_birth")
	private Date dateOfBirth;

	@DateTimeFormat(pattern = "dd-MM-yyyy")
	@Column(name = "date_of_joining")
	private Date dateOfJoining;

	@DateTimeFormat(pattern = "dd-MM-yyyy")
	@Column(name = "posting_start_date")
	private Date postingStartDate;

	@DateTimeFormat(pattern = "dd-MM-yyyy")
	@Column(name = "posting_end_date")
	private Date postingEndDate;

	@Column(name = "approver")
	private byte approver;

	@Column(name = "epfo_no")
	private String epfo_no;

	@Column(name = "cash_limit")
	private Double cash_limit;

	@Column(name = "first_name")
	@NotBlank(message = "Required")
	private String firstName;

	@Column(name = "second_name")
	private String secondName;

	@Column(name = "last_name")
	@NotBlank(message = "Required")
	private String lastName;

	private byte status;

	@Column(name = "user_code")
	@NotBlank(message = "Required")
	private String userCode;

	@Column(name = "user_id")
	private String userId;

	@Column(name = "user_password")
	private String userPassword;

	@Transient
	private String confirmPassword;

	// bi-directional many-to-one association to BranchManagement
	@ManyToOne
	@JoinColumn(name = "b_id")
	@JsonIgnore
	private BranchManagement branchManagement;

	// bi-directional many-to-one association to UserBranch
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
	@JsonIgnore
	private List<UserBranch> userBranches;
	

	@OneToMany(mappedBy = "approvarId", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<BidRequestApproval> bidRequestApprovals;

	@Transient
	private String wayToCreate;

	@Transient
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date fromDate;

	@Transient
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date toDate;

	@Transient
	private byte entityType;
	
	@Transient
	private boolean search;
	
	

	/*
	 * //bi-directional many-to-one association to UserRole
	 * 
	 * @OneToMany(mappedBy="user") private List<UserRole> userRoles;
	 */


	// bi-directional many-to-one association to UserRole
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<UserRole> userRoles;

	@OneToMany(mappedBy = "userCreatedBy",cascade = CascadeType.ALL)
	@JsonIgnore
	private List<PlaceCashRequestSwapAuditTrial> PlaceCashRequestSwapAuditTrialCreatedBy;

	@OneToMany(mappedBy = "userModifiedBy",cascade = CascadeType.ALL)
	@JsonIgnore
	private List<PlaceCashRequestSwapAuditTrial> PlaceCashRequestSwapAuditTrialModifiedBy;
	
	@Column(name="ip")
	private String ip;
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public User() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public byte getEntityType() {
		return entityType;
	}

	public void setEntityType(byte entityType) {
		this.entityType = entityType;
	}	

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public byte getGender() {
		return gender;
	}

	public void setGender(byte gender) {
		this.gender = gender;
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

	public Designation getDesignation() {
		return designation;
	}

	public void setDesignation(Designation designation) {
		this.designation = designation;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Date getDateOfJoining() {
		return dateOfJoining;
	}

	public void setDateOfJoining(Date dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}

	public Date getPostingStartDate() {
		return postingStartDate;
	}

	public void setPostingStartDate(Date postingStartDate) {
		this.postingStartDate = postingStartDate;
	}

	public Date getPostingEndDate() {
		return postingEndDate;
	}

	public void setPostingEndDate(Date postingEndDate) {
		this.postingEndDate = postingEndDate;
	}

	public byte getApprover() {
		return approver;
	}

	public void setApprover(byte approver) {
		this.approver = approver;
	}

	public String getEpfo_no() {
		return epfo_no;
	}

	public void setEpfo_no(String epfo_no) {
		this.epfo_no = epfo_no;
	}

	public Double getCash_limit() {
		return cash_limit;
	}

	public void setCash_limit(Double cash_limit) {
		this.cash_limit = cash_limit;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public BranchManagement getBranchManagement() {
		return branchManagement;
	}

	public void setBranchManagement(BranchManagement branchManagement) {
		this.branchManagement = branchManagement;
	}

	public List<UserBranch> getUserBranches() {
		return userBranches;
	}

	public void setUserBranches(List<UserBranch> userBranches) {
		this.userBranches = userBranches;
	}

	public String getWayToCreate() {
		return wayToCreate;
	}

	public void setWayToCreate(String wayToCreate) {
		this.wayToCreate = wayToCreate;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public Set<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	public List<PlaceCashRequestSwapAuditTrial> getPlaceCashRequestSwapAuditTrialCreatedBy() {
		return PlaceCashRequestSwapAuditTrialCreatedBy;
	}

	public void setPlaceCashRequestSwapAuditTrialCreatedBy(
			List<PlaceCashRequestSwapAuditTrial> placeCashRequestSwapAuditTrialCreatedBy) {
		PlaceCashRequestSwapAuditTrialCreatedBy = placeCashRequestSwapAuditTrialCreatedBy;
	}

	public List<PlaceCashRequestSwapAuditTrial> getPlaceCashRequestSwapAuditTrialModifiedBy() {
		return PlaceCashRequestSwapAuditTrialModifiedBy;
	}

	public void setPlaceCashRequestSwapAuditTrialModifiedBy(
			List<PlaceCashRequestSwapAuditTrial> placeCashRequestSwapAuditTrialModifiedBy) {
		PlaceCashRequestSwapAuditTrialModifiedBy = placeCashRequestSwapAuditTrialModifiedBy;
	}

	public boolean isSearch() {
		return search;
	}

	public void setSearch(boolean search) {
		this.search = search;
	}
	
	@Override
	public boolean equals(Object arg0) {
		User user = (User)arg0;
		return this.id.equals(user.getId());
	}
	
	@Override 
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}
	
//	public UserDetails toCurrentUserDetails() {
//	    return CurrentUserDetails.create(this);
//	}

	/*public List<UserAuditTrail> getUserAuditTrails() {
		return userAuditTrails;
	}

	public void setUserAuditTrails(List<UserAuditTrail> userAuditTrails) {
		this.userAuditTrails = userAuditTrails;
	}*/

}