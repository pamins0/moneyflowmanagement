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

import com.fasterxml.jackson.annotation.JsonIgnore;
/**
 * The persistent class for the bid_requested_approval_audit database table.
 * 
 */
@Entity
@Table(name = "bid_request_approval_audit")
@NamedQuery(name = "BidRequestApprovalAuditTrail.findAll", query = "SELECT b FROM BidRequestApprovalAuditTrail b")
public class BidRequestApprovalAuditTrail implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "MySequence", sequenceName = "BID_REQ_APPROVAL_AUDIT_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="MySequence")
	private Integer id;
	
	/*@ManyToOne
	@JoinColumn(name="bid_request_approval_id")
	@JsonIgnore
	private BidRequestApproval bidRequestApprovalId; */
	
	@Column(name="bid_request_approval_id")
	private String bidRequestApprovalId; 
	
	@ManyToOne
	@JoinColumn(name="bid_request_id")
	@JsonIgnore
	private BidRequest bidRequestId;
	
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
	
	@Column(name="operation")
	private String operation;
	
	@Column(name="ip")
	private String ip;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	/*public BidRequestApproval getBidRequestApprovalId() {
		return bidRequestApprovalId;
	}

	public void setBidRequestApprovalId(BidRequestApproval bidRequestApprovalId) {
		this.bidRequestApprovalId = bidRequestApprovalId;
	}*/

	public BidRequest getBidRequestId() {
		return bidRequestId;
	}

	public void setBidRequestId(BidRequest bidRequestId) {
		this.bidRequestId = bidRequestId;
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

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getBidRequestApprovalId() {
		return bidRequestApprovalId;
	}

	public void setBidRequestApprovalId(String bidRequestApprovalId) {
		this.bidRequestApprovalId = bidRequestApprovalId;
	}
	
	
}
