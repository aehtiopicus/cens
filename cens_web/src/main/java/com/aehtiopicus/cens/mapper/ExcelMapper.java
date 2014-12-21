package com.aehtiopicus.cens.mapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.aehtiopicus.cens.domain.Beneficio;
import com.aehtiopicus.cens.domain.InformeConsolidado;
import com.aehtiopicus.cens.domain.InformeConsolidadoDetalle;
import com.aehtiopicus.cens.domain.InformeConsolidadoDetalleBeneficio;
import com.aehtiopicus.cens.domain.InformeMensual;
import com.aehtiopicus.cens.domain.InformeMensualDetalle;
import com.aehtiopicus.cens.domain.InformeMensualDetalleBeneficio;
import com.aehtiopicus.cens.domain.Sueldo;
import com.aehtiopicus.cens.dto.ExcelColumnTypeDto;
import com.aehtiopicus.cens.dto.ExcelInformeConsolidadoDetalleBeneficioDto;
import com.aehtiopicus.cens.dto.ExcelInformeConsolidadoDetalleDataDto;
import com.aehtiopicus.cens.dto.ExcelInformeMensualBeneficioDetalleDataDto;
import com.aehtiopicus.cens.dto.ExcelInformeMensualDetalleDataDto;
import com.aehtiopicus.cens.enumeration.InformeConsolidadoEstadoEnum;
import com.aehtiopicus.cens.enumeration.InformeMensualEstadoEnum;
import com.aehtiopicus.cens.util.Utils;

public class ExcelMapper {
	private static final Logger logger = Logger.getLogger(ExcelMapper.class);
	
	public static Map<String, Object> getMapFromInformeMensual(InformeMensual informe) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		logger.info("genero column Headers");
		List<String> headers = getInformeMensualHeaders(informe);
		result.put("colHeaders", headers);
		
		logger.info("genero column Types");
		List<ExcelColumnTypeDto> types = getInformeMensualTypes(informe);
		result.put("colTypes", types);

		logger.info("genero column Data");
		List<ExcelInformeMensualDetalleDataDto> data = getInformeMensualData(informe);
		result.put("colData", data);

		return result;
	}

	private static List<ExcelColumnTypeDto> getInformeMensualTypes(InformeMensual informe) {
		List<ExcelColumnTypeDto> types = new ArrayList<ExcelColumnTypeDto>();
//		types.add(getColumnType("id","numeric",true,null,null));
		
		if(informe.getEstado().equals(InformeMensualEstadoEnum.BORRADOR)) {
			types.add(getColumnType("empleado","text",true,null,null));
			types.add(getColumnType("condicion","text",false,null,null));
			types.add(getColumnType("porcentaje","numeric",false,"0.00",null));
			types.add(getColumnType("fechaIngreso","date",true,null,"dd/mm/yy"));
			types.add(getColumnType("fechaEgreso","date",true,null,"dd/mm/yy"));
			types.add(getColumnType("sueldoBasico","numeric",true,"0.00",null,true));
			types.add(getColumnType("sueldoPresentismo","numeric",false,"0.00",null,true));
			types.add(getColumnType("diasTrabajados","numeric",false,null,null));
			types.add(getColumnType("hsExtrasLiquidar50","numeric",false,"0.00",null,true));		
			types.add(getColumnType("hsExtrasLiquidar100","numeric",false,"0.00",null,true));
			types.add(getColumnType("hsExtrasFacturar50","numeric",false,"0.00",null,true));		
			types.add(getColumnType("hsExtrasFacturar100","numeric",false,"0.00",null,true));
			
			List<InformeMensualDetalleBeneficio> imBeneficiosDetalle = null;
			if(informe.getDetalle().size() > 0) {
				imBeneficiosDetalle = informe.getDetalle().get(0).getBeneficios();
				Collections.sort(imBeneficiosDetalle);
			}
			if (imBeneficiosDetalle != null && imBeneficiosDetalle.size() > 0) {
				for (InformeMensualDetalleBeneficio imBeneficioDetalle : imBeneficiosDetalle) {
					types.add(getColumnType("b"+imBeneficioDetalle.getBeneficio().getId()+"Valor","numeric",false,"0.00",null,true));
				}
			}
		}else {
			types.add(getColumnType("empleado","text",true,null,null));
			types.add(getColumnType("condicion","text",true,null,null));
			types.add(getColumnType("porcentaje","numeric",true,"0.00",null));
			types.add(getColumnType("fechaIngreso","date",true,null,"dd/mm/yy"));
			types.add(getColumnType("fechaEgreso","date",true,null,"dd/mm/yy"));
			types.add(getColumnType("sueldoBasico","numeric",true,"0.00",null,true));
			types.add(getColumnType("sueldoPresentismo","numeric",true,"0.00",null,true));
			types.add(getColumnType("diasTrabajados","numeric",true,null,null));
			types.add(getColumnType("hsExtrasLiquidar50","numeric",true,"0.00",null,true));		
			types.add(getColumnType("hsExtrasLiquidar100","numeric",true,"0.00",null,true));
			types.add(getColumnType("hsExtrasFacturar50","numeric",true,"0.00",null,true));		
			types.add(getColumnType("hsExtrasFacturar100","numeric",true,"0.00",null,true));
			
			List<InformeMensualDetalleBeneficio> imBeneficiosDetalle = null;
			if(informe.getDetalle().size() > 0) {
				imBeneficiosDetalle = informe.getDetalle().get(0).getBeneficios();
				Collections.sort(imBeneficiosDetalle);
			}
			if (imBeneficiosDetalle != null && imBeneficiosDetalle.size() > 0) {
				for (InformeMensualDetalleBeneficio imBeneficioDetalle : imBeneficiosDetalle) {
					types.add(getColumnType("b"+imBeneficioDetalle.getBeneficio().getId()+"Valor","numeric",false,"0.00",null,true));
				}
			}
		}
		return types;
	}

	private static ExcelColumnTypeDto getColumnType(String data, String type, Boolean readOnly, String format, String dateFormat) {
		ExcelColumnTypeDto colType = new ExcelColumnTypeDto();
		colType.setType(type);
		colType.setData(data);
		colType.setReadOnly(readOnly);
		colType.setDateFormat(dateFormat);
		colType.setFormat(format);
		
		return colType;
	}
	
	private static ExcelColumnTypeDto getColumnType(String data, String type, Boolean readOnly, String format, String dateFormat, Boolean showTotal) {
		ExcelColumnTypeDto colType = new ExcelColumnTypeDto();
		colType.setType(type);
		colType.setData(data);
		colType.setReadOnly(readOnly);
		colType.setDateFormat(dateFormat);
		colType.setFormat(format);
		colType.setShowTotal(showTotal);
		return colType;
	}
	
	private static List<String> getInformeMensualHeaders(InformeMensual informe) {
		List<String> headers = new ArrayList<String>();
//		headers.add("Codigo");
		headers.add("Apellido y Nombre");
		headers.add("Cond");
		headers.add("%");
		headers.add("Fecha Ingreso");
		headers.add("Fecha Egreso");
		headers.add("S. B&aacute;sico");
		headers.add("Asistencia y Puntualidad");
		headers.add("Nro D&iacute;as trab.");
		headers.add("HE. Liq. 50%");
		headers.add("HE. Liq. 100%");
		headers.add("HE. Fact. 50%");
		headers.add("HE. Fact. 100%");
		
		List<InformeMensualDetalleBeneficio> imBeneficiosDetalle = null;
		
		if(informe.getDetalle().size() > 0) {
			imBeneficiosDetalle = informe.getDetalle().get(0).getBeneficios();
			Collections.sort(imBeneficiosDetalle);
		}
		if (imBeneficiosDetalle != null && imBeneficiosDetalle.size() > 0) {
			for (InformeMensualDetalleBeneficio imBeneficio : imBeneficiosDetalle) {
				headers.add(imBeneficio.getBeneficio().getBeneficio().getTitulo());					
			}
		}

		return headers;
	}

	private static List<ExcelInformeMensualDetalleDataDto> getInformeMensualData(InformeMensual informe) {
		List<InformeMensualDetalle> detalles = informe.getDetalle();
		
		List<ExcelInformeMensualDetalleDataDto> data = new ArrayList<ExcelInformeMensualDetalleDataDto>();
		
		ExcelInformeMensualDetalleDataDto detalleDto;
		for (InformeMensualDetalle detalle : detalles) {
			detalleDto = new ExcelInformeMensualDetalleDataDto();
			
			detalleDto.setId(detalle.getId());
			detalleDto.setCondicion(detalle.getCondicion());
			detalleDto.setPorcentaje(detalle.getPorcentaje());
			
			if(detalle.getRelacionLaboral() != null) {
				
				if(detalle.getRelacionLaboral().getEmpleado() != null) {
					detalleDto.setEmpleado(detalle.getRelacionLaboral().getEmpleado().getApellidos() + ", " + detalle.getRelacionLaboral().getEmpleado().getNombres());

					//Sueldo sueldo = detalle.getRelacionLaboral().getEmpleado().getSueldoVigente(); 
					Sueldo sueldo = detalle.getRelacionLaboral().getEmpleado().getSueldoEnPeriodo(informe.getPeriodo()); 
					if(detalle.getPresentismo() != null) {
						detalleDto.setSueldoPresentismo(detalle.getPresentismo());
					}else {
						detalleDto.setSueldoPresentismo(sueldo.getPresentismo());
					}
					if(detalle.getSueldoBasico() != null){
						detalleDto.setSueldoBasico(detalle.getSueldoBasico());
					}else {
						detalleDto.setSueldoBasico(sueldo.getBasico());
					}
				}

				detalleDto.setFechaIngreso(Utils.sdf.format(detalle.getRelacionLaboral().getFechaInicio()));
				if(detalle.getRelacionLaboral().getFechaFin() != null) {
					detalleDto.setFechaEgreso(Utils.sdf.format(detalle.getRelacionLaboral().getFechaFin()));
				}
			}
			
			detalleDto.setDiasTrabajados(detalle.getDiasTrabajados());
			detalleDto.setHsExtrasLiquidar50(detalle.getHsExtrasALiquidarAl50());
			detalleDto.setHsExtrasLiquidar100(detalle.getHsExtrasALiquidarAl100());
			detalleDto.setHsExtrasFacturar50(detalle.getHsExtrasAFacturarAl50());
			detalleDto.setHsExtrasFacturar100(detalle.getHsExtrasAFacturarAl100());
			
			if(detalle.getBeneficios().size() > 0) {
				for(InformeMensualDetalleBeneficio detBeneficio : detalle.getBeneficios()) {
					detalleDto.getBeneficios().add(getBeneficioDtoFromBeneficioInforme(detBeneficio));
				}
			}
					
			data.add(detalleDto);
		}
		
		Collections.sort(data);
		return data;
	}

	private static ExcelInformeMensualBeneficioDetalleDataDto getBeneficioDtoFromBeneficioInforme(InformeMensualDetalleBeneficio detalleBeneficio) {
		ExcelInformeMensualBeneficioDetalleDataDto dto = new ExcelInformeMensualBeneficioDetalleDataDto();
		dto.setBeneficioId(detalleBeneficio.getBeneficio().getId());
		if(detalleBeneficio.getValor() != null) {
			dto.setValor(detalleBeneficio.getValor());
		}else {
			dto.setValor(detalleBeneficio.getBeneficio().getValor().doubleValue());						
		}
		
		return dto;
	}

	
	
	public static Map<String, Object> getMapFromInformeConsolidado(InformeConsolidado informe, boolean showVacaciones, boolean showSac) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		logger.info("obtengo los beneficios a cargar en el informe");
		List<Beneficio> beneficios = getBeneficiosParaInforme(informe);
		
		logger.info("genero column Headers");
		List<String> headers = getInformeConsolidadoHeaders(beneficios, showVacaciones, showSac, informe.getEstado());
		result.put("colHeaders", headers);
		
		logger.info("genero column Types");
		List<ExcelColumnTypeDto> types = getInformeConsolidadoTypes(informe.getEstado(), beneficios, showVacaciones, showSac);
		result.put("colTypes", types);

		logger.info("genero column Data");
		List<ExcelInformeConsolidadoDetalleDataDto> data = getInformeConsolidadoData(informe.getDetalle());
		Collections.sort(data);
		result.put("colData", data);

		return result;
	}

	private static List<Beneficio> getBeneficiosParaInforme(InformeConsolidado informe) {
		Map<Long, Beneficio> beneficiosMap = new HashMap<Long, Beneficio>();
		
		for (InformeConsolidadoDetalle det : informe.getDetalle()) {
			for(InformeConsolidadoDetalleBeneficio detBen : det.getBeneficios()) {
				if(!beneficiosMap.containsKey(detBen.getBeneficio().getId())) {
					beneficiosMap.put(detBen.getBeneficio().getId(), detBen.getBeneficio());
				}
			}
		}
		
		ArrayList<Beneficio> beneficios = new ArrayList<Beneficio>(beneficiosMap.values());
		Collections.sort(beneficios);
		return beneficios;
	}

	private static List<ExcelInformeConsolidadoDetalleDataDto> getInformeConsolidadoData(List<InformeConsolidadoDetalle> detalles) {
		List<ExcelInformeConsolidadoDetalleDataDto> data = new ArrayList<ExcelInformeConsolidadoDetalleDataDto>();
	
		ExcelInformeConsolidadoDetalleDataDto detalleDto;
		for (InformeConsolidadoDetalle detalle : detalles) {
			detalleDto = new ExcelInformeConsolidadoDetalleDataDto();
			detalleDto.setId(detalle.getId());
			
			detalleDto.setEmpleado(detalle.getNombreEmpleado());
			detalleDto.setLegajo(detalle.getLegajoEmpleado());
			detalleDto.setCuil(detalle.getCuilEmpleado());
			detalleDto.setCliente(detalle.getNombreCliente());
			detalleDto.setFechaIngresoNovatium(Utils.sdf.format(detalle.getFechaIngresoNovatiumEmpleado()));
			
			detalleDto.setSueldoBasico(detalle.getSueldoBasico());
			detalleDto.setDiasTrabajados(detalle.getDiasTrabajados());
			detalleDto.setBasicoMes(detalle.getBasicoMes());
			detalleDto.setAsistenciaPuntualidad(detalle.getAsistenciaPuntualidad());
			
			detalleDto.setNroInasistenciasInjustificadas(detalle.getNroInasistenciasInjustificadas());
			detalleDto.setImporteInasistenciasInjustificadas(detalle.getImporteInasistenciasInjustificadas());
			detalleDto.setNroInasistenciasSinGoceSueldo(detalle.getNroInasistenciasSinGoceDeSueldo());
			detalleDto.setImporteInasistenciasSinGoceSueldo(detalle.getImporteInasistenciasSinGoceDeSueldo());
			
			detalleDto.setHsExtras100(detalle.getHsExtras100());
			detalleDto.setHsExtras50(detalle.getHsExtras50());
			detalleDto.setValorHsExtras100(detalle.getValorHsExtras100());
			detalleDto.setValorHsExtras50(detalle.getValorHsExtras50());
			
			detalleDto.setVacacionesDias(detalle.getVacacionesDias());
			detalleDto.setVacacionesValor(detalle.getVacacionesValor());
			detalleDto.setConceptoRemurativoPlus(detalle.getConceptoRemunerativoPlus());
			detalleDto.setConceptoNoRemurativo(detalle.getConceptoNoRemunerativo());
			detalleDto.setSacBase(detalle.getSacBase());
			detalleDto.setSacDias(detalle.getSacDias());
			detalleDto.setSacValor(detalle.getSacValor());
			detalleDto.setSueldoBruto(detalle.getSueldoBruto());
			detalleDto.setSueldoBrutoRemunerativo(detalle.getSueldoBrutoRemunerativo());
			
			detalleDto.setRet11y3(detalle.getRet11y3());
			detalleDto.setRetObraSocial(detalle.getRetObraSocial());
			detalleDto.setRetGanancia(detalle.getRetGanancia());
			
			detalleDto.setCodigoContratacionEmpleado(detalle.getCodigoContratacionEmpleado());
			detalleDto.setCont176510(detalle.getCont176510());
			detalleDto.setContOS(detalle.getContOS());
			
			detalleDto.setNeto(detalle.getNeto());
			detalleDto.setCelular(detalle.getCelular());
			detalleDto.setPrepaga(detalle.getPrepaga());
			detalleDto.setAdelantos(detalle.getAdelantos());
			detalleDto.setReintegros(detalle.getReintegros());
			detalleDto.setEmbargos(detalle.getEmbargos());
			
			detalleDto.setCheque(detalle.getCheque());
			
			detalleDto.setNetoADepositar(detalle.getNetoADepositar());
			
			ExcelInformeConsolidadoDetalleBeneficioDto detalleBeneficioDto; 
			for (InformeConsolidadoDetalleBeneficio detalleBeneficio : detalle.getBeneficios()) {
				detalleBeneficioDto = new ExcelInformeConsolidadoDetalleBeneficioDto();
				detalleBeneficioDto.setBeneficioId(detalleBeneficio.getBeneficio().getId());
				detalleBeneficioDto.setId(detalleBeneficio.getId());
				detalleBeneficioDto.setValor(detalleBeneficio.getImporte());
				detalleDto.getBeneficios().add(detalleBeneficioDto);
			}
			
			//indicar si se debe pagar con cheque:
			detalleDto.setUsarCheque(detalle.getUsarCheque());
			
			if(detalle.getInformeMensualDetalle() != null && detalle.getInformeMensualDetalle().getRelacionLaboral() != null 
					&& detalle.getInformeMensualDetalle().getRelacionLaboral().getCliente() != null) {
				
				detalleDto.setHsExtrasConPresentismo(detalle.getInformeMensualDetalle().getRelacionLaboral().getCliente().getHsExtrasConPresentismo());
			}else {
				detalleDto.setHsExtrasConPresentismo(false);
			}
			
			data.add(detalleDto);
		}

		
		return data;
	}

	private static List<ExcelColumnTypeDto> getInformeConsolidadoTypes(InformeConsolidadoEstadoEnum estado, List<Beneficio> beneficios, boolean showVacaciones, boolean showSac) {
		List<ExcelColumnTypeDto> types = new ArrayList<ExcelColumnTypeDto>();
		
		if(estado.equals(InformeConsolidadoEstadoEnum.BORRADOR)) {
			types.add(getColumnType("empleado","text",true,null,null));
			types.add(getColumnType("legajo","text",true,null,null));
			types.add(getColumnType("cliente","text",true,null,null));
			types.add(getColumnType("cuil","numeric",true,null,null));
			types.add(getColumnType("sueldoBasico","numeric",true,"0.00",null,true));
			types.add(getColumnType("diasTrabajados","numeric",false,null,null));
			types.add(getColumnType("basicoMes","numeric",true,"0.00",null,true));
			types.add(getColumnType("asistenciaPuntualidad","numeric",false,"0.00",null,true));

			types.add(getColumnType("nroInasistenciasInjustificadas","numeric",false,"0",null,false));
			types.add(getColumnType("importeInasistenciasInjustificadas","numeric",true,"0.00",null,true));
			types.add(getColumnType("nroInasistenciasSinGoceSueldo","numeric",false,"0",null,false));
			types.add(getColumnType("importeInasistenciasSinGoceSueldo","numeric",true,"0.00",null,true));
			
			types.add(getColumnType("hsExtras50","numeric",false,"0.00",null,true));
			types.add(getColumnType("hsExtras100","numeric",false,"0.00",null,true));
			types.add(getColumnType("valorHsExtras50","numeric",false,"0.00",null,true));
			types.add(getColumnType("valorHsExtras100","numeric",false,"0.00",null,true));
			

			//Beneficios
			if (beneficios != null && beneficios.size() > 0) {
				Collections.sort(beneficios);
				for (Beneficio beneficio : beneficios) {
					types.add(getColumnType("b_"+ beneficio.getId() +"_Valor","numeric",false,"0.00",null,true));						
				}
			}
			//----------

			if(showVacaciones) {
				//solo en febrero...
				types.add(getColumnType("vacacionesDias","numeric",false,null,null));
				types.add(getColumnType("vacacionesValor","numeric",false,"0.00",null,true));
				//------------------				
			}
			
			types.add(getColumnType("conceptoRemurativoPlus","numeric",false,"0.00",null,true));
			types.add(getColumnType("conceptoNoRemurativo","numeric",false,"0.00",null,true));

			//if(showSac) {
				//solo en junio y dic
				types.add(getColumnType("sacBase","numeric",false,"0.00",null,true));
				types.add(getColumnType("sacDias","numeric",false,null,null));
				types.add(getColumnType("sacValor","numeric",false,"0.00",null,true));
				//-------------------
			//}

			types.add(getColumnType("sueldoBruto","numeric",false,"0.00",null,true));
			types.add(getColumnType("sueldoBrutoRemunerativo","numeric",false,"0.00",null,true));

			types.add(getColumnType("ret11y3","numeric",false,"0.00",null,true));
			types.add(getColumnType("retObraSocial","numeric",false,"0.00",null,true));
			types.add(getColumnType("retGanancia","numeric",false,"0.00",null,true));

			types.add(getColumnType("cont176510","numeric",true,"0.00",null,true));
			types.add(getColumnType("contOS","numeric",true,"0.00",null,true));
			
			types.add(getColumnType("neto","numeric",false,"0.00",null,true));

			types.add(getColumnType("celular","numeric",false,"0.00",null,true));
			types.add(getColumnType("prepaga","numeric",false,"0.00",null,true));
			types.add(getColumnType("adelantos","numeric",false,"0.00",null,true));
			types.add(getColumnType("reintegros","numeric",false,"0.00",null,true));
			types.add(getColumnType("embargos","numeric",false,"0.00",null,true));

			types.add(getColumnType("cheque","numeric",false,"0.00",null,true));
			
			types.add(getColumnType("netoADepositar","numeric",false,"0.00",null,true));

			types.add(getColumnType("fechaIngresoNovatium","date",true,null,"dd/mm/yy"));
			types.add(getColumnType("usarCheque","checkbox",false, null, null));

		}else {
			types.add(getColumnType("empleado","text",true,null,null));
			types.add(getColumnType("legajo","text",true,null,null));
			types.add(getColumnType("cliente","text",true,null,null));
			types.add(getColumnType("cuil","numeric",true,null,null));
			types.add(getColumnType("sueldoBasico","numeric",true,"0.00",null,true));
			types.add(getColumnType("diasTrabajados","numeric",true,null,null));
			types.add(getColumnType("basicoMes","numeric",true,"0.00",null,true));
			types.add(getColumnType("asistenciaPuntualidad","numeric",true,"0.00",null,true));

			types.add(getColumnType("nroInasistenciasInjustificadas","numeric",true,"0",null,false));
			types.add(getColumnType("importeInasistenciasInjustificadas","numeric",true,"0.00",null,true));
			types.add(getColumnType("nroInasistenciasSinGoceSueldo","numeric",true,"0",null,false));
			types.add(getColumnType("importeInasistenciasSinGoceSueldo","numeric",true,"0.00",null,true));			
			
			types.add(getColumnType("hsExtras50","numeric",true,"0.00",null,true));
			types.add(getColumnType("hsExtras100","numeric",true,"0.00",null,true));
			types.add(getColumnType("valorHsExtras50","numeric",true,"0.00",null,true));
			types.add(getColumnType("valorHsExtras100","numeric",true,"0.00",null,true));
			

			//Beneficios
			if (beneficios != null && beneficios.size() > 0) {
				Collections.sort(beneficios);
				for (Beneficio beneficio : beneficios) {
					types.add(getColumnType("b_"+ beneficio.getId() +"_Valor","numeric",true,"0.00",null,true));						
				}
			}
			//----------
			
			if(showVacaciones) {
				//solo en febrero...
				types.add(getColumnType("vacacionesDias","numeric",true,null,null));
				types.add(getColumnType("vacacionesValor","numeric",true,"0.00",null,true));
				//------------------
			}
			
			types.add(getColumnType("conceptoRemurativoPlus","numeric",true,"0.00",null,true));
			types.add(getColumnType("conceptoNoRemurativo","numeric",true,"0.00",null,true));

			//if(showSac) {
				//solo en junio y dic
				types.add(getColumnType("sacBase","numeric",true,"0.00",null,true));
				types.add(getColumnType("sacDias","numeric",true,null,null));
				types.add(getColumnType("sacValor","numeric",true,"0.00",null,true));
				//-------------------
			//}

			types.add(getColumnType("sueldoBruto","numeric",true,"0.00",null,true));
			types.add(getColumnType("sueldoBrutoRemunerativo","numeric",true,"0.00",null,true));

			types.add(getColumnType("ret11y3","numeric",true,"0.00",null,true));
			types.add(getColumnType("retObraSocial","numeric",true,"0.00",null,true));
			types.add(getColumnType("retGanancia","numeric",true,"0.00",null,true));

			types.add(getColumnType("cont176510","numeric",true,"0.00",null,true));
			types.add(getColumnType("contOS","numeric",true,"0.00",null,true));
			
			
			types.add(getColumnType("neto","numeric",true,"0.00",null,true));

			types.add(getColumnType("celular","numeric",true,"0.00",null,true));
			types.add(getColumnType("prepaga","numeric",true,"0.00",null,true));
			types.add(getColumnType("adelantos","numeric",true,"0.00",null,true));
			types.add(getColumnType("reintegros","numeric",true,"0.00",null,true));
			types.add(getColumnType("embargos","numeric",true,"0.00",null,true));
			
			types.add(getColumnType("cheque","numeric",true,"0.00",null,true));
			
			types.add(getColumnType("netoADepositar","numeric",true,"0.00",null,true));

			types.add(getColumnType("fechaIngresoNovatium","date",true,null,"dd/mm/yy"));
//			types.add(getColumnType("usarCheque","checkbox",true, null, null));
		}
		return types;
	}

	private static List<String> getInformeConsolidadoHeaders(List<Beneficio> beneficios, boolean showVacaciones, boolean showSac, InformeConsolidadoEstadoEnum estado) {
		List<String> headers = new ArrayList<String>();

		headers.add("Apellido y Nombre");
		headers.add("Legajo");
		headers.add("Cliente");
		headers.add("CUIL");
		headers.add("Dato b&aacute;sico");
		headers.add("D&iacute;as");
		headers.add("B&aacute;sico");
		headers.add("Asist. y punt.");
		
		headers.add("I. Injustif.");
		headers.add("Imp. I. Injustif.");
		headers.add("I. sin goce de Sueldo");
		headers.add("Imp. I. sin goce de Sueldo");
		
		headers.add("c50");
		headers.add("c100");
		headers.add("HE 50");
		headers.add("HE 100");

		//Beneficios
		if(beneficios != null && beneficios.size() > 0) {
			Collections.sort(beneficios);
			for (Beneficio beneficio : beneficios) {
				headers.add(beneficio.getTitulo());
			}			
		}
		//----------------------------
		
		
		if(showVacaciones) {
			//Solo debe aparecer en febrero
			headers.add("Vac. D&iacute;as");
			headers.add("Vacaciones");
			//-----------------------------
		}
		
		headers.add("Con Rem Plus");
		headers.add("Con No Rem");
		
		//if(showSac) {
			//Solo en junio y diciembre
			headers.add("SAC Base");
			headers.add("SAC D&iacute;as");
			headers.add("SAC");
			//-----------------------------
		//}
		
		headers.add("Bruto");
		headers.add("Bruto Rem");
		
		headers.add("Ret (11+3)");
		headers.add("Aportes OS");
		headers.add("Ret Ganancias");

		headers.add("Cont 17-6,5-10%");
		headers.add("Cont OS 6%");

		headers.add("Neto");
		
		headers.add("Celular");
		headers.add("Prepaga");
		headers.add("Adelantos");
		headers.add("Reintegros");
		headers.add("Embargos");
		
		headers.add("Cheque");
		

		headers.add("Neto a Depositar");
		
		
		headers.add("Fecha Ingreso");
		
		if(estado.equals(InformeConsolidadoEstadoEnum.BORRADOR)) {
			headers.add("Pagar con Cheque");
		}
		return headers;
	}
}
