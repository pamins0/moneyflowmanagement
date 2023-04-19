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
 * The persistent class for the dashboard_final_bids_audit_trial database table.
 * 
 */
@Entity
@Table(name = "dashboard_final_bids_audit")
@NamedQuery(name = "DashboardFinalBidsAuditTrial.findAll", query = "SELECT d FROM DashboardFinalBidsAuditTrial d")
public class DashboardFinalBidsAuditTrial implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "MySequence", sequenceName = "DASHBOARD_FINAL_BIDS_AUDIT_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="MySequence")
	@Column(name = "id")
	private Integer id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_time")
	private Date createdTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dashboard_created_time")
	private Date dashboardFinalBidCreatedTime;

	@Column(name = "dashboard_final_bid_id")
	private Integer dashboardFinalBidId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dashboard_modified_time")
	private Date dashboardFinalBidModifiedTime;

	@Column(name = "eod_total")
	private double eodTotal;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_time")
	private Date modifiedTime;

	private String operations;

	private String position;

	/* private String status; */

	@ManyToOne
	@JoinColumn(name = "status")
	private RequestStatus requestStatus;

	private double total;

	@Column(name = "modified_value")
	private String modified_value;

	// bi-directional many-to-one association to BranchManagement
	@ManyToOne
	@JoinColumn(name = "branch_id")
	private BranchManagement branchManagement;
	
	@Column(name="ip")
	private String ip;
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public DashboardFinalBidsAuditTrial() {
	}

	public String getModified_value() {
		return modified_value;
	}

	public void setModified_value(String modified_value) {
		this.modified_value = modified_value;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreatedTime() {
		return this.createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getDashboardFinalBidCreatedTime() {
		return this.dashboardFinalBidCreatedTime;
	}

	public void setDashboardFinalBidCreatedTime(Date dashboardFinalBidCreatedTime) {
		this.dashboardFinalBidCreatedTime = dashboardFinalBidCreatedTime;
	}

	public Integer getDashboardFinalBidId() {
		return this.dashboardFinalBidId;
	}

	public void setDashboardFinalBidId(Integer dashboardFinalBidId) {
		this.dashboardFinalBidId = dashboardFinalBidId;
	}

	public Date getDashboardFinalBidModifiedTime() {
		return this.dashboardFinalBidModifiedTime;
	}

	public void setDashboardFinalBidModifiedTime(Date dashboardFinalBidModifiedTime) {
		this.dashboardFinalBidModifiedTime = dashboardFinalBidModifiedTime;
	}

	public double getEodTotal() {
		return this.eodTotal;
	}

	public void setEodTotal(double eodTotal) {
		this.eodTotal = eodTotal;
	}

	public Date getModifiedTime() {
		return this.modifiedTime;
	}

	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	public String getOperations() {
		return this.operations;
	}

	public void setOperations(String operations) {
		this.operations = operations;
	}

	public String getPosition() {
		return this.position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	/*
	 * public String getStatus() { return this.status; }
	 * 
	 * public void setStatus(String status) { this.status = status; }
	 */

	public double getTotal() {
		return this.total;
	}

	public RequestStatus getRequestStatus() {
		return requestStatus;
	}

	public void setRequestStatus(RequestStatus requestStatus) {
		this.requestStatus = requestStatus;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public BranchManagement getBranchManagement() {
		return this.branchManagement;
	}

	public void setBranchManagement(BranchManagement branchManagement) {
		this.branchManagement = branchManagement;
	}

}