package com.emorphis.cashmanagement.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the cash_management_audit_log database table.
 * 
 */
@Entity
@Table(name = "cash_management_audit_log")
@NamedQuery(name = "CashManagementAuditLog.findAll", query = "SELECT c FROM CashManagementAuditLog c")
public class CashManagementAuditLog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Column(name = "branch_id")
	private int branchId;

	private String ipdetails;

	@Column(name = "modified_by")
	private String modifiedBy;

	@Column(name = "modified_time")
	private int modifiedTime;

	private int operation;

	@Column(name = "table_name")
	private String tableName;

	@Column(name = "user_id")
	private int userId;

	// bi-directional many-to-one association to OrgManagement
	@ManyToOne
	@JoinColumn(name = "org_id")
	private OrgManagement orgManagement;
	
	@Column(name="ip")
	private String ip;
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public CashManagementAuditLog() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getBranchId() {
		return this.branchId;
	}

	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}

	public String getIpdetails() {
		return this.ipdetails;
	}

	public void setIpdetails(String ipdetails) {
		this.ipdetails = ipdetails;
	}

	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public int getModifiedTime() {
		return this.modifiedTime;
	}

	public void setModifiedTime(int modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	public int getOperation() {
		return this.operation;
	}

	public void setOperation(int operation) {
		this.operation = operation;
	}

	public String getTableName() {
		return this.tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public OrgManagement getOrgManagement() {
		return this.orgManagement;
	}

	public void setOrgManagement(OrgManagement orgManagement) {
		this.orgManagement = orgManagement;
	}

}