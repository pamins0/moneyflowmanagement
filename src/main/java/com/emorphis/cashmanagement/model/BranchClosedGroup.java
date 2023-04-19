package com.emorphis.cashmanagement.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;

/**
 * 
 * The persistent class for the branch_closed_groups database table.
 * 
 */
@Entity
@FilterDef(name = "test", defaultCondition = "deleted=0")
@Table(name = "branch_closed_groups")
@NamedQuery(name = "BranchClosedGroup.findAll", query = "SELECT b FROM BranchClosedGroup b")
public class BranchClosedGroup implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
/*	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)*/
	private String id;
	
	/*@OneToMany(mappedBy="branchClosedGroupId",cascade = CascadeType.ALL)
	@JsonIgnore
	private List<BranchClosedGroupAuditTrail> branchClosedGroupAudittrails;*/

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

	// bi-directional many-to-one association to BranchManagement
	@ManyToOne
	@Filter(name = "test")
	@JoinColumn(name = "branch_id")
	private BranchManagement parentBranch;

	// bi-directional many-to-one association to BranchManagement
	@ManyToOne
	@Filter(name = "test")
	@JoinColumn(name = "closed_branch_id")
	private BranchManagement closedGroupBranch;
	
	@Column(name="ip")
	private String ip;	

	@Column(name = "branch_distance")	
	@DecimalMax("999999999999999.00000000")
	@DecimalMin("0.00000000")
	private Double branchDistance;
	
	public Double getBranchDistance() {
		return branchDistance;
	}

	public void setBranchDistance(Double branchDistance) {
		this.branchDistance = branchDistance;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Transient
	List<String> insertedVA = new ArrayList<String>();

	@Transient
	List<String> selectedVA = new ArrayList<String>();

	@Transient
	List<String> deletedVA = new ArrayList<String>();

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

	public BranchClosedGroup() {
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

	public BranchManagement getParentBranch() {
		return parentBranch;
	}

	public void setParentBranch(BranchManagement parentBranch) {
		this.parentBranch = parentBranch;
	}

	public BranchManagement getClosedGroupBranch() {
		return closedGroupBranch;
	}

	public void setClosedGroupBranch(BranchManagement closedGroupBranch) {
		this.closedGroupBranch = closedGroupBranch;
	}
	

	/*public List<BranchClosedGroupAuditTrail> getBranchClosedGroupAudittrails() {
		return branchClosedGroupAudittrails;
	}

	public void setBranchClosedGroupAudittrails(List<BranchClosedGroupAuditTrail> branchClosedGroupAudittrails) {
		this.branchClosedGroupAudittrails = branchClosedGroupAudittrails;
	}*/

	@Override
	public boolean equals(Object obj) {
		BranchClosedGroup branchClosedGroup = (BranchClosedGroup)obj;
		return this.id.equals(branchClosedGroup.getId());
	}
	
	@Override
	public int hashCode() {
	
		return super.hashCode();
	}


}