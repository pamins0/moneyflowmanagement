package com.emorphis.cashmanagement.model;

import java.io.Serializable;
import java.math.BigInteger;
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
 * The persistent class for the branch_parameter_status_audit_trial database
 * table.
 * 
 */
@Entity
@Table(name = "branch_parameter_status_audit")
@NamedQuery(name = "BranchParameterStatusAuditTrial.findAll", query = "SELECT b FROM BranchParameterStatusAuditTrial b")
public class BranchParameterStatusAuditTrial implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "MySequence", sequenceName = "BR_PARAM_STATUS_AUDIT_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="MySequence")
	private Integer id;

	/*@ManyToOne
	@JoinColumn(name = "branch_param_status_id")
	@JsonIgnore
	private BranchParameterStatus branchParamStatusId;*/

	
	@Column(name = "branch_param_status_id")
	private String branchParamStatusId;
	
	@Column(name = "created_by")
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_time")
	private Date createdTime;

	/*@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dashboard_created_time")
	private Date dashboardFinalBidCreatedTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dashboard_modified_time")
	private Date dashboardFinalBidModifiedTime;*/

	@Column(name = "dc_1")
	private Integer dc1;

	@Column(name = "dc_10")
	private Integer dc10;

	@Column(name = "dc_2")
	private Integer dc2;

	@Column(name = "dc_5")
	private Integer dc5;

	@Column(name = "dn_10")
	private Integer dn10;

	@Column(name = "dn_100")
	private Integer dn100;

	@Column(name = "dn_20")
	private Integer dn20;

	@Column(name = "dn_2000")
	private Integer dn2000;

	@Column(name = "dn_5")
	private Integer dn5;

	@Column(name = "dn_50")
	private Integer dn50;

	@Column(name = "dn_500")
	private Integer dn500;

	@Column(name = "modified_by")
	private String modifiedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_time")
	private Date modifiedTime;

//	private String operations;

	@Column(name = "ex_others")
	private Double others;

	private String status;

	private Double total;

	@Column(name = "approverflag")
	private int approverFlag;
	
	@Column(name = "eod_hault_Time")
	private BigInteger eodHaultTime;

	@Column(name = "processed_by")
	private String processedBy;

	// bi-directional many-to-one association to BranchManagement
	@ManyToOne
	@JoinColumn(name = "branch_id")
	private BranchManagement branchManagement;

	// bi-directional many-to-one association to Branch_Parameter
	@ManyToOne
	@JoinColumn(name = "branch_parameter_id")
	private BranchParameter branchParameter;
	
	@Column(name="ip")
	private String ip;
	
	@Column(name="operation")
	private String operation;
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public BranchParameterStatusAuditTrial() {
	}


	/*public BranchParameterStatus getBranchParamStatusId() {
		return branchParamStatusId;
	}

	public void setBranchParamStatusId(BranchParameterStatus branchParamStatusId) {
		this.branchParamStatusId = branchParamStatusId;
	}*/

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreatedTime() {
		return this.createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	/*public Date getDashboardFinalBidCreatedTime() {
		return this.dashboardFinalBidCreatedTime;
	}

	public void setDashboardFinalBidCreatedTime(Date dashboardFinalBidCreatedTime) {
		this.dashboardFinalBidCreatedTime = dashboardFinalBidCreatedTime;
	}

	public Date getDashboardFinalBidModifiedTime() {
		return this.dashboardFinalBidModifiedTime;
	}

	public void setDashboardFinalBidModifiedTime(Date dashboardFinalBidModifiedTime) {
		this.dashboardFinalBidModifiedTime = dashboardFinalBidModifiedTime;
	}*/

	public Integer getDc1() {
		return this.dc1;
	}

	public void setDc1(Integer dc1) {
		this.dc1 = dc1;
	}

	public Integer getDc10() {
		return this.dc10;
	}

	public void setDc10(Integer dc10) {
		this.dc10 = dc10;
	}

	public Integer getDc2() {
		return this.dc2;
	}

	public void setDc2(Integer dc2) {
		this.dc2 = dc2;
	}

	public Integer getDc5() {
		return this.dc5;
	}

	public void setDc5(Integer dc5) {
		this.dc5 = dc5;
	}

	public Integer getDn10() {
		return this.dn10;
	}

	public void setDn10(Integer dn10) {
		this.dn10 = dn10;
	}

	public Integer getDn100() {
		return this.dn100;
	}

	public void setDn100(Integer dn100) {
		this.dn100 = dn100;
	}

	public Integer getDn20() {
		return this.dn20;
	}

	public void setDn20(Integer dn20) {
		this.dn20 = dn20;
	}

	public Integer getDn2000() {
		return this.dn2000;
	}

	public void setDn2000(Integer dn2000) {
		this.dn2000 = dn2000;
	}

	public Integer getDn5() {
		return this.dn5;
	}

	public void setDn5(Integer dn5) {
		this.dn5 = dn5;
	}

	public Integer getDn50() {
		return this.dn50;
	}

	public void setDn50(Integer dn50) {
		this.dn50 = dn50;
	}

	public Integer getDn500() {
		return this.dn500;
	}

	public void setDn500(Integer dn500) {
		this.dn500 = dn500;
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
		return this.modifiedTime;
	}

	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

//	public String getOperations() {
//		return this.operations;
//	}
//
//	public void setOperations(String operations) {
//		this.operations = operations;
//	}

	public Double getOthers() {
		return this.others;
	}

	public void setOthers(Double others) {
		this.others = others;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Double getTotal() {
		return this.total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public BranchManagement getBranchManagement() {
		return this.branchManagement;
	}

	public void setBranchManagement(BranchManagement branchManagement) {
		this.branchManagement = branchManagement;
	}

	public BranchParameter getBranchParameter() {
		return this.branchParameter;
	}

	public void setBranchParameter(BranchParameter branchParameter) {
		this.branchParameter = branchParameter;
	}

	public int getApproverFlag() {
		return approverFlag;
	}

	public void setApproverFlag(int approverFlag) {
		this.approverFlag = approverFlag;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public BigInteger getEodHaultTime() {
		return eodHaultTime;
	}

	public void setEodHaultTime(BigInteger eodHaultTime) {
		this.eodHaultTime = eodHaultTime;
	}

	public String getProcessedBy() {
		return processedBy;
	}

	public void setProcessedBy(String processedBy) {
		this.processedBy = processedBy;
	}

	public String getBranchParamStatusId() {
		return branchParamStatusId;
	}

	public void setBranchParamStatusId(String branchParamStatusId) {
		this.branchParamStatusId = branchParamStatusId;
	}

}