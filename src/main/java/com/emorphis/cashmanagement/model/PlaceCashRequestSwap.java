package com.emorphis.cashmanagement.model;

import java.math.BigInteger;
import java.util.Date;

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



@Entity
@Table(name = "place_cash_Request_swap")
@NamedQuery(name = "PlaceCashRequestSwap.findAll", query = "SELECT p FROM PlaceCashRequestSwap p")
public class PlaceCashRequestSwap {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private double amount;

	@Transient
	private double actualCashReuires;

	@Column(name = "request_type")
	private String requestType;

	@ManyToOne
	@JoinColumn(name="status")
	private RequestStatus requestStatus;

	// bi-directional many-to-one association to BranchManagement
	@ManyToOne
	@JoinColumn(name = "from_branch_id")
	private BranchManagement branchManagementRequestedFrom;

	// bi-directional many-to-one association to BranchManagement
	@ManyToOne
	@JoinColumn(name = "to_branch_id")
	private BranchManagement branchManagementRequestedTo;

	@Column(name = "Request_Created_By")
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "Request_Created_Time")
	private Date created_Time;

	@Column(name = "Request_Modified_By")
	private String modifiedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "Request_Modified_Time")
	private Date modified_Time;

	@Column(name = "bid_hault_Time")
	private BigInteger bidHaultTime;

	@Column(name = "approverflag")
	private int approverFlag;
	
	@Column(name="ip")
	private String ip;
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public PlaceCashRequestSwap() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getApproverFlag() {
		return approverFlag;
	}

	public void setApproverFlag(int approverFlag) {
		this.approverFlag = approverFlag;
	}

	public String getRequestType() {
		return this.requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	/*public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}*/
	
	

	public BranchManagement getBranchManagementRequestedFrom() {
		return branchManagementRequestedFrom;
	}

	public RequestStatus getRequestStatus() {
		return requestStatus;
	}

	public void setRequestStatus(RequestStatus requestStatus) {
		this.requestStatus = requestStatus;
	}

	public void setBranchManagementRequestedFrom(BranchManagement branchManagementRequestedFrom) {
		this.branchManagementRequestedFrom = branchManagementRequestedFrom;
	}

	public BranchManagement getBranchManagementRequestedTo() {
		return branchManagementRequestedTo;
	}

	public void setBranchManagementRequestedTo(BranchManagement branchManagementRequestedTo) {
		this.branchManagementRequestedTo = branchManagementRequestedTo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Date getCreated_Time() {
		return created_Time;
	}

	public void setCreated_Time(Date created_Time) {
		this.created_Time = created_Time;
	}

	public Date getModified_Time() {
		return modified_Time;
	}

	public void setModified_Time(Date modified_Time) {
		this.modified_Time = modified_Time;
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
	
	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getActualCashReuires() {
		return actualCashReuires;
	}

	public void setActualCashReuires(double actualCashReuires) {
		this.actualCashReuires = actualCashReuires;
	}

	public BigInteger getBidHaultTime() {
		return bidHaultTime;
	}

	public void setBidHaultTime(BigInteger bidHaultTime) {
		this.bidHaultTime = bidHaultTime;
	}

}