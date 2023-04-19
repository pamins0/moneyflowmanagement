package com.emorphis.cashmanagement.controllers;

import org.springframework.beans.factory.annotation.Autowired;

import com.emorphis.cashmanagement.model.MySession;
import com.emorphis.cashmanagement.service.BranchParameterStatusService;
import com.emorphis.cashmanagement.service.MailSenderService;

public class MyBean {
	
	@Autowired
	MailSenderService mailSenderService; 
	
	@Autowired
	MySession mySession;
	
	@Autowired
	BranchParameterStatusService branchParameterStatusService;
	

	//@Scheduled(cron = "${approve.request.dashboard.scheduling.job.cron}")
	public void sendMailToApproversForRequestonDashboard(){
		System.out.println("I am called by Spring scheduler first time for bid update and approve");
		boolean flag = mailSenderService.mailToAllBranchesToUpdateApproveBidAmount();
		System.out.println("flag value after mailToAllBranchesToUpdateApproveBidAmount is : "+flag); 
		
	}	

	//@Scheduled(cron = "${mail.excess.below.scheduling.job.cron}")
	public void sendMailToAccessCashBrances() {
		System.out.println("I am called by Spring scheduler second time");
		boolean flag = mailSenderService.mailToAccessCashBranchesInClosedGroups();
		System.out.println("flag value mailToAccessCashBranchesInClosedGroups is : "+flag); 
	}
	
	//@Scheduled(cron = "${approve.request.eod.scheduling.job.cron}") 
	public void autoApproveEODPositionAndProcessForDashboard(){
		System.out.println("hello cashmap |  "+System.currentTimeMillis()); 
		branchParameterStatusService.autoApproveEODRequestForDashboardProcess();
	}
	
}
