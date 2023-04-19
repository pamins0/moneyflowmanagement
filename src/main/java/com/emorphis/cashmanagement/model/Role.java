package com.emorphis.cashmanagement.model;

import java.io.Serializable;
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
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;

import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.FilterDefs;
import org.hibernate.validator.constraints.NotBlank;

/**
 * The persistent class for the role database table.
 * 
 */
@Entity
@Table(name = "role",uniqueConstraints = @UniqueConstraint(columnNames = {"title", "hc_id"}))
@FilterDefs({
		@FilterDef(name = "test", defaultCondition = "deleted=0"),
		@FilterDef(name = "test_status", defaultCondition = "status=0")
})
@NamedQuery(name = "Role.findAll", query = "SELECT r FROM Role r")
public class Role extends CommonModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	/*@OneToMany(mappedBy="roleId",cascade = CascadeType.ALL)
	@JsonIgnore
	private List<RoleAuditTrail> roleAuditTrails;*/
	
	@Column(name = "b_id")
	private String bId;

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

	private int parent;

	@NotBlank(message = "Required")
	@Column(name = "title")
	private String title;

	@Column(name = "deleted")
	private byte deleted;

	@Column(name = "status")
	private byte status;
	
	@Column(name = "ip")
	private String ip;

	/*
	 * @Transient private String saveBtn;
	 */

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Transient
	private String saveAndNewBtn;

	// bi-directional many-to-one association to OrgManagement
	/*
	 * @ManyToOne
	 * 
	 * @JoinColumn(name = "om_id") private OrgManagement orgManagement;
	 */

	@Column(name = "om_id")
	private String orgManagement;

	@ManyToOne
	@JoinColumn(name = "hc_id")
	@Valid
	private HierarchyControl hierarchyControl;

	/*
	 * //bi-directional many-to-one association to RolePermission
	 * 
	 * @OneToMany(mappedBy="role") private List<RolePermission> rolePermissions;
	 */

	// bi-directional many-to-one association to UserRole
	@OneToMany(mappedBy = "role", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<RolePermission> rolePermissions;

	// bi-directional many-to-one association to UserRole
	@OneToMany(mappedBy = "role",cascade = CascadeType.ALL)
	private List<UserRole> userRoles;

	public Role() {
	}

	/*
	 * public String getSaveBtn() { return saveBtn; }
	 * 
	 * public void setSaveBtn(String saveBtn) { this.saveBtn = saveBtn; }
	 */

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}
	
	public String getSaveAndNewBtn() {
		return saveAndNewBtn;
	}

	public void setSaveAndNewBtn(String saveAndNewBtn) {
		this.saveAndNewBtn = saveAndNewBtn;
	}

	public byte getDeleted() {
		return deleted;
	}

	public void setDeleted(byte deleted) {
		this.deleted = deleted;
	}

	public HierarchyControl getHierarchyControl() {
		return hierarchyControl;
	}

	public void setHierarchyControl(HierarchyControl hierarchyControl) {
		this.hierarchyControl = hierarchyControl;
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

	public int getParent() {
		return this.parent;
	}

	public void setParent(int parent) {
		this.parent = parent;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	/*
	 * public OrgManagement getOrgManagement() { return this.orgManagement; }
	 * 
	 * public void setOrgManagement(OrgManagement orgManagement) {
	 * this.orgManagement = orgManagement; }
	 */

	public Set<RolePermission> getRolePermissions() {
		return this.rolePermissions;
	}

	public String getbId() {
		return bId;
	}

	public void setbId(String bId) {
		this.bId = bId;
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

	public String getOrgManagement() {
		return orgManagement;
	}

	public void setOrgManagement(String orgManagement) {
		this.orgManagement = orgManagement;
	}

	public void setRolePermissions(Set<RolePermission> rolePermissions) {
		this.rolePermissions = rolePermissions;
	}

	public RolePermission addRolePermission(RolePermission rolePermission) {
		getRolePermissions().add(rolePermission);
		rolePermission.setRole(this);

		return rolePermission;
	}

	public RolePermission removeRolePermission(RolePermission rolePermission) {
		getRolePermissions().remove(rolePermission);
		rolePermission.setRole(null);

		return rolePermission;
	}

	public List<UserRole> getUserRoles() {
		return this.userRoles;
	}

	public void setUserRoles(List<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	public UserRole addUserRole(UserRole userRole) {
		getUserRoles().add(userRole);
		userRole.setRole(this);

		return userRole;
	}

	public UserRole removeUserRole(UserRole userRole) {
		getUserRoles().remove(userRole);
		userRole.setRole(null);

		return userRole;
	}

	/*public List<RoleAuditTrail> getRoleAuditTrails() {
		return roleAuditTrails;
	}

	public void setRoleAuditTrails(List<RoleAuditTrail> roleAuditTrails) {
		this.roleAuditTrails = roleAuditTrails;
	}*/

}