package com.jianglibo.nutchbuilder.katharsis.vo;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import io.katharsis.queryspec.Direction;
import io.katharsis.queryspec.QuerySpec;

public class SimplePageable implements Pageable {
	
	private final Sort sort;
	private final int perPage;
	private final int curPage;
	
	private final int offset;
	
	public SimplePageable(QuerySpec querySpec) {
		if (querySpec.getLimit() == null) {
			this.perPage = 0;
			this.offset = (int)querySpec.getOffset();
			this.curPage = 0;
		} else {
			this.perPage = querySpec.getLimit().intValue();
			this.offset = (int)querySpec.getOffset();
			this.curPage = (int) Math.ceil(querySpec.getOffset() / querySpec.getLimit());
		}
		List<Order> orders = querySpec.getSort().stream().map(sc -> {
			return new Order(sc.getDirection() == Direction.ASC ? Sort.Direction.ASC : Sort.Direction.DESC, sc.getAttributePath().get(0));
		}).collect(Collectors.toList());
		if (!orders.isEmpty()) {
			sort = new Sort(orders);
		} else {
			sort = null;
		}
	}


	@Override
	public int getPageNumber() {
		return curPage;
	}

	@Override
	public int getPageSize() {
		return perPage;
	}

	@Override
	public int getOffset() {
		return offset;
	}

	@Override
	public Sort getSort() {
		return sort;
	}

	@Override
	public Pageable next() {
		return null;
	}

	@Override
	public Pageable previousOrFirst() {
		return null;
	}

	@Override
	public Pageable first() {
		return null;
	}

	@Override
	public boolean hasPrevious() {
		return false;
	}

}
