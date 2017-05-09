package com.jianglibo.nutchbuilder.facade.jpa;

import org.springframework.stereotype.Component;

import com.jianglibo.nutchbuilder.domain.SourceFile;
import com.jianglibo.nutchbuilder.facade.SourceFileFacadeRepository;
import com.jianglibo.nutchbuilder.repository.SourceFileRepository;

/**
 * @author jianglibo@gmail.com
 *
 */
@Component
public class SourceFileFacadeRepositoryImpl extends FacadeRepositoryBaseImpl<SourceFile, SourceFileRepository> implements SourceFileFacadeRepository{

	public SourceFileFacadeRepositoryImpl(SourceFileRepository jpaRepo) {
		super(jpaRepo);
	}
}
