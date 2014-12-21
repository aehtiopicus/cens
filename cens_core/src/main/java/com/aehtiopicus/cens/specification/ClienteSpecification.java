package com.aehtiopicus.cens.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.aehtiopicus.cens.domain.Cliente;
import com.aehtiopicus.cens.domain.Empleado;
import com.aehtiopicus.cens.enumeration.EstadoClienteEnum;

public class ClienteSpecification {

	
	public static  Specification<Cliente>  estadoEquals(final EstadoClienteEnum searchTerm) {
	        
	        return new Specification<Cliente>() {
	            @Override
	            public Predicate toPredicate(Root<Cliente> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	                return cb.equal(root.get("estadoClienteEnum"),searchTerm); 
	            }
	       };
	    }
	 
	
	 public static Specification<Cliente> nombreEquals(final String searchTerm) {
	        
	        return new Specification<Cliente>() {
	            @Override
	            public Predicate toPredicate(Root<Cliente> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	            	String likePattern = getLikePattern(searchTerm);   
	            	return cb.like(cb.lower(root.<String>get("nombre")), likePattern);
	                 
	            }
	       };
	    }
	 
	 
	 private static String getLikePattern(final String searchTerm) {
         StringBuilder pattern = new StringBuilder();
         pattern.append(searchTerm.toLowerCase());
         pattern.append("%");
         return pattern.toString();
     }
	
 

}
