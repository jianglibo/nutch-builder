package com.jianglibo.nutchbuilder.katharsis.repository;

import io.katharsis.resource.meta.PagedMetaInformation;

public class DtoListMeta implements PagedMetaInformation {

	private Long totalResourceCount;

	public DtoListMeta(Long total) {
		this.totalResourceCount = total;
	}

	@Override
	public Long getTotalResourceCount() {
		return totalResourceCount;
	}

	@Override
	public void setTotalResourceCount(Long totalResourceCount) {
		this.totalResourceCount = totalResourceCount;
	}
	
}
