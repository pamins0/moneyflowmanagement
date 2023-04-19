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

import org.hibernate.validator.constraints.NotBlank;


/**
 * The persistent class for the branch_groups database table.
 * 
 */
@Entity
@Table(name="branch_groups")
@NamedQuery(name="BranchGroup.findAll", query="SELECT b FROM BranchGroup b")
public class BranchGroup implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	
	/*@OneToMany(mappedBy="branchGroupId",cascade = CascadeType.ALL)
	@JsonIgnore
	private List<BranchGroupAuditTrail> branchgroupAuditTrails;*/

	@Column(name="created_by")
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_time")
	private Date createdTime;

	@Column(name="group_id")
	private String groupId;
	
	@Column(name="group_name")
	@NotBlank
	private String groupName;

	@Column(name="modified_by")
	private String modifiedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="modified_time")
	private Date modifiedTime;

	//bi-directional many-to-one association to BranchManagement
	@ManyToOne
	@JoinColumn(name="branch_id")
	private BranchManagement branchManagement;
	
	@Transient
	private int count;
	
	/*@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "group_id")
	@JsonIgnore
	private Set<BranchGroup> branchControlalias = new HashSet<BranchGroup>();*/
	
	@Transient
	List<String> insertedVA = new ArrayList<String>();

	@Transient
	List<String> selectedVA = new ArrayList<String>();

	@Transient
	List<String> deletedVA = new ArrayList<String>();

	
	
	/*public Set<BranchGroup> getBranchControlalias() {
		return branchControlalias;
	}

	public void setBranchControlalias(Set<BranchGroup> branchControlalias) {
		this.branchControlalias = branchControlalias;
	}
*/
	@Column(name="ip")
	private String ip;
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
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


	public BranchGroup() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedTime() {
		return this.createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getGroupId() {
		return this.groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedTime() {
		return this.modifiedTime;
	}

	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	public BranchManagement getBranchManagement() {
		return this.branchManagement;
	}

	public void setBranchManagement(BranchManagement branchManagement) {
		this.branchManagement = branchManagement;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	/*public List<BranchGroupAuditTrail> getBranchgroupAuditTrails() {
		return branchgroupAuditTrails;
	}

	public void setBranchgroupAuditTrails(List<BranchGroupAuditTrail> branchgroupAuditTrails) {
		this.branchgroupAuditTrails = branchgroupAuditTrails;
	}*/
	

}