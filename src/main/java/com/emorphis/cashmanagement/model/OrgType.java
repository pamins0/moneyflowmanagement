package com.emorphis.cashmanagement.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The persistent class for the org_type database table.
 * 
 */
@Entity
@Table(name = "org_type")
@FilterDef(name = "test", defaultCondition = "deleted=0")
@NamedQuery(name = "OrgType.findAll", query = "SELECT o FROM OrgType o")
public class OrgType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
/*	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)*/
	private String id;
	
	/*@OneToMany(mappedBy="orgTypeId",cascade = CascadeType.ALL)
	@JsonIgnore
	private List<OrgTypeAuditTrail> orgTypeAuditTrails;*/

	@Column(name = "created_by")
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_time")
	private Date createdTime;

	private byte deleted;

	@Column(name = "modified_by")
	private String modifiedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_time")
	private Date modifiedTime;

	@Column(name = "org_detail")
	private String orgDetail;

	@NotNull
	@NotBlank(message="Required")
	@Column(name = "org_type")
	private String orgType;

	@Filter(name = "test")
	@OneToMany(mappedBy = "orgType", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<OrgManagement> orgManagements;
	
/*	@OneToMany(mappedBy = "orgType")
	@JsonIgnore
	private List<OrgManagementAuditTrail> orgManagementAuditTrails;
*/
	@Column(name="ip")
	private String ip;
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public OrgType() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreatedTime() {
		return this.createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public byte getDeleted() {
		return this.deleted;
	}

	public void setDeleted(byte deleted) {
		this.deleted = deleted;
	}

	public Date getModifiedTime() {
		return this.modifiedTime;
	}

	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
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
	
	public String getOrgDetail() {
		return this.orgDetail;
	}

	public void setOrgDetail(String orgDetail) {
		this.orgDetail = orgDetail;
	}

	public String getOrgType() {
		return this.orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public Set<OrgManagement> getOrgManagements() {
		return orgManagements;
	}

	public void setOrgManagements(Set<OrgManagement> orgManagements) {
		this.orgManagements = orgManagements;
	}

	public OrgManagement addOrgManagement(OrgManagement orgManagement) {
		getOrgManagements().add(orgManagement);
		orgManagement.setOrgType(this);

		return orgManagement;
	}

	public OrgManagement removeOrgManagement(OrgManagement orgManagement) {
		getOrgManagements().remove(orgManagement);
		orgManagement.setOrgType(null);

		return orgManagement;
	}

	/*public List<OrgTypeAuditTrail> getOrgTypeAuditTrails() {
		return orgTypeAuditTrails;
	}

	public void setOrgTypeAuditTrails(List<OrgTypeAuditTrail> orgTypeAuditTrails) {
		this.orgTypeAuditTrails = orgTypeAuditTrails;
	}*/

	/*public List<OrgManagementAuditTrail> getOrgManagementAuditTrails() {
		return this.orgManagementAuditTrails;
	}

	public void setOrgManagementAuditTrails(List<OrgManagementAuditTrail> orgManagementAuditTrails) {
		this.orgManagementAuditTrails = orgManagementAuditTrails;
	}

	public OrgManagementAuditTrail addOrgManagementAuditTrail(OrgManagementAuditTrail orgManagementAuditTrail) {
		getOrgManagementAuditTrails().add(orgManagementAuditTrail);
		orgManagementAuditTrail.setOrgType(this);

		return orgManagementAuditTrail;
	}

	public OrgManagementAuditTrail removeOrgManagementAuditTrail(OrgManagementAuditTrail orgManagementAuditTrail) {
		getOrgManagementAuditTrails().remove(orgManagementAuditTrail);
		orgManagementAuditTrail.setOrgType(null);

		return orgManagementAuditTrail;
	}*/

	/*
	 * @Override public String toString() { return "OrgType [id=" + id +
	 * ", createdBy=" + createdBy + ", createdTime=" + createdTime +
	 * ", deleted=" + deleted + ", modifiedBy=" + modifiedBy + ", modifiedTime="
	 * + modifiedTime + ", orgDetail=" + orgDetail + ", orgType=" + orgType +
	 * ", orgManagements=" + orgManagements + ", orgManagementAuditTrails=" +
	 * orgManagementAuditTrails + "]"; }
	 */

	
	
}