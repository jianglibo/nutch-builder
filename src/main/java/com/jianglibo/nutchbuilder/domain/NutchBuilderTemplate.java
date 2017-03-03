package com.jianglibo.nutchbuilder.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "nutchbuildertemplate", uniqueConstraints = { @UniqueConstraint(columnNames = "name") })
public class NutchBuilderTemplate extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
