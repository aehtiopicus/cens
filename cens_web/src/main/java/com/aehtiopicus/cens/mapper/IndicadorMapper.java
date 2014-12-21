package com.aehtiopicus.cens.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import com.aehtiopicus.cens.domain.Empleado;
import com.aehtiopicus.cens.domain.InformeConsolidado;
import com.aehtiopicus.cens.domain.InformeConsolidadoDetalle;
import com.aehtiopicus.cens.dto.DetalleIndicadoresDto;
import com.aehtiopicus.cens.dto.IndicadorDto;
import com.aehtiopicus.cens.utils.Utils;


public class IndicadorMapper {

	
	private static final Logger logger = Logger.getLogger(IndicadorMapper.class);

	/**
	 * se calcula en base a los informes, los indicadores
	 * @param informeConsolidados1
	 * @param informeConsolidados2
	 * @return
	 */
	public static List<IndicadorDto> getIndicadorDtoFromEmpleados(InformeConsolidado informeConsolidados1,
			InformeConsolidado informeConsolidados2){	
		logger.info("getIndicadorDtoFromEmpleados");
	    List<IndicadorDto> indicadores = new ArrayList<IndicadorDto>();
		Map<Long,IndicadorDto> indicadoresMapP1 = new HashMap<Long,IndicadorDto>();
		Map<Long,IndicadorDto> indicadoresMapP2 = new HashMap<Long,IndicadorDto>();
		getInformacionPeriodo1(informeConsolidados1, indicadoresMapP1);
		getInformacionPeriodo2(informeConsolidados2, indicadoresMapP2);
		Map<Long,IndicadorDto> resultMap = new HashMap<Long,IndicadorDto>();
		if(!indicadoresMapP1.isEmpty()){
			for(IndicadorDto periodo1 : indicadoresMapP1.values()){
				IndicadorDto result = new IndicadorDto(); 
				if(indicadoresMapP2.containsKey(periodo1.getClienteId())){
					if(periodo1.getCantidadSueldoP1() !=null && indicadoresMapP2.get(periodo1.getClienteId()).getCantidadSueldoP2() != null){
						result.setDiferenciaSueldos(Utils.redondear2Decimales(periodo1.getCantidadSueldoP1() -  indicadoresMapP2.get(periodo1.getClienteId()).getCantidadSueldoP2()));
					}
					if(periodo1.getCantidadEmpleadosP1() !=null && indicadoresMapP2.get(periodo1.getClienteId()).getCantidadEmpleadosP2() != null){
						result.setDiferenciaEmpleados(periodo1.getCantidadEmpleadosP1() -  indicadoresMapP2.get(periodo1.getClienteId()).getCantidadEmpleadosP2());
					}	
					result.setCantidadEmpleadosP2(indicadoresMapP2.get(periodo1.getClienteId()).getCantidadEmpleadosP2());
					result.setCantidadSueldoP2(Utils.redondear2Decimales(indicadoresMapP2.get(periodo1.getClienteId()).getCantidadSueldoP2()));
				}else{
					result.setDiferenciaSueldos(Utils.redondear2Decimales(periodo1.getCantidadSueldoP1()));
					result.setDiferenciaEmpleados(periodo1.getCantidadEmpleadosP1());
					result.setCantidadEmpleadosP2(0l);
					result.setCantidadSueldoP2(0.0);
					
				}
				result.setCantidadEmpleadosP1(periodo1.getCantidadEmpleadosP1());
				result.setCantidadSueldoP1(Utils.redondear2Decimales(periodo1.getCantidadSueldoP1()));
				result.setCliente(periodo1.getCliente());				
				result.setClienteId(periodo1.getClienteId());
				
				resultMap.put(result.getClienteId(), result);
				
			}
		}else if(!indicadoresMapP2.isEmpty() && indicadoresMapP1.isEmpty()){
			for(IndicadorDto periodo2 : indicadoresMapP2.values()){
				IndicadorDto result = new IndicadorDto(); 
				result.setDiferenciaSueldos(Utils.redondear2Decimales(0 - periodo2.getCantidadSueldoP2() ));	
				result.setDiferenciaEmpleados(0-periodo2.getCantidadEmpleadosP2());
				result.setCantidadEmpleadosP2(periodo2.getCantidadEmpleadosP2());
				result.setCantidadSueldoP2(Utils.redondear2Decimales(periodo2.getCantidadSueldoP2()));
				result.setCantidadEmpleadosP1(0l);
				result.setCantidadSueldoP1(0.0);
				result.setCliente(periodo2.getCliente());				
				result.setClienteId(periodo2.getClienteId());
				
				resultMap.put(result.getClienteId(), result);
				
			}
			
		}
		
		for(Long id : indicadoresMapP2.keySet()){
			if(!resultMap.containsKey(id)){
				IndicadorDto resultP2 = new IndicadorDto(); 
				resultP2.setCantidadEmpleadosP1(indicadoresMapP2.get(id).getCantidadEmpleadosP1());
				resultP2.setCantidadSueldoP1(Utils.redondear2Decimales(indicadoresMapP2.get(id).getCantidadSueldoP1()));
				resultP2.setCliente(indicadoresMapP2.get(id).getCliente());			
				resultP2.setClienteId(id);
				resultMap.put(indicadoresMapP2.get(id).getClienteId(), resultP2);
			}
		}
		if(!resultMap.isEmpty()){
			Collection<IndicadorDto> indicadoresResult = resultMap.values();
			for(IndicadorDto indicador : indicadoresResult) {
				if(indicador.getCantidadEmpleadosP1() == null) {
					indicador.setCantidadEmpleadosP1(0l);
				}
				if(indicador.getCantidadEmpleadosP2() == null) {
					indicador.setCantidadEmpleadosP2(0l);
				}
				if(indicador.getCantidadSueldoP1() == null) {
					indicador.setCantidadSueldoP1(0.0);
				}
				if(indicador.getCantidadSueldoP2() == null) {
					indicador.setCantidadSueldoP2(0.0);
				}
				if(indicador.getDiferenciaEmpleados() == null) {
					indicador.setDiferenciaEmpleados(0l);
				}
				if(indicador.getDiferenciaSueldos() == null) {
					indicador.setDiferenciaSueldos(0.0);
					
				}
			}
			indicadores.addAll(resultMap.values());
		}
		
		return indicadores;
	}

	/**
	 * se recorren los detalles y se obtiene la informacion del sueldo y
	 * la cantidad de empleados para el periodo 1
	 * @param informeConsolidados1
	 * @param indicadoresMapP1
	 */
	private static void getInformacionPeriodo1(
			InformeConsolidado informeConsolidados1,
			Map<Long, IndicadorDto> indicadoresMapP1) {
		logger.info("getInformacionPeriodo1");
		if(informeConsolidados1 != null){
			
				List<InformeConsolidadoDetalle> detalles = informeConsolidados1.getDetalle();
				if(CollectionUtils.isNotEmpty(detalles)){
					setInformacionPeriodo1(indicadoresMapP1, detalles);
				  }
			
		 }
	}

	/**
	 * se carga en el mapa la informacion para el cliente correspondiente del periodo 2
	 * @param indicadoresMapP1
	 * @param detalles
	 */
	private static void setInformacionPeriodo1(
			Map<Long, IndicadorDto> indicadoresMapP1,
			List<InformeConsolidadoDetalle> detalles) {
		for(InformeConsolidadoDetalle detalle : detalles){
			logger.info("setInformacionPeriodo1");
			IndicadorDto indicador = new IndicadorDto();
			// se obtiene el id del cliente y se arma un mapa con la informacion para ese cliente
			Long id = getClienteId(detalle);
			if(indicadoresMapP1.containsKey(id)){
				indicador = indicadoresMapP1.get(id);
			}else{
				indicador = new IndicadorDto();
				
			}
			if(detalle.getSueldoBruto() == null){
				detalle.setSueldoBruto(0.0);
			}
			if(indicador.getCantidadSueldoP1() == null){
				indicador.setCantidadSueldoP1(0.0);
			}
			// sumamos el sueldo de al indicador
			indicador.setCantidadSueldoP1(indicador.getCantidadSueldoP1()+detalle.getSueldoBruto());
			// verificamos la cantidad de empleados y  sumamos x cliente 
			if(indicador.getCantidadEmpleadosP1() == null){
				indicador.setCantidadEmpleadosP1(0l);
			}
			indicador.setClienteId(id);
			setClienteRazonSocial(detalle, indicador);
			indicador.setCantidadEmpleadosP1(indicador.getCantidadEmpleadosP1()+1);
			indicadoresMapP1.put(id, indicador);
		 }
	}
	
	/**
	 * se obtiene a partir del detalle el id del empleado
	 * @param detalle
	 * @return
	 */
	private static Long getClienteId(InformeConsolidadoDetalle detalle) {
		Long clienteId;
		try{
			clienteId = detalle.getInformeMensualDetalle().getRelacionLaboral().getCliente().getId();	
		}catch(NullPointerException e){
			clienteId = null;
		}
		return clienteId;
	}
	
	/**
	 * se obtiene a partir del detalle la razon social
	 * @param detalle
	 * @return
	 */
	private static void setClienteRazonSocial(
			InformeConsolidadoDetalle detalle, IndicadorDto indicador) {
		if(detalle.getInformeMensualDetalle() != null && detalle.getInformeMensualDetalle().getRelacionLaboral() != null && detalle.getInformeMensualDetalle().getRelacionLaboral() != null && detalle.getInformeMensualDetalle().getRelacionLaboral().getCliente() != null){
			indicador.setCliente(detalle.getInformeMensualDetalle().getRelacionLaboral().getCliente().getNombre());
		}
	}

	/**
	 * se recorren los detalles y se obtiene la informacion del sueldo y
	 * la cantidad de empleados para el periodo 1
	 * @param informeConsolidados1
	 * @param indicadoresMapP1
	 */
	private static void getInformacionPeriodo2(
			InformeConsolidado informeConsolidados2,
			Map<Long, IndicadorDto> indicadoresMapP2) {
		logger.info("getInformacionPeriodo2");
		if(informeConsolidados2 != null){
			List<InformeConsolidadoDetalle> detalles = informeConsolidados2.getDetalle();
				if(CollectionUtils.isNotEmpty(detalles)){
					setInformacionPeriodo2(indicadoresMapP2, detalles);
				  }
			}
	}

	/**
	 * se carga en el mapa la informacion para el cliente correspondiente del periodo 2
	 * @param indicadoresMapP1
	 * @param detalles
	 */
	private static void setInformacionPeriodo2(
			Map<Long, IndicadorDto> indicadoresMapP2,
			List<InformeConsolidadoDetalle> detalles) {
		logger.info("setInformacionPeriodo2");
		for(InformeConsolidadoDetalle detalle : detalles){
			IndicadorDto indicador = new IndicadorDto();
			// se obtiene el id del cliente y se arma un mapa con la informacion para ese cliente
			Long id = getClienteId(detalle);
			if(indicadoresMapP2.containsKey(id)){
				indicador = indicadoresMapP2.get(id);
			}else{
				indicador = new IndicadorDto();
				
			}
			if(detalle.getSueldoBruto() == null){
				detalle.setSueldoBruto(0.0);
			}
			if(indicador.getCantidadSueldoP2() == null){
				indicador.setCantidadSueldoP2(0.0);
			}
			// sumamos el sueldo de al indicador
			indicador.setCantidadSueldoP2(Utils.redondear2Decimales(indicador.getCantidadSueldoP2()+detalle.getSueldoBruto()));
			// verificamos la cantidad de empleados y  sumamos x cliente 
			if(indicador.getCantidadEmpleadosP2() == null){
				indicador.setCantidadEmpleadosP2(0l);
			}
			indicador.setClienteId(id);
			setClienteRazonSocial(detalle, indicador);
			indicador.setCantidadEmpleadosP2(indicador.getCantidadEmpleadosP2()+1);
			indicadoresMapP2.put(id, indicador);
		 }
	}
	
	/**
	 * Carga los detalles de los sueldos por empleado
	 * @param informeConsolidadoDetalle
	 * @param informeConsolidadosDetalle2
	 */
	public static List<DetalleIndicadoresDto> getInformeDetalle(
			List<InformeConsolidadoDetalle> informeConsolidadoDetalle,List<InformeConsolidadoDetalle> informeConsolidadosDetalle2) {
		logger.info("getInformeDetalle");
		List<DetalleIndicadoresDto> detalles = new ArrayList<DetalleIndicadoresDto>(); 
		Map<Long, DetalleIndicadoresDto> indicadoresMap = new HashMap<Long,DetalleIndicadoresDto>();
		getDetalleIndicadoresPeriodo1(informeConsolidadoDetalle, indicadoresMap);
		getDetalleIndicadoresPeriodo2(informeConsolidadosDetalle2, indicadoresMap);
		// Cargar las diferencias de sueldos
		for(DetalleIndicadoresDto indicador : indicadoresMap.values()){
			if(indicador.getSueldoP1() == null){
				indicador.setSueldoP1(0.0);
			}
			if(indicador.getSueldoP2() == null){
				indicador.setSueldoP2(0.0);
			}
			Double sueldoDiferencia = indicador.getSueldoP1() - indicador.getSueldoP2();
			indicador.setDiferencia(Utils.redondear2Decimales(sueldoDiferencia));
			detalles.add(indicador);
		}
		return detalles;	
	}

	private static void getDetalleIndicadoresPeriodo2(
			List<InformeConsolidadoDetalle> detalles,Map<Long, DetalleIndicadoresDto> indicadoresMap) {
				if(CollectionUtils.isNotEmpty(detalles)){
					cargarDetallesPeriodo2(indicadoresMap, detalles);
				}
	}

	private static void getDetalleIndicadoresPeriodo1(
			List<InformeConsolidadoDetalle> detalles,
			Map<Long, DetalleIndicadoresDto> indicadoresMap) {
			if(CollectionUtils.isNotEmpty(detalles)){
					cargarDetallesPeriodo1(indicadoresMap, detalles);
				
			}
		
	}

	private static void cargarDetallesPeriodo1(
			Map<Long, DetalleIndicadoresDto> indicadoresMap,
			List<InformeConsolidadoDetalle> detalles) {
		for(InformeConsolidadoDetalle detalle : detalles){
				if(detalle.getInformeMensualDetalle() !=null && detalle.getInformeMensualDetalle().getRelacionLaboral() != null){
					DetalleIndicadoresDto detalleDto = loadMap(
							indicadoresMap, detalle);
					detalleDto.setSueldoP1(Utils.redondear2Decimales(detalle.getSueldoBruto()));
				}
		}
	}
	private static void cargarDetallesPeriodo2(
			Map<Long, DetalleIndicadoresDto> indicadoresMap,
			List<InformeConsolidadoDetalle> detalles) {
		for(InformeConsolidadoDetalle detalle : detalles){
				if(detalle.getInformeMensualDetalle() !=null && detalle.getInformeMensualDetalle().getRelacionLaboral() != null){
					DetalleIndicadoresDto detalleDto = loadMap(
							indicadoresMap, detalle);
					detalleDto.setSueldoP2(Utils.redondear2Decimales(detalle.getSueldoBruto()));
				}
		}
	}

	private static DetalleIndicadoresDto loadMap(
			Map<Long, DetalleIndicadoresDto> indicadoresMap,
			InformeConsolidadoDetalle detalle) {
		Empleado empleado = detalle.getInformeMensualDetalle().getRelacionLaboral().getEmpleado();
		Long idEmpleado = empleado.getId();
		DetalleIndicadoresDto detalleDto = new DetalleIndicadoresDto();
		if(!indicadoresMap.containsKey(idEmpleado)){
			indicadoresMap.put(idEmpleado, detalleDto);	
		}else{
			detalleDto = indicadoresMap.get(idEmpleado);
		}
		detalleDto.setEmpleado(empleado.getApellidos()+" "+empleado.getNombres());
		return detalleDto;
	}

}







