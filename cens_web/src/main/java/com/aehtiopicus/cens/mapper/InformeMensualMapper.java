package com.aehtiopicus.cens.mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import com.aehtiopicus.cens.domain.Beneficio;
import com.aehtiopicus.cens.domain.BeneficioCliente;
import com.aehtiopicus.cens.domain.Cliente;
import com.aehtiopicus.cens.domain.InformeMensual;
import com.aehtiopicus.cens.domain.InformeMensualDetalle;
import com.aehtiopicus.cens.domain.InformeMensualDetalleBeneficio;
import com.aehtiopicus.cens.dto.ComboDto;
import com.aehtiopicus.cens.dto.ExcelInformeMensualBeneficioDetalleDataDto;
import com.aehtiopicus.cens.dto.ExcelInformeMensualDetalleDataDto;
import com.aehtiopicus.cens.dto.InformeIntermedioDto;
import com.aehtiopicus.cens.dto.InformeMensualHeaderDto;
import com.aehtiopicus.cens.dto.InformeMensualHeaderNuevoDto;
import com.aehtiopicus.cens.enumeration.InformeIntermedioEstadoEnum;
import com.aehtiopicus.cens.enumeration.InformeMensualEstadoEnum;
import com.aehtiopicus.cens.utils.PeriodoUtils;



public class InformeMensualMapper {

	public static List<InformeMensualHeaderDto> getInformesMensualesHeaderDtoFromInformesMensuales(List<InformeMensual> informes) {
		List<InformeMensualHeaderDto> dtos = new ArrayList<InformeMensualHeaderDto>();
		
		for (InformeMensual entity : informes) {
			dtos.add(getInformeMensualHeaderDtoFromInformeMensual(entity));
		}
		return dtos;
	}


	public static InformeMensualHeaderDto getInformeMensualHeaderDtoFromInformeMensual(InformeMensual informe) {
		InformeMensualHeaderDto dto = new InformeMensualHeaderDto();
		
		if(informe != null) {
			dto.setEstado(informe.getEstado().getNombre());
			
			if(informe.getId() != null) {
				dto.setInformeMensualId(informe.getId());
			}
			if(informe.getCliente() != null) {
				dto.setNombreCliente(informe.getCliente().getNombre());
			}
			if(informe.getPeriodo() != null) {
				dto.setPeriodo(PeriodoUtils.getPeriodoFromDate(informe.getPeriodo()));
			}
				
		}
		
		return dto;
	}


	public static InformeMensual getInformeMensualFromInformeMensualHeaderNuevoDto(	InformeMensualHeaderNuevoDto dto) {
		InformeMensual entity = new InformeMensual();
		
		if(dto != null) {
			if(dto.getClienteId() != null) {
				Cliente c = new Cliente();
				c.setId(dto.getClienteId());
				entity.setCliente(c);
			}
			if(StringUtils.isNotEmpty(dto.getPeriodo())) {
				entity.setPeriodo(PeriodoUtils.getDateFormPeriodo(dto.getPeriodo()));
			}
			entity.setEstado(InformeMensualEstadoEnum.BORRADOR);
		}
		return entity;
	}


	public static InformeMensualDetalle updateDetalleFromDetalleDto(InformeMensualDetalle detalle, ExcelInformeMensualDetalleDataDto detalleDto) {

		detalle.setDiasTrabajados(detalleDto.getDiasTrabajados());
		detalle.setCondicion(detalleDto.getCondicion());
		detalle.setPorcentaje(detalleDto.getPorcentaje());
		detalle.setHsExtrasALiquidarAl50(detalleDto.getHsExtrasLiquidar50());
		detalle.setHsExtrasALiquidarAl100(detalleDto.getHsExtrasLiquidar100());
		detalle.setHsExtrasAFacturarAl50(detalleDto.getHsExtrasFacturar50());
		detalle.setHsExtrasAFacturarAl100(detalleDto.getHsExtrasFacturar100());
		detalle.setPresentismo(detalleDto.getSueldoPresentismo());
		
		if(detalleDto.getBeneficios() != null && detalleDto.getBeneficios().size() > 0) {
			Map<Long, InformeMensualDetalleBeneficio> aux = new HashMap<Long, InformeMensualDetalleBeneficio>();
			for(InformeMensualDetalleBeneficio detalleBeneficio : detalle.getBeneficios()) {
				aux.put(detalleBeneficio.getBeneficio().getId(), detalleBeneficio);
			}
			
			for(ExcelInformeMensualBeneficioDetalleDataDto detalleBeneficioDto : detalleDto.getBeneficios()) {
				if(aux.containsKey(detalleBeneficioDto.getBeneficioId())) {
					aux.get(detalleBeneficioDto.getBeneficioId()).setValor(detalleBeneficioDto.getValor());
				}
			}
			
			detalle.setBeneficios(new ArrayList<InformeMensualDetalleBeneficio>(aux.values()));
		}
		
		return detalle;
	}


	public static List<InformeIntermedioDto> getInformeIntermedioDtoFromInformeMensual(
			List<InformeMensual> informes, List<Cliente> clientes, String estado, String periodo) {
		List<InformeIntermedioDto> informesIntermediosDto = new ArrayList<InformeIntermedioDto>();
		if(!CollectionUtils.isEmpty(informes)){
			for(InformeMensual informe : informes){
				InformeIntermedioDto informeIntermedio = new InformeIntermedioDto();
				// cargar id
				informeIntermedio.setInformeId(informe.getId());
				// cargar cliente
				informeIntermedio.setClienteNombre(informe.getCliente().getNombre());
				// cargar estado
				if(informe.getEstado().equals(InformeMensualEstadoEnum.BORRADOR)){
					informeIntermedio.setEstado(InformeIntermedioEstadoEnum.PENDIENTE.getNombre());
				}
				if(informe.getEstado().equals(InformeMensualEstadoEnum.CONSOLIDADO)){
					informeIntermedio.setEstado(InformeIntermedioEstadoEnum.CONSOLIDADO.getNombre());
				}
				if(informe.getEstado().equals(InformeMensualEstadoEnum.ENVIADO)){
					informeIntermedio.setEstado(InformeIntermedioEstadoEnum.RECIBIDO.getNombre());
				}	
				// cargar nombre
				informeIntermedio.setGerenteNombre(informe.getCliente().getGerenteOperacion().getApellido()+" "+informe.getCliente().getGerenteOperacion().getNombre());
				// cargar periodo
				informeIntermedio.setPeriodo(PeriodoUtils.getPeriodoFromDate(informe.getPeriodo()));
				informesIntermediosDto.add(informeIntermedio);
				clientes.remove(informe.getCliente());
			}
		}
		// clientes que no tienen informes
		InformeIntermedioEstadoEnum estadoIntermedio = null;
		if(StringUtils.isNotEmpty(estado)){
			estadoIntermedio = InformeIntermedioEstadoEnum.getInformeMensualEstadoEnumFromString(estado);
		}
		if(estadoIntermedio ==  null || estadoIntermedio.equals(InformeIntermedioEstadoEnum.PENDIENTE)){
		if(!CollectionUtils.isEmpty(clientes)){
			for(Cliente cliente : clientes){
				InformeIntermedioDto informeIntermedio = new InformeIntermedioDto();
				// cargar cliente
				informeIntermedio.setClienteNombre(cliente.getNombre());
				// cargar estado
				informeIntermedio.setEstado(InformeIntermedioEstadoEnum.PENDIENTE.getNombre());
				// cargar periodo
				informeIntermedio.setPeriodo(PeriodoUtils.getPeriodoFromDate(getPeriodo(periodo)));
				// cargar nombre
				informeIntermedio.setGerenteNombre(cliente.getGerenteOperacion().getApellido()+" "+cliente.getGerenteOperacion().getNombre());
				informesIntermediosDto.add(informeIntermedio);
			}
		}
		}
		return informesIntermediosDto;
	}


	public static List<ComboDto> getComboDtoFromEstadoInforme() {
		List<ComboDto> comboData = new ArrayList<ComboDto>();
		ComboDto c;
		c = new ComboDto();
		c.setValue(InformeIntermedioEstadoEnum.PENDIENTE.getNombre());
		comboData.add(c);
		c = new ComboDto();
		c.setValue(InformeIntermedioEstadoEnum.RECIBIDO.getNombre());
		comboData.add(c);
		
		return comboData;
	}
	
	/**
	 * obtener el periodo segun el string enviado o si es null devolver el periodo actual
	 * @param periodo
	 * @return
	 */
	private static Date getPeriodo(String periodo) {
		Date periodoActual = null;
		if(!StringUtils.isEmpty(periodo)){
			periodoActual = PeriodoUtils.getDateFormPeriodo(PeriodoUtils.getPeriodoFromDate(PeriodoUtils.getDateFormPeriodo(periodo)));
			
		}else{
			periodoActual = PeriodoUtils.getDateFormPeriodo(PeriodoUtils.getPeriodoFromDate(new Date())); 
		}
		return periodoActual;
	}
}
