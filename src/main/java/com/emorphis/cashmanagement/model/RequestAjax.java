package com.emorphis.cashmanagement.model;

import java.util.Set;

public class RequestAjax {

	String id;
	String dashboardId;
	String toBranchId;
	Double toBranchTotallAmount;
	String toBranchPosition;
	String fromBranchId;
	String placedRequestId;
	String amount;
	String message;

	Set<BranchManagement> branchManagementsList;

	BranchManagement branchManagement;

	BranchParameterStatus branchParameterStatus;

	PlaceCashRequestSwap placeCashRequestSwap;

	public PlaceCashRequestSwap getPlaceCashRequestSwap() {
		return placeCashRequestSwap;
	}

	public void setPlaceCashRequestSwap(PlaceCashRequestSwap placeCashRequestSwap) {
		this.placeCashRequestSwap = placeCashRequestSwap;
	}

	public BranchManagement getBranchManagement() {
		return branchManagement;
	}

	public void setBranchManagement(BranchManagement branchManagement) {
		this.branchManagement = branchManagement;
	}

	public Set<BranchManagement> getBranchManagementsList() {
		return branchManagementsList;
	}

	public void setBranchManagementsList(Set<BranchManagement> branchManagementsList) {
		this.branchManagementsList = branchManagementsList;
	}

	public BranchParameterStatus getBranchParameterStatus() {
		return branchParameterStatus;
	}

	public void setBranchParameterStatus(BranchParameterStatus branchParameterStatus) {
		this.branchParameterStatus = branchParameterStatus;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getDashboardId() {
		return dashboardId;
	}

	public void setDashboardId(String dashboardId) {
		this.dashboardId = dashboardId;
	}

	public String getPlacedRequestId() {
		return placedRequestId;
	}

	public void setPlacedRequestId(String placedRequestId) {
		this.placedRequestId = placedRequestId;
	}

	public String getToBranchId() {
		return toBranchId;
	}

	public void setToBranchId(String toBranchId) {
		this.toBranchId = toBranchId;
	}

	public Double getToBranchTotallAmount() {
		return toBranchTotallAmount;
	}

	public void setToBranchTotallAmount(Double toBranchTotallAmount) {
		this.toBranchTotallAmount = toBranchTotallAmount;
	}

	public String getFromBranchId() {
		return fromBranchId;
	}

	public void setFromBranchId(String fromBranchId) {
		this.fromBranchId = fromBranchId;
	}

	public String getToBranchPosition() {
		return toBranchPosition;
	}

	public void setToBranchPosition(String toBranchPosition) {
		this.toBranchPosition = toBranchPosition;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
