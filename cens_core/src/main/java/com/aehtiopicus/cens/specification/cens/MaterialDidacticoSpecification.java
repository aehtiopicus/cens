package com.aehtiopicus.cens.specification.cens;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.aehtiopicus.cens.domain.entities.MaterialDidactico;

public class MaterialDidacticoSpecification {

	public static Specification<MaterialDidactico> programaEquals(final Long programaId) {
		return new Specification<MaterialDidactico>() {

			@Override
			public Predicate toPredicate(Root<MaterialDidactico> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.get("programa").<Long>get("id"), programaId);
			}
		};
	}
}
