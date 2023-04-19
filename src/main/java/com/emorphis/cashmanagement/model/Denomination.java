/**
 * 
 */
package com.emorphis.cashmanagement.model;

import java.math.BigInteger;

/**
 * User for Denomination
 * @author gourav
 *
 */
public class Denomination {

	private BigInteger amount;

	private Integer dn2000;

	private Integer dn500;

	private Integer dn100;

	private Integer dn50;

	private Integer dn20;

	private Integer dn10;

	private Integer dn5;

	private Integer dn2;

	private Integer dn1;

	public BigInteger getAmount() {
		return amount;
	}

	public void setAmount(BigInteger amount) {
		this.amount = amount;
	}

	public Integer getDn2000() {
		return dn2000;
	}

	public void setDn2000(Integer dn2000) {
		this.dn2000 = dn2000;
	}

	public Integer getDn500() {
		return dn500;
	}

	public void setDn500(Integer dn500) {
		this.dn500 = dn500;
	}

	public Integer getDn100() {
		return dn100;
	}

	public void setDn100(Integer dn100) {
		this.dn100 = dn100;
	}

	public Integer getDn50() {
		return dn50;
	}

	public void setDn50(Integer dn50) {
		this.dn50 = dn50;
	}

	public Integer getDn20() {
		return dn20;
	}

	public void setDn20(Integer dn20) {
		this.dn20 = dn20;
	}

	public Integer getDn10() {
		return dn10;
	}

	public void setDn10(Integer dn10) {
		this.dn10 = dn10;
	}

	public Integer getDn5() {
		return dn5;
	}

	public void setDn5(Integer dn5) {
		this.dn5 = dn5;
	}

	public Integer getDn2() {
		return dn2;
	}

	public void setDn2(Integer dn2) {
		this.dn2 = dn2;
	}

	public Integer getDn1() {
		return dn1;
	}

	public void setDn1(Integer dn1) {
		this.dn1 = dn1;
	}

}
