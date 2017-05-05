package com.jianglibo.nutchbuilder.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-05-05T08:56:19.060+0800")
@StaticMetamodel(MySite.class)
public class MySite_ extends BaseEntity_ {
	public static volatile SingularAttribute<MySite, String> homepage;
	public static volatile SingularAttribute<MySite, BootUser> creator;
	public static volatile SingularAttribute<MySite, Site> site;
	public static volatile SetAttribute<MySite, UrlFilter> urlfilters;
	public static volatile SingularAttribute<MySite, String> cburl;
	public static volatile SingularAttribute<MySite, Boolean> cburlVerified;
	public static volatile SingularAttribute<MySite, String> cbsecret;
}
