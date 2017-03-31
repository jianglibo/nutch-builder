package com.jianglibo.nutchbuilder.repository.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.jianglibo.nutchbuilder.domain.Role;
import com.jianglibo.nutchbuilder.domain.Role_;

public class RoleSpecifications {
	
	  public static Specification<Role> nameLike(String name) {
		    return new Specification<Role>() {
		      public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> query,
		            CriteriaBuilder builder) {
		    	 query.distinct(true);
		    	 if (name == null || name.trim().isEmpty()) {
		    		 return null;
		    	 } else {
		    		 return builder.like(root.get(Role_.name), name);
		    	 }
		      }
		    };
		  }

}
