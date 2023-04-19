package com.emorphis.cashmanagement.model;

import java.io.Serializable;

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

/**
 * The persistent class for the branch_property_field_audit_trail database table.
 * 
 */
@Entity
@Table(name = "branch_property_field_audit")
@NamedQuery(name = "BranchPropertyFieldAuditTrail.findAll", query = "SELECT b FROM BranchPropertyFieldAuditTrail b")
public class BranchPropertyFieldAuditTrail implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "MySequence", sequenceName = "BRANCH_PRO_FIELD_AUDIT_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="MySequence")
	private String id;
	
	/*@ManyToOne
	@JoinColumn(name="branch_property_field_id")
	@JsonIgnore
	private BranchPropertyField branchPropertyFieldId;*/
	
	
	@Column(name="branch_property_field_id")
	private String branchPropertyFieldId;
	
	@Column(name = "key_property")
	private String keyProperty;

	@Column(name = "value_property")
	private String valueProperty;

	// bi-directional many-to-one association to BranchManagement
	@ManyToOne
	@JoinColumn(name = "branch_id")
	private BranchManagement branchManagement;

	@Column(name="ip")
	private String ip;
	
	@Column(name="operation")
	private String operation;

	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/*public BranchPropertyField getBranchPropertyFieldId() {
		return branchPropertyFieldId;
	}

	public void setBranchPropertyFieldId(BranchPropertyField branchPropertyFieldId) {
		this.branchPropertyFieldId = branchPropertyFieldId;
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

	public BranchManagement getBranchManagement() {
		return branchManagement;
	}

	public void setBranchManagement(BranchManagement branchManagement) {
		this.branchManagement = branchManagement;
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

	public String getBranchPropertyFieldId() {
		return branchPropertyFieldId;
	}

	public void setBranchPropertyFieldId(String branchPropertyFieldId) {
		this.branchPropertyFieldId = branchPropertyFieldId;
	}
	
	
}
