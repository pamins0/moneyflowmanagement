package com.emorphis.cashmanagement.model;

import java.io.Serializable;
import java.math.BigDecimal;
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
 * The persistent class for the Commision_Rate database table.
 * 
 */
@Entity
@Table(name = "commision_rate")
@NamedQuery(name = "CommisionRate.findAll", query = "SELECT c FROM CommisionRate c")
public class CommisionRate implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	
	/*@OneToMany(mappedBy="commisionRateId",cascade = CascadeType.ALL)
	@JsonIgnore
	private List<CommisionRateAuditTrail> commisionRateAuditTrails;*/

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

	
	/*@OneToMany(mappedBy = "commisionRate")
	@JsonIgnore
	private List<CommisionRateAuditTrial> commisionRateAuditTrials;
*/
	@Column(name="ip")
	private String ip;
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public CommisionRate() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public byte getActive_Status() {
		return this.active_Status;
	}

	public void setActive_Status(byte active_Status) {
		this.active_Status = active_Status;
	}

	public BigDecimal getCommission_Rate() {
		return this.commission_Rate;
	}

	public void setCommission_Rate(BigDecimal commission_Rate) {
		this.commission_Rate = commission_Rate;
	}
	
	public Date getCreated_Time() {
		return this.created_Time;
	}

	public void setCreated_Time(Date created_Time) {
		this.created_Time = created_Time;
	}
	
	public Date getModified_Time() {
		return this.modified_Time;
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

	public ModelType getModelType() {
		return this.modelType;
	}

	public void setModelType(ModelType modelType) {
		this.modelType = modelType;
	}

	/*public List<CommisionRateAuditTrail> getCommisionRateAuditTrails() {
		return commisionRateAuditTrails;
	}

	public void setCommisionRateAuditTrails(List<CommisionRateAuditTrail> commisionRateAuditTrails) {
		this.commisionRateAuditTrails = commisionRateAuditTrails;
	}*/

	/*public List<CommisionRateAuditTrial> getCommisionRateAuditTrials() {
		return this.commisionRateAuditTrials;
	}

	public void setCommisionRateAuditTrials(List<CommisionRateAuditTrial> commisionRateAuditTrials) {
		this.commisionRateAuditTrials = commisionRateAuditTrials;
	}

	public CommisionRateAuditTrial addCommisionRateAuditTrial(CommisionRateAuditTrial commisionRateAuditTrial) {
		getCommisionRateAuditTrials().add(commisionRateAuditTrial);
		commisionRateAuditTrial.setCommisionRate(this);

		return commisionRateAuditTrial;
	}

	public CommisionRateAuditTrial removeCommisionRateAuditTrial(CommisionRateAuditTrial commisionRateAuditTrial) {
		getCommisionRateAuditTrials().remove(commisionRateAuditTrial);
		commisionRateAuditTrial.setCommisionRate(null);

		return commisionRateAuditTrial;
	}*/

}