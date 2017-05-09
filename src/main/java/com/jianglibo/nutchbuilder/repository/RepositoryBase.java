package com.jianglibo.nutchbuilder.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface RepositoryBase<T> extends JpaRepository<T, Long>, JpaSpecificationExecutor<T> {
}
