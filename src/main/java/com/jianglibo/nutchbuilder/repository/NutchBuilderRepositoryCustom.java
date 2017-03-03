/**
 * 2016 jianglibo@gmail.com
 *
 */
package com.jianglibo.nutchbuilder.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.jianglibo.nutchbuilder.domain.NutchBuilder;

/**
 * @author jianglibo@gmail.com
 *         2015年9月28日
 *
 */
public interface NutchBuilderRepositoryCustom {
    <S extends NutchBuilder> S save(S entity);

    Page<NutchBuilder> findAll(Specification<NutchBuilder> spec, Pageable pageable);
}
