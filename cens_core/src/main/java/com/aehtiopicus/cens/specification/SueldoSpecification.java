package com.aehtiopicus.cens.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.aehtiopicus.cens.domain.Sueldo;

public class SueldoSpecification {

	 

	 
	 public static Specification<Sueldo> emptyFinalDateEquals() {
	        
	        return new Specification<Sueldo>() {
	            @Override
	            public Predicate toPredicate(Root<Sueldo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	            	return cb.isNull(root.get("fechaFin"));
	                
	            }
	       };
	    }
	 
	 public static Specification<Sueldo> empleadoEquals(final Long id) {
	        
	        return new Specification<Sueldo>() {
	            @Override
	            public Predicate toPredicate(Root<Sueldo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	            	return cb.equal(root.get("empleado").get("id"),id);
	                
	            }
	       };
	    }


	
}
