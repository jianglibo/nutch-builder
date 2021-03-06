package com.jianglibo.nutchbuilder.katharsis.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jianglibo.nutchbuilder.domain.BaseEntity;
import com.jianglibo.nutchbuilder.facade.FacadeRepositoryBase;
import com.jianglibo.nutchbuilder.katharsis.dto.Dto;
import com.jianglibo.nutchbuilder.katharsis.exception.AppException;
import com.jianglibo.nutchbuilder.katharsis.exception.UnsortableException;
import com.jianglibo.nutchbuilder.util.QuerySpecUtil;
import com.jianglibo.nutchbuilder.util.QuerySpecUtil.RelationQuery;

import io.katharsis.queryspec.QuerySpec;
import io.katharsis.repository.ResourceRepositoryBase;
import io.katharsis.resource.list.ResourceListBase;

public abstract class DtoRepositoryBase<T extends Dto<T, E>, L extends ResourceListBase<T, DtoListMeta, DtoListLinks>, E extends BaseEntity, F extends FacadeRepositoryBase<E>>
		extends ResourceRepositoryBase<T, Long> {
	
	private static Logger log = LoggerFactory.getLogger(DtoRepositoryBase.class);

	private final F repository;

	private final Class<L> resourceListClass;
	
	private final Class<E> entityClass;
	
	private Validator validator;
	
	public void validate(Dto<?, ?> o, Class<?>...groups) {
		Set<ConstraintViolation<Dto<?, ?>>> cve = validator.validate(o, groups);
		if (!cve.isEmpty()) {
			throw new ConstraintViolationException(cve);
		}
	}

	protected DtoRepositoryBase(Class<T> resourceClass, Class<L> resourceListClass,Class<E> entityClass, F repository) {
		super(resourceClass);
		this.repository = repository;
		this.resourceListClass = resourceListClass;
		this.entityClass = entityClass;
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	    validator = factory.getValidator();
	}
	
	//MethodSecurityMetadataSourceAdvisor
	@Override
	public void delete(Long id) {
		repository.delete(id);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <S extends T> S save(S dto) {
		if (dto.getId() == null || dto.getId() == 0) {
			return (S) createNew(dto);
		} else {
			return (S) modify(dto);
		}
	}
	
	public T modify(T dto) {
		validate(dto);
		E entity = repository.findOne(dto.getId(), false);
		entity = dto.patch(entity);
		return dto.fromEntity(saveToBackendRepo(dto, entity));
	}
	
	public T createNew(T dto) {
		validate(dto);
		try {
			E entity = entityClass.newInstance();
			entity = dto.patch(entity);
			return dto.fromEntity(saveToBackendRepo(dto, entity));
		} catch (InstantiationException | IllegalAccessException e) {
			log.error("instantiationException {}", entityClass.getName());
			throw new AppException().addError(1000, entityClass.getName(), "cannot instantiation " + entityClass.getName());
		}
	}
	
	public E saveToBackendRepo(T dto, E entity) {
		return repository.save(entity);
	}
	
	@Override
	public T findOne(Long id, QuerySpec querySpec) {
		E entity = repository.findOne(id, false);
		return convertToDto(entity);
	}
	
	protected T convertToDto(E entity) {
		if (entity == null) {
			return null;
		}
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
			bus.add(repository.findOne(lid, false));
		}
		L udl;
		try {
			udl = resourceListClass.newInstance();
			udl.addAll(bus.stream().map(entity -> convertToDto(entity)).collect(Collectors.toList()));
			return udl;
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Branch out from here. Implements common pattern here.
	 * [creator.id EQ [229376]]
	 */
	@Override
	public L findAll(QuerySpec querySpec) {
		List<Long> ids = QuerySpecUtil.hasMyId(querySpec);
		List<E> entities = new ArrayList<>();
		if (ids.size() > 0) {
			entities = ids.stream().map(id -> repository.findOne(id, true)).filter(ne -> ne != null).collect(Collectors.toList());
			return convertToResourceList(entities, entities.size());
		}
		
		List<String> unsported = checkAllSortableFieldAllowed(querySpec); 
		if (unsported != null && unsported.size() > 0) {
			 throw new UnsortableException(String.join(",", unsported));
		}
		
		if (querySpec.getFilters().isEmpty()) {
			entities = repository.findRange(querySpec.getOffset(), querySpec.getLimit(), QuerySpecUtil.getSortBrokers(querySpec));
			long count = repository.count();
			return convertToResourceList(entities, count);
		} else {
			RelationQuery rq = QuerySpecUtil.findRelationQuery(querySpec); 
			if (rq != null) {
				return findWithRelationAdnSpec(rq, querySpec);
			}
			return findAllWithQuerySpec(querySpec);
		}
	}
	
	protected abstract L findWithRelationAdnSpec(RelationQuery rq, QuerySpec querySpec);

	protected abstract List<String> checkAllSortableFieldAllowed(QuerySpec querySpec);

	protected abstract L findAllWithQuerySpec(QuerySpec querySpec);

	protected L convertToResourceList(List<E> entities, long count) {
		List<T> list = entities.stream().map(entity -> convertToDto(entity)).collect(Collectors.toList());		
		L listOb = null;
		try {
			listOb = resourceListClass.newInstance();
			listOb.setMeta(new DtoListMeta(count));
			listOb.setLinks(new DtoListLinks());
			listOb.addAll(list);
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return listOb;
	}
	
	public F getRepository() {
		return repository;
	}
}
