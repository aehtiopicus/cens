package com.aehtiopicus.cens.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.aehtiopicus.cens.domain.RelacionPuestoEmpleado;

public class RelacionPuestoEmpleadoSpecification {
	 
	 public static Specification<RelacionPuestoEmpleado> idEquals(final Long id) {
	        
	        return new Specification<RelacionPuestoEmpleado>() {
	            @Override
	            public Predicate toPredicate(Root<RelacionPuestoEmpleado> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	                return cb.equal(root.get("relacionLaboral").get("id"),id); 
	            }
	       };
	    }

	 public static Specification<RelacionPuestoEmpleado> fechaFinEmpty() {
	        
	        return new Specification<RelacionPuestoEmpleado>() {
	            @Override
	            public Predicate toPredicate(Root<RelacionPuestoEmpleado> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	                return cb.isNull(root.get("fechaFin")); 
	            }
	       };
	    }

}
