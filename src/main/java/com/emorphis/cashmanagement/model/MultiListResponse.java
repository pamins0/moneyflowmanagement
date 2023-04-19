package com.emorphis.cashmanagement.model;

import java.util.List;

public class MultiListResponse {
	List<PlaceCashRequest> placeCashRequestsList;
	List<PlaceCashRequestSwap> placeCashRequestSwapsList;

	public List<PlaceCashRequest> getPlaceCashRequestsList() {
		return placeCashRequestsList;
	}

	public void setPlaceCashRequestsList(List<PlaceCashRequest> placeCashRequestsList) {
		this.placeCashRequestsList = placeCashRequestsList;
	}

	public List<PlaceCashRequestSwap> getPlaceCashRequestSwapsList() {
		return placeCashRequestSwapsList;
	}

	public void setPlaceCashRequestSwapsList(List<PlaceCashRequestSwap> placeCashRequestSwapsList) {
		this.placeCashRequestSwapsList = placeCashRequestSwapsList;
	}

}
