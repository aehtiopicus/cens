package com.aehtiopicus.cens.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.aehtiopicus.cens.domain.UsuarioPerfil;

public class UsuarioSpecification {

	 public static Specification<UsuarioPerfil> nombreEquals(final String searchTerm) {
	        
	        return new Specification<UsuarioPerfil>() {
	            @Override
	            public Predicate toPredicate(Root<UsuarioPerfil> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	                return cb.equal(root.get("perfil").get("nombre"),searchTerm); 
	            }
	       };
	    }
	 
	 public static Specification<UsuarioPerfil> idEquals(final Integer id) {
	        
	        return new Specification<UsuarioPerfil>() {
	            @Override
	            public Predicate toPredicate(Root<UsuarioPerfil> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	                return cb.equal(root.get("perfil").get("id"),id); 
	            }
	       };
	    }

	 public  Specification<UsuarioPerfil> apellidoEquals(final String searchTerm) {
	        
	        return new Specification<UsuarioPerfil>() {
	            @Override
	            public Predicate toPredicate(Root<UsuarioPerfil> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	            	String likePattern = getLikePattern(searchTerm);   
	            	return cb.like(cb.lower(root.get("usuario").<String>get("apellido")), likePattern);
	                 
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
