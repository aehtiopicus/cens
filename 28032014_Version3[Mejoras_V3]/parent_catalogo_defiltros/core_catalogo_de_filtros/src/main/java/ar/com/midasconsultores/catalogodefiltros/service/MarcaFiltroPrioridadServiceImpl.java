package ar.com.midasconsultores.catalogodefiltros.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mysema.query.BooleanBuilder;

import ar.com.midasconsultores.catalogodefiltros.domain.Filtro;
import ar.com.midasconsultores.catalogodefiltros.domain.MarcaFiltroPrioridad;
import ar.com.midasconsultores.catalogodefiltros.domain.Vehiculo;
import ar.com.midasconsultores.catalogodefiltros.predicates.VehiculoPredicates;
import ar.com.midasconsultores.catalogodefiltros.repository.MarcaFiltroPrioridadRepository;
import ar.com.midasconsultores.catalogodefiltros.repository.VehiculoRepository;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class MarcaFiltroPrioridadServiceImpl implements MarcaFiltroPrioridadService {

    @Autowired
    private MarcaFiltroPrioridadRepository marcaFiltroPrioridadRepository;

    @Override
    public MarcaFiltroPrioridad get(Long idMarcaFiltroPrioridad) {
        MarcaFiltroPrioridad resultado;
        resultado = marcaFiltroPrioridadRepository.findOne(idMarcaFiltroPrioridad);
        return resultado;
    }

    @Override
    public Page<MarcaFiltroPrioridad> list(int page, int start, int limit) {
        Page<MarcaFiltroPrioridad> resultado;
        resultado = marcaFiltroPrioridadRepository.findAll(constructPageSpecification(page, limit));
        return resultado;
    }

    @Override
    public List<MarcaFiltroPrioridad> list() {
//        List<MarcaFiltroPrioridad> resultado;
//        resultado = marcaFiltroPrioridadRepository.findAll(sortByPrioridadAndNombreMarca());
//        return resultado;
        return marcaFiltroPrioridadRepository.findAllOrderByPrioridadZeroLast();
    }

    @Override
    public void save(MarcaFiltroPrioridad marcaFiltroPrioridad) {
        marcaFiltroPrioridadRepository.save(marcaFiltroPrioridad);
    }

    @Override
    public void saveAll(List<MarcaFiltroPrioridad> listaMarcaFiltroPrioridad) {
        marcaFiltroPrioridadRepository.save(listaMarcaFiltroPrioridad);
    }

    /**
     * Returns a new object which specifies the the wanted result page.
     *
     * @param pageIndex The index of the wanted result page
     * @return
     */
    private Pageable constructPageSpecification(int pageIndex, int limit) {
        Pageable pageSpecification = new PageRequest(pageIndex, limit,
                sortByPrioridadAndNombreMarca());
        return pageSpecification;
    }

    /**
     * Returns a Sort object which sorts persons in ascending order by using the
     * last name.
     *
     * @return
     */
    private Sort sortByPrioridadAndNombreMarca() {
        return new Sort(Sort.Direction.DESC, "prioridad").and(new Sort(Sort.Direction.ASC, "nombreMarca"));
    }
//	private static final String PBE_WITH_MD5_AND_DES = "PBEWithMD5AndDES";
//
//	public static void main(String []args){
//		HibernatePBEStringEncryptor decriptor = new HibernatePBEStringEncryptor();
//		decriptor.setAlgorithm(PBE_WITH_MD5_AND_DES);
//		decriptor.setPassword(InterpretadorDeSerial.CLAVE_DE_ENCRIPTACION);//4K2pESb3/RbeAGwpmYrPHg==
//		decriptor.setSaltGenerator(new ZeroSaltGenerator());
//		System.out.println(decriptor.decrypt("nCyul5ao+il+WoQfV6Ur8Q=="));
//
//		for(String s:TipoFiltroEnum.Combustible.getValoresTipoFiltro()){
//			System.out.println(s + " >>>>>>> " + decriptor.decrypt(s));
//		}
//
//	}
}
