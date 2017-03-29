package com.jianglibo.nutchbuilder.katharsis.dto;

import org.springframework.data.domain.AbstractPageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import io.katharsis.queryspec.QuerySpec;

public interface DtoRepository {

	default Pageable convertToPageable(QuerySpec querySpec) {
		Pageable pg = new AbstractPageRequest(0, 0) {
			
			@Override
			public Sort getSort() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Pageable previous() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Pageable next() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Pageable first() {
				// TODO Auto-generated method stub
				return null;
			}
		};
		
		return pg;
	}
}
