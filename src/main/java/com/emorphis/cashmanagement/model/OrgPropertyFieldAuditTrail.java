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

/**
 * The persistent class for the org_property_field_audit_trail database table.
 * 
 */
@Entity
@Table(name="org_property_field_audit_trail")
@NamedQuery(name="OrgPropertyFieldAuditTrail.findAll", query="SELECT o FROM OrgPropertyFieldAuditTrail o")
public class OrgPropertyFieldAuditTrail implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "MySequence", sequenceName = "ORG_PROPERTY_FIELD_AUDIT_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="MySequence")
	private Integer id;
	
	/*@ManyToOne
	@JoinColumn(name="org_property_field_Id")
	@JsonIgnore
	private OrgPropertyField orgPropertyFieldId;*/
	
	
	@Column(name="org_property_field_Id")
	private String orgPropertyFieldId;
	
	@Column(name="key_property")
	private String keyProperty;

	@Column(name="value_property")
	private String valueProperty;

	//bi-directional many-to-one association to OrgManagement
	@ManyToOne
	@JoinColumn(name="om_id")
	private OrgManagement orgManagement;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_time")
	private Date create_Time;

	@Column(name="created_by")
	private String createdBy;
	
	@Column(name = "modified_by")
	private String modifiedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_time")
	private Date modifiedTime;
	
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


	/*public OrgPropertyField getOrgPropertyFieldId() {
		return orgPropertyFieldId;
	}

	public void setOrgPropertyFieldId(OrgPropertyField orgPropertyFieldId) {
		this.orgPropertyFieldId = orgPropertyFieldId;
	}*/

	public String getKeyProperty() {
		return keyProperty;
	}

	public void setKeyProperty(String keyProperty) {
		this.keyProperty = keyProperty;
	}

	public String getValueProperty() {
		return valueProperty;
	}

	public void setValueProperty(String valueProperty) {
		this.valueProperty = valueProperty;
	}

	public OrgManagement getOrgManagement() {
		return orgManagement;
	}

	public void setOrgManagement(OrgManagement orgManagement) {
		this.orgManagement = orgManagement;
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

	public String getOrgPropertyFieldId() {
		return orgPropertyFieldId;
	}

	public void setOrgPropertyFieldId(String orgPropertyFieldId) {
		this.orgPropertyFieldId = orgPropertyFieldId;
	}
	
	
}
