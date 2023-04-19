package com.emorphis.cashmanagement.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the permission_uri database table.
 * 
 */
@Entity
@Table(name = "permission_uri")
@NamedQuery(name = "PermissionUri.findAll", query = "SELECT p FROM PermissionUri p")
public class PermissionUri implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	
	/*@OneToMany(mappedBy="permissionUriId",cascade = CascadeType.ALL)
	@JsonIgnore
	private List<PermissionUriAuditTrail> permissionUriAuditTrails;*/

	@Column(name = "created_by")
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_time")
	private Date createdTime;

	private String description;

	@Column(name = "modified_by")
	private String modifiedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_time")
	private Date modifiedTime;

	private String uri;

	// bi-directional many-to-one association to Permission
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

	public PermissionUri() {
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

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getUri() {
		return this.uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public Permission getPermission() {
		return this.permission;
	}

	public void setPermission(Permission permission) {
		this.permission = permission;
	}

	/*public List<PermissionUriAuditTrail> getPermissionUriAuditTrails() {
		return permissionUriAuditTrails;
	}

	public void setPermissionUriAuditTrails(List<PermissionUriAuditTrail> permissionUriAuditTrails) {
		this.permissionUriAuditTrails = permissionUriAuditTrails;
	}*/

	

}