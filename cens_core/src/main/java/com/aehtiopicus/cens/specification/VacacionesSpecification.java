package com.aehtiopicus.cens.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.aehtiopicus.cens.domain.Vacaciones;

public class VacacionesSpecification {

	 public static  Specification<Vacaciones> idEquals(final Long empleado) {
	        
	        return new Specification<Vacaciones>() {
	            @Override
	            public Predicate toPredicate(Root<Vacaciones> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	            	return cb.equal(root.get("empleado").get("id"),empleado);
	                
	            }
	       };
	    }





}
