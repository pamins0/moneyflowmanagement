package com.emorphis.cashmanagement.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the user_permission database table.
 * 
 */
@Entity
@Table(name="user_permission")
@NamedQuery(name="UserPermission.findAll", query="SELECT u FROM UserPermission u")
public class UserPermission implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	/*@OneToMany(mappedBy="userPermissionId",cascade = CascadeType.ALL)
	@JsonIgnore
	private List<UserPermissionAuditTrail> userPermissionAuditTrails;*/
	
	@Column(name="created_by")
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_time")
	private Date createdTime;

	@Column(name="grant_permission")
	private byte grant;

	@Column(name="modified_by")
	private String modifiedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="modified_time")
	private Date modifiedTime;

	@Column(name="permission_id")
	private int permissionId;

	@Column(name="user_id")
	private int userId;
	
	@Column(name="ip")
	private String ip;
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public UserPermission() {
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

	public byte getGrant() {
		return this.grant;
	}

	public void setGrant(byte grant) {
		this.grant = grant;
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

	public int getPermissionId() {
		return this.permissionId;
	}

	public void setPermissionId(int permissionId) {
		this.permissionId = permissionId;
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	/*public List<UserPermissionAuditTrail> getUserPermissionAuditTrails() {
		return userPermissionAuditTrails;
	}

	public void setUserPermissionAuditTrails(List<UserPermissionAuditTrail> userPermissionAuditTrails) {
		this.userPermissionAuditTrails = userPermissionAuditTrails;
	}*/

}