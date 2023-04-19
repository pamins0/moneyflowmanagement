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


/**
 * The persistent class for the role_permission database table.
 * 
 */
@Entity
@Table(name="role_permission")
@NamedQuery(name="RolePermission.findAll", query="SELECT r FROM RolePermission r")
public class RolePermission implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	/*@OneToMany(mappedBy="rolePermissionId",cascade = CascadeType.ALL)
	@JsonIgnore
	private List<RolePermissionAuditTrail> rolePermissionAuditTrails;*/
	
	@Column(name="created_by")
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_time")
	private Date createdTime;

	@Column(name="grant_permission")
	private byte grantPermission;

	@Column(name="modified_by")
	private String modifiedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="modified_time")
	private Date modifiedTime;

	//bi-directional many-to-one association to Role
	@ManyToOne
	private Role role;

	//bi-directional many-to-one association to Permission
	@ManyToOne
	private Permission permission;
	
	@Column(name="ip")
	private String ip;
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * For Managing RolePermission using transient variable
	 * 
	 * @return
	 */

	@Transient
	List<String> insertedVA = new ArrayList<String>();

	@Transient
	List<String> selectedVA = new ArrayList<String>();

	@Transient
	List<String> deletedVA = new ArrayList<String>();
	

	public RolePermission() {
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

	public byte getGrantPermission() {
		return this.grantPermission;
	}

	public void setGrantPermission(byte grantPermission) {
		this.grantPermission = grantPermission;
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

	public Permission getPermission() {
		return this.permission;
	}

	public void setPermission(Permission permission) {
		this.permission = permission;
	}

	/*public List<RolePermissionAuditTrail> getRolePermissionAuditTrails() {
		return rolePermissionAuditTrails;
	}

	public void setRolePermissionAuditTrails(List<RolePermissionAuditTrail> rolePermissionAuditTrails) {
		this.rolePermissionAuditTrails = rolePermissionAuditTrails;
	}*/

}