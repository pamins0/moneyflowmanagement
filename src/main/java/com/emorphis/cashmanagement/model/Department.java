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
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
/**
 * The persistent class for the department database table.
 * 
 */
@Entity
@Table(name = "department",uniqueConstraints = @UniqueConstraint(columnNames = {"title", "hc_id"}))
@FilterDef(name = "test", defaultCondition = "deleted=0")
@NamedQuery(name = "Department.findAll", query = "SELECT d FROM Department d")
public class Department extends CommonModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	/*@OneToMany(mappedBy="departmentId",cascade = CascadeType.ALL)
	@JsonIgnore
	private List<DepartmentAuditTrail> departmentAuditTrails;*/
	
	@Column(name = "created_by")
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_time")
	private Date createdTime;

	@Column(name = "deleted")
	private byte deleted;

	@Column(name = "modified_by")
	private String modifiedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_time")
	private Date modifiedTime;

	@ManyToOne
	@JoinColumn(name = "hc_id")
	@JsonIgnore
	private HierarchyControl hierarchyControl;

	@OneToMany(mappedBy = "department",cascade = CascadeType.ALL)
	@JsonIgnore
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<User> users;

	@Column(name = "om_id")
	private String orgManagement;

	@Column(name = "parent")
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
	
	public Department(){
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public HierarchyControl getHierarchyControl() {
		return hierarchyControl;
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

	public String getOrgManagement() {
		return orgManagement;
	}

	public void setOrgManagement(String orgManagement) {
		this.orgManagement = orgManagement;
	}

	public int getParent() {
		return parent;
	}

	public void setParent(int parent) {
		this.parent = parent;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/*public List<DepartmentAuditTrail> getDepartmentAuditTrails() {
		return departmentAuditTrails;
	}

	public void setDepartmentAuditTrails(List<DepartmentAuditTrail> departmentAuditTrails) {
		this.departmentAuditTrails = departmentAuditTrails;
	}	*/
	
}
