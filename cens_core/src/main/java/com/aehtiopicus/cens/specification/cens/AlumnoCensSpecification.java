package com.aehtiopicus.cens.specification.cens;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.data.jpa.domain.Specification;

import com.aehtiopicus.cens.domain.entities.Alumno;
import com.aehtiopicus.cens.domain.entities.InscripcionAlumnos;
import com.aehtiopicus.cens.enumeration.cens.PerfilTrabajadorCensType;

public class AlumnoCensSpecification {

	
	public static Specification<Alumno> inThisAsignatura(final Long asignaturaId) {

		return new Specification<Alumno>() {

			@Override
			public Predicate toPredicate(Root<Alumno> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				
				Subquery<Alumno> sq = query.subquery(Alumno.class);
				Root<InscripcionAlumnos> project = sq.from(InscripcionAlumnos.class);
				Join<InscripcionAlumnos, Alumno> sqEmp = project.join("alumno");
				
				sq.select(sqEmp).where(cb.equal(project.get("asignatura").<Long>get("id"),asignaturaId));
				
				return cb.in(root).value(sq);

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
						PerfilTrabajadorCensType.ALUMNO.getNombre());

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

	public static Specification<Alumno> notBaja() {
		return new Specification<Alumno>() {

			@Override
			public Predicate toPredicate(Root<Alumno> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				return cb.and(cb.isFalse(root.<Boolean> get("baja")),cb.isFalse(root.get("miembroCens").<Boolean> get("baja")));
			}
			
		};
	}

	public static Specification<Alumno> notThisOne(final Long asignaturaId) {
		return new Specification<Alumno>() {

			@Override
			public Predicate toPredicate(Root<Alumno> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.notEqual(root.join("asignaturas").get("id"), asignaturaId);
			}
		};
	}
	
	public static Specification<Alumno> innerQueryToFilter(final Long asignaturaId,final String data) {
		return new Specification<Alumno>() {

			@Override
			public Predicate toPredicate(Root<Alumno> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {				
				
				Subquery<Alumno> sq = query.subquery(Alumno.class);
				Root<InscripcionAlumnos> project = sq.from(InscripcionAlumnos.class);
				Join<InscripcionAlumnos, Alumno> sqEmp = project.join("alumno");
				
				String likePattern = getLikePattern(data);
				
				sq.select(sqEmp).where(cb.notEqual(project.get("asignatura").<Long>get("id"),asignaturaId));
				
				return cb.and(
            			cb.isFalse(root.<Boolean> get("baja")),
            			cb.isFalse(root.get("miembroCens").<Boolean> get("baja")),
            			cb.or(
	        				 	cb.like(cb.lower(root.join("miembroCens").<String> get("apellido")), likePattern),
	        				 	cb.like(cb.lower(root.join("miembroCens").<String> get("nombre")), likePattern),
	        				 	cb.like(cb.lower(root.join("miembroCens").<String> get("dni")), likePattern)
	        				 ),
            			cb.not(cb.in(root).value(sq)) 
            			);													
			}
		};
	}
}
