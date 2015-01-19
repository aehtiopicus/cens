package ar.com.midasconsultores.catalogodefiltros.predicates;

import ar.com.midasconsultores.catalogodefiltros.domain.QFiltro;
import ar.com.midasconsultores.catalogodefiltros.domain.Vehiculo;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.Predicate;

public class FiltroPredicates {

	private static final String WHITE_SPACE_STRING = " ";
	private static final String EMPTY_STRING = "";
	private static final String SPECIAL_CHARACTER_PATTERN = "\\W";


	
	// String tipoAplicacion,
	public static Predicate tipoAplicacion(final String searchTerm) {
		QFiltro qfiltro = QFiltro.filtro;
		return qfiltro.aplicaciones.any().tipoVehiculo.nombre
				.containsIgnoreCase(searchTerm);
	}

	// String marcaAplicacion,
	public static Predicate marcaAplicacion(final String searchTerm) {
		QFiltro qfiltro = QFiltro.filtro;
		return qfiltro.aplicaciones.any().marca.nombre.containsIgnoreCase(searchTerm);
	}

	// String modeloAplicacion,
	public static Predicate modeloAplicacion(final String searchTerm) {
		QFiltro qfiltro = QFiltro.filtro;
		return qfiltro.aplicaciones.any().modelo.nombre.containsIgnoreCase(searchTerm);
	}

	// String marcaFiltro,
	public static Predicate marcaFiltro(final String searchTerm) {
		QFiltro qfiltro = QFiltro.filtro;
		return qfiltro.marca.eq(searchTerm);
	}

	// String tipoFiltro,
	public static Predicate tipoFiltro(final String searchTerm) {
		QFiltro qfiltro = QFiltro.filtro;
		if(searchTerm == null || searchTerm.isEmpty()){			
			return qfiltro.tipo.isNull();
		}else{
			return qfiltro.tipo.eq(searchTerm);
		}
	}

	public static Predicate tipoFiltroEquals(final String searchTerm) {
		QFiltro qfiltro = QFiltro.filtro;
		if(searchTerm == null || searchTerm.isEmpty()){			
			return qfiltro.tipo.isNull();
		}else{
			return qfiltro.tipo.eq(searchTerm);
		}
	}
	
	// String subTipoFiltro,
	public static Predicate subTipoFiltro(final String searchTerm) {
		QFiltro qfiltro = QFiltro.filtro;
		return qfiltro.subTipo.eq(searchTerm);
	}

	// String codigoFiltro
	public static Predicate codigoFiltro(String searchTerm) {
		BooleanBuilder booleanBuilder = new BooleanBuilder();
		
		String codigoLimpio = searchTerm.trim()
				.replace(WHITE_SPACE_STRING, EMPTY_STRING).replaceAll(SPECIAL_CHARACTER_PATTERN, EMPTY_STRING);
		
		booleanBuilder.or(codigoCortoLimpioContains(codigoLimpio))
				.or(codigoLarcoLimpioContains(codigoLimpio))
				.or(correspondeAlReemplazoLimpio(codigoLimpio));
		return booleanBuilder.getValue();
	}

	public static Predicate esAplicacion(final Vehiculo vehiculo){
		QFiltro filtro = QFiltro.filtro;
		return filtro.aplicaciones.contains(vehiculo);
		
	}
	
	// Boolean propio
	public static Predicate esPropio(final Boolean propio) {
		QFiltro qfiltro = QFiltro.filtro;
		return qfiltro.propio.eq(propio);
	}

	private static Predicate codigoCortoLimpioContains(String value) {
		QFiltro filtro = QFiltro.filtro;		
		return filtro.codigoCortoLimpio.containsIgnoreCase(value);
		
	}

	private static Predicate codigoLarcoLimpioContains(String value) {
		QFiltro filtro = QFiltro.filtro;
		return filtro.codigoLargoLimpio.containsIgnoreCase(value);
	}

	public static Predicate forceZeroResults() {
		QFiltro filtro = QFiltro.filtro;
		return filtro.id.eq(0l).and(filtro.id.ne(0l));
	}

	
	private static Predicate correspondeAlReemplazoLimpio(String value) {
		QFiltro filtro = QFiltro.filtro;
		return filtro.reemplazos.any().codigoCortoLimpio.containsIgnoreCase(value)
				.or(filtro.reemplazos.any().codigoLargoLimpio
						.containsIgnoreCase(value));
	}
        
        public static Predicate mayorPrioridad(){
            QFiltro filtro = QFiltro.filtro;
            return filtro.prioridadMarca.eq(filtro.prioridadMarca.max());

        }

    public static Predicate prioridadMarcaEq(Integer mayorPrioridad) {
         QFiltro filtro = QFiltro.filtro;
            return filtro.prioridadMarca.eq(mayorPrioridad);
    }

}
