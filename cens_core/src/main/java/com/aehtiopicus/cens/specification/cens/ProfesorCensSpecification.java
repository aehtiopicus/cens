package com.aehtiopicus.cens.specification.cens;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

import com.aehtiopicus.cens.domain.entities.Profesor;
import com.aehtiopicus.cens.enumeration.cens.PerfilTrabajadorCensType;

public class ProfesorCensSpecification {

	public static Specification<Profesor> perfilTrabajadorCensEquals() {

		return new Specification<Profesor>() {

			@Override
			public Predicate toPredicate(Root<Profesor> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(
						root.join("miembroCens").join("usuario").join("perfil")
								.get("perfilType"),
						PerfilTrabajadorCensType.PROFESOR.getNombre());

			}
		};
	}
	
	public static Specification<Profesor> profesorNotBaja() {

		return new Specification<Profesor>() {

			@Override
			public Predicate toPredicate(Root<Profesor> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.isFalse(root.<Boolean> get("baja"));

			}
		};
	}

	public static Specification<Profesor> nombreApellidoDniLikeNotBaja(final String searchTerm){
		return new Specification<Profesor>() {
			
			@Override
			public Predicate toPredicate(Root<Profesor> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				String likePattern = getLikePattern(searchTerm);
				return cb.and(cb.or(cb.like(cb.lower(root.join("miembroCens").<String> get("apellido")), likePattern),cb.like(cb.lower(root.join("miembroCens").<String> get("nombre")), likePattern),cb.like(cb.lower(root.join("miembroCens").<String> get("dni")), likePattern)),cb.isFalse(root.<Boolean> get("baja")),cb.isFalse(root.get("miembroCens").<Boolean> get("baja")));
			}
		};
	}
	public static Specification<Profesor> apellidoLike(final String searchTerm) {

		return new Specification<Profesor>() {
			@Override
			public Predicate toPredicate(Root<Profesor> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				String likePattern = getLikePattern(searchTerm);
				return cb.like(
						cb.lower(root.join("miembroCens").<String> get(
								"apellido")), likePattern);

			}
		};
	}

	public static Specification<Profesor> nombreLike(final String searchTerm) {

		return new Specification<Profesor>() {
			@Override
			public Predicate toPredicate(Root<Profesor> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				String likePattern = getLikePattern(searchTerm);
				return cb.like(
						cb.lower(root.join("miembroCens").<String> get(
								"nombre")), likePattern);

			}
		};
	}
	
	public static Specification<Profesor> dniLike(final String searchTerm) {

		return new Specification<Profesor>() {
			@Override
			public Predicate toPredicate(Root<Profesor> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				String likePattern = getLikePattern(searchTerm);
				return cb.like(
						cb.lower(root.join("miembroCens").<String> get(
								"dni")), likePattern);

			}
		};
	}

	public static Specification<Profesor> miembroBajaFalse() {

		return new Specification<Profesor>() {

			@Override
			public Predicate toPredicate(Root<Profesor> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.isFalse(root.join("miembroCens").<Boolean> get("baja"));

			}
		};
	}

	private static String getLikePattern(final String searchTerm) {
		StringBuilder pattern = new StringBuilder();
		pattern.append(searchTerm.toLowerCase());
		pattern.append("%");
		return pattern.toString();
	}

	public static Specification<Profesor> NotBaja() {
		return new Specification<Profesor>() {

			@Override
			public Predicate toPredicate(Root<Profesor> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				return cb.and(cb.isFalse(root.<Boolean> get("baja")),cb.isFalse(root.get("miembroCens").<Boolean> get("baja")));
			}
			
		};
	}
}
