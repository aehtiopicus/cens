package com.aehtiopicus.cens.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.aehtiopicus.cens.domain.Empleado;
import com.aehtiopicus.cens.domain.InformeConsolidado;
import com.aehtiopicus.cens.domain.InformeConsolidadoDetalle;
import com.aehtiopicus.cens.domain.InformeConsolidadoStub;
import com.aehtiopicus.cens.enumeration.InformeConsolidadoEstadoEnum;
import com.aehtiopicus.cens.enumeration.InformeConsolidadoTipoEnum;

@Service
public interface InformeConsolidadoService {

	public List<InformeConsolidadoStub> searchInformesConsolidados(String periodo,	Integer page, Integer rows);

	public List<InformeConsolidado> searchInformesConsolidados(String periodoStr);

	public List<InformeConsolidado> searchInformesConsolidados(InformeConsolidadoEstadoEnum estado);

	public int getNroTotalInformesConsolidados(String periodo);

	public InformeConsolidado getByPeriodo(Date p);

	public InformeConsolidado getByPeriodoAndTipo(Date periodo, InformeConsolidadoTipoEnum tipo);
	
	public InformeConsolidado crear(InformeConsolidado informeConsolidado);

	public InformeConsolidado getById(Long informeConsolidadoId);

	public List<InformeConsolidadoDetalle> searchInformesConsolidadosByCliente(String periodo1, Long clienteId);

	public InformeConsolidadoDetalle getDetalleById(Long detalleInformeId);

	public List<InformeConsolidadoDetalle> updateDetalles(List<InformeConsolidadoDetalle> detalles);

	public boolean finalizarInforme(Long informeConsolidadoId);

	public boolean eliminarInforme(Long informeConsolidadoId);
	
	public List<InformeConsolidadoDetalle> getDetallesByPeriodoAndBancoDistintoGalicia(String periodo1);

	public InformeConsolidado crearInformeSac(InformeConsolidado informe);

	public List<InformeConsolidadoDetalle> getDetallesAbiertosByEmpleado(Long empleadoId);

	public InformeConsolidadoDetalle calcularCargasSociales(InformeConsolidadoDetalle detalle, Empleado empleado);

}
