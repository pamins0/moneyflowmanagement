package com.emorphis.cashmanagement.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Constant {

	private static final Logger log = LoggerFactory.getLogger(Constant.class);
	

	public static final String STATUS_PENDING="PENDING";
	public static final String STATUS_PENDING_TO_APPROVE="PENDING TO APPROVE";
	public static final String STATUS_APPROVED="APPROVED";
	public static final String STATUS_BID_COMPLETED="BID COMPLETED";
	
}
