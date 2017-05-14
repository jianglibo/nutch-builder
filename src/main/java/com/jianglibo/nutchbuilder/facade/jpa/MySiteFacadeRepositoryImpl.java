package com.jianglibo.nutchbuilder.facade.jpa;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import com.jianglibo.nutchbuilder.constant.PreAuthorizeExpression;
import com.jianglibo.nutchbuilder.domain.BootUser_;
import com.jianglibo.nutchbuilder.domain.MySite;
import com.jianglibo.nutchbuilder.domain.MySite_;
import com.jianglibo.nutchbuilder.facade.MySiteFacadeRepository;
import com.jianglibo.nutchbuilder.facade.SimplePageable;
import com.jianglibo.nutchbuilder.facade.SortBroker;
import com.jianglibo.nutchbuilder.repository.MySiteRepository;

/**
 * @author jianglibo@gmail.com
 *
 */
@Component
public class MySiteFacadeRepositoryImpl extends FacadeRepositoryBaseImpl<MySite, MySiteRepository> implements MySiteFacadeRepository {
	
	public MySiteFacadeRepositoryImpl(MySiteRepository jpaRepo) {
		super(jpaRepo);
	}
	
	@Override
	@PreAuthorize(PreAuthorizeExpression.IS_FULLY_AUTHENTICATED)
	public MySite save(MySite entity) {
		return super.save(entity);
	}
	
	@Override
	@PreAuthorize(PreAuthorizeExpression.HAS_ADMINISTRATOR_ROLE)
	public List<MySite> findRange(long offset, long limit, SortBroker... sortFields) {
		return super.findRange(offset, limit, sortFields);
	}

	@Override
	@PreAuthorize(PreAuthorizeExpression.ID_EQUAL_OR_HAS_ADMINISTRATOR_ROLE)
	public List<MySite> findMine(@P("id") long userId, long offset, Long limit, SortBroker... sortBrokers) {
		return getRepository().findAll(creatorEq(userId), new SimplePageable(offset, limit, sortBrokers)).getContent();
	}

	@Override
	public long countMine(@P("id") long userId, long offset, Long limit, SortBroker... sortBrokers) {
		return getRepository().count(creatorEq(userId));
	}
	
	@Override
	@PostAuthorize("(returnObject.creator.id == principal.id) or #internalCall")
	public MySite findOne(Long id, @P("internalCall") boolean internalCall) {
		return super.findOne(id, internalCall);
	}
	
	public Specification<MySite> creatorEq(long userId) {
		return new Specification<MySite>() {
			public Predicate toPredicate(Root<MySite> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				query.distinct(true);
				return builder.equal(root.get(MySite_.creator).get(BootUser_.id), userId);
			}
		};
	}
}
