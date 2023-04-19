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
 * The persistent class for the Model_Type database table.
 * 
 */
@Entity
@Table(name = "model_type")
@NamedQuery(name="ModelType.findAll", query="SELECT m FROM ModelType m")
public class ModelType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	
	/*@OneToMany(mappedBy="modelTypeId",cascade = CascadeType.ALL)
	@JsonIgnore
	private List<ModelTypeAuditTrail> modelTypeAuditTrails;*/

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date create_Time;

	@Column(name="created_by")
	private String createdBy;

	@Column(name="model_active_status")
	private byte model_Active_Status;

	@Column(name="model_type")
	private String model_Type;

	@Column(name="modified_by")
	private String modifiedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="modified_time")
	private Date modified_Time;

	@OneToMany(mappedBy="modelType",cascade = CascadeType.ALL)
	@JsonIgnore
	private List<CommisionRate> commisionRates;

	/*@OneToMany(mappedBy="modelType")
	@JsonIgnore
	private List<CommisionRateAuditTrial> commisionRateAuditTrials;*/
	
/*	@OneToMany(mappedBy="modelType")
	@JsonIgnore
	private List<ModelTypeAuditTrial> modelTypeAuditTrials;
*/
	
	@Column(name="ip")
	private String ip;
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public ModelType() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreate_Time() {
		return this.create_Time;
	}

	public void setCreate_Time(Date create_Time) {
		this.create_Time = create_Time;
	}

	
	public byte getModel_Active_Status() {
		return this.model_Active_Status;
	}

	public void setModel_Active_Status(byte model_Active_Status) {
		this.model_Active_Status = model_Active_Status;
	}

	public String getModel_Type() {
		return this.model_Type;
	}

	public void setModel_Type(String model_Type) {
		this.model_Type = model_Type;
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

	public Date getModified_Time() {
		return this.modified_Time;
	}

	public void setModified_Time(Date modified_Time) {
		this.modified_Time = modified_Time;
	}

	public List<CommisionRate> getCommisionRates() {
		return this.commisionRates;
	}

	public void setCommisionRates(List<CommisionRate> commisionRates) {
		this.commisionRates = commisionRates;
	}

	public CommisionRate addCommisionRate(CommisionRate commisionRate) {
		getCommisionRates().add(commisionRate);
		commisionRate.setModelType(this);

		return commisionRate;
	}

	public CommisionRate removeCommisionRate(CommisionRate commisionRate) {
		getCommisionRates().remove(commisionRate);
		commisionRate.setModelType(null);

		return commisionRate;
	}

	/*public List<CommisionRateAuditTrial> getCommisionRateAuditTrials() {
		return this.commisionRateAuditTrials;
	}

	public void setCommisionRateAuditTrials(List<CommisionRateAuditTrial> commisionRateAuditTrials) {
		this.commisionRateAuditTrials = commisionRateAuditTrials;
	}

	public CommisionRateAuditTrial addCommisionRateAuditTrial(CommisionRateAuditTrial commisionRateAuditTrial) {
		getCommisionRateAuditTrials().add(commisionRateAuditTrial);
		commisionRateAuditTrial.setModelType(this);

		return commisionRateAuditTrial;
	}

	public CommisionRateAuditTrial removeCommisionRateAuditTrial(CommisionRateAuditTrial commisionRateAuditTrial) {
		getCommisionRateAuditTrials().remove(commisionRateAuditTrial);
		commisionRateAuditTrial.setModelType(null);

		return commisionRateAuditTrial;
	}*/

	/*public List<ModelTypeAuditTrial> getModelTypeAuditTrials() {
		return this.modelTypeAuditTrials;
	}

	public void setModelTypeAuditTrials(List<ModelTypeAuditTrial> modelTypeAuditTrials) {
		this.modelTypeAuditTrials = modelTypeAuditTrials;
	}

	public ModelTypeAuditTrial addModelTypeAuditTrial(ModelTypeAuditTrial modelTypeAuditTrial) {
		getModelTypeAuditTrials().add(modelTypeAuditTrial);
		modelTypeAuditTrial.setModelType(this);

		return modelTypeAuditTrial;
	}

	public ModelTypeAuditTrial removeModelTypeAuditTrial(ModelTypeAuditTrial modelTypeAuditTrial) {
		getModelTypeAuditTrials().remove(modelTypeAuditTrial);
		modelTypeAuditTrial.setModelType(null);

		return modelTypeAuditTrial;
	}
*/
	

	/*public List<ModelTypeAuditTrail> getModelTypeAuditTrails() {
		return modelTypeAuditTrails;
	}

	public void setModelTypeAuditTrails(List<ModelTypeAuditTrail> modelTypeAuditTrails) {
		this.modelTypeAuditTrails = modelTypeAuditTrails;
	}*/

}