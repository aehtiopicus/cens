package ar.com.midasconsultores.catalogodefiltros.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import ar.com.midasconsultores.catalogodefiltros.domain.Filtro;
import ar.com.midasconsultores.catalogodefiltros.domain.FiltroParaQueriesOptimizadas;
import ar.com.midasconsultores.catalogodefiltros.domain.MarcaFiltroPrioridad;
import ar.com.midasconsultores.catalogodefiltros.domain.QFiltro;
import ar.com.midasconsultores.catalogodefiltros.domain.QFiltroParaQueriesOptimizadas;
import ar.com.midasconsultores.catalogodefiltros.predicates.FiltroParaQueriesOptimizadasPredicates;
import ar.com.midasconsultores.catalogodefiltros.predicates.FiltroPredicates;
import ar.com.midasconsultores.catalogodefiltros.repository.FiltroParaQueriesOptimizadasRepository;
import ar.com.midasconsultores.catalogodefiltros.repository.FiltroRepository;
import ar.com.midasconsultores.catalogodefiltros.repository.MarcaFiltroPrioridadRepository;
import ar.com.midasconsultores.catalogodefiltros.repository.VehiculoRepository;
import ar.com.midasconsultores.catalogodefiltros.utils.MsgEnum;
import ar.com.midasconsultores.catalogodefiltros.utils.TipoFiltroEnum;
import ar.com.midasconsultores.utils.ExcepcionDeNegocios;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.jpa.impl.JPASubQuery;
import com.mysema.query.types.Projections;
import com.mysema.query.types.path.StringPath;
import org.jasypt.hibernate4.encryptor.HibernatePBEStringEncryptor;
import org.springframework.beans.factory.annotation.Qualifier;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class FiltroServiceImpl implements FiltroService {

    private static final String MARCA_APLICACION = "marcaAplicacion";
    private static final String TIPO_APLICACION = "tipoAplicacion";
    private static final String EMPTY_STRING = "";
    private static final String UNDEFINED = "undefined";
    private static final String SUB_TIPO_FILTRO = "subTipoFiltro";
    private static final String TIPO_FILTRO = "tipoFiltro";
    private static final String MARCA_FILTRO = "marcaFiltro";
    private static final String MODELO_APLICACION = "modeloAplicacion";
    private static final String WHITE_SPACE_STRING = " ";
    private static final String SPECIAL_CHARACTER_PATTERN = "\\W";
    @PersistenceContext
    EntityManager entityManager;
    @Autowired
    private FiltroRepository filtroRepository;
    @Autowired
    private FiltroParaQueriesOptimizadasRepository filtroParaQueriesOptimizadasRepository;
    @Autowired
    private VehiculoRepository vehiculoRepository;
    @Autowired
    private MarcaFiltroPrioridadRepository marcaFiltroPrioridadRepository;
    @Autowired
    @Qualifier(value = "hibernateStringZeroSaltEncryptor")
    private HibernatePBEStringEncryptor hibernatePBEStringEncryptor;
    private static final Logger logger = LoggerFactory
            .getLogger(FiltroServiceImpl.class);

    @Override
    public Filtro get(Long idFiltro) {

        logger.info("Inicio - public Filtro get(Long idFiltro)");

        try {

            Filtro resultado = filtroRepository.findOne(idFiltro);

            if (resultado != null) {

                logger.info("Fin - public Filtro get(Long idFiltro)");
                return resultado;

            } else {
                logger.error("Error - public Filtro get(Long idFiltro)");
                throw new ExcepcionDeNegocios(
                        MsgEnum.ERROR_ID_DE_FILTRO_INEXISTENTE.toString());
            }

        } catch (IllegalArgumentException e) {
            logger.error("Error - public Filtro get(Long idFiltro)");
            throw new ExcepcionDeNegocios(
                    MsgEnum.ERROR_ID_DE_FILTRO_INEXISTENTE.toString());
        }

    }

    @Override
    public Page<FiltroParaQueriesOptimizadas> list(int page, int start,
            int limit, String tipoAplicacion, String marcaAplicacion,
            String modeloAplicacion, String marcaFiltro, String tipoFiltro,
            String subTipoFiltro, String codigoFiltro, Boolean esPropio) {

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        // String tipoAplicacion,
        if (tipoAplicacion != null && !tipoAplicacion.equals(EMPTY_STRING)
                && !tipoAplicacion.equals(UNDEFINED)) {
            booleanBuilder.and(FiltroParaQueriesOptimizadasPredicates
                    .tipoAplicacion(tipoAplicacion));
        }
        // String marcaAplicacion,
        if (marcaAplicacion != null && !marcaAplicacion.equals(EMPTY_STRING)
                && !marcaAplicacion.equals(UNDEFINED)) {
            booleanBuilder.and(FiltroParaQueriesOptimizadasPredicates
                    .marcaAplicacionEQ(marcaAplicacion));
        }
        // String modeloAplicacion,
        if (modeloAplicacion != null && !modeloAplicacion.equals(EMPTY_STRING)
                && !modeloAplicacion.equals(UNDEFINED)) {
            booleanBuilder.and(FiltroParaQueriesOptimizadasPredicates
                    .modeloAplicacionEQ(modeloAplicacion));
        }

        // String marcaFiltro,
        if (marcaFiltro != null && !marcaFiltro.equals(EMPTY_STRING)
                && !marcaFiltro.equals(UNDEFINED)) {
            booleanBuilder.and(FiltroParaQueriesOptimizadasPredicates
                    .marcaFiltroEQ(marcaFiltro));
        }
        // String tipoFiltro,
        if (tipoFiltro != null && !tipoFiltro.equals(EMPTY_STRING)
                && !tipoFiltro.equals(UNDEFINED)) {
            booleanBuilder.and(FiltroParaQueriesOptimizadasPredicates
                    .tipoFiltro(tipoFiltro));
        }
        // String subTipoFiltro,
        if (subTipoFiltro != null && !subTipoFiltro.equals(EMPTY_STRING)
                && !subTipoFiltro.equals(UNDEFINED)) {
            booleanBuilder.and(FiltroParaQueriesOptimizadasPredicates
                    .subTipoFiltro(subTipoFiltro));
        }
        // String codigoFiltro
        if (codigoFiltro != null && !codigoFiltro.equals(EMPTY_STRING)
                && !codigoFiltro.equals(UNDEFINED)) {
            booleanBuilder.and(FiltroParaQueriesOptimizadasPredicates
                    .codigoFiltro(codigoFiltro));
        }

        Page<FiltroParaQueriesOptimizadas> listaFiltros;

        if (!booleanBuilder.hasValue()) {
            booleanBuilder.and(FiltroParaQueriesOptimizadasPredicates
                    .forceZeroResults());
        }

        booleanBuilder.and(FiltroParaQueriesOptimizadasPredicates
                .esPropio(true));

        if (booleanBuilder.getValue() != null) {

            JPAQuery query = new JPAQuery(entityManager);

            QFiltroParaQueriesOptimizadas qFiltroParaQueriesOptimizadas = QFiltroParaQueriesOptimizadas.filtroParaQueriesOptimizadas;

            JPAQuery queryFiltros = query.from(qFiltroParaQueriesOptimizadas)
                    .where(booleanBuilder);

            JPAQuery queryFiltrosOrdenadaSinPrioridadCero = queryFiltros
                    .clone(entityManager)
                    .offset(page * limit).limit(limit)
//                    .where(qFiltroParaQueriesOptimizadas.prioridadMarca.ne(0)) agregar para obtener el sort anterior JV 19/08/2014
                    .orderBy(qFiltroParaQueriesOptimizadas.prioridadMarca.asc())
                    .orderBy(qFiltroParaQueriesOptimizadas.codigoCorto.asc());


            List<Object[]> listaFiltrosPaginadaCodigoCortoSinPrioridadCero = queryFiltrosOrdenadaSinPrioridadCero.listDistinct(qFiltroParaQueriesOptimizadas.codigoCorto, qFiltroParaQueriesOptimizadas.prioridadMarca);

            List<Object[]> listaFiltrosPaginadaCodigoCorto = new ArrayList<>();
            long listaFiltrosCount = 0;
           if (listaFiltrosPaginadaCodigoCortoSinPrioridadCero.size() != 0) {
               //remover la linea de abajo y dejar Integer prioridadMasAlta =(Integer) listaFiltrosPaginadaCodigoCortoSinPrioridadCero.get(0)[1] JV 19/08/2014
               Integer prioridadMasAlta = getBiggestPriority(listaFiltrosPaginadaCodigoCortoSinPrioridadCero) ;
               BooleanBuilder bb2 = new BooleanBuilder(qFiltroParaQueriesOptimizadas.prioridadMarca.eq(prioridadMasAlta));
               bb2.or(qFiltroParaQueriesOptimizadas.prioridadMarca.eq(0));
               listaFiltrosPaginadaCodigoCorto =  queryFiltros                        
                       .offset(page * limit).limit(limit)
                       //cambiar bb2 por qFiltroParaQueriesOptimizadas.prioridadMarca.eq(prioridadMasAlta) 19/08/2014 
                       .where(bb2)
                       .orderBy(qFiltroParaQueriesOptimizadas.codigoCorto.asc()).distinct().list(qFiltroParaQueriesOptimizadas.codigoCorto, qFiltroParaQueriesOptimizadas.prioridadMarca);
              listaFiltrosCount = queryFiltros  
                      //cambiar bb2 por qFiltroParaQueriesOptimizadas.prioridadMarca.eq(prioridadMasAlta) 19/08/2014 
                       .where(bb2)
                       .orderBy(qFiltroParaQueriesOptimizadas.codigoCorto.asc()).distinct().list(qFiltroParaQueriesOptimizadas.codigoCorto, qFiltroParaQueriesOptimizadas.prioridadMarca).size();
           } else {
                listaFiltrosPaginadaCodigoCorto = queryFiltros.offset(page * limit).limit(limit).orderBy(qFiltroParaQueriesOptimizadas.codigoCorto.asc()).distinct().list(qFiltroParaQueriesOptimizadas.codigoCorto, qFiltroParaQueriesOptimizadas.prioridadMarca);
                listaFiltrosCount = queryFiltros.orderBy(qFiltroParaQueriesOptimizadas.codigoCorto.asc()).distinct().list(qFiltroParaQueriesOptimizadas.codigoCorto, qFiltroParaQueriesOptimizadas.prioridadMarca).size();
               
           }

           List<FiltroParaQueriesOptimizadas> listaFiltrosContenido = new ArrayList<FiltroParaQueriesOptimizadas>();



            for (Object[] parCodigoCortoPrioridad : listaFiltrosPaginadaCodigoCorto) {
                listaFiltrosContenido
                        .add(filtroParaQueriesOptimizadasRepository
                        .findOneByCodigocorto((String) parCodigoCortoPrioridad[0]));
            }

            Pageable pageSpec = constructPageSpecification(page);

            listaFiltros = new PageImpl<FiltroParaQueriesOptimizadas>(
                    listaFiltrosContenido, pageSpec, listaFiltrosCount);

        } else {
            listaFiltros = filtroParaQueriesOptimizadasRepository
                    .findAll(constructPageSpecification(page));
        }
        return listaFiltros;

    }

    /**
     * Returns a new object which specifies the the wanted result page.
     *
     * @param pageIndex The index of the wanted result page
     * @return
     */
    private Pageable constructPageSpecification(int pageIndex) {
        Pageable pageSpecification = new PageRequest(pageIndex, 5,
                sortByCodigoCortoAsc());
        return pageSpecification;
    }

    private Pageable constructPageSpecification(int pageIndex, int rows) {
        Pageable pageSpecification = new PageRequest(pageIndex, rows,
                sortByCodigoCortoAsc());
        return pageSpecification;
    }

    /**
     * Returns a Sort object which sorts persons in ascending order by using the
     * last name.
     *
     * @return
     */
    private Sort sortByCodigoCortoAsc() {
        return new Sort(Sort.Direction.ASC, "codigoCorto");
    }

    @Override
    public Page<Filtro> listReemplazos(int page, int start, int limit,
            Long idFiltro) {

        logger.info("Inicio - public List<Filtro> listReemplazos(Long idFiltro)");

        try {

            if (filtroRepository.exists(idFiltro)) {

                Page<Filtro> resultado = filtroRepository
                        .findAllByReemplazosAndPropio(
                        filtroRepository.findOne(idFiltro), true,
                        constructPageSpecification(page));

                logger.info("Fin - public List<Filtro> listReemplazos(Long idFiltro)");
                return resultado;

            } else {
                logger.error("Error - public List<Filtro> listReemplazos(Long idFiltro)");
                throw new ExcepcionDeNegocios(
                        MsgEnum.ERROR_ID_DE_FILTRO_INEXISTENTE.toString());
            }

        } catch (IllegalArgumentException e) {
            logger.error("Error - public List<Filtro> listReemplazos(Long idFiltro)");
            throw new ExcepcionDeNegocios(
                    MsgEnum.ERROR_ID_DE_FILTRO_INEXISTENTE.toString());
        }

    }

    @Override
    public Page<Filtro> listFiltrosAplicacion(int page, int start, int limit,
            Long idAplicacion, String filtro) {

        BooleanBuilder booleanBuilder = new BooleanBuilder(
                FiltroPredicates.esAplicacion(vehiculoRepository
                .findOne(idAplicacion)));

        TipoFiltroEnum tfe = TipoFiltroEnum.valueOf(filtro);
        BooleanBuilder bbAux = new BooleanBuilder();

        for (String filtroVal : tfe.getValoresTipoFiltro()) {

            bbAux.or(FiltroPredicates.tipoFiltroEquals(filtroVal));

        }
        booleanBuilder.and(bbAux.getValue());

        booleanBuilder.and(FiltroPredicates.esPropio(true));

        Page<Filtro> listaFiltros;

        if (booleanBuilder.getValue() != null) {
             JPAQuery query = new JPAQuery(entityManager);
             QFiltro qFiltro = QFiltro.filtro;
             
            List<Integer> obj= query.from(qFiltro).where(booleanBuilder,qFiltro.prioridadMarca.ne(0)).orderBy(qFiltro.prioridadMarca.asc()).distinct().list(qFiltro.prioridadMarca);
            if(obj!=null && !obj.isEmpty()){
                booleanBuilder.and(FiltroPredicates.prioridadMarcaEq(obj.get(0)));
            }
//          listFiltros2 =   query2.where(qFiltro.prioridadMarca.in(
//                    new JPASubQuery(query2.clone(entityManager).getMetadata()).groupBy(qFiltro.id).list(qFiltro.prioridadMarca.max())
//               )).offset(page * limit).limit(limit).orderBy(qFiltro.codigoCorto.asc()).list(Projections.bean(Filtro.class));
//           
//                booleanBuilder.and(FiltroPredicates.mayorPrioridad());
            listaFiltros = filtroRepository.findAll(booleanBuilder.getValue(),
                    constructPageSpecification(page, limit));
        } else {
            listaFiltros = filtroRepository
                    .findAll(constructPageSpecification(page, limit));
        }
        return listaFiltros;

    }

    @Override
    public List<String> listValores(String nombreCombo, String tipoAplicacion,
            String marcaAplicacion, String modeloAplicacion,
            String marcaFiltro, String tipoFiltro, String subTipoFiltro,
            String codigoFiltro) {

        if (nombreCombo.equals(MODELO_APLICACION)
                && StringUtils.isEmpty(marcaAplicacion)) {
            return new ArrayList<String>();
        }

        JPAQuery query = new JPAQuery(entityManager);

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        StringPath propiedad = getExpresionForFromClause(nombreCombo);
        List<String> valores = new ArrayList<String>();

        if (nombreCombo.equals(MARCA_FILTRO) || nombreCombo.equals(TIPO_FILTRO)
                || nombreCombo.equals(SUB_TIPO_FILTRO)) {

            valores = listFiltrosValues(nombreCombo, marcaFiltro, tipoFiltro,
                    subTipoFiltro, query, booleanBuilder, propiedad);

        } else if (nombreCombo.equals(TIPO_APLICACION)
                || nombreCombo.equals(MARCA_APLICACION)
                || nombreCombo.equals(MODELO_APLICACION)) {

            valores = listAplicacionesValues(nombreCombo, tipoAplicacion,
                    marcaAplicacion, modeloAplicacion, query, booleanBuilder,
                    propiedad);

        }

        valores.removeAll(Collections.singleton(null));
        Collections.sort(valores);

        return valores;
    }

    private List<String> listAplicacionesValues(String nombreCombo,
            String tipoAplicacion, String marcaAplicacion,
            String modeloAplicacion, JPAQuery query,
            BooleanBuilder booleanBuilder, StringPath propiedad) {

        List<String> valores;

        String startWith = null;

        // String tipoAplicacion,
        if (!StringUtils.isEmpty(tipoAplicacion)
                && !tipoAplicacion.equals(UNDEFINED)) {

            if (!nombreCombo.equals(TIPO_APLICACION)) {
                booleanBuilder.and(FiltroParaQueriesOptimizadasPredicates
                        .tipoAplicacion(tipoAplicacion));
            } else {
                startWith = tipoAplicacion;
            }
        }

        // String marcaAplicacion,
        if (!StringUtils.isEmpty(marcaAplicacion)
                && !marcaAplicacion.equals(UNDEFINED)) {

            if (!nombreCombo.equals(MARCA_APLICACION)) {
                booleanBuilder.and(FiltroParaQueriesOptimizadasPredicates
                        .marcaAplicacionEQ(marcaAplicacion));
            } else {
                startWith = marcaAplicacion;
            }

        }

        // String modeloAplicacion,
        if (!StringUtils.isEmpty(modeloAplicacion)
                && !modeloAplicacion.equals(UNDEFINED)) {

            if (!nombreCombo.equals(MODELO_APLICACION)) {
                booleanBuilder.and(FiltroParaQueriesOptimizadasPredicates
                        .modeloAplicacionEQ(modeloAplicacion));
            } else {
                startWith = modeloAplicacion;
            }
        }

        if (booleanBuilder.hasValue()) {
            valores = query
                    .from(QFiltroParaQueriesOptimizadas.filtroParaQueriesOptimizadas)
                    .where(booleanBuilder).orderBy(propiedad.asc())
                    .listDistinct(propiedad);
        } else {
            valores = query
                    .from(QFiltroParaQueriesOptimizadas.filtroParaQueriesOptimizadas)
                    .orderBy(propiedad.asc()).listDistinct(propiedad);
        }

        filtrarValores(valores, startWith);

        return valores;
    }

    private void filtrarValores(List<String> valores, String startWith) {
        if (!StringUtils.isEmpty(valores) && !StringUtils.isEmpty(startWith)) {
            List<String> valoresARemover = new ArrayList<String>();

            for (String current : valores) {
                if (current == null
                        || !current.toLowerCase().startsWith(
                        startWith.toLowerCase())) {
                    valoresARemover.add(current);
                }
            }
            valores.removeAll(valoresARemover);
        }
    }

    private List<String> listFiltrosValues(String nombreCombo,
            String marcaFiltro, String tipoFiltro, String subTipoFiltro,
            JPAQuery query, BooleanBuilder booleanBuilder, StringPath propiedad) {

        List<String> valores;
        String startWith = null;

        // String marcaFiltro,
        if (!StringUtils.isEmpty(marcaFiltro) && !marcaFiltro.equals(UNDEFINED)) {

            if (!nombreCombo.equals(MARCA_FILTRO)) {
                booleanBuilder.and(FiltroPredicates.marcaFiltro(marcaFiltro));
            } else {
                startWith = marcaFiltro;
            }

        }

        // String tipoFiltro,
        if (!StringUtils.isEmpty(tipoFiltro) && !tipoFiltro.equals(UNDEFINED)) {

            if (!nombreCombo.equals(TIPO_FILTRO)) {
                booleanBuilder.and(FiltroPredicates.tipoFiltro(tipoFiltro));
            } else {
                startWith = tipoFiltro;
            }

        }

        // String subTipoFiltro,
        if (!StringUtils.isEmpty(subTipoFiltro)
                && !subTipoFiltro.equals(UNDEFINED)) {

            if (!nombreCombo.equals(SUB_TIPO_FILTRO)) {
                booleanBuilder.and(FiltroPredicates.subTipoFiltro(subTipoFiltro));
            } else {
                startWith = subTipoFiltro;
            }

        }

        booleanBuilder.and(QFiltro.filtro.propio.eq(true));
        if (booleanBuilder.hasValue()) {
            valores = query.from(QFiltro.filtro).where(booleanBuilder)
                    .orderBy(propiedad.asc()).listDistinct(propiedad);
        } else {
            valores = query.from(QFiltro.filtro).where(QFiltro.filtro.propio.eq(true)).orderBy(propiedad.asc())
                    .listDistinct(propiedad);
        }

        filtrarValores(valores, startWith);

        return valores;
    }

    private StringPath getExpresionForFromClause(String nombreCombo) {
        switch (nombreCombo) {
            case TIPO_APLICACION:
                return QFiltroParaQueriesOptimizadas.filtroParaQueriesOptimizadas.tipoAplicacion;
            case MARCA_APLICACION:
                return QFiltroParaQueriesOptimizadas.filtroParaQueriesOptimizadas.marcaAplicacion;
            case MODELO_APLICACION:
                return QFiltroParaQueriesOptimizadas.filtroParaQueriesOptimizadas.modeloAplicacion;
            case MARCA_FILTRO:
                return QFiltro.filtro.marca;
            case TIPO_FILTRO:
                return QFiltro.filtro.tipo;
            case SUB_TIPO_FILTRO:
                return QFiltro.filtro.subTipo;
        }
        return null;

    }

    @Override
    public List<Filtro> getFiltros(String filtroName) {
        String codigoLimpio = filtroName.trim()
                .replace(WHITE_SPACE_STRING, EMPTY_STRING)
                .replaceAll(SPECIAL_CHARACTER_PATTERN, EMPTY_STRING);
        Pageable topTen = new PageRequest(0, 5);
        return filtroRepository.findAllByCodigoCortoLimpio(codigoLimpio, topTen);
    }

    @Override
    public void updatePrioridades(List<MarcaFiltroPrioridad> listaMarcaFiltroPrioridad) {
        for (MarcaFiltroPrioridad mfp : listaMarcaFiltroPrioridad) {
            MarcaFiltroPrioridad mfp_old = marcaFiltroPrioridadRepository.findOne(mfp.getId());
            if (mfp_old != null && mfp_old.getPrioridad() != mfp.getPrioridad()) {
                if (mfp.getPrioridad() == null) {
                    mfp.setPrioridad(0);
                }
                filtroParaQueriesOptimizadasRepository.updatePrioridadByMarca(mfp.getNombreMarca(), mfp.getPrioridad());
                filtroRepository.updatePrioridadByMarca(mfp.getNombreMarca(), mfp.getPrioridad());
            }
        }
    }

    private Integer getBiggestPriority(List<Object[]> listaFiltrosPaginadaCodigoCortoSinPrioridadCero) {
        int mayor = Integer.MAX_VALUE;
        for(Object obj[] : listaFiltrosPaginadaCodigoCortoSinPrioridadCero){
            
            if(((Integer)obj[1])>0 && (((Integer)obj[1])<mayor)){
                mayor =((Integer)obj[1]);
            }
        }
        return mayor;
    }
    
   
}
