package com.jianglibo.nutchbuilder.katharsis.repository;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;

import com.jianglibo.nutchbuilder.domain.BaseEntity;
import com.jianglibo.nutchbuilder.json.exception.AppException;
import com.jianglibo.nutchbuilder.katharsis.dto.Dto;
import com.jianglibo.nutchbuilder.repository.RepositoryBase;

import io.katharsis.queryspec.QuerySpec;
import io.katharsis.repository.ResourceRepositoryBase;
import io.katharsis.resource.list.ResourceListBase;

public abstract class DtoRepositoryBase<T extends Dto<T, E>, L extends ResourceListBase<T, DtoListMeta, DtoListLinks>, E extends BaseEntity>
		extends ResourceRepositoryBase<T, Long> {
	
	private static Logger log = LoggerFactory.getLogger(DtoRepositoryBase.class);

	private final RepositoryBase<E> repository;

	private final Class<L> resourceListClass;
	
	private final Class<E> entityClass;

	protected DtoRepositoryBase(Class<T> resourceClass, Class<L> resourceListClass,Class<E> entityClass, RepositoryBase<E> repository) {
		super(resourceClass);
		this.repository = repository;
		this.resourceListClass = resourceListClass;
		this.entityClass = entityClass;
	}
	
	@Override
	public void delete(Long id) {
		repository.delete(id);
	}
	
	@Override
	public <S extends T> S save(S dto) {
		E entity;
		if (dto.getId() != null) {
			entity = repository.findOne(dto.getId());
		} else {
			try {
				entity = entityClass.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				log.error("instantiationException {}", entityClass.getName());
				throw new AppException().addError(1000, entityClass.getName(), "cannot instantiation " + entityClass.getName());
			}
		}
		entity = dto.patch(entity);
		try {
			return (S) dto.fromEntity(repository.save(entity));
		} catch (DataIntegrityViolationException div) {
			log.error("DataIntegrityViolationException {}", entityClass.getName());
			throw new AppException().addError(2000, "DataIntegrityViolationException", div.getMessage());
		}
	}
	

	@Override
	public T findOne(Long id, QuerySpec querySpec) {
		E entity = repository.findOne(id);
		T t;
		try {
			t = getResourceClass().newInstance();
			return t.fromEntity(entity);
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public L findAll(Iterable<Long> ids, QuerySpec querySpec) {
		List<E> bus = new ArrayList<>();
		for (Long lid : ids) {
			bus.add(repository.findOne(lid));
		}
		L udl;
		try {
			udl = resourceListClass.newInstance();
			udl.addAll(Dto.convertToDto(getResourceClass(), bus));
			return udl;
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public L findAll(QuerySpec querySpec) {
		Long count = repository.count(querySpec);
		List<E> entities = repository.findAll(querySpec);
		List<T> list = Dto.convertToDto(getResourceClass(), entities);
		
		L listOb;
		try {
			listOb = resourceListClass.newInstance();
			listOb.setMeta(new DtoListMeta(count));
			listOb.setLinks(new DtoListLinks());
			listOb.addAll(list);
			return listOb;
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
}
