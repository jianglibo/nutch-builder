package com.jianglibo.nutchbuilder.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jianglibo.nutchbuilder.domain.Site;
import com.jianglibo.nutchbuilder.domain.Site.SiteProtocol;

public class HomepageSplitter {
	
	public static String URL_PTN_STR = "^(http|https)://([^/]+?)(.*)$";
	
	public static Pattern URL_PTN = Pattern.compile("^(http|https)://([^/]+)(.*)$");
	
	private String domainName;
	private SiteProtocol protocol;
	private String entryPath;
	
	private String homepage;
	
	public HomepageSplitter(String homepage) {
		this.setHomepage(homepage);
	}
	
	public HomepageSplitter(Site site) {
		setProtocol(site.getProtocol());
		setDomainName(site.getDomainName());
		setEntryPath(site.getEntryPath());
	}
	
	public HomepageSplitter(SiteProtocol protocol, String domainName, String entryPath) {
		setProtocol(protocol);
		setDomainName(domainName);
		setEntryPath(entryPath);
	}


	public HomepageSplitter concat() {
		setHomepage(String.format("%s://%s%s", getProtocol().name().toLowerCase(),getDomainName(), getEntryPath()));
		return this;
	}
	
	
	public HomepageSplitter split() {
		Matcher m = URL_PTN.matcher(homepage);
		if (m.matches()) {
			setDomainName(m.group(2));
			setEntryPath(m.group(3));
			if (getEntryPath() == null || getEntryPath().isEmpty()) {
				setEntryPath("/");
			}
			setProtocol(SiteProtocol.valueOf(m.group(1).toUpperCase()));
		}
		return this;
	}
	
	public String getDomainName() {
		return domainName;
	}
	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}
	public SiteProtocol getProtocol() {
		return protocol;
	}
	public void setProtocol(SiteProtocol protocol) {
		this.protocol = protocol;
	}
	public String getEntryPath() {
		return entryPath;
	}
	public void setEntryPath(String entryPath) {
		this.entryPath = entryPath;
	}

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}
	
	

}
