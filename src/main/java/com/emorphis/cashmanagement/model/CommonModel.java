package com.emorphis.cashmanagement.model;

public class CommonModel {

	private String requestFrom;
	
	private String urlReferer;
	
	private String editable;
	
	private String hasPermission;
	
	private boolean isMaker;
	
	private String saveBtn;
	
	public String getSaveBtn() {
		return saveBtn;
	}

	public void setSaveBtn(String saveBtn) {
		this.saveBtn = saveBtn;
	}

	public boolean isMaker() {
		return isMaker;
	}

	public void setMaker(boolean isMaker) {
		this.isMaker = isMaker;
	}

	public String getHasPermission() {
		return hasPermission;
	}

	public void setHasPermission(String hasPermission) {
		this.hasPermission = hasPermission;
	}

	public String getEditable() {
		return editable;
	}

	public void setEditable(String editable) {
		this.editable = editable;
	}

	public String getUrlReferer() {
		return urlReferer;
	}

	public void setUrlReferer(String urlReferer) {
		this.urlReferer = urlReferer;
	}

	public String getRequestFrom() {
		return requestFrom;
	}

	public void setRequestFrom(String requestFrom) {
		this.requestFrom = requestFrom;
	}	
}
