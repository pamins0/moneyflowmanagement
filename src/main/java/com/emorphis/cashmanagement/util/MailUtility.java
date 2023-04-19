package com.emorphis.cashmanagement.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.emorphis.cashmanagement.model.BranchManagement;
import com.emorphis.cashmanagement.model.BranchParameterStatus;
import com.emorphis.cashmanagement.model.DashboardFinalBid;
import com.emorphis.cashmanagement.model.MySession;
import com.emorphis.cashmanagement.model.PlaceCashRequest;
import com.emorphis.cashmanagement.model.User;
import com.emorphis.cashmanagement.service.PlaceCashRequestService;

@Component
public class MailUtility {

	private static final Logger log = LoggerFactory.getLogger(MailUtility.class);

	@Autowired
	MySession mySession;

	@Autowired
	PlaceCashRequestService placeCashRequestService;

	@Autowired
	Environment environment;

	// String mailFromId = null;
	// String mailFromPwd = null;

	public boolean sendMailForgotPassword(String userMessage, User user) {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		String mailFromId = environment.getProperty("mail.from");
		String mailFromPwd = environment.getProperty("mail.password");
		

		log.info("smtp mail id : " + mailFromId);
		log.info("smtp mail pwd : " + mailFromPwd);

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(mailFromId, mailFromPwd);
			}
		});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(mailFromId));
			// message.setRecipients(Message.RecipientType.TO,
			// InternetAddress.parse(StringUtils.join(addressList, ',')));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));
			message.setSubject("Forgot Password");
			message.setText(userMessage);

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			e.printStackTrace();
			System.out.println("exception generated at mailing : " + e);
			// throw new RuntimeException(e);
			return false;
		}

		return true;
	}

	public boolean sendMail(String userMessage, List<User> userApproverList) {
		List<String> addressList = new ArrayList<String>();
		for (User user : userApproverList) {
			addressList.add(user.getEmail());
			System.out.println("Email are : " + user.getEmail());
		}

		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("farzifake7@gmail.com", "silencer");
			}
		});

		System.out.println("In send mail section msg is : " + userMessage);

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("farzifake7@gmail.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(StringUtils.join(addressList, ',')));
			message.setSubject("Please Approve Bid Associated In CMS");
			message.setText(userMessage);

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			e.printStackTrace();
			System.out.println("exception generated at mailing : " + e);
			// throw new RuntimeException(e);
			return false;
		}
		return true;
	}

	public boolean sendAcceptanceMail(String userMessage, String email) {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("farzifake7@gmail.com", "silencer");
			}
		});

		System.out.println("In send mail section msg is : " + userMessage);

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("farzifake7@gmail.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
			message.setSubject("Bid is Accepted");
			message.setText(userMessage);

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			e.printStackTrace();
			System.out.println("exception generated at mailing : " + e);
			// throw new RuntimeException(e);
			return false;
		}
		return true;
	}

	public boolean sendMailToExcessCashBranchOfBelowCashBranches(
			List<BranchManagement> toMailBranchesHavingBelowCashList, BranchManagement branchManagement) {
		List<String> addressList = new ArrayList<String>();
		StringBuffer userMessage = new StringBuffer();
		addressList.add(branchManagement.getBranchEmail());
		System.out.println("Email are : " + branchManagement.getBranchEmail() + " and branch name : "
				+ branchManagement.getBranchName() + " and branch id : " + branchManagement.getId());

		for (BranchManagement branchManagement2 : toMailBranchesHavingBelowCashList) {
			System.out.println("Email are : " + branchManagement2.getBranchEmail() + " and branch name : "
					+ branchManagement2.getBranchName() + " and branch id : " + branchManagement2.getId());

			double currentCashPosition = 0.0;
			for (DashboardFinalBid dashboardFinalBid : branchManagement2.getDashboardFinalBids()) {
				currentCashPosition = dashboardFinalBid.getTotal();
			}

			userMessage.append("\n");
			userMessage.append("Branch id : " + branchManagement2.getId());
			userMessage.append("\n");
			userMessage.append("Branch Name : " + branchManagement2.getBranchName());
			userMessage.append("\n");
			userMessage.append("Branch Cash Limit : " + branchManagement2.getBranchCashlimit());
			userMessage.append("\n");
			/*
			 * userMessage.append("Branch Current Position : " +
			 * branchParameterStatus.getTotal()); userMessage.append("\n");
			 */
			userMessage.append("Branch Required Cash : " + currentCashPosition);
			userMessage.append("\n \n \n");
		}

		System.out.println("Address list size : " + addressList.size() + " addresses are : " + addressList);

		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("farzifake7@gmail.com", "silencer");
			}
		});
		System.out.println("In send mail section msg is : " + userMessage);

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("farzifake7@gmail.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(StringUtils.join(addressList, ',')));
			message.setSubject("Branches Having Low Cash");
			message.setText(userMessage.toString());

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			e.printStackTrace();
			System.out.println("exception generated at mailing : " + e);
			// throw new RuntimeException(e);
			return false;
		}
		return true;
	}

	public boolean sendMailToBranchesHavingBelowCash(List<BranchManagement> toMailBranchesHavingBelowCashList,
			BranchManagement branchManagement) {
		List<String> addressList = new ArrayList<String>();
		StringBuffer userMessage = new StringBuffer();
		addressList.add(branchManagement.getBranchEmail());
		System.out.println("Email are : " + branchManagement.getBranchEmail() + " and branch name : "
				+ branchManagement.getBranchName() + " and branch id : " + branchManagement.getId());

		for (BranchManagement branchManagement2 : toMailBranchesHavingBelowCashList) {
			System.out.println("Email are : " + branchManagement2.getBranchEmail() + " and branch name : "
					+ branchManagement2.getBranchName() + " and branch id : " + branchManagement2.getId());
			List<BranchParameterStatus> filteredBranchParameterStatusList = branchManagement2
					.getBranchParameterStatuses().stream()
					.filter(p -> p.getBranchParameter().getParameterName().equals("branch_availability"))
					.collect(Collectors.toList());
			BranchParameterStatus branchParameterStatus = null;
			for (BranchParameterStatus branchParameterStatus1 : filteredBranchParameterStatusList) {
				branchParameterStatus = branchParameterStatus1;
			}

			double currentCashPosition = (branchManagement2.getMaxThresholdAmount() - branchParameterStatus.getTotal());

			userMessage.append("\n");
			userMessage.append("Branch id : " + branchManagement2.getId());
			userMessage.append("\n");
			userMessage.append("Branch Name : " + branchManagement2.getBranchName());
			userMessage.append("\n");
			userMessage.append("Branch Cash Limit : " + branchManagement2.getBranchCashlimit());
			userMessage.append("\n");
			userMessage.append("Branch Current Position : " + branchParameterStatus.getTotal());
			userMessage.append("\n");
			userMessage.append("Branch Required Cash : " + currentCashPosition);
			userMessage.append("\n \n \n");
		}

		System.out.println("Address list size : " + addressList.size() + " addresses are : " + addressList);

		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("farzifake7@gmail.com", "silencer");
			}
		});
		System.out.println("In send mail section msg is : " + userMessage);

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("farzifake7@gmail.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(StringUtils.join(addressList, ',')));
			message.setSubject("Branches Having Low Cash");
			message.setText(userMessage.toString());

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			e.printStackTrace();
			System.out.println("exception generated at mailing : " + e);
			// throw new RuntimeException(e);
			return false;
		}
		return true;
	}

	public boolean sendMailToApprovers(List<User> userApproverList) {
		boolean flag = false;
		String hostUrl = environment.getProperty("host.url");
		String textBody = hostUrl + "/userapprovebiddashboard";
		List<String> addressList = new ArrayList<String>();
		for (User user : userApproverList) {
			addressList.add(user.getEmail());
			System.out.println("Email for approver are : || " + user.getEmail());
		}
		flag = sendMailToMultiple("Please Approve The Bid ", textBody, addressList);
		return flag;
	}
	
	/**
	 * This in reference to mail the branch whose bid is approved......
	 * @param userApproverList
	 * @return
	 */
	
	public boolean sendMailToAcceptedBranch(List<String> mailIdList,BranchManagement branchManagement) { 
		boolean flag = false;
		String hostUrl = environment.getProperty("host.url");
		String textBody = "";
		textBody+="Your branch amount is requested by the branch : "+branchManagement.getBranchName();
		List<String> addressList = new ArrayList<String>();
		for (String email : mailIdList) {
			addressList.add(email);
			System.out.println("Email for approver are : || " + email);
		}
		flag = sendMailToMultiple("Request accepted by the branch "+branchManagement.getBranchName(), textBody, addressList);
		return flag;
	}

	public boolean sendMailToApproversForSwapping(List<User> userApproverList, List<String> swappingHierarchyList,
			String fromBranch, String toBranch) {
		boolean flag = false;
		String hostUrl = environment.getProperty("host.url");
		String textBody = hostUrl + "/userapprovebiddashboard";
		List<String> addressList = new ArrayList<String>();
		for (User user : userApproverList) {
			addressList.add(user.getEmail());
			System.out.println("Email for approver are : || " + user.getEmail());
		}
		flag = sendMailToMultiple("Please Approve The Bid Swapped By The Parent Branches", textBody, addressList);

		flag = sendMailToMultiple("Bid is Swapped by the Parent Branch Hierarchy ",
				"Bid Swapped Between Branches " + fromBranch + " and " + toBranch, swappingHierarchyList);
		return flag;
	}

	public boolean sendMailToMultiple(String subject, String userMessage, List<String> addressList) {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("farzifake7@gmail.com", "silencer");
			}
		});

		System.out.println("In send mail section msg is : " + userMessage);

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("farzifake7@gmail.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(StringUtils.join(addressList, ',')));
			message.setSubject(subject);
			message.setText(userMessage);

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			e.printStackTrace();
			System.out.println("exception generated at mailing : " + e);
			// throw new RuntimeException(e);
			return false;
		}
		return true;
	}

	public boolean sendMailToApproverAndBidPlacedByBranches(String placedRequestId) {
		PlaceCashRequest placeCashRequest = placeCashRequestService.findById(placedRequestId);
		BranchManagement branchManagementTo = placeCashRequest.getBranchManagementRequestedTo();
		BranchManagement branchManagementFrom = placeCashRequest.getBranchManagementRequestedFrom();

		boolean flag = false;
		String textBody = "Bid Approved by branch " + branchManagementFrom.getBranchName()
				+ " for the bid placed branch | " + branchManagementTo.getBranchName();
		List<String> addressList = new ArrayList<String>();
		addressList.add(branchManagementTo.getBranchEmail());
		addressList.add(branchManagementFrom.getBranchEmail());
		System.out.println("Email for approver branch from are : || " + branchManagementFrom.getBranchEmail());
		System.out.println("Email for approver branch to are : || " + branchManagementTo.getBranchEmail());

		flag = sendMailToMultiple("Bid Approved", textBody, addressList);
		return flag;
	}

	public boolean sendMailToAllBranchApprovers(List<User> userApproversList, String subject, String textbody) {
		boolean flag = false;
		String textBody = textbody;
		List<String> addressList = new ArrayList<String>();
		for (User user : userApproversList) {
			addressList.add(user.getEmail());
			System.out.println("Email for user who are the approvers are : || " + user.getEmail());
		}
		flag = sendMailToMultiple(subject, textBody, addressList);
		return flag;
	}

	/**
	 * CUG Automate which are added to the parent branch sending notification to
	 * them
	 * 
	 * @param cugMailBranchAddressList
	 * @param branchName
	 * @return
	 */
	public boolean sendMailToCUGAutomateBranches(List<BranchManagement> cugMailBranchAddressList, String branchName) {
		boolean flag = false;
		/*
		 * String hostUrl = environment.getProperty("host.url"); String textBody
		 * = hostUrl + "/userapprovebiddashboard";
		 */

		String textBody = "Branch is automatic associated with closed user group to the branch "
				+ branchName.toUpperCase();

		List<String> addressList = new ArrayList<String>();
		for (BranchManagement branchManagement : cugMailBranchAddressList) {
			addressList.add(branchManagement.getBranchEmail());
			System.out.println("Email for branches in cug are : || " + branchManagement.getBranchEmail());
		}
		flag = sendMailToMultiple("Branches Automatic associaed with closed user group", textBody, addressList);

		return flag;
	}

	public boolean sendMailToApproversForEODFormApproval(List<User> userApproverListForEODBranch) {
		boolean flag = false;
		String hostUrl = environment.getProperty("host.url");
		String textBody = "Hello All,\n" + " Please Approve the EOD filled by the user : "
				+ mySession.getUser().getUserId();
		textBody += hostUrl + "/branchmanagement";
		List<String> addressList = new ArrayList<String>();
		for (User user : userApproverListForEODBranch) {
			addressList.add(user.getEmail());
			System.out.println("Email for approver are : || " + user.getEmail());
		}
		flag = sendMailToMultiple("Please Approve The EOD Form ", textBody, addressList);
		return flag;
	}

	public boolean sendMailToBrancheForEodApproval(List<User> userApproverListForEODBranch,
			DashboardFinalBid dashboardFinalBid) {
		boolean flag = false;
		String hostUrl = environment.getProperty("host.url");
		String textBody = "Hello All,\n" + " EOD for the day is approved and process for the bidding : ";
		textBody += "\n The EOD Total Amount for the day is - " + dashboardFinalBid.getEodTotal();
		textBody += "\n The Request Amount for bidding would be - " + dashboardFinalBid.getTotal();
		textBody += "\n The position is to - " + dashboardFinalBid.getPosition();
		List<String> addressList = new ArrayList<String>();
		for (User user : userApproverListForEODBranch) {
			addressList.add(user.getEmail());
			System.out.println("Email for approver are : || " + user.getEmail());
		}
		addressList.add(mySession.getUser().getBranchManagement().getBranchEmail());
		flag = sendMailToMultiple("EOD Approved For The Day ", textBody, addressList);
		return flag;
	}

	public boolean sendMailToBrancheForEodCancel(List<User> userApproverListForEODBranch,
			DashboardFinalBid dashboardFinalBid) {
		boolean flag = false;
		String hostUrl = environment.getProperty("host.url");
		String textBody = "Hello All,\n" + " EOD for the day is Approved.";
		if (dashboardFinalBid != null) {
			textBody += "\n The User cancels the eod amount to process on dashboard for bidding";
			textBody += "\n The EOD Total Amount for the day is - " + dashboardFinalBid.getEodTotal();
			textBody += "\n \n The request canceled by the user id is " + mySession.getUser().getId();
		}
		List<String> addressList = new ArrayList<String>();
		for (User user : userApproverListForEODBranch) {
			addressList.add(user.getEmail());
			System.out.println("Email for approver are : || " + user.getEmail());
		}
		addressList.add(mySession.getUser().getBranchManagement().getBranchEmail());
		flag = sendMailToMultiple("Request On Dashboard Canceled For The Day ", textBody, addressList);
		return flag;
	}
}
