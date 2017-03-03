package com.jianglibo.nutchbuilder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.jianglibo.nutchbuilder.domain.SourceFile;

@RepositoryRestResource //(collectionResourceRel = "nbuilder", path = "nbuilder")
public interface SourceFileRepository extends JpaRepository<SourceFile, Long>, SourceFileRepositoryCustom, JpaSpecificationExecutor<SourceFile> {

}
