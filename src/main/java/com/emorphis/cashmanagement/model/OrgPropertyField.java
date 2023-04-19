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
 * The persistent class for the org_property_field database table.
 * 
 */
@Entity
@Table(name="org_property_field")
@NamedQuery(name="OrgPropertyField.findAll", query="SELECT o FROM OrgPropertyField o")
public class OrgPropertyField implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	
	/*@OneToMany(mappedBy="orgPropertyFieldId",cascade = CascadeType.ALL)
	@JsonIgnore
	private List<OrgPropertyFieldAuditTrail> orgPropertyFieldAuditTrails;*/

	@Column(name="key_property")
	private String keyProperty;

	@Column(name="value_property")
	private String valueProperty;

	//bi-directional many-to-one association to OrgManagement
	@ManyToOne
	@JoinColumn(name="om_id")
	private OrgManagement orgManagement;
	
	@Column(name="ip")
	private String ip;
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public OrgPropertyField() {
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

	public OrgManagement getOrgManagement() {
		return this.orgManagement;
	}

	public void setOrgManagement(OrgManagement orgManagement) {
		this.orgManagement = orgManagement;
	}

	/*public List<OrgPropertyFieldAuditTrail> getOrgPropertyFieldAuditTrails() {
		return orgPropertyFieldAuditTrails;
	}

	public void setOrgPropertyFieldAuditTrails(List<OrgPropertyFieldAuditTrail> orgPropertyFieldAuditTrails) {
		this.orgPropertyFieldAuditTrails = orgPropertyFieldAuditTrails;
	}*/

}