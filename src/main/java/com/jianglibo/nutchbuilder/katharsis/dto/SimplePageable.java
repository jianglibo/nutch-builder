package com.jianglibo.nutchbuilder.katharsis.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import io.katharsis.queryspec.Direction;
import io.katharsis.queryspec.QuerySpec;

public class SimplePageable implements Pageable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Sort sort = null;
	
	private final QuerySpec querySpec;
	
	private final Long total;
	
	public SimplePageable(QuerySpec querySpec, Long total) {
		this.querySpec = querySpec;
		this.total = total;
		List<Order> orders = querySpec.getSort().stream().map(sc -> {
			return new Order(sc.getDirection() == Direction.ASC ? Sort.Direction.ASC : Sort.Direction.DESC, sc.getAttributePath().get(0));
		}).collect(Collectors.toList());
		if (!orders.isEmpty()) {
			sort = new Sort(orders);
		}
	}


	@Override
	public int getPageNumber() {
		return (int) Math.ceil(total / querySpec.getLimit());
	}

	@Override
	public int getPageSize() {
		return querySpec.getLimit().intValue();
	}

	@Override
	public int getOffset() {
		return (int) querySpec.getOffset();
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
