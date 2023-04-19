package com.emorphis.cashmanagement.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The persistent class for the hierarchy_control_audit_trail database table.
 * 
 */
@Entity
@Table(name = "hierarchy_control_audit_trail")
@NamedQuery(name = "HierarchyControlAuditTrail.findAll", query = "SELECT h FROM HierarchyControlAuditTrail h")
public class HierarchyControlAuditTrail implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "MySequence", sequenceName = "HIERARCHY_CONTROL_AUDIT_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="MySequence")
	private Integer id;
	
	/*@ManyToOne
	@JoinColumn(name="hierarchy_Control_Id")
	@JsonIgnore
	private HierarchyControl hierarchyControlId;*/
	
	
	@Column(name="hierarchy_Control_Id")
	private String hierarchyControlId;
	
	private String abbreviation;

	@Column(name = "created_by")
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_time")
	private Date createdTime;

	private byte deleted;

	@Column(name = "hierarchy_level")
	private int hierarchyLevel;

	@Column(name = "hierarchy_type")
	private int hierarchyType;

	@Column(name = "is_created")
	private byte isCreated;

	@Column(name = "modified_by")
	private String modifiedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_time")
	private Date modifiedTime;

	@Column(name = "name")
	private String name;

	@Column(name = "parent_id")
	private String parentId;

	@OneToMany(mappedBy = "hierarchyControl",cascade = CascadeType.ALL)
	//@Filter(name = "test")
	@JsonIgnore
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<BranchManagement> branchManagements;

	@ManyToOne
	@JoinColumn(name = "om_id")
	@JsonIgnore
	private OrgManagement orgManagement;

	@OneToMany(mappedBy = "hierarchyControl",cascade = CascadeType.ALL)
	//@Filters({ @Filter(name = "test"), @Filter(name = "test_status") })
	@JsonIgnore
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Role> roles;

	@OneToMany(mappedBy = "hierarchyControl",cascade = CascadeType.ALL)
	//@Filter(name = "test")
	@JsonIgnore
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Designation> designation;
	
	@OneToMany(mappedBy = "hierarchyControl",cascade = CascadeType.ALL)
	//@Filter(name = "test")
	@JsonIgnore
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Department> department;

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

	/*public HierarchyControl getHierarchyControlId() {
		return hierarchyControlId;
	}

	public void setHierarchyControlId(HierarchyControl hierarchyControlId) {
		this.hierarchyControlId = hierarchyControlId;
	}*/

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
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

	public int getHierarchyLevel() {
		return hierarchyLevel;
	}

	public void setHierarchyLevel(int hierarchyLevel) {
		this.hierarchyLevel = hierarchyLevel;
	}

	public int getHierarchyType() {
		return hierarchyType;
	}

	public void setHierarchyType(int hierarchyType) {
		this.hierarchyType = hierarchyType;
	}

	public byte getIsCreated() {
		return isCreated;
	}

	public void setIsCreated(byte isCreated) {
		this.isCreated = isCreated;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public List<BranchManagement> getBranchManagements() {
		return branchManagements;
	}

	public void setBranchManagements(List<BranchManagement> branchManagements) {
		this.branchManagements = branchManagements;
	}

	public OrgManagement getOrgManagement() {
		return orgManagement;
	}

	public void setOrgManagement(OrgManagement orgManagement) {
		this.orgManagement = orgManagement;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public List<Designation> getDesignation() {
		return designation;
	}

	public void setDesignation(List<Designation> designation) {
		this.designation = designation;
	}

	public List<Department> getDepartment() {
		return department;
	}

	public void setDepartment(List<Department> department) {
		this.department = department;
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

	public String getHierarchyControlId() {
		return hierarchyControlId;
	}

	public void setHierarchyControlId(String hierarchyControlId) {
		this.hierarchyControlId = hierarchyControlId;
	}
	
	
}
