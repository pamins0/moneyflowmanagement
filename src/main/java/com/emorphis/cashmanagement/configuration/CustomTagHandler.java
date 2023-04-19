package com.emorphis.cashmanagement.configuration;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

@SuppressWarnings("serial")
public class CustomTagHandler extends TagSupport {

	private String divKey;
	
	public int doStartTag() throws JspException {
		// returns the instance of JspWriter
		JspWriter out = pageContext.getOut();
		try {
			// printing date and time using JspWriter
			//out.print(Calendar.getInstance().getTime());
			if(divKey.equalsIgnoreCase("hello")){
				out.print(true);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		// will not evaluate the body content of the tag
		return SKIP_BODY;
	}

	public void setDivKey(String divKey) {
		this.divKey = divKey;
	}
}