package com.aehtiopicus.cens.specification;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.aehtiopicus.cens.domain.InformeMensual;
import com.aehtiopicus.cens.domain.Usuario;
import com.aehtiopicus.cens.enumeration.InformeIntermedioEstadoEnum;
import com.aehtiopicus.cens.enumeration.InformeMensualEstadoEnum;

public class InformeMensualSpecification {

	 public static Specification<InformeMensual> periodoEquals(final Date periodo) {
	        
	        return new Specification<InformeMensual>() {
	            @Override
	            public Predicate toPredicate(Root<InformeMensual> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	                return cb.equal(root.get("periodo"),periodo); 
	            }
	       };
	    }
	 
	 public static Specification<InformeMensual> clienteEquals(final Long clienteId) {
	        
	        return new Specification<InformeMensual>() {
	            @Override
	            public Predicate toPredicate(Root<InformeMensual> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	                return cb.equal(root.get("cliente").<Long> get("id"),clienteId); 
	            }
	       };
	    }

	public static Specification<InformeMensual> usuarioEquals(final Long usuarioId) {
        return new Specification<InformeMensual>() {
            @Override
            public Predicate toPredicate(Root<InformeMensual> root, CriteriaQuery<?> query, CriteriaBuilder cb) {            	
            	Predicate gerente = cb.equal(root.get("cliente").<Usuario> get("gerenteOperacion").<Long> get("id"),usuarioId);
                Predicate jefe = cb.equal(root.get("cliente").<Usuario> get("jefeOperacion").<Long> get("id"),usuarioId);
            	return cb.or(gerente, jefe);
            }
       };
	}

	public static Specification<InformeMensual> estadoEquals(final InformeMensualEstadoEnum estado) {
        return new Specification<InformeMensual>() {
            @Override
            public Predicate toPredicate(Root<InformeMensual> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
            	return cb.equal(root.get("estado"),estado); 
            }
       };
	}
	
	public static Specification<InformeMensual> estadoEqualsAllStatus() {
        return new Specification<InformeMensual>() {
            @Override
            public Predicate toPredicate(Root<InformeMensual> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
            	 Path<Boolean> estado = root.get("estado");
            	 Predicate borrador = cb.equal(root.get("estado"), InformeMensualEstadoEnum.BORRADOR);
            	 Predicate consolidado = cb.equal(root.get("estado"), InformeMensualEstadoEnum.CONSOLIDADO);
            	 Predicate enviado = cb.equal(root.get("estado"), InformeMensualEstadoEnum.ENVIADO);
            	 cb.or(estado,borrador);
            	 cb.or(estado,consolidado);
            	 cb.or(estado,enviado);
            	 return cb.conjunction();
            }
       };
	}

}
