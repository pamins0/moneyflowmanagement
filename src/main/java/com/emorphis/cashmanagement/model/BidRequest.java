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
import javax.persistence.Transient;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The persistent class for the bid_request database table.
 * 
 */
@Entity
@Table(name = "bid_request")
@NamedQuery(name = "BidRequest.findAll", query = "SELECT b FROM BidRequest b")
public class BidRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	/*@OneToMany(mappedBy = "bidRequestId", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<BidRequestAuditTrail> bidRequestAuditTrails;*/

	@ManyToOne
	@JoinColumn(name = "request_placed_by_id")
	@JsonIgnore
	private BranchManagement requestPlacedById;

	@Column(name = "request_place_date")
	private Date requestPlaceDate;

	@Column(name = "request_type")
	private String requestType;

	@Column(name = "amount")
	private BigInteger amount;

	@Column(name = "dn_2000", nullable = true)
	private Integer dn2000;

	@Column(name = "dn_500", nullable = true)
	private Integer dn500;

	@Column(name = "dn_100", nullable = true)
	private Integer dn100;

	@Column(name = "dn_50", nullable = true)
	private Integer dn50;

	@Column(name = "dn_20", nullable = true)
	private Integer dn20;

	@Column(name = "dn_10", nullable = true)
	private Integer dn10;

	@Column(name = "dn_5", nullable = true)
	private Integer dn5;

	@Column(name = "dn_2", nullable = true)
	private Integer dn2;

	@Column(name = "dn_1", nullable = true)
	private Integer dn1;

	@ManyToOne
	@JoinColumn(name = "request_accept_by_id", nullable = true)
	@JsonIgnore
	private BranchManagement requestAcceptById;

	@OneToMany(mappedBy = "bidRequestId", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<BidRequestedTo> bidRequestedTo;

	@OneToMany(mappedBy = "bidRequestId", cascade = CascadeType.ALL)
	@JsonIgnore
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<BidRequestApproval> bidRequestApprovals;

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

	@Column(name = "deleted")
	private byte deleted;

	@Column(name = "sent")
	private byte sent;

	@Column(name = "receive")
	private byte receive;

	@Column(name = "ip")
	private String ip;

	private String hierarchyControlId;

	@Transient
	private List<String> bidRequestedToBranchIdList;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/*public List<BidRequestAuditTrail> getBidRequestAuditTrails() {
		return bidRequestAuditTrails;
	}

	public void setBidRequestAuditTrails(List<BidRequestAuditTrail> bidRequestAuditTrails) {
		this.bidRequestAuditTrails = bidRequestAuditTrails;
	}*/

	public BranchManagement getRequestPlacedById() {
		return requestPlacedById;
	}

	public void setRequestPlacedById(BranchManagement requestPlacedById) {
		this.requestPlacedById = requestPlacedById;
	}

	public Date getRequestPlaceDate() {
		return requestPlaceDate;
	}

	public void setRequestPlaceDate(Date requestPlaceDate) {
		this.requestPlaceDate = requestPlaceDate;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public BigInteger getAmount() {
		return amount;
	}

	public void setAmount(BigInteger amount) {
		this.amount = amount;
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

	public BranchManagement getRequestAcceptById() {
		return requestAcceptById;
	}

	public void setRequestAcceptById(BranchManagement requestAcceptById) {
		this.requestAcceptById = requestAcceptById;
	}

	public List<BidRequestedTo> getBidRequestedTo() {
		return bidRequestedTo;
	}

	public void setBidRequestedTo(List<BidRequestedTo> bidRequestedTo) {
		this.bidRequestedTo = bidRequestedTo;
	}

	public String getRequestStatus() {
		return requestStatus;
	}

	public void setRequestStatus(String requestStatus) {
		this.requestStatus = requestStatus;
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

	public byte getSent() {
		return sent;
	}

	public void setSent(byte sent) {
		this.sent = sent;
	}

	public byte getReceive() {
		return receive;
	}

	public void setReceive(byte receive) {
		this.receive = receive;
	}

	public String getHierarchyControlId() {
		return hierarchyControlId;
	}

	public void setHierarchyControlId(String hierarchyControlId) {
		this.hierarchyControlId = hierarchyControlId;
	}

	public List<String> getBidRequestedToBranchIdList() {
		return bidRequestedToBranchIdList;
	}

	public void setBidRequestedToBranchIdList(List<String> bidRequestedToBranchIdList) {
		this.bidRequestedToBranchIdList = bidRequestedToBranchIdList;
	}

	public Integer getDn10() {
		return dn10;
	}

	public void setDn10(Integer dn10) {
		this.dn10 = dn10;
	}

	public List<BidRequestApproval> getBidRequestApprovals() {
		return bidRequestApprovals;
	}

	public void setBidRequestApprovals(List<BidRequestApproval> bidRequestApprovals) {
		this.bidRequestApprovals = bidRequestApprovals;
	}

}
