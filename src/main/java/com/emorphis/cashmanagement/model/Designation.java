package com.emorphis.cashmanagement.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.FilterDef;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The persistent class for the designation database table.
 * 
 */
@Entity
@Table(name = "designation",uniqueConstraints = @UniqueConstraint(columnNames = {"title", "hc_id"}))
@FilterDef(name = "test", defaultCondition = "deleted=0")
@NamedQuery(name = "Designation.findAll", query = "SELECT d FROM Designation d")
public class Designation extends CommonModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	
	/*@OneToMany(mappedBy="designationId",cascade = CascadeType.ALL)
	@JsonIgnore
	private List<DesignationAuditTrail> designationAuditTrails;*/

	@Column(name = "created_by")
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_time")
	private Date createdTime;

	private byte deleted;

	@Column(name = "modified_by")
	private String modifiedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_time")
	private Date modifiedTime;

	/*
	 * @ManyToOne
	 * 
	 * @JoinColumn(name = "om_id")
	 * 
	 * @JsonIgnore // added ny pavan private OrgManagement orgManagement;
	 */

	@ManyToOne
	@JoinColumn(name = "hc_id")
	@JsonIgnore // added ny pavan
	private HierarchyControl hierarchyControl;

	@OneToMany(mappedBy = "designation",cascade = CascadeType.ALL)
	@JsonIgnore // added ny pavan
	private List<User> users;

	@Column(name = "om_id")
	private String orgManagement;

	private int parent;

	
	@NotBlank(message="Required")
	@Column(name = "title")
	private String title;
	
	@Column(name="ip")
	private String ip;
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Designation() {
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

	public byte getDeleted() {
		return this.deleted;
	}

	public void setDeleted(byte deleted) {
		this.deleted = deleted;
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
	
	/*
	 * public OrgManagement getOrgManagement() { return orgManagement; }
	 * 
	 * public void setOrgManagement(OrgManagement orgManagement) {
	 * this.orgManagement = orgManagement; }
	 */

	public HierarchyControl getHierarchyControl() {
		return hierarchyControl;
	}

	public String getOrgManagement() {
		return orgManagement;
	}

	public void setOrgManagement(String orgManagement) {
		this.orgManagement = orgManagement;
	}

	public void setHierarchyControl(HierarchyControl hierarchyControl) {
		this.hierarchyControl = hierarchyControl;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
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

	/*public List<DesignationAuditTrail> getDesignationAuditTrails() {
		return designationAuditTrails;
	}

	public void setDesignationAuditTrails(List<DesignationAuditTrail> designationAuditTrails) {
		this.designationAuditTrails = designationAuditTrails;
	}*/
	
}