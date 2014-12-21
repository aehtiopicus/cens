package com.aehtiopicus.cens.specification;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.data.jpa.domain.Specification;

import com.aehtiopicus.cens.domain.Cliente;
import com.aehtiopicus.cens.domain.Empleado;
import com.aehtiopicus.cens.domain.RelacionLaboral;
import com.aehtiopicus.cens.enumeration.EstadoEmpleado;

public class EmpleadoSpecification {

	 public static Specification<Empleado> nombreEquals(final String searchTerm) {
	        
	        return new Specification<Empleado>() {
	            @Override
	            public Predicate toPredicate(Root<Empleado> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	                return cb.equal(root.get("nombres"),searchTerm); 
	            }
	       };
	    }
	 
	 public  Specification<Empleado> apellidoEquals(final String searchTerm) {
	        
	        return new Specification<Empleado>() {
	            @Override
	            public Predicate toPredicate(Root<Empleado> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	            	String likePattern = getLikePattern(searchTerm);   
	            	return cb.like(cb.lower(root.<String>get("apellidos")), likePattern);
	                 
	            }
	       };
	    }
	 
	 public  Specification<Empleado> dniEquals(final String searchTerm) {
	        
	        return new Specification<Empleado>() {
	            @Override
	            public Predicate toPredicate(Root<Empleado> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	            	String likePattern = getLikePattern(searchTerm);   
	            	return cb.like(cb.lower(root.<String>get("dni")), likePattern);
	                
	            }
	       };
	    }
	 
	 public  Specification<Empleado> legajoEquals(final Integer legajo) {
	        
	        return new Specification<Empleado>() {
	            @Override
	            public Predicate toPredicate(Root<Empleado> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	            	return cb.equal(root.<String>get("legajo"), legajo);
	            }
	       };
	    }
	 
	 public  Specification<Empleado> cuilEquals(final String searchTerm) {
	        
	        return new Specification<Empleado>() {
	            @Override
	            public Predicate toPredicate(Root<Empleado> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	            	String likePattern = getLikePattern(searchTerm);   
	            	return cb.like(cb.lower(root.<String>get("cuil")), likePattern);
	                
	            }
	       };
	    }
	 
	 public  Specification<Empleado> idNoEquals(final Long id) {
	        
	        return new Specification<Empleado>() {
	            @Override
	            public Predicate toPredicate(Root<Empleado> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	            	return cb.notEqual(root.get("id"),id);
	                
	            }
	       };
	    }

	 public  Specification<Empleado> estadoEquals(final EstadoEmpleado searchTerm) {
	        
	        return new Specification<Empleado>() {
	            @Override
	            public Predicate toPredicate(Root<Empleado> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	                return cb.equal(root.get("estado"),searchTerm); 
	            }
	       };
	    }
	 
	 private String getLikePattern(final String searchTerm) {
         StringBuilder pattern = new StringBuilder();
         pattern.append(searchTerm.toLowerCase());
         pattern.append("%");
         return pattern.toString();
     }
	 
	 
	public static Specification<Empleado> estadoEmpleadoEquals(final EstadoEmpleado searchTerm) {
		return new Specification<Empleado>() {
			@Override
			public Predicate toPredicate(Root<Empleado> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.get("estado"), searchTerm);
			}
		};
	}

	public static Specification<Empleado> nombreLike(final String searchTerm) {
		return new Specification<Empleado>() {
			@Override
			public Predicate toPredicate(Root<Empleado> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.like(cb.lower(root.<String>get("nombres")), searchTerm.toLowerCase() + "%");
			}
		};
	}

	public static Specification<Empleado> apellidoLike(final String searchTerm) {
		return new Specification<Empleado>() {
			@Override
			public Predicate toPredicate(Root<Empleado> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.like(cb.lower(root.<String>get("apellidos")), searchTerm.toLowerCase() + "%");
			}
		};
	}
	

	
	 public static Specification<Empleado> clienteIdEquals(final Long clienteId) {
	        
	        return new Specification<Empleado>() {
	            @Override
	            public Predicate toPredicate(Root<Empleado> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

	            	final Subquery<Long> relacionLaboralQuery = query.subquery(Long.class);
	            	final Root<RelacionLaboral> relacionLaboral = relacionLaboralQuery.from(RelacionLaboral.class);
	            	final Join<RelacionLaboral, Empleado> empleados = relacionLaboral.join("empleado");        	
	            	relacionLaboralQuery.select(empleados.<Long> get("id"));
	            	relacionLaboralQuery.where(
	            			cb.equal(relacionLaboral.<Cliente> get("cliente").<Long> get("id") , clienteId),
	            			cb.isNull(relacionLaboral.<Date> get("fechaFin"))
	            	);
	            	
	            	return cb.in(root.get("id")).value(relacionLaboralQuery);
	            }
	       };
	    }
	 
	 public static Specification<Empleado> clienteIdEqualsAll(final Long clienteId) {
	        
	        return new Specification<Empleado>() {
	            @Override
	            public Predicate toPredicate(Root<Empleado> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

	            	final Subquery<Long> relacionLaboralQuery = query.subquery(Long.class);
	            	final Root<RelacionLaboral> relacionLaboral = relacionLaboralQuery.from(RelacionLaboral.class);
	            	final Join<RelacionLaboral, Empleado> empleados = relacionLaboral.join("empleado");        	
	            	relacionLaboralQuery.select(empleados.<Long> get("id"));
	            	relacionLaboralQuery.where(
	            			cb.equal(relacionLaboral.<Cliente> get("cliente").<Long> get("id") , clienteId)
	            			
	            	);
	            	
	            	return cb.in(root.get("id")).value(relacionLaboralQuery);
	            }
	       };
	    }


}
