package com.emorphis.cashmanagement.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.NotBlank;

/**
 * The persistent class for the permission database table.
 * 
 */
@Entity
@Table(name = "permission")
@NamedQuery(name = "Permission.findAll", query = "SELECT p FROM Permission p")
public class Permission implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	
	/*@OneToMany(mappedBy="permissionId",cascade = CascadeType.ALL)
	@JsonIgnore
	private List<PermissionAuditTrail> permissionAuditTrails;*/

	@Column(name = "created_by")
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_time")
	private Date createdTime;

	private String description;

	@NotBlank(message="Required")
	private String keyVal;

	@Column(name = "modified_by")
	private String modifiedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_time")
	private Date modifiedTime;

	@NotBlank(message="Required")
	private String module;

	@NotBlank(message="Required")
	private String title;

	@Column(name = "abbreviation")
	private String abbreviation;

	// bi-directional many-to-one association to PermissionUri
	@OneToMany(mappedBy = "permission",cascade = CascadeType.ALL)
	private List<PermissionUri> permissionUris;

	// bi-directional many-to-one association to RolePermission
	@OneToMany(mappedBy = "permission",cascade = CascadeType.ALL)
	private List<RolePermission> rolePermissions;
	
	@Column(name="ip")
	private String ip;
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Permission() {
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
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

	public String getKeyVal() {
		return this.keyVal;
	}

	public void setKeyVal(String keyVal) {
		this.keyVal = keyVal;
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

	public String getModule() {
		return this.module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<PermissionUri> getPermissionUris() {
		return this.permissionUris;
	}

	public void setPermissionUris(List<PermissionUri> permissionUris) {
		this.permissionUris = permissionUris;
	}

	public PermissionUri addPermissionUri(PermissionUri permissionUri) {
		getPermissionUris().add(permissionUri);
		permissionUri.setPermission(this);

		return permissionUri;
	}

	public PermissionUri removePermissionUri(PermissionUri permissionUri) {
		getPermissionUris().remove(permissionUri);
		permissionUri.setPermission(null);

		return permissionUri;
	}

	public List<RolePermission> getRolePermissions() {
		return this.rolePermissions;
	}

	public void setRolePermissions(List<RolePermission> rolePermissions) {
		this.rolePermissions = rolePermissions;
	}

	public RolePermission addRolePermission(RolePermission rolePermission) {
		getRolePermissions().add(rolePermission);
		rolePermission.setPermission(this);

		return rolePermission;
	}

	public RolePermission removeRolePermission(RolePermission rolePermission) {
		getRolePermissions().remove(rolePermission);
		rolePermission.setPermission(null);

		return rolePermission;
	}

	/*public List<PermissionAuditTrail> getPermissionAuditTrails() {
		return permissionAuditTrails;
	}

	public void setPermissionAuditTrails(List<PermissionAuditTrail> permissionAuditTrails) {
		this.permissionAuditTrails = permissionAuditTrails;
	}*/

}