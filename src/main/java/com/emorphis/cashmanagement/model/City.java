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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The persistent class for the city database table.
 * 
 */
@Entity
@Table(name="city")
@NamedQuery(name = "City.findAll", query = "SELECT c FROM City c")
public class City implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "MySequence", sequenceName = "CITY_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="MySequence")
	private Integer id;
	
	/*@OneToMany(mappedBy="cityId",cascade = CascadeType.ALL)
	@JsonIgnore
	private List<CityAuditTrail> cityAuditTrails;
*/
	@ManyToOne
	@JoinColumn(name="state_id")
	@JsonIgnore
	private State state;
	
	@Column(name="city_name")
	private String cityName;
	
	@Column(name="city_code")
    private String cityCode;
	
	@Column(name="deleted")
	private byte deleted;
	
	@Column(name="created_by")
	 private String createdBy;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_time")
	 private Date createdTime;
	
	@Column(name="modified_by")
	 private String modifiedBy;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="modified_time")
     private Date modifiedTime;
	
	@OneToMany(mappedBy="city",cascade = CascadeType.ALL)
	@JsonIgnore
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<BranchManagement> branchManagements;
	
	@OneToMany(mappedBy="city",cascade = CascadeType.ALL)
	@JsonIgnore
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<OrgManagement> orgManagements;
	
	@Column(name="ip")
	private String ip;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public byte getDeleted() {
		return deleted;
	}

	public void setDeleted(byte deleted) {
		this.deleted = deleted;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
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

	public List<BranchManagement> getBranchManagements() {
		return branchManagements;
	}

	public void setBranchManagements(List<BranchManagement> branchManagements) {
		this.branchManagements = branchManagements;
	}

	public List<OrgManagement> getOrgManagements() {
		return orgManagements;
	}

	public void setOrgManagements(List<OrgManagement> orgManagements) {
		this.orgManagements = orgManagements;
	}

	/*public List<CityAuditTrail> getCityAuditTrails() {
		return cityAuditTrails;
	}

	public void setCityAuditTrails(List<CityAuditTrail> cityAuditTrails) {
		this.cityAuditTrails = cityAuditTrails;
	}*/

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}


}
