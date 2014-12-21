package com.aehtiopicus.cens.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.aehtiopicus.cens.controller.IndicadorController;
import com.aehtiopicus.cens.domain.InformeConsolidado;
import com.aehtiopicus.cens.domain.InformeConsolidadoDetalle;
import com.aehtiopicus.cens.domain.InformeConsolidadoDetalleBeneficio;
import com.aehtiopicus.cens.domain.InformeConsolidadoStub;
import com.aehtiopicus.cens.domain.InformeMensualDetalleBeneficio;
import com.aehtiopicus.cens.dto.ExcelInformeConsolidadoDetalleBeneficioDto;
import com.aehtiopicus.cens.dto.ExcelInformeConsolidadoDetalleDataDto;
import com.aehtiopicus.cens.dto.InformeConsolidadoAdicionalDetalleDto;
import com.aehtiopicus.cens.dto.InformeConsolidadoHeaderDto;
import com.aehtiopicus.cens.enumeration.InformeConsolidadoEstadoEnum;
import com.aehtiopicus.cens.util.Utils;
import com.aehtiopicus.cens.utils.PeriodoUtils;



public class InformeConsolidadoMapper {

	private static final Logger logger = Logger.getLogger(IndicadorController.class);
	
	public static List<InformeConsolidadoHeaderDto> getInformesConsolidadosHeaderDtoFromInformesConsolidados(List<InformeConsolidado> informes) {
		List<InformeConsolidadoHeaderDto> dtos = new ArrayList<InformeConsolidadoHeaderDto>();
		logger.info("getInformesConsolidadosHeaderDtoFromInformesConsolidados");
		for (InformeConsolidado entity : informes) {
			dtos.add(getInformeConsolidadoHeaderDtoFromInformeConsolidado(entity));
		}
		return dtos;
	}


	public static InformeConsolidadoHeaderDto getInformeConsolidadoHeaderDtoFromInformeConsolidado(InformeConsolidado informe) {
		InformeConsolidadoHeaderDto dto = new InformeConsolidadoHeaderDto();
		logger.info("getInformeConsolidadoHeaderDtoFromInformeConsolidado");
		if(informe != null) {
			dto.setEstado(informe.getEstado().getNombre());
			
			if(informe.getId() != null) {
				dto.setInformeConsolidadoId(informe.getId());
			}
			if(informe.getPeriodo() != null) {
				dto.setPeriodo(PeriodoUtils.getPeriodoFromDate(informe.getPeriodo()));
			}
			if(informe.getTipo() != null) {
				dto.setTipo(informe.getTipo().getNombre());
			}
				
		}
		
		return dto;
	}


	public static List<InformeConsolidadoHeaderDto> getInformesConsolidadosHeaderDtoFromInformesConsolidadosStub(List<InformeConsolidadoStub> informes) {
		List<InformeConsolidadoHeaderDto> dtos = new ArrayList<InformeConsolidadoHeaderDto>();
		logger.info("getInformesConsolidadosHeaderDtoFromInformesConsolidados");
		for (InformeConsolidadoStub entity : informes) {
			dtos.add(getInformeConsolidadoHeaderDtoFromInformeConsolidadoStub(entity));
		}
		return dtos;
	}	
	
	public static InformeConsolidadoHeaderDto getInformeConsolidadoHeaderDtoFromInformeConsolidadoStub(InformeConsolidadoStub informe) {
		InformeConsolidadoHeaderDto dto = new InformeConsolidadoHeaderDto();
		logger.info("getInformeConsolidadoHeaderDtoFromInformeConsolidado");
		if(informe != null) {
			dto.setEstado(informe.getEstado().getNombre());
			
			if(informe.getId() != null) {
				dto.setInformeConsolidadoId(informe.getId());
			}
			if(informe.getPeriodo() != null) {
				dto.setPeriodo(PeriodoUtils.getPeriodoFromDate(informe.getPeriodo()));
			}
			if(informe.getTipo() != null) {
				dto.setTipo(informe.getTipo().getNombre());
			}
				
		}
		
		return dto;
	}
	
	public static InformeConsolidado getInformeConsolidadoFromInformeConsolidadoHeaderDto(InformeConsolidadoHeaderDto dto) {
		InformeConsolidado entity = new InformeConsolidado();
		logger.info("getInformeConsolidadoFromInformeConsolidadoHeaderDto");
		if(dto != null) {
			if(dto.getInformeConsolidadoId() != null) {
				entity.setId(dto.getInformeConsolidadoId());
			}
			if(StringUtils.isNotEmpty(dto.getEstado())){
				entity.setEstado(InformeConsolidadoEstadoEnum.getInformeConsolidadoEstadoEnumFromString(dto.getEstado()));
			}else {
				entity.setEstado(InformeConsolidadoEstadoEnum.BORRADOR);
			}
			
			if(StringUtils.isNotEmpty(dto.getPeriodo())) {
				entity.setPeriodo(PeriodoUtils.getDateFormPeriodo(dto.getPeriodo()));
			}
		}
		return entity;
	}


	public static List<InformeConsolidadoAdicionalDetalleDto> getInformesConsolidadosAdicionalesDetalleFromBeneficiosCliente(List<InformeMensualDetalleBeneficio> beneficios) {
		List<InformeConsolidadoAdicionalDetalleDto> dtos = new ArrayList<InformeConsolidadoAdicionalDetalleDto>();
		
		if(beneficios != null) {
			InformeConsolidadoAdicionalDetalleDto dto;
			for (InformeMensualDetalleBeneficio entity : beneficios) {
				dto = new InformeConsolidadoAdicionalDetalleDto();
				dto.setBeneficio(entity.getBeneficio().getBeneficio().getTitulo());
				dto.setValor(entity.getValor());
				dtos.add(dto);
			}
		}
		return dtos;
	}


	public static InformeConsolidadoDetalle updateDetalleFromDetalleDto(InformeConsolidadoDetalle entity, ExcelInformeConsolidadoDetalleDataDto dto) {
		
		entity.setDiasTrabajados(dto.getDiasTrabajados());
		entity.setSueldoBasico(dto.getSueldoBasico());
		entity.setBasicoMes(Utils.redondear2Decimales(dto.getBasicoMes()));
		entity.setAsistenciaPuntualidad(dto.getAsistenciaPuntualidad());
		
		entity.setNroInasistenciasInjustificadas(dto.getNroInasistenciasInjustificadas());
		entity.setImporteInasistenciasInjustificadas(dto.getImporteInasistenciasInjustificadas());
		entity.setNroInasistenciasSinGoceDeSueldo(dto.getNroInasistenciasSinGoceSueldo());
		entity.setImporteInasistenciasSinGoceDeSueldo(dto.getImporteInasistenciasSinGoceSueldo());
		
		entity.setHsExtras50(dto.getHsExtras50());
		entity.setHsExtras100(dto.getHsExtras100());
		entity.setValorHsExtras50(Utils.redondear2Decimales(dto.getValorHsExtras50()));
		entity.setValorHsExtras100(Utils.redondear2Decimales(dto.getValorHsExtras100()));
		
		if(dto.getBeneficios() != null && dto.getBeneficios().size() > 0) {
			Map<Long, InformeConsolidadoDetalleBeneficio> aux = new HashMap<Long, InformeConsolidadoDetalleBeneficio>();
			for(InformeConsolidadoDetalleBeneficio detalleBeneficio : entity.getBeneficios()) {
				aux.put(detalleBeneficio.getId(), detalleBeneficio);
			}
			
			for(ExcelInformeConsolidadoDetalleBeneficioDto detalleBeneficioDto : dto.getBeneficios()) {
				if(aux.containsKey(detalleBeneficioDto.getId())) {
					aux.get(detalleBeneficioDto.getId()).setImporte(detalleBeneficioDto.getValor());
				}
			}
			
			entity.setBeneficios(new ArrayList<InformeConsolidadoDetalleBeneficio>(aux.values()));
		}
		
		entity.setVacacionesDias(dto.getVacacionesDias());
		entity.setVacacionesValor(Utils.redondear2Decimales(dto.getVacacionesValor()));
		entity.setConceptoRemunerativoPlus(dto.getConceptoRemurativoPlus());
		entity.setConceptoNoRemunerativo(dto.getConceptoNoRemurativo());
		
		entity.setSacBase(dto.getSacBase());
		entity.setSacDias(dto.getSacDias());
		entity.setSacValor(Utils.redondear2Decimales(dto.getSacValor()));
		
		entity.setSueldoBruto(Utils.redondear2Decimales(dto.getSueldoBruto()));
		entity.setSueldoBrutoRemunerativo(Utils.redondear2Decimales(dto.getSueldoBrutoRemunerativo()));

		entity.setRet11y3(Utils.redondear2Decimales(dto.getRet11y3()));
		entity.setRetObraSocial(Utils.redondear2Decimales(dto.getRetObraSocial()));

		entity.setRetGanancia(Utils.redondear2Decimales(dto.getRetGanancia()));

		entity.setCont176510(Utils.redondear2Decimales(dto.getCont176510()));
		entity.setContOS(Utils.redondear2Decimales(dto.getContOS()));
		
		entity.setNeto(Utils.redondear2Decimales(dto.getNeto()));

		entity.setCelular(dto.getCelular());
		entity.setPrepaga(dto.getPrepaga());
		entity.setAdelantos(dto.getAdelantos());
		entity.setReintegros(dto.getReintegros());
		entity.setEmbargos(dto.getEmbargos());

		entity.setCheque(Utils.redondear2Decimales(dto.getCheque()));
		entity.setNetoADepositar(Utils.redondear2Decimales(dto.getNetoADepositar()));
		
		entity.setUsarCheque(dto.getUsarCheque());
		
		return entity;
	}


}
