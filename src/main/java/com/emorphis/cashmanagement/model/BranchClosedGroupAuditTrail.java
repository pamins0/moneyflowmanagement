package com.emorphis.cashmanagement.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * The persistent class for the branch_closed_groups_audit_trail database table.
 * 
 */
@Entity
@Table(name="branch_closed_groups_audit")
@NamedQuery(name = "BranchClosedGroupAuditTrail.findAll", query = "SELECT b FROM BranchClosedGroupAuditTrail b")
public class BranchClosedGroupAuditTrail implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "MySequence", sequenceName = "BRANCH_CLOSED_GRP_AUDIT_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="MySequence")
	private Integer id;
	
	/*@ManyToOne
	@JoinColumn(name="branch_closed_group_id")
	@JsonIgnore
	private BranchClosedGroup branchClosedGroupId;*/
	
	@Column(name="branch_closed_group_id")
	private String branchClosedGroupId;
	
	// bi-directional many-to-one association to BranchManagement
		@ManyToOne
		@JoinColumn(name = "branch_id")
		private BranchManagement parentBranch;

		// bi-directional many-to-one association to BranchManagement
		@ManyToOne
		@JoinColumn(name = "closed_branch_id")
		private BranchManagement closedGroupBranch;
	
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

	/*public BranchClosedGroup getBranchClosedGroupId() {
		return branchClosedGroupId;
	}

	public void setBranchClosedGroupId(BranchClosedGroup branchClosedGroupId) {
		this.branchClosedGroupId = branchClosedGroupId;
	}*/

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

	public String getBranchClosedGroupId() {
		return branchClosedGroupId;
	}

	public void setBranchClosedGroupId(String branchClosedGroupId) {
		this.branchClosedGroupId = branchClosedGroupId;
	}
	
    
}
