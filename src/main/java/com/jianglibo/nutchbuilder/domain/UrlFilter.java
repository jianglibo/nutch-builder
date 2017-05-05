package com.jianglibo.nutchbuilder.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "urlfilter", uniqueConstraints = { @UniqueConstraint(columnNames = {"regex", "MYSITE_ID"}) })
public class UrlFilter extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@NotNull
	private String regex;
	
	@ManyToOne
	@JoinColumn(name="MYSITE_ID", nullable=false)
	private MySite mysite;

	public String getRegex() {
		return regex;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}

	public MySite getMysite() {
		return mysite;
	}

	public void setMysite(MySite mysite) {
		this.mysite = mysite;
	}

}
