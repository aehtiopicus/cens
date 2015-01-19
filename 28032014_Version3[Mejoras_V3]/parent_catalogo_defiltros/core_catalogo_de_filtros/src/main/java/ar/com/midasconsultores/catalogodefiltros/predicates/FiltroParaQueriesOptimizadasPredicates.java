package ar.com.midasconsultores.catalogodefiltros.predicates;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ar.com.midasconsultores.catalogodefiltros.domain.QFiltro;
import ar.com.midasconsultores.catalogodefiltros.domain.QFiltroParaQueriesOptimizadas;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.impl.JPASubQuery;
import com.mysema.query.types.Predicate;

public class FiltroParaQueriesOptimizadasPredicates {

	private static final String WHITE_SPACE_STRING = " ";
	private static final String EMPTY_STRING = "";
	private static final String SPECIAL_CHARACTER_PATTERN = "\\W";


	@PersistenceContext
	EntityManager entityManager;

	// String tipoAplicacion,
	public static Predicate tipoAplicacion(final String searchTerm) {
		QFiltroParaQueriesOptimizadas qfiltro = QFiltroParaQueriesOptimizadas.filtroParaQueriesOptimizadas;
		return qfiltro.tipoAplicacion.eq(searchTerm);
	}

	// String marcaAplicacion,
	public static Predicate marcaAplicacionEQ(final String searchTerm) {
		QFiltroParaQueriesOptimizadas qfiltro = QFiltroParaQueriesOptimizadas.filtroParaQueriesOptimizadas;
		return qfiltro.marcaAplicacion.eq(searchTerm);
	}

	// String modeloAplicacion,
	public static Predicate modeloAplicacionEQ(final String searchTerm) {
		QFiltroParaQueriesOptimizadas qfiltro = QFiltroParaQueriesOptimizadas.filtroParaQueriesOptimizadas;
		return qfiltro.modeloAplicacion.eq(searchTerm);
	}

	// String marcaFiltro,
	public static Predicate marcaFiltroEQ(final String searchTerm) {
		QFiltroParaQueriesOptimizadas qfiltro = QFiltroParaQueriesOptimizadas.filtroParaQueriesOptimizadas;
		return qfiltro.marca.eq(searchTerm);
	}

	// String marcaAplicacion,
	public static Predicate marcaAplicacionStartsWithIgnoreCase(final String searchTerm) {
		QFiltroParaQueriesOptimizadas qfiltro = QFiltroParaQueriesOptimizadas.filtroParaQueriesOptimizadas;
		return qfiltro.marcaAplicacion.startsWithIgnoreCase(searchTerm);
	}

	// String modeloAplicacion,
	public static Predicate modeloAplicacionStartsWithIgnoreCase(final String searchTerm) {
		QFiltroParaQueriesOptimizadas qfiltro = QFiltroParaQueriesOptimizadas.filtroParaQueriesOptimizadas;
		return qfiltro.modeloAplicacion.startsWithIgnoreCase(searchTerm);
	}

	// String tipoAplicacion,
	public static Predicate tipoAplicacionStartsWithIgnoreCase(final String searchTerm) {
		QFiltroParaQueriesOptimizadas qfiltro = QFiltroParaQueriesOptimizadas.filtroParaQueriesOptimizadas;
		return qfiltro.tipoAplicacion.startsWithIgnoreCase(searchTerm);
	}

	
	// String marcaFiltro,
	public static Predicate marcaFiltroStartsWithIgnoreCase(final String searchTerm) {
		QFiltroParaQueriesOptimizadas qfiltro = QFiltroParaQueriesOptimizadas.filtroParaQueriesOptimizadas;
		return qfiltro.marca.startsWithIgnoreCase(searchTerm);
	}
	
	// String tipoFiltro,
	public static Predicate tipoFiltro(final String searchTerm) {
		QFiltroParaQueriesOptimizadas qfiltro = QFiltroParaQueriesOptimizadas.filtroParaQueriesOptimizadas;
		if(searchTerm == null || searchTerm.isEmpty()){			
			return qfiltro.tipo.isNull();
		}else{
			return qfiltro.tipo.eq(searchTerm);
		}
	}

	// String subTipoFiltro,
	public static Predicate subTipoFiltro(final String searchTerm) {
		QFiltroParaQueriesOptimizadas qfiltro = QFiltroParaQueriesOptimizadas.filtroParaQueriesOptimizadas;
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
	
	// Boolean propio
	public static Predicate esPropio(final Boolean propio) {
		QFiltroParaQueriesOptimizadas qfiltro = QFiltroParaQueriesOptimizadas.filtroParaQueriesOptimizadas;
		return qfiltro.propio.eq(propio);
	}

	private static Predicate codigoCortoLimpioContains(String value) {
		QFiltroParaQueriesOptimizadas filtro = QFiltroParaQueriesOptimizadas.filtroParaQueriesOptimizadas;		
		return filtro.codigoCortoLimpio.containsIgnoreCase(value);
		 
	}

	private static Predicate codigoLarcoLimpioContains(String value) {
		QFiltroParaQueriesOptimizadas filtro = QFiltroParaQueriesOptimizadas.filtroParaQueriesOptimizadas;
		return filtro.codigoLargoLimpio.containsIgnoreCase(value);
	}

	public static Predicate forceZeroResults() {
		QFiltroParaQueriesOptimizadas filtro = QFiltroParaQueriesOptimizadas.filtroParaQueriesOptimizadas;
		return filtro.id.eq(0l).and(filtro.id.ne(0l));
	}
	
	private static Predicate correspondeAlReemplazoLimpio(String value) {
		QFiltroParaQueriesOptimizadas filtroquery = QFiltroParaQueriesOptimizadas.filtroParaQueriesOptimizadas;
		QFiltro filtro = QFiltro.filtro;
		//remover elfiltro.prioridadMarca.ne(0) para obtener el sort anterior JV 19/08/2014
		return filtroquery.reemplazos.any().in(((new JPASubQuery()).from(filtro)
		 .where(filtro.propio.eq(false).andAnyOf(	filtro.codigoLargoLimpio.containsIgnoreCase(value), 
				 									filtro.codigoCortoLimpio.containsIgnoreCase(value)),filtro.prioridadMarca.ne(0)
			 									)).list(filtro));
	}
/**
 * select distinct filtropara0_.codigocorto as col_0_0_, filtropara0_.prioridadmarca as col_1_0_ 


from filtroparaqueriesoptimizadas filtropara0_ 


where (lower(filtropara0_.codigocortolimpio) like '%03l115562%' escape '!' 

or lower(filtropara0_.codigolargolimpio) like '%03l115562%' escape '!' or exists (select 1 from filtro filtro1_ 

where (filtro1_.id in (select reemplazos2_.reemplazos_id from filtroparaqueriesoptimizadas_filtro reemplazos2_ 

where filtropara0_.id=reemplazos2_.filtro_id)) and 

(filtro1_.id in (select filtro3_.id from filtro filtro3_ where (filtro3_.propio='f') and 
(lower(filtro3_.codigolargolimpio) like '%03l115562%' escape '!' or lower(filtro3_.codigocortolimpio) like '%03l115562%' escape '!')
and filtro3_.prioridadmarca<>'0')))) 
and filtropara0_.propio='t' 

order by filtropara0_.prioridadmarca asc, filtropara0_.codigocorto asc limit '10'

--$1 = '%03l115562%', $2 = '%03l115562%', $3 = 'f', $4 = '%03l115562%', $5 = '%03l115562%', $6 = 't', $7 = '0', $8 = '10'
 */
}
