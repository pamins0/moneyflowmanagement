package com.emorphis.cashmanagement.model;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

public class ReportsFilterCriteria {

	@NotNull
	private User user;

	@NotNull
	private BranchManagement management;

	@NotNull
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date fromDate;

	@NotNull
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date toDate;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public BranchManagement getManagement() {
		return management;
	}

	public void setManagement(BranchManagement management) {
		this.management = management;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

}
