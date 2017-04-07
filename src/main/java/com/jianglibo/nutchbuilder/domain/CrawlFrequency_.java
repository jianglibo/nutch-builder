package com.jianglibo.nutchbuilder.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-04-07T15:39:19.848+0800")
@StaticMetamodel(CrawlFrequency.class)
public class CrawlFrequency_ extends BaseEntity_ {
	public static volatile SingularAttribute<CrawlFrequency, String> regex;
	public static volatile SingularAttribute<CrawlFrequency, Integer> seconds;
	public static volatile SingularAttribute<CrawlFrequency, Site> site;
}
