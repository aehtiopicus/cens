package com.aehtiopicus.cens.specification.cens;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.aehtiopicus.cens.domain.entities.MiembroCens;
import com.aehtiopicus.cens.domain.entities.Perfil;
import com.aehtiopicus.cens.enumeration.cens.PerfilTrabajadorCensType;

public class MiembroCensSpecification {

	 public static Specification<MiembroCens> perfilTrabajadorCensEquals(final PerfilTrabajadorCensType ptct) {
	        
	        return new Specification<MiembroCens>() {
				
				@Override
				public Predicate toPredicate(Root<MiembroCens> root, CriteriaQuery<?> query,
						CriteriaBuilder cb) {
					return cb.equal(root.join("usuario").join("perfil").get("perfilType"),ptct);
					
				}
			};
	    }

	 public static Specification<MiembroCens> apellidoEquals(final String searchTerm) {
	        
	        return new Specification<MiembroCens>() {
	            @Override
	            public Predicate toPredicate(Root<MiembroCens> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	            	String likePattern = getLikePattern(searchTerm);   
	            	return cb.like(cb.lower(root.<String>get("apellido")), likePattern);
	                 
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
