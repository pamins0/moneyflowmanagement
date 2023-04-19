package com.emorphis.cashmanagement.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the user_branch database table.
 * 
 */
@Entity
@Table(name="user_branch")
@NamedQuery(name="UserBranch.findAll", query="SELECT u FROM UserBranch u")
public class UserBranch implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	/*@OneToMany(mappedBy="userBranchId",cascade = CascadeType.ALL)
	@JsonIgnore
	private List<UserBranchAuditTrail> userBranchAuditTrails;*/
	
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
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public UserBranch() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public Date getCreatedTime() {
		return this.createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getModifiedTime() {
		return this.modifiedTime;
	}

	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
	
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public BranchManagement getBranchManagement() {
		return this.branchManagement;
	}

	public void setBranchManagement(BranchManagement branchManagement) {
		this.branchManagement = branchManagement;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	/*public List<UserBranchAuditTrail> getUserBranchAuditTrails() {
		return userBranchAuditTrails;
	}

	public void setUserBranchAuditTrails(List<UserBranchAuditTrail> userBranchAuditTrails) {
		this.userBranchAuditTrails = userBranchAuditTrails;
	}*/

}