package com.emorphis.cashmanagement.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the branch_parameter database table.
 * 
 */
@Entity
@Table(name = "branch_parameter")
@NamedQuery(name = "BranchParameter.findAll", query = "SELECT b FROM BranchParameter b")
public class BranchParameter implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	
	/*@OneToMany(mappedBy="branchParameterId",cascade = CascadeType.ALL)
	@JsonIgnore
	private List<BranchParameterAuditTrail> branchParameterAuditTrails;*/

	@Column(name = "parameter_details")
	private String parameterDetails;

	@Column(name = "parameter_name")
	private String parameterName;

	@Column(name = "parameter_abbreviation")
	private String parameterAbbreviation;

	// bi-directional many-to-one association to BranchParameterStatus
	@OneToMany(mappedBy = "branchParameter",cascade = CascadeType.ALL)
	private List<BranchParameterStatus> branchParameterStatuses;
	
	@Column(name="ip")
	private String ip;
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public BranchParameter() {
	}

	public String getParameterAbbreviation() {
		return parameterAbbreviation;
	}

	public void setParameterAbbreviation(String parameterAbbreviation) {
		this.parameterAbbreviation = parameterAbbreviation;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParameterDetails() {
		return this.parameterDetails;
	}

	public void setParameterDetails(String parameterDetails) {
		this.parameterDetails = parameterDetails;
	}

	public String getParameterName() {
		return this.parameterName;
	}

	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}

	public List<BranchParameterStatus> getBranchParameterStatuses() {
		return this.branchParameterStatuses;
	}

	public void setBranchParameterStatuses(List<BranchParameterStatus> branchParameterStatuses) {
		this.branchParameterStatuses = branchParameterStatuses;
	}

	public BranchParameterStatus addBranchParameterStatus(BranchParameterStatus branchParameterStatus) {
		getBranchParameterStatuses().add(branchParameterStatus);
		branchParameterStatus.setBranchParameter(this);

		return branchParameterStatus;
	}

	public BranchParameterStatus removeBranchParameterStatus(BranchParameterStatus branchParameterStatus) {
		getBranchParameterStatuses().remove(branchParameterStatus);
		branchParameterStatus.setBranchParameter(null);

		return branchParameterStatus;
	}

	/*public List<BranchParameterAuditTrail> getBranchParameterAuditTrails() {
		return branchParameterAuditTrails;
	}

	public void setBranchParameterAuditTrails(List<BranchParameterAuditTrail> branchParameterAuditTrails) {
		this.branchParameterAuditTrails = branchParameterAuditTrails;
	}*/

	

}