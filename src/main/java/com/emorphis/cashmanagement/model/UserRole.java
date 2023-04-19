package com.emorphis.cashmanagement.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.Filters;

/**
 * The persistent class for the user_role database table.
 * 
 */
@Entity
@Table(name = "user_role")
// @FilterDef(name="test", defaultCondition="role.getDeleted=0")
/*
 * @Filters({
 * 
 * @Filter(name="test",condition=":role.deleted==0") })
 */
//@Filter(name="test_status",condition=":role.status==0")
@NamedQuery(name = "UserRole.findAll", query = "SELECT u FROM UserRole u")
public class UserRole implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	// @GeneratedValue(strategy = GenerationType.AUTO)
	private String id;

	/*@OneToMany(mappedBy="userRoleId",cascade = CascadeType.ALL)
	@JsonIgnore
	private List<UserRoleAuditTrail> userRoleAuditTrails;*/
	
	@Column(name = "created_by")
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_time")
	private Date createdTime;

	@Column(name = "modified_by")
	private String modifiedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_time")
	private Date modifiedTime;

	// bi-directional many-to-one association to Role
	@ManyToOne
	@Filters({ @Filter(name = "test"), @Filter(name = "test_status") })
//	@Where(clause = "status= 0")
	private Role role;

	// bi-directional many-to-one association to User
	@ManyToOne
	private User user;

	/**
	 * For Managing Roles using transient variable
	 * 
	 * @return
	 */

	@Transient
	List<String> insertedVA = new ArrayList<String>();

	@Transient
	List<String> selectedVA = new ArrayList<String>();

	@Transient
	List<String> deletedVA = new ArrayList<String>();
	
	@Column(name="ip")
	private String ip;
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public UserRole() {
	}

	public List<String> getInsertedVA() {
		return insertedVA;
	}

	public void setInsertedVA(List<String> insertedVA) {
		this.insertedVA = insertedVA;
	}

	public List<String> getSelectedVA() {
		return selectedVA;
	}

	public void setSelectedVA(List<String> selectedVA) {
		this.selectedVA = selectedVA;
	}

	public List<String> getDeletedVA() {
		return deletedVA;
	}

	public void setDeletedVA(List<String> deletedVA) {
		this.deletedVA = deletedVA;
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

	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	/*public List<UserRoleAuditTrail> getUserRoleAuditTrails() {
		return userRoleAuditTrails;
	}

	public void setUserRoleAuditTrails(List<UserRoleAuditTrail> userRoleAuditTrails) {
		this.userRoleAuditTrails = userRoleAuditTrails;
	}*/
	
}