package com.jianglibo.nutchbuilder.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-04-07T15:39:19.830+0800")
@StaticMetamodel(CrawlCat.class)
public class CrawlCat_ extends BaseEntity_ {
	public static volatile SingularAttribute<CrawlCat, String> name;
	public static volatile SingularAttribute<CrawlCat, String> projectRoot;
	public static volatile SetAttribute<CrawlCat, Site> sites;
}
