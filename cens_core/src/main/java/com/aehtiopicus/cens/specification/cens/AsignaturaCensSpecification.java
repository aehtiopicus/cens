package com.aehtiopicus.cens.specification.cens;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.aehtiopicus.cens.domain.entities.Asignatura;

public class AsignaturaCensSpecification {

	public static Specification<Asignatura> cursoYearEquals(final Integer year) {

		return new Specification<Asignatura>() {

			@Override
			public Predicate toPredicate(Root<Asignatura> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.join("curso").get("yearCurso"), year);

			}
		};
	}

	public static Specification<Asignatura> nombreLike(final String searchTerm) {

		return new Specification<Asignatura>() {
			@Override
			public Predicate toPredicate(Root<Asignatura> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				String likePattern = getLikePattern(searchTerm);
				return cb.like(cb.lower(root.<String> get("nombre")),
						likePattern);

			}
		};
	}
	
	public static Specification<Asignatura> modalidadLike(final String searchTerm) {

		return new Specification<Asignatura>() {
			@Override
			public Predicate toPredicate(Root<Asignatura> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				String likePattern = getLikePattern(searchTerm);
				return cb.like(cb.lower(root.<String> get("modalidad")),
						likePattern);

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
