package com.jianglibo.nutchbuilder.domain;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.jianglibo.nutchbuilder.katharsis.dto.MySiteDto;


/**
 * When the sites changed, regex-urlfilter.txt will changed. nutch should be rebuilded.
 * @author Administrator
 *
 */
@Entity
@Table(name = "mysite")
public class MySite extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * urlfilter must contain homeurl. 
	 */
	private String homepage;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@NotNull
	private BootUser creator;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private Site site;
	
	private String cburl;
	
	private boolean cburlVerified;
	
	private String cbsecret;

	public String getCburl() {
		return cburl;
	}

	public void setCburl(String cburl) {
		this.cburl = cburl;
	}

	public boolean isCburlVerified() {
		return cburlVerified;
	}

	public void setCburlVerified(boolean cburlVerified) {
		this.cburlVerified = cburlVerified;
	}

	public String getCbsecret() {
		return cbsecret;
	}

	public void setCbsecret(String cbsecret) {
		this.cbsecret = cbsecret;
	}


	public MySiteDto toDto() {
		MySiteDto sdto = new MySiteDto();
		sdto.setCbsecret(getCbsecret());
		sdto.setCburl(getCburl());
		sdto.setCreatedAt(getCreatedAt());
		sdto.setHomepage(getHomepage());
		sdto.setId(getId());
		return sdto;
	}

	public BootUser getCreator() {
		return creator;
	}

	public void setCreator(BootUser creator) {
		this.creator = creator;
	}

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

}
