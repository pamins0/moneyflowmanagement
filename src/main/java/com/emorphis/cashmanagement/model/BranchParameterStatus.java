package com.emorphis.cashmanagement.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The persistent class for the branch_parameter_status database table.
 * 
 */
@Entity
@Table(name = "branch_parameter_status")
@NamedQuery(name = "BranchParameterStatus.findAll", query = "SELECT b FROM BranchParameterStatus b")
public class BranchParameterStatus extends CommonModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	/*
	 * @GeneratedValue(strategy = GenerationType.IDENTITY)
	 * 
	 * @Column(unique = true, nullable = false)
	 */
	private String id;

	/*@OneToMany(mappedBy="branchParamStatusId",cascade = CascadeType.ALL)
	@JsonIgnore
	private List<BranchParameterStatusAuditTrial> branchParameterStatusAuditTrials;*/
	
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

	@Column(name = "total")
	private Double total;

	@Column(name = "created_by")
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_time")
	private Date createdTime;

	@Column(name = "modified_by")
	private String modifiedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_time")
	private Date modifiedTime;

	@Column(name = "status")
	private String status;

	@Column(name = "ex_others")
	private Double others;

	@Column(name = "approverflag")
	private Integer approverFlag;

	@Column(name = "eod_hault_Time")
	private BigInteger eodHaultTime;

	@Column(name = "processed_by")
	private String processedBy;

	// bi-directional many-to-one association to BranchManagement
	@ManyToOne
	@JoinColumn(name = "branch_id")
	@JsonIgnore
	private BranchManagement branchManagement;

	// bi-directional many-to-one association to Branch_Parameter
	@ManyToOne
	@JoinColumn(name = "branch_parameter_id")
	@JsonIgnore
	private BranchParameter branchParameter;

	@Transient
	@JsonIgnore
	private List<BranchParameterStatus> branchParameterStatusList;
	
	@Transient
	private String prameterType;
	
	@Column(name="ip")
	private String ip;
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPrameterType() {
		return prameterType;
	}

	public void setPrameterType(String prameterType) {
		this.prameterType = prameterType;
	}

	public BranchParameterStatus() {
	}

	public String getProcessedBy() {
		return processedBy;
	}

	public void setProcessedBy(String processedBy) {
		this.processedBy = processedBy;
	}

	public BigInteger getEodHaultTime() {
		return eodHaultTime;
	}

	public void setEodHaultTime(BigInteger eodHaultTime) {
		this.eodHaultTime = eodHaultTime;
	}

	public List<BranchParameterStatus> getBranchParameterStatusList() {
		return branchParameterStatusList;
	}

	public void setBranchParameterStatusList(List<BranchParameterStatus> branchParameterStatusList) {
		this.branchParameterStatusList = branchParameterStatusList;
	}

	public Double getOthers() {
		return others;
	}

	public void setOthers(Double others) {
		this.others = others;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getModifiedTime() {
		return modifiedTime;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getDc1() {
		return dc1;
	}

	public void setDc1(Integer dc1) {
		this.dc1 = dc1;
	}

	public Integer getDc10() {
		return dc10;
	}

	public void setDc10(Integer dc10) {
		this.dc10 = dc10;
	}

	public Integer getDc2() {
		return dc2;
	}

	public void setDc2(Integer dc2) {
		this.dc2 = dc2;
	}

	public Integer getDc5() {
		return dc5;
	}

	public void setDc5(Integer dc5) {
		this.dc5 = dc5;
	}

	public Integer getDn10() {
		return dn10;
	}

	public void setDn10(Integer dn10) {
		this.dn10 = dn10;
	}

	public Integer getDn100() {
		return dn100;
	}

	public void setDn100(Integer dn100) {
		this.dn100 = dn100;
	}

	public Integer getDn20() {
		return dn20;
	}

	public void setDn20(Integer dn20) {
		this.dn20 = dn20;
	}

	public Integer getDn2000() {
		return dn2000;
	}

	public void setDn2000(Integer dn2000) {
		this.dn2000 = dn2000;
	}

	public Integer getDn5() {
		return dn5;
	}

	public void setDn5(Integer dn5) {
		this.dn5 = dn5;
	}

	public Integer getDn50() {
		return dn50;
	}

	public void setDn50(Integer dn50) {
		this.dn50 = dn50;
	}

	public Integer getDn500() {
		return dn500;
	}

	public void setDn500(Integer dn500) {
		this.dn500 = dn500;
	}

	public Double getTotal() {
		return total;
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

	public Integer getApproverFlag() {
		return approverFlag;
	}

	public void setApproverFlag(Integer approverFlag) {
		this.approverFlag = approverFlag;
	}

	/*public List<BranchParameterStatusAuditTrial> getBranchParameterStatusAuditTrials() {
		return branchParameterStatusAuditTrials;
	}

	public void setBranchParameterStatusAuditTrials(
			List<BranchParameterStatusAuditTrial> branchParameterStatusAuditTrials) {
		this.branchParameterStatusAuditTrials = branchParameterStatusAuditTrials;
	}*/

}