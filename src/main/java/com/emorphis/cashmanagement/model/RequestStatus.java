package com.emorphis.cashmanagement.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the request_status database table.
 * 
 */
@Entity
@Table(name="request_status")
@NamedQuery(name="RequestStatus.findAll", query="SELECT r FROM RequestStatus r")
public class RequestStatus implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	/*@OneToMany(mappedBy="requestStatusId",cascade = CascadeType.ALL)
	@JsonIgnore
	private List<RequestStatusAuditTrail> requestStatusAuditTrails;*/
	
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
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public RequestStatus() {
	}

	public List<PlaceCashRequestSwapAuditTrial> getPlaceCashRequestSwapAuditTrials() {
		return placeCashRequestSwapAuditTrials;
	}

	public void setPlaceCashRequestSwapAuditTrials(List<PlaceCashRequestSwapAuditTrial> placeCashRequestSwapAuditTrials) {
		this.placeCashRequestSwapAuditTrials = placeCashRequestSwapAuditTrials;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAlias() {
		return this.alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getCreated_By() {
		return this.created_By;
	}

	public void setCreated_By(String created_By) {
		this.created_By = created_By;
	}

	public Date getCreated_Time() {
		return this.created_Time;
	}

	public void setCreated_Time(Date created_Time) {
		this.created_Time = created_Time;
	}

	public byte getIsActive() {
		return this.isActive;
	}

	public void setIsActive(byte isActive) {
		this.isActive = isActive;
	}

	public String getModified_By() {
		return this.modified_By;
	}

	public void setModified_By(String modified_By) {
		this.modified_By = modified_By;
	}

	public Date getModified_Time() {
		return this.modified_Time;
	}

	public void setModified_Time(Date modified_Time) {
		this.modified_Time = modified_Time;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<DashboardFinalBid> getDashboardFinalBids() {
		return this.dashboardFinalBids;
	}

	public void setDashboardFinalBids(List<DashboardFinalBid> dashboardFinalBids) {
		this.dashboardFinalBids = dashboardFinalBids;
	}

	public DashboardFinalBid addDashboardFinalBid(DashboardFinalBid dashboardFinalBid) {
		getDashboardFinalBids().add(dashboardFinalBid);
		dashboardFinalBid.setRequestStatus(this);

		return dashboardFinalBid;
	}

	public DashboardFinalBid removeDashboardFinalBid(DashboardFinalBid dashboardFinalBid) {
		getDashboardFinalBids().remove(dashboardFinalBid);
		dashboardFinalBid.setRequestStatus(null);

		return dashboardFinalBid;
	}

	public List<DashboardFinalBidsAuditTrial> getDashboardFinalBidsAuditTrials() {
		return this.dashboardFinalBidsAuditTrials;
	}

	public void setDashboardFinalBidsAuditTrials(List<DashboardFinalBidsAuditTrial> dashboardFinalBidsAuditTrials) {
		this.dashboardFinalBidsAuditTrials = dashboardFinalBidsAuditTrials;
	}

	public DashboardFinalBidsAuditTrial addDashboardFinalBidsAuditTrial(DashboardFinalBidsAuditTrial dashboardFinalBidsAuditTrial) {
		getDashboardFinalBidsAuditTrials().add(dashboardFinalBidsAuditTrial);
		dashboardFinalBidsAuditTrial.setRequestStatus(this);

		return dashboardFinalBidsAuditTrial;
	}

	public DashboardFinalBidsAuditTrial removeDashboardFinalBidsAuditTrial(DashboardFinalBidsAuditTrial dashboardFinalBidsAuditTrial) {
		getDashboardFinalBidsAuditTrials().remove(dashboardFinalBidsAuditTrial);
		dashboardFinalBidsAuditTrial.setRequestStatus(null);

		return dashboardFinalBidsAuditTrial;
	}

	public List<PlaceCashRequest> getPlaceCashRequests() {
		return this.placeCashRequests;
	}

	public void setPlaceCashRequests(List<PlaceCashRequest> placeCashRequests) {
		this.placeCashRequests = placeCashRequests;
	}

	public PlaceCashRequest addPlaceCashRequest(PlaceCashRequest placeCashRequest) {
		getPlaceCashRequests().add(placeCashRequest);
		placeCashRequest.setRequestStatus(this);

		return placeCashRequest;
	}

	public PlaceCashRequest removePlaceCashRequest(PlaceCashRequest placeCashRequest) {
		getPlaceCashRequests().remove(placeCashRequest);
		placeCashRequest.setRequestStatus(null);

		return placeCashRequest;
	}

	public List<PlaceCashRequestAuditTrial> getPlaceCashRequestAuditTrials() {
		return this.placeCashRequestAuditTrials;
	}

	public void setPlaceCashRequestAuditTrials(List<PlaceCashRequestAuditTrial> placeCashRequestAuditTrials) {
		this.placeCashRequestAuditTrials = placeCashRequestAuditTrials;
	}

	public PlaceCashRequestAuditTrial addPlaceCashRequestAuditTrial(PlaceCashRequestAuditTrial placeCashRequestAuditTrial) {
		getPlaceCashRequestAuditTrials().add(placeCashRequestAuditTrial);
		placeCashRequestAuditTrial.setRequestStatus(this);

		return placeCashRequestAuditTrial;
	}

	public PlaceCashRequestAuditTrial removePlaceCashRequestAuditTrial(PlaceCashRequestAuditTrial placeCashRequestAuditTrial) {
		getPlaceCashRequestAuditTrials().remove(placeCashRequestAuditTrial);
		placeCashRequestAuditTrial.setRequestStatus(null);

		return placeCashRequestAuditTrial;
	}

	public List<PlaceCashRequestSwap> getPlaceCashRequestSwaps() {
		return this.placeCashRequestSwaps;
	}

	public void setPlaceCashRequestSwaps(List<PlaceCashRequestSwap> placeCashRequestSwaps) {
		this.placeCashRequestSwaps = placeCashRequestSwaps;
	}

	public PlaceCashRequestSwap addPlaceCashRequestSwap(PlaceCashRequestSwap placeCashRequestSwap) {
		getPlaceCashRequestSwaps().add(placeCashRequestSwap);
		placeCashRequestSwap.setRequestStatus(this);

		return placeCashRequestSwap;
	}

	public PlaceCashRequestSwap removePlaceCashRequestSwap(PlaceCashRequestSwap placeCashRequestSwap) {
		getPlaceCashRequestSwaps().remove(placeCashRequestSwap);
		placeCashRequestSwap.setRequestStatus(null);

		return placeCashRequestSwap;
	}

	/*public List<RequestStatusAuditTrail> getRequestStatusAuditTrails() {
		return requestStatusAuditTrails;
	}

	public void setRequestStatusAuditTrails(List<RequestStatusAuditTrail> requestStatusAuditTrails) {
		this.requestStatusAuditTrails = requestStatusAuditTrails;
	}*/

	/*public List<Place_cash_Request_swap_audit_trial> getPlaceCashRequestSwapAuditTrials() {
		return this.placeCashRequestSwapAuditTrials;
	}

	public void setPlaceCashRequestSwapAuditTrials(List<Place_cash_Request_swap_audit_trial> placeCashRequestSwapAuditTrials) {
		this.placeCashRequestSwapAuditTrials = placeCashRequestSwapAuditTrials;
	}

	public Place_cash_Request_swap_audit_trial addPlaceCashRequestSwapAuditTrial(Place_cash_Request_swap_audit_trial placeCashRequestSwapAuditTrial) {
		getPlaceCashRequestSwapAuditTrials().add(placeCashRequestSwapAuditTrial);
		placeCashRequestSwapAuditTrial.setRequestStatus(this);

		return placeCashRequestSwapAuditTrial;
	}

	public Place_cash_Request_swap_audit_trial removePlaceCashRequestSwapAuditTrial(Place_cash_Request_swap_audit_trial placeCashRequestSwapAuditTrial) {
		getPlaceCashRequestSwapAuditTrials().remove(placeCashRequestSwapAuditTrial);
		placeCashRequestSwapAuditTrial.setRequestStatus(null);

		return placeCashRequestSwapAuditTrial;
	}*/

}