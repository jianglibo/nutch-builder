package com.jianglibo.nutchbuilder.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Basic;

@Entity
@Table(name = "sourcefile", uniqueConstraints = {
	    @UniqueConstraint(columnNames = { "fileName", "sftype","nutchBuilder_id"})})
public class SourceFile extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String fileName;
	
	@Enumerated(EnumType.STRING)
	private SourceFileType sftype;
	
	@Lob
	@Basic
	private String content;
	
	
	@ManyToOne
	private NutchBuilder nutchBuilder;
	
	public SourceFile() {
	}
	
	public SourceFile(String fileName, String content) {
		this.fileName = fileName;
		this.content = content;
	}


	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public NutchBuilder getNutchBuilder() {
		return nutchBuilder;
	}

	public void setNutchBuilder(NutchBuilder nutchBuilder) {
		this.nutchBuilder = nutchBuilder;
	}
	
	public SourceFileType getSftype() {
		return sftype;
	}

	public void setSftype(SourceFileType sftype) {
		this.sftype = sftype;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public static enum SourceFileType {
		CONF,PLUGIN,IVY,ARBITRARY
	}
}
