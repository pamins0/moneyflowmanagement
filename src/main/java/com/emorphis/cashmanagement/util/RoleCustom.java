package com.emorphis.cashmanagement.util;

import java.util.List;

import com.emorphis.cashmanagement.model.Role;

public class RoleCustom {

	private String id;
	private int parent;
	private String title;
	private List<RoleCustom> childs;

	public RoleCustom(Role role) {	
		this.id = role.getId();
		this.parent = role.getParent();
		this.title = role.getTitle();
	}
	
	public RoleCustom() {
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getParent() {
		return parent;
	}

	public void setParent(int parent) {
		this.parent = parent;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<RoleCustom> getChilds() {
		return childs;
	}

	public void setChilds(List<RoleCustom> childs) {
		this.childs = childs;
	}

	@Override
	public String toString() {
		return "RoleCustom [id=" + id + ", parent=" + parent + ", title=" + title + ", childs=" + childs + "]";
	}

	
	
}
