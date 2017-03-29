package com.jianglibo.nutchbuilder.katharsis.repository;

import com.jianglibo.nutchbuilder.katharsis.dto.UserDto;

import io.katharsis.queryspec.QuerySpec;
import io.katharsis.repository.ResourceRepositoryV2;
import io.katharsis.resource.links.PagedLinksInformation;
import io.katharsis.resource.list.ResourceListBase;
import io.katharsis.resource.meta.PagedMetaInformation;

public interface UserDtoRepository extends ResourceRepositoryV2<UserDto, Long> {

	public class BootUserListMeta implements PagedMetaInformation {

		private Long totalResourceCount;

//		public BootUserListMeta(Long total) {
//			this.totalResourceCount = total;
//		}

		@Override
		public Long getTotalResourceCount() {
			return totalResourceCount;
		}

		@Override
		public void setTotalResourceCount(Long totalResourceCount) {
			this.totalResourceCount = totalResourceCount;
		}

	}

	public class UserDtoListLinks implements PagedLinksInformation {

		private String first;

		private String last;

		private String next;

		private String prev;

		@Override
		public String getFirst() {
			return first;
		}

		@Override
		public void setFirst(String first) {
			this.first = first;
		}

		@Override
		public String getLast() {
			return last;
		}

		@Override
		public void setLast(String last) {
			this.last = last;
		}

		@Override
		public String getNext() {
			return next;
		}

		@Override
		public void setNext(String next) {
			this.next = next;
		}

		@Override
		public String getPrev() {
			return prev;
		}

		@Override
		public void setPrev(String prev) {
			this.prev = prev;
		}
	}

	public class UserDtoList extends ResourceListBase<UserDto, BootUserListMeta, UserDtoListLinks> {

	}

	@Override
	public UserDtoList findAll(QuerySpec querySpec);
}

