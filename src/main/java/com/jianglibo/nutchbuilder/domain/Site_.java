package com.jianglibo.nutchbuilder.domain;

import com.jianglibo.nutchbuilder.domain.Site.SiteProtocol;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-05-09T14:43:41.257+0800")
@StaticMetamodel(Site.class)
public class Site_ extends BaseEntity_ {
	public static volatile SingularAttribute<Site, SiteProtocol> protocol;
	public static volatile SingularAttribute<Site, String> domainName;
	public static volatile SingularAttribute<Site, String> entryPath;
	public static volatile SingularAttribute<Site, CrawlCat> crawlCat;
	public static volatile SingularAttribute<Site, Date> updatedAt;
}
