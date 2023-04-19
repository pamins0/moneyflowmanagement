package com.emorphis.cashmanagement.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
/**
 * The persistent class for the user_branch_audit_trail database table.
 * 
 */
@Entity
@Table(name="user_branch_audit_trail")
@NamedQuery(name="UserBranchAuditTrail.findAll", query="SELECT u FROM UserBranchAuditTrail u")
public class UserBranchAuditTrail implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "MySequence", sequenceName = "USER_BRANCH_AUDIT_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="MySequence")
	private Integer id;
	
	/*@ManyToOne
	@JoinColumn(name="user_branch_id")
	@JsonIgnore
	private UserBranch userBranchId;*/
	
	
	@Column(name="user_branch_id")
	private String userBranchId;
	
	@Column(name="created_by")
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_time")
	private Date createdTime;

	@Column(name="modified_by")
	private String modifiedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="modified_time")
	private Date modifiedTime;

	//bi-directional many-to-one association to BranchManagement
	@ManyToOne
	@JoinColumn(name="b_id")
	private BranchManagement branchManagement;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="u_id")
	private User user;
	
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

	/*public UserBranch getUserBranchId() {
		return userBranchId;
	}

	public void setUserBranchId(UserBranch userBranchId) {
		this.userBranchId = userBranchId;
	}*/

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

	public BranchManagement getBranchManagement() {
		return branchManagement;
	}

	public void setBranchManagement(BranchManagement branchManagement) {
		this.branchManagement = branchManagement;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public String getUserBranchId() {
		return userBranchId;
	}

	public void setUserBranchId(String userBranchId) {
		this.userBranchId = userBranchId;
	}
	
	
}
