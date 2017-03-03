package com.jianglibo.nutchbuilder.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "nutchbuilder", uniqueConstraints = {
	    @UniqueConstraint(columnNames = {"name"})})
public class NutchBuilder extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String name;
	
	@ManyToOne
	private BootUser owner;
	
	@OneToMany(mappedBy="nutchBuilder", cascade=CascadeType.REMOVE)
	private Set<SourceFile> changedFiles = new HashSet<>();
	
	@ManyToOne
	private NutchBuilderTemplate template;

	public BootUser getOwner() {
		return owner;
	}

	public void setOwner(BootUser owner) {
		this.owner = owner;
	}

	public Set<SourceFile> getChangedFiles() {
		return changedFiles;
	}

	public void setChangedFiles(Set<SourceFile> changedFiles) {
		this.changedFiles = changedFiles;
	}

	public NutchBuilderTemplate getTemplate() {
		return template;
	}

	public void setTemplate(NutchBuilderTemplate template) {
		this.template = template;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
