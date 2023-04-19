package com.emorphis.cashmanagement.model;

import java.io.Serializable;
import java.math.BigDecimal;
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
 * The persistent class for the commision_rate_audit_trail database table.
 * 
 */
@Entity
@Table(name = "commision_rate_audit_trail")
@NamedQuery(name = "CommisionRateAuditTrail.findAll", query = "SELECT c FROM CommisionRateAuditTrail c")
public class CommisionRateAuditTrail implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "MySequence", sequenceName = "COMMISION_RATE_AUDIT_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="MySequence")
	private Integer id;
	
	/*@ManyToOne
	@JoinColumn(name="commision_rate_id")
	@JsonIgnore
	private CommisionRate commisionRateId;*/
	
	
	@Column(name="commision_rate_id")
	private String commisionRateId;
	
	@Column(name = "active_status")
	private byte active_Status;

	@Column(name = "commission_rate")
	private BigDecimal commission_Rate;

	@Column(name = "created_by")
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_time")
	private Date created_Time;

	@Column(name = "modified_by")
	private String modifiedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_time")
	private Date modified_Time;

	@ManyToOne
	@JoinColumn(name = "model_id")
	@JsonIgnore
	private ModelType modelType;
	
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

	
	/*public CommisionRate getCommisionRateId() {
		return commisionRateId;
	}

	public void setCommisionRateId(CommisionRate commisionRateId) {
		this.commisionRateId = commisionRateId;
	}*/

	public byte getActive_Status() {
		return active_Status;
	}

	public void setActive_Status(byte active_Status) {
		this.active_Status = active_Status;
	}

	public BigDecimal getCommission_Rate() {
		return commission_Rate;
	}

	public void setCommission_Rate(BigDecimal commission_Rate) {
		this.commission_Rate = commission_Rate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreated_Time() {
		return created_Time;
	}

	public void setCreated_Time(Date created_Time) {
		this.created_Time = created_Time;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModified_Time() {
		return modified_Time;
	}

	public void setModified_Time(Date modified_Time) {
		this.modified_Time = modified_Time;
	}

	public ModelType getModelType() {
		return modelType;
	}

	public void setModelType(ModelType modelType) {
		this.modelType = modelType;
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

	public String getCommisionRateId() {
		return commisionRateId;
	}

	public void setCommisionRateId(String commisionRateId) {
		this.commisionRateId = commisionRateId;
	}

	
}
