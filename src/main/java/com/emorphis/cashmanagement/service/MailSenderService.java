package com.emorphis.cashmanagement.service;

public interface MailSenderService {

	public boolean mailToAccessCashBranchesInClosedGroups();

	public boolean mailToAllBranchesToUpdateApproveBidAmount(); 
}
