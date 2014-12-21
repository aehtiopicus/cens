package com.aehtiopicus.cens.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.aehtiopicus.cens.domain.ObraSocial;

public class ObraSocialSpecification {
	 
	public static Specification<ObraSocial> nombreEquals(final String searchTerm) {

		return new Specification<ObraSocial>() {
			@Override
			public Predicate toPredicate(Root<ObraSocial> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				String likePattern = getLikePattern(searchTerm);
				return cb.like(cb.lower(root.<String> get("nombre")),likePattern);
			}
		};
	}
	 
	 
	private static String getLikePattern(final String searchTerm) {
		StringBuilder pattern = new StringBuilder();
		pattern.append("%");
		pattern.append(searchTerm.toLowerCase());
		pattern.append("%");
		return pattern.toString();
	}
}
