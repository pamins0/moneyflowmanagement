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
import javax.persistence.Transient;

/**
 * The persistent class for the dashboard_final_bids database table.
 * 
 */
@Entity
@Table(name = "dashboard_final_bids")
@NamedQuery(name = "DashboardFinalBid.findAll", query = "SELECT d FROM DashboardFinalBid d")
public class DashboardFinalBid extends CommonModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "MySequence", sequenceName = "DASHBOARD_FINAL_BIDS_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="MySequence")
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_time")
	private Date createdTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_time")
	private Date modifiedTime;

	private String position;

	// private String status;
	@ManyToOne
	@JoinColumn(name = "status")
	private RequestStatus requestStatus;

	@Column(name = "total")
	private double total;

	@Column(name = "eod_total")
	private double eodTotal;

	@Column(name = "modified_value")
	private String modified_value;

	// bi-directional many-to-one association to BranchManagement
	@ManyToOne
	@JoinColumn(name = "branch_id")
	private BranchManagement branchManagement;

	@Transient
	private String sameGroup;

	@Transient
	private boolean maker;

	@Transient
	private byte requestType;

	@Transient
	private boolean ccGroup;
	
	@Column(name="ip")
	private String ip;
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public boolean isCcGroup() {
		return ccGroup;
	}

	public void setCcGroup(boolean ccGroup) {
		this.ccGroup = ccGroup;
	}

	public String getModified_value() {
		return modified_value;
	}

	public void setModified_value(String modified_value) {
		this.modified_value = modified_value;
	}

	public byte getRequestType() {
		return requestType;
	}

	public void setRequestType(byte requestType) {
		this.requestType = requestType;
	}

	public double getEodTotal() {
		return eodTotal;
	}

	public void setEodTotal(double eodTotal) {
		this.eodTotal = eodTotal;
	}

	public boolean isMaker() {
		return maker;
	}

	public void setMaker(boolean maker) {
		this.maker = maker;
	}

	public DashboardFinalBid() {
	}

	public String getSameGroup() {
		return sameGroup;
	}

	public void setSameGroup(String sameGroup) {
		this.sameGroup = sameGroup;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
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