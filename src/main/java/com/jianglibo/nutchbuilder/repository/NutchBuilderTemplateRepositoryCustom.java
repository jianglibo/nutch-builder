package com.jianglibo.nutchbuilder.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.jianglibo.nutchbuilder.domain.NutchBuilderTemplate;

/**
 * @author jianglibo@gmail.com
 *         2015年9月28日
 *
 */
public interface NutchBuilderTemplateRepositoryCustom {
    <S extends NutchBuilderTemplate> S save(S entity);

    Page<NutchBuilderTemplate> findAll(Specification<NutchBuilderTemplate> spec, Pageable pageable);
}
