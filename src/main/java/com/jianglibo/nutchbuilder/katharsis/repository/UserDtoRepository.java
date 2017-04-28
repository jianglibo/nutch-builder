package com.jianglibo.nutchbuilder.katharsis.repository;

import com.jianglibo.nutchbuilder.katharsis.dto.UserDto;

import io.katharsis.queryspec.QuerySpec;
import io.katharsis.repository.ResourceRepositoryV2;
import io.katharsis.resource.list.ResourceListBase;
/**
 * 
 * MethodSecurityMetadataSourceAdvisor, 
 * 
 * When method called from outside of object, security works, if called from object self, will be ignored.
 * @author admin
 *
 */
public interface UserDtoRepository extends ResourceRepositoryV2<UserDto, Long> {

	public class UserDtoList extends ResourceListBase<UserDto, DtoListMeta, DtoListLinks> {

	}

	@Override
	public UserDtoList findAll(QuerySpec querySpec);
	
	@Override
//	@PreAuthorize("hasRole('kkkk5')")
	// security does'nt work on this interface.
	<S extends UserDto> S create(S entity);
}

