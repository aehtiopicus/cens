package com.aehtiopicus.cens.specification.cens;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.aehtiopicus.cens.domain.entities.Alumno;
import com.aehtiopicus.cens.enumeration.cens.PerfilTrabajadorCensType;

public class AlumnoCensSpecification {

	
	public static Specification<Alumno> inThisAsignatura(final Long asignaturaId) {

		return new Specification<Alumno>() {

			@Override
			public Predicate toPredicate(Root<Alumno> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(
						root.join("asignaturas").join("asignaturas").<Long>get("id")
								.get("perfilType"),asignaturaId);

			}
		};
	}
	
	public static Specification<Alumno> perfilTrabajadorCensEquals() {

		return new Specification<Alumno>() {

			@Override
			public Predicate toPredicate(Root<Alumno> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(
						root.join("miembroCens").join("usuario").join("perfil")
								.get("perfilType"),
						PerfilTrabajadorCensType.ASESOR.getNombre());

			}
		};
	}
	
	public static Specification<Alumno> alumnoNotBaja() {

		return new Specification<Alumno>() {

			@Override
			public Predicate toPredicate(Root<Alumno> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.isFalse(root.<Boolean> get("baja"));

			}
		};
	}

	public static Specification<Alumno> nombreApellidoDniLikeNotBaja(final String searchTerm){
		return new Specification<Alumno>() {
			
			@Override
			public Predicate toPredicate(Root<Alumno> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				String likePattern = getLikePattern(searchTerm);
				return cb.and(cb.or(cb.like(cb.lower(root.join("miembroCens").<String> get("apellido")), likePattern),cb.like(cb.lower(root.join("miembroCens").<String> get("nombre")), likePattern),cb.like(cb.lower(root.join("miembroCens").<String> get("dni")), likePattern)),cb.isFalse(root.<Boolean> get("baja")),cb.isFalse(root.get("miembroCens").<Boolean> get("baja")));
			}
		};
	}
	public static Specification<Alumno> apellidoLike(final String searchTerm) {

		return new Specification<Alumno>() {
			@Override
			public Predicate toPredicate(Root<Alumno> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				String likePattern = getLikePattern(searchTerm);
				return cb.like(
						cb.lower(root.join("miembroCens").<String> get(
								"apellido")), likePattern);

			}
		};
	}

	public static Specification<Alumno> nombreLike(final String searchTerm) {

		return new Specification<Alumno>() {
			@Override
			public Predicate toPredicate(Root<Alumno> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				String likePattern = getLikePattern(searchTerm);
				return cb.like(
						cb.lower(root.join("miembroCens").<String> get(
								"nombre")), likePattern);

			}
		};
	}
	
	public static Specification<Alumno> dniLike(final String searchTerm) {

		return new Specification<Alumno>() {
			@Override
			public Predicate toPredicate(Root<Alumno> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				String likePattern = getLikePattern(searchTerm);
				return cb.like(
						cb.lower(root.join("miembroCens").<String> get(
								"dni")), likePattern);

			}
		};
	}

	public static Specification<Alumno> miembroBajaFalse() {

		return new Specification<Alumno>() {

			@Override
			public Predicate toPredicate(Root<Alumno> root,
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

	public static Specification<Alumno> NotBaja() {
		return new Specification<Alumno>() {

			@Override
			public Predicate toPredicate(Root<Alumno> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				return cb.and(cb.isFalse(root.<Boolean> get("baja")),cb.isFalse(root.get("miembroCens").<Boolean> get("baja")));
			}
			
		};
	}

	public static Specification<Alumno> notThisOne(final Long asesorId) {
		return new Specification<Alumno>() {

			@Override
			public Predicate toPredicate(Root<Alumno> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.notEqual(root.get("id"), asesorId);
			}
		};
	}
}
