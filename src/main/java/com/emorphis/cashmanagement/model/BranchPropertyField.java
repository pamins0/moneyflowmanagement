package com.emorphis.cashmanagement.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the branch_property_field database table.
 * 
 */
@Entity
@Table(name = "branch_property_field")
@NamedQuery(name = "BranchPropertyField.findAll", query = "SELECT b FROM BranchPropertyField b")
public class BranchPropertyField implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	/*@OneToMany(mappedBy="branchPropertyFieldId",cascade = CascadeType.ALL)
	@JsonIgnore
	private List<BranchPropertyFieldAuditTrail> branchPropertyFieldAuditTrails;*/
	
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
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public BranchPropertyField() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKeyProperty() {
		return this.keyProperty;
	}

	public void setKeyProperty(String keyProperty) {
		this.keyProperty = keyProperty;
	}

	public String getValueProperty() {
		return this.valueProperty;
	}

	public void setValueProperty(String valueProperty) {
		this.valueProperty = valueProperty;
	}

	public BranchManagement getBranchManagement() {
		return this.branchManagement;
	}

	public void setBranchManagement(BranchManagement branchManagement) {
		this.branchManagement = branchManagement;
	}

	/*public List<BranchPropertyFieldAuditTrail> getBranchPropertyFieldAuditTrails() {
		return branchPropertyFieldAuditTrails;
	}

	public void setBranchPropertyFieldAuditTrails(List<BranchPropertyFieldAuditTrail> branchPropertyFieldAuditTrails) {
		this.branchPropertyFieldAuditTrails = branchPropertyFieldAuditTrails;
	}*/

}