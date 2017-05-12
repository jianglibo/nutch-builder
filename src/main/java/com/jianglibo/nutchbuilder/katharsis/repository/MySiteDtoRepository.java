package com.jianglibo.nutchbuilder.katharsis.repository;


import com.jianglibo.nutchbuilder.katharsis.dto.MySiteDto;

import io.katharsis.queryspec.QuerySpec;
import io.katharsis.repository.ResourceRepositoryV2;
import io.katharsis.resource.links.PagedLinksInformation;
import io.katharsis.resource.list.ResourceListBase;

public interface MySiteDtoRepository extends ResourceRepositoryV2<MySiteDto, Long> {


	public class MySiteDtoList extends ResourceListBase<MySiteDto, DtoListMeta, DtoListLinks> {

	}

	@Override
	public MySiteDtoList findAll(QuerySpec querySpec);
	
	
	public static class MySiteListLinks implements PagedLinksInformation {

		@Override
		public String getFirst() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void setFirst(String first) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public String getLast() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void setLast(String last) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public String getNext() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void setNext(String next) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public String getPrev() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void setPrev(String prev) {
			// TODO Auto-generated method stub
			
		}
		
	}
}

