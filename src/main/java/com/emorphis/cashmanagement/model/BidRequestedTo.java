package com.emorphis.cashmanagement.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
/**
 * The persistent class for the bid_requested_to database table.
 * 
 */
@Entity
@Table(name = "bid_requested_to")
@NamedQuery(name = "BidRequestedTo.findAll", query = "SELECT b FROM BidRequestedTo b")
public class BidRequestedTo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
	
	/*@OneToMany(mappedBy="bidRequestedToId",cascade = CascadeType.ALL)
	@JsonIgnore
	private List<BidRequestedToAuditTrail> bidRequestedToAuditTrails;*/
	

	@OneToMany(mappedBy = "bidRequestedTo", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<BidAcceptApproval> bidAcceptApprovals;
	
	@ManyToOne
	@JoinColumn(name="bid_request_id")
	@JsonIgnore
	private BidRequest bidRequestId;
	
	@ManyToOne
	@JoinColumn(name="request_to_branch_id")
	@JsonIgnore
	private BranchManagement requestToBranchId;
	
	@Column(name="accept_amount")
	private BigInteger acceptAmount;
	
	@Column(name = "dn_2000",nullable=true)
	private Integer dn2000;
	
	@Column(name = "dn_500",nullable=true)
	private Integer dn500;
	
	@Column(name = "dn_100",nullable=true)
	private Integer dn100;
	
	@Column(name = "dn_50",nullable=true)
	private Integer dn50;
	
	@Column(name = "dn_20",nullable=true)
	private Integer dn20;

	@Column(name = "dn_10",nullable=true)
	private Integer dn10;
	
	@Column(name = "dn_5",nullable=true)
	private Integer dn5;
	
	@Column(name = "dn_2",nullable=true)
	private Integer dn2;
	
	@Column(name = "dn_1",nullable=true)
	private Integer dn1;
	
	@Column(name="is_denomination_customized")
	private boolean denominationCustomized;
	
	@Column(name="is_bid_approve")
	private boolean bidApprove;

	@Column(name = "request_status")
	private String requestStatus;
	
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
	
	@Column(name="deleted")
	private byte deleted;
	
	@Column(name="ip")
	private String ip;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/*public List<BidRequestedToAuditTrail> getBidRequestedToAuditTrails() {
		return bidRequestedToAuditTrails;
	}

	public void setBidRequestedToAuditTrails(List<BidRequestedToAuditTrail> bidRequestedToAuditTrails) {
		this.bidRequestedToAuditTrails = bidRequestedToAuditTrails;
	}*/

	public BidRequest getBidRequestId() {
		return bidRequestId;
	}

	public void setBidRequestId(BidRequest bidRequestId) {
		this.bidRequestId = bidRequestId;
	}

	public BranchManagement getRequestToBranchId() {
		return requestToBranchId;
	}

	public void setRequestToBranchId(BranchManagement requestToBranchId) {
		this.requestToBranchId = requestToBranchId;
	}

	public BigInteger getAcceptAmount() {
		return acceptAmount;
	}

	public void setAcceptAmount(BigInteger acceptAmount) {
		this.acceptAmount = acceptAmount;
	}

	public Integer getDn2000() {
		return dn2000;
	}

	public void setDn2000(Integer dn2000) {
		this.dn2000 = dn2000;
	}

	public Integer getDn500() {
		return dn500;
	}

	public void setDn500(Integer dn500) {
		this.dn500 = dn500;
	}

	public Integer getDn100() {
		return dn100;
	}

	public void setDn100(Integer dn100) {
		this.dn100 = dn100;
	}

	public Integer getDn50() {
		return dn50;
	}

	public void setDn50(Integer dn50) {
		this.dn50 = dn50;
	}

	public Integer getDn20() {
		return dn20;
	}

	public void setDn20(Integer dn20) {
		this.dn20 = dn20;
	}

	public Integer getDn5() {
		return dn5;
	}

	public void setDn5(Integer dn5) {
		this.dn5 = dn5;
	}

	public Integer getDn2() {
		return dn2;
	}

	public void setDn2(Integer dn2) {
		this.dn2 = dn2;
	}

	public Integer getDn1() {
		return dn1;
	}

	public void setDn1(Integer dn1) {
		this.dn1 = dn1;
	}


	public boolean isDenominationCustomized() {
		return denominationCustomized;
	}

	public void setDenominationCustomized(boolean denominationCustomized) {
		this.denominationCustomized = denominationCustomized;
	}


	public boolean isBidApprove() {
		return bidApprove;
	}

	public void setBidApprove(boolean bidApprove) {
		this.bidApprove = bidApprove;
	}

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

	public byte getDeleted() {
		return deleted;
	}

	public void setDeleted(byte deleted) {
		this.deleted = deleted;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getDn10() {
		return dn10;
	}

	public void setDn10(Integer dn10) {
		this.dn10 = dn10;
	}

	public String getRequestStatus() {
		return requestStatus;
	}

	public void setRequestStatus(String requestStatus) {
		this.requestStatus = requestStatus;
	}

	public List<BidAcceptApproval> getBidAcceptApprovals() {
		return bidAcceptApprovals;
	}

	public void setBidAcceptApprovals(List<BidAcceptApproval> bidAcceptApprovals) {
		this.bidAcceptApprovals = bidAcceptApprovals;
	}
	
}
