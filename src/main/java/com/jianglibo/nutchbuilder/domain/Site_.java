package com.jianglibo.nutchbuilder.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-05-03T14:18:59.845+0800")
@StaticMetamodel(Site.class)
public class Site_ extends BaseEntity_ {
	public static volatile SingularAttribute<Site, String> homeUrl;
	public static volatile SingularAttribute<Site, CrawlCat> crawlCat;
	public static volatile SingularAttribute<Site, BootUser> creator;
	public static volatile SetAttribute<Site, UrlFilter> urlfilters;
	public static volatile SetAttribute<Site, CrawlFrequency> crawlFrequencies;
	public static volatile SetAttribute<Site, SiteBuilder> siteBuilders;
	public static volatile SingularAttribute<Site, String> cburl;
	public static volatile SingularAttribute<Site, Boolean> cburlVerified;
	public static volatile SingularAttribute<Site, String> cbsecret;
}
