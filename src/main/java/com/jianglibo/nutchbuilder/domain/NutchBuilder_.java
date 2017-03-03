package com.jianglibo.nutchbuilder.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-03-02T08:57:40.490+0800")
@StaticMetamodel(NutchBuilder.class)
public class NutchBuilder_ extends BaseEntity_ {
	public static volatile SingularAttribute<NutchBuilder, String> name;
	public static volatile SingularAttribute<NutchBuilder, BootUser> owner;
	public static volatile SetAttribute<NutchBuilder, SourceFile> changedFiles;
	public static volatile SingularAttribute<NutchBuilder, NutchBuilderTemplate> template;
}
