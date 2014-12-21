package com.aehtiopicus.cens.specification;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.aehtiopicus.cens.domain.InformeConsolidado;
import com.aehtiopicus.cens.domain.InformeConsolidadoStub;

public class InformeConsolidadoSpecification {

	 public static Specification<InformeConsolidado> periodoEquals(final Date periodo) {
	        
	        return new Specification<InformeConsolidado>() {
	            @Override
	            public Predicate toPredicate(Root<InformeConsolidado> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	                return cb.equal(root.get("periodo"),periodo); 
	            }
	       };
	    }

	 public static Specification<InformeConsolidadoStub> stub_periodoEquals(final Date periodo) {
	        
	        return new Specification<InformeConsolidadoStub>() {
	            @Override
	            public Predicate toPredicate(Root<InformeConsolidadoStub> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	                return cb.equal(root.get("periodo"),periodo); 
	            }
	       };
	    }
}
