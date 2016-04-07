package ar.com.midasconsultores.catalogodefiltros.predicates;

import ar.com.midasconsultores.catalogodefiltros.domain.QVehiculo;

import com.mysema.query.types.Predicate;

public class VehiculoPredicates {

	// String tipoAplicacion,
	public static Predicate tipoAplicacion(final String searchTerm) {
		QVehiculo qVehiculo = QVehiculo.vehiculo;
		return qVehiculo.tipoVehiculo.nombre
				.containsIgnoreCase(searchTerm);
	}

	// String marcaAplicacion,
	public static Predicate marcaAplicacion(final String searchTerm) {
		QVehiculo qVehiculo = QVehiculo.vehiculo;
		return qVehiculo.marca.nombre.containsIgnoreCase(searchTerm);
	}

	// String modeloAplicacion,
	public static Predicate modeloAplicacion(final String searchTerm) {
		QVehiculo qVehiculo = QVehiculo.vehiculo;
		return qVehiculo.modelo.nombre.containsIgnoreCase(searchTerm);
	}


	public static Predicate tipoAplicacionEquals(final String searchTerm) {
		QVehiculo qVehiculo = QVehiculo.vehiculo;
		return qVehiculo.tipoVehiculo.nombre.eq(searchTerm);
	}
	public static Predicate marcaAplicacionEquals(final String searchTerm) {
		QVehiculo qVehiculo = QVehiculo.vehiculo;
		return qVehiculo.marca.nombre.eq(searchTerm);
	}
	public static Predicate modeloAplicacionEquals(final String searchTerm) {
		QVehiculo qVehiculo = QVehiculo.vehiculo;
		return qVehiculo.modelo.nombre.eq(searchTerm);
	}

}
