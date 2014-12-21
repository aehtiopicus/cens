package com.aehtiopicus.cens.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.aehtiopicus.cens.domain.RelacionLaboral;

public class RelacionLaboralSpecification {
	 
	 public static Specification<RelacionLaboral> idEquals(final Long id) {
	        
	        return new Specification<RelacionLaboral>() {
	            @Override
	            public Predicate toPredicate(Root<RelacionLaboral> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	                return cb.equal(root.get("cliente").get("id"),id); 
	            }
	       };
	    }


}
