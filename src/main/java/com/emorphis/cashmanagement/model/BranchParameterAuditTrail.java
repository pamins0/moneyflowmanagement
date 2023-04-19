package com.emorphis.cashmanagement.model;

import java.io.Serializable;
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
/**
 * The persistent class for the branch_parameter_audit_trail database table.
 * 
 */
@Entity
@Table(name = "branch_parameter_audit_trail")
@NamedQuery(name = "BranchParameterAuditTrail.findAll", query = "SELECT b FROM BranchParameterAuditTrail b")
public class BranchParameterAuditTrail implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "MySequence", sequenceName = "BRANCH_PARAMETER_AUDIT_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="MySequence")
	private Integer id;
	
	/*@ManyToOne
	@JoinColumn(name="branch_parameter_id")
	@JsonIgnore
	private BranchParameter branchParameterId;*/
	
	
	@Column(name="branch_parameter_id")
	private String branchParameterId;
	
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
	
	@Column(name="operation")
	private String operation;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	/*public BranchParameter getBranchParameterId() {
		return branchParameterId;
	}

	public void setBranchParameterId(BranchParameter branchParameterId) {
		this.branchParameterId = branchParameterId;
	}*/

	public String getParameterDetails() {
		return parameterDetails;
	}

	public void setParameterDetails(String parameterDetails) {
		this.parameterDetails = parameterDetails;
	}

	public String getParameterName() {
		return parameterName;
	}

	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}

	public String getParameterAbbreviation() {
		return parameterAbbreviation;
	}

	public void setParameterAbbreviation(String parameterAbbreviation) {
		this.parameterAbbreviation = parameterAbbreviation;
	}

	public List<BranchParameterStatus> getBranchParameterStatuses() {
		return branchParameterStatuses;
	}

	public void setBranchParameterStatuses(List<BranchParameterStatus> branchParameterStatuses) {
		this.branchParameterStatuses = branchParameterStatuses;
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

	public String getBranchParameterId() {
		return branchParameterId;
	}

	public void setBranchParameterId(String branchParameterId) {
		this.branchParameterId = branchParameterId;
	}
	
	
}
