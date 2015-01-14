package com.aehtiopicus.cens.specification.cens;



import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.aehtiopicus.cens.domain.entities.Curso;

public class CursoSpecification {

	public static Specification<Curso> yearEqual(final int year) {
		return new Specification<Curso>() {

			@Override
			public Predicate toPredicate(Root<Curso> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.get("yearCurso"), year);
			}
		};
	}

}
