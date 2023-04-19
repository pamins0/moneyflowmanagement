package com.emorphis.cashmanagement.model;

import java.io.Serializable;
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

import com.fasterxml.jackson.annotation.JsonIgnore;
/**
 * The persistent class for the bid_requested_approval database table.
 * 
 */
@Entity
@Table(name = "bid_accept_approval")
@NamedQuery(name = "BidAcceptApproval.findAll", query = "SELECT b FROM BidAcceptApproval b")
public class BidAcceptApproval implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
	
//	@OneToMany(mappedBy="bidRequestApprovalId",cascade = CascadeType.ALL)
//	@JsonIgnore
//	private List<BidRequestedToAuditTrail> bidRequestedToAuditTrails;
	
	/*@OneToMany(mappedBy="bidRequestApprovalId")
	@JsonIgnore
	private List<BidAcceptApprovalAuditTrail> bidRequestApprovalAuditTrails;*/

	@ManyToOne
	@JoinColumn(name="bid_requested_to_id")
	@JsonIgnore
	private BidRequestedTo bidRequestedTo;
	
	@ManyToOne
	@JoinColumn(name="approvar_id")
	@JsonIgnore
	private User approvarId;
	
	@Column(name="approve_status")
	private byte approveStatus;
	
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

	

	public BidRequestedTo getBidRequestedTo() {
		return bidRequestedTo;
	}

	public void setBidRequestedTo(BidRequestedTo bidRequestedTo) {
		this.bidRequestedTo = bidRequestedTo;
	}

	public User getApprovarId() {
		return approvarId;
	}

	public void setApprovarId(User approvarId) {
		this.approvarId = approvarId;
	}

	public byte getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(byte approveStatus) {
		this.approveStatus = approveStatus;
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

	/*public List<BidAcceptApprovalAuditTrail> getBidAcceptApprovalAuditTrails() {
		return bidRequestApprovalAuditTrails;
	}

	public void setBidAcceptApprovalAuditTrails(List<BidAcceptApprovalAuditTrail> bidRequestApprovalAuditTrails) {
		this.bidRequestApprovalAuditTrails = bidRequestApprovalAuditTrails;
	}*/
	
}
