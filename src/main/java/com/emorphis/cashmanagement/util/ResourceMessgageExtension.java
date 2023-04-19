package com.emorphis.cashmanagement.util;

import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

import org.springframework.context.support.ResourceBundleMessageSource;

public class ResourceMessgageExtension extends ResourceBundleMessageSource {

	public Enumeration<String> getBundleContent(String baseName, Locale locale) {

		ResourceBundle bundle = getResourceBundle(baseName, locale);

		return bundle.getKeys();
	}
}
