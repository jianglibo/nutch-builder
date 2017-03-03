package com.jianglibo.nutchbuilder.domain;

import com.jianglibo.nutchbuilder.domain.SourceFile.SourceFileType;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-03-01T10:33:43.902+0800")
@StaticMetamodel(SourceFile.class)
public class SourceFile_ extends BaseEntity_ {
	public static volatile SingularAttribute<SourceFile, String> fileName;
	public static volatile SingularAttribute<SourceFile, SourceFileType> sftype;
	public static volatile SingularAttribute<SourceFile, String> content;
	public static volatile SingularAttribute<SourceFile, NutchBuilder> nutchBuilder;
}
