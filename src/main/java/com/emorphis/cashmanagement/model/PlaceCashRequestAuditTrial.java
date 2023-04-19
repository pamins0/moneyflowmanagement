package com.emorphis.cashmanagement.model;

import java.io.Serializable;
import java.math.BigInteger;
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
 * The persistent class for the place_cash_Request_audit_trial database table.
 * 
 */
@Entity
@Table(name = "place_cash_request_audit")
@NamedQuery(name = "PlaceCashRequestAuditTrial.findAll", query = "SELECT p FROM PlaceCashRequestAuditTrial p")
public class PlaceCashRequestAuditTrial implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "MySequence", sequenceName = "PLACE_CASH_REQ_AUDIT_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="MySequence")
	private int id;

	private double amount;

	private int approverflag;

	private BigInteger bid_hault_Time;

	private String operations;

	private String place_cash_Request_id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date place_Request_Created_Time;

	@Temporal(TemporalType.TIMESTAMP)
	private Date place_Request_Modified_Time;

	@Column(name = "Request_Created_By")
	private String request_Created_By;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "Request_Created_Time")
	private Date request_Created_Time;

	@Column(name = "Request_Modified_by")
	private String request_Modified_by;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "Request_Modified_Time")
	private Date request_Modified_Time;

	@Column(name = "request_type")
	private String requestType;

	// bi-directional many-to-one association to BranchManagement
	@ManyToOne
	@JoinColumn(name = "from_branch_id")
	private BranchManagement branchManagementRequestedFrom;

	// bi-directional many-to-one association to BranchManagement
	@ManyToOne
	@JoinColumn(name = "to_branch_id")
	private BranchManagement branchManagementRequestedTo;

	// bi-directional many-to-one association to RequestStatus
	@ManyToOne
	@JoinColumn(name = "status")
	private RequestStatus requestStatus;
	
	@Column(name="ip")
	private String ip;
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public PlaceCashRequestAuditTrial() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getAmount() {
		return this.amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getApproverflag() {
		return this.approverflag;
	}

	public void setApproverflag(int approverflag) {
		this.approverflag = approverflag;
	}

	public BigInteger getBid_hault_Time() {
		return this.bid_hault_Time;
	}

	public void setBid_hault_Time(BigInteger bid_hault_Time) {
		this.bid_hault_Time = bid_hault_Time;
	}

	public String getOperations() {
		return this.operations;
	}

	public void setOperations(String operations) {
		this.operations = operations;
	}

	public String getPlace_cash_Request_id() {
		return this.place_cash_Request_id;
	}

	public void setPlace_cash_Request_id(String place_cash_Request_id) {
		this.place_cash_Request_id = place_cash_Request_id;
	}

	public Date getPlace_Request_Created_Time() {
		return this.place_Request_Created_Time;
	}

	public void setPlace_Request_Created_Time(Date place_Request_Created_Time) {
		this.place_Request_Created_Time = place_Request_Created_Time;
	}

	public Date getPlace_Request_Modified_Time() {
		return this.place_Request_Modified_Time;
	}

	public void setPlace_Request_Modified_Time(Date place_Request_Modified_Time) {
		this.place_Request_Modified_Time = place_Request_Modified_Time;
	}

	public String getRequest_Created_By() {
		return this.request_Created_By;
	}

	public void setRequest_Created_By(String request_Created_By) {
		this.request_Created_By = request_Created_By;
	}

	public Date getRequest_Created_Time() {
		return this.request_Created_Time;
	}

	public void setRequest_Created_Time(Date request_Created_Time) {
		this.request_Created_Time = request_Created_Time;
	}

	public String getRequest_Modified_by() {
		return this.request_Modified_by;
	}

	public void setRequest_Modified_by(String request_Modified_by) {
		this.request_Modified_by = request_Modified_by;
	}

	public Date getRequest_Modified_Time() {
		return this.request_Modified_Time;
	}

	public void setRequest_Modified_Time(Date request_Modified_Time) {
		this.request_Modified_Time = request_Modified_Time;
	}

	public String getRequestType() {
		return this.requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public BranchManagement getBranchManagementRequestedFrom() {
		return branchManagementRequestedFrom;
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

	public RequestStatus getRequestStatus() {
		return this.requestStatus;
	}

	public void setRequestStatus(RequestStatus requestStatus) {
		this.requestStatus = requestStatus;
	}

}