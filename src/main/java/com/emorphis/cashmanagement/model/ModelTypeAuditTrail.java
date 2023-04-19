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
 * The persistent class for the model_type_audit_trail database table.
 * 
 */
@Entity
@Table(name = "model_type_audit_trail")
@NamedQuery(name="ModelTypeAuditTrail.findAll", query="SELECT m FROM ModelTypeAuditTrail m")
public class ModelTypeAuditTrail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "MySequence", sequenceName = "MODEL_TYPE_AUDIT_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="MySequence")
	private Integer id;
	
	/*@ManyToOne
	@JoinColumn(name="model_type_id")
	@JsonIgnore
	private ModelType modelTypeId;*/
	
	
	@Column(name="model_type_id")
	private String modelTypeId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_time")
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


		/*public ModelType getModelTypeId() {
			return modelTypeId;
		}

		public void setModelTypeId(ModelType modelTypeId) {
			this.modelTypeId = modelTypeId;
		}*/

		public Date getCreate_Time() {
			return create_Time;
		}

		public void setCreate_Time(Date create_Time) {
			this.create_Time = create_Time;
		}

		public String getCreatedBy() {
			return createdBy;
		}

		public void setCreatedBy(String createdBy) {
			this.createdBy = createdBy;
		}

		public byte getModel_Active_Status() {
			return model_Active_Status;
		}

		public void setModel_Active_Status(byte model_Active_Status) {
			this.model_Active_Status = model_Active_Status;
		}

		public String getModel_Type() {
			return model_Type;
		}

		public void setModel_Type(String model_Type) {
			this.model_Type = model_Type;
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

		public List<CommisionRate> getCommisionRates() {
			return commisionRates;
		}

		public void setCommisionRates(List<CommisionRate> commisionRates) {
			this.commisionRates = commisionRates;
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

		public String getModelTypeId() {
			return modelTypeId;
		}

		public void setModelTypeId(String modelTypeId) {
			this.modelTypeId = modelTypeId;
		}
		
		
}
