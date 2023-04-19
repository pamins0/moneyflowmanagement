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
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The persistent class for the request_status_audit_trail database table.
 * 
 */
@Entity
@Table(name="request_status_audit_trail")
@NamedQuery(name="RequestStatusAuditTrail.findAll", query="SELECT r FROM RequestStatusAuditTrail r")
public class RequestStatusAuditTrail implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "MySequence", sequenceName = "REQUEST_STATUS_AUDIT_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="MySequence")
	private Integer id;
	
	/*@ManyToOne
	@JoinColumn(name="request_status_Id")
	@JsonIgnore
	private RequestStatus requestStatusId;*/
	
	
	@Column(name="request_status_Id")
	private String requestStatusId;
	
	@Column(name="alias")
	private String alias;

	@Column(name="created_by")
	private String created_By;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_time")
	private Date created_Time;

	@Column(name="is_active")
	private byte isActive;

	@Column(name="modified_by")
	private String modified_By;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="modified_time")
	private Date modified_Time;

	@Column(name="status")
	private String status;

	//bi-directional many-to-one association to DashboardFinalBid
	@OneToMany(mappedBy="requestStatus",cascade = CascadeType.ALL)
	@JsonIgnore
	private List<DashboardFinalBid> dashboardFinalBids;

	//bi-directional many-to-one association to DashboardFinalBidsAuditTrial
	@OneToMany(mappedBy="requestStatus",cascade = CascadeType.ALL)
	@JsonIgnore
	private List<DashboardFinalBidsAuditTrial> dashboardFinalBidsAuditTrials;

	//bi-directional many-to-one association to Place_cash_Request
	@OneToMany(mappedBy="requestStatus",cascade = CascadeType.ALL)
	@JsonIgnore
	private List<PlaceCashRequest> placeCashRequests;

	//bi-directional many-to-one association to Place_cash_Request_audit_trial
	@OneToMany(mappedBy="requestStatus")
	private List<PlaceCashRequestAuditTrial> placeCashRequestAuditTrials;

	//bi-directional many-to-one association to Place_cash_Request_swap
	@OneToMany(mappedBy="requestStatus",cascade = CascadeType.ALL)
	@JsonIgnore
	private List<PlaceCashRequestSwap> placeCashRequestSwaps;

	//bi-directional many-to-one association to Place_cash_Request_swap_audit_trial
	@OneToMany(mappedBy="requestStatus",cascade = CascadeType.ALL)
	@JsonIgnore
	private List<PlaceCashRequestSwapAuditTrial> placeCashRequestSwapAuditTrials;
	
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

	/*public RequestStatus getRequestStatusId() {
		return requestStatusId;
	}

	public void setRequestStatusId(RequestStatus requestStatusId) {
		this.requestStatusId = requestStatusId;
	}*/

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getCreated_By() {
		return created_By;
	}

	public void setCreated_By(String created_By) {
		this.created_By = created_By;
	}

	public Date getCreated_Time() {
		return created_Time;
	}

	public void setCreated_Time(Date created_Time) {
		this.created_Time = created_Time;
	}

	public byte getIsActive() {
		return isActive;
	}

	public void setIsActive(byte isActive) {
		this.isActive = isActive;
	}

	public String getModified_By() {
		return modified_By;
	}

	public void setModified_By(String modified_By) {
		this.modified_By = modified_By;
	}

	public Date getModified_Time() {
		return modified_Time;
	}

	public void setModified_Time(Date modified_Time) {
		this.modified_Time = modified_Time;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<DashboardFinalBid> getDashboardFinalBids() {
		return dashboardFinalBids;
	}

	public void setDashboardFinalBids(List<DashboardFinalBid> dashboardFinalBids) {
		this.dashboardFinalBids = dashboardFinalBids;
	}

	public List<DashboardFinalBidsAuditTrial> getDashboardFinalBidsAuditTrials() {
		return dashboardFinalBidsAuditTrials;
	}

	public void setDashboardFinalBidsAuditTrials(List<DashboardFinalBidsAuditTrial> dashboardFinalBidsAuditTrials) {
		this.dashboardFinalBidsAuditTrials = dashboardFinalBidsAuditTrials;
	}

	public List<PlaceCashRequest> getPlaceCashRequests() {
		return placeCashRequests;
	}

	public void setPlaceCashRequests(List<PlaceCashRequest> placeCashRequests) {
		this.placeCashRequests = placeCashRequests;
	}

	public List<PlaceCashRequestSwap> getPlaceCashRequestSwaps() {
		return placeCashRequestSwaps;
	}

	public void setPlaceCashRequestSwaps(List<PlaceCashRequestSwap> placeCashRequestSwaps) {
		this.placeCashRequestSwaps = placeCashRequestSwaps;
	}

	public List<PlaceCashRequestSwapAuditTrial> getPlaceCashRequestSwapAuditTrials() {
		return placeCashRequestSwapAuditTrials;
	}

	public void setPlaceCashRequestSwapAuditTrials(List<PlaceCashRequestSwapAuditTrial> placeCashRequestSwapAuditTrials) {
		this.placeCashRequestSwapAuditTrials = placeCashRequestSwapAuditTrials;
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

	public String getRequestStatusId() {
		return requestStatusId;
	}

	public void setRequestStatusId(String requestStatusId) {
		this.requestStatusId = requestStatusId;
	}

	public List<PlaceCashRequestAuditTrial> getPlaceCashRequestAuditTrials() {
		return placeCashRequestAuditTrials;
	}

	public void setPlaceCashRequestAuditTrials(List<PlaceCashRequestAuditTrial> placeCashRequestAuditTrials) {
		this.placeCashRequestAuditTrials = placeCashRequestAuditTrials;
	}
	
	
}
