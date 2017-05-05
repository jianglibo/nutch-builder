package com.jianglibo.nutchbuilder.domain;


import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.validator.constraints.NotEmpty;

import com.jianglibo.nutchbuilder.katharsis.dto.CrawlCatDto;
import com.jianglibo.nutchbuilder.katharsis.dto.SiteDto;

/**
 * When the sites changed, regex-urlfilter.txt will changed. nutch should be rebuilded.
 * @author Administrator
 *
 */
@Entity
@Table(name = "site", uniqueConstraints = { @UniqueConstraint(columnNames = "domainName")})
public class Site extends BaseEntity {
	
	public static enum SiteProtocol {
		HTTP, HTTPS
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Enumerated(EnumType.STRING)
	private SiteProtocol protocol = SiteProtocol.HTTP;
	
	@NotEmpty
	private String domainName;
	
	private String entryPath;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private CrawlCat crawlCat;

	public CrawlCat getCrawlCat() {
		return crawlCat;
	}

	public void setCrawlCat(CrawlCat crawlCat) {
		this.crawlCat = crawlCat;
	}

	public SiteDto toDto() {
		SiteDto sdto = new SiteDto();
		sdto.setCrawlCat(new CrawlCatDto().fromEntity(getCrawlCat()));
		sdto.setCreatedAt(getCreatedAt());
		sdto.setDomainName(getDomainName());
		sdto.setEntryPath(getEntryPath());
		sdto.setProtocol(getProtocol());
		sdto.setId(getId());
		return sdto;
	}

	public SiteProtocol getProtocol() {
		return protocol;
	}

	public void setProtocol(SiteProtocol protocol) {
		this.protocol = protocol;
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public String getEntryPath() {
		return entryPath;
	}

	public void setEntryPath(String entryPath) {
		this.entryPath = entryPath;
	}
}
