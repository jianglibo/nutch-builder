package com.jianglibo.nutchbuilder.repository.specification;

import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.jianglibo.nutchbuilder.domain.Role;
import com.jianglibo.nutchbuilder.domain.Role_;

public class RoleSpecifications {
	
	  public static Specification<Role> nameLike(Optional<String> name) {
		    return new Specification<Role>() {
		      public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> query,
		            CriteriaBuilder builder) {
		    	 query.distinct(true);
		    	 if (name.isPresent() && name.get().trim().length() > 0) {
		    		 return builder.like(root.get(Role_.name), name.get());
		    	 } else {
		    		 return null;
		    	 }
		      }
		    };
		  }

}
