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
import ar.com.midasconsultores.catalogodefiltros.domain.Vehiculo;
import ar.com.midasconsultores.catalogodefiltros.predicates.VehiculoPredicates;
import ar.com.midasconsultores.catalogodefiltros.repository.VehiculoRepository;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class VehiculoServiceImpl implements VehiculoService {

	@Autowired
	private VehiculoRepository vehiculoRepository;

	@Override
	public Vehiculo get(Long idVehiculo) {
		Vehiculo resultado;
		resultado = vehiculoRepository.findOne(idVehiculo);
		return resultado;
	}

	@Override
	public Page<Vehiculo> list(int page, int start, int limit, Filtro filtro) {
		Page<Vehiculo> resultado;
		resultado = vehiculoRepository.findByRepuestos(filtro, constructPageSpecification(page,limit));
		return resultado;
	}

	@Override
	public List<Vehiculo> list() {
		List<Vehiculo> resultado;
		resultado = vehiculoRepository.findAll();
		return resultado;
	}

	/**
	 * Returns a new object which specifies the the wanted result page.
	 * 
	 * @param pageIndex
	 *            The index of the wanted result page
	 * @return
	 */
	private Pageable constructPageSpecification(int pageIndex,int limit) {
		Pageable pageSpecification = new PageRequest(pageIndex, limit,
				sortByMarca());
		return pageSpecification;
	}

	/**
	 * Returns a Sort object which sorts persons in ascending order by using the
	 * last name.
	 * 
	 * @return
	 */
	private Sort sortByMarca() {
		return new Sort(Sort.Direction.ASC, "marca");
	}

	@Override
	public Page<Vehiculo> list(int page, int start, int limit, String tipo, String marca, String modelo) {
		Page<Vehiculo> aplicaciones = null;
		
		BooleanBuilder booleanBuilder = new BooleanBuilder();
		
		if(StringUtils.isEmpty(tipo) && StringUtils.isEmpty(marca) && StringUtils.isEmpty(modelo)) {
			return aplicaciones;
		}
		
		if(StringUtils.isNotEmpty(tipo)) {
			booleanBuilder.and(VehiculoPredicates.tipoAplicacionEquals(tipo));
		}
		if(StringUtils.isNotEmpty(marca)) {
			booleanBuilder.and(VehiculoPredicates.marcaAplicacionEquals(marca));
		}
		if(StringUtils.isNotEmpty(modelo)) {
			booleanBuilder.and(VehiculoPredicates.modeloAplicacionEquals(modelo));
		}
		
		aplicaciones = vehiculoRepository.findAll(booleanBuilder, constructPageSpecification(page, limit));
		
		return aplicaciones;
	}
	

}
