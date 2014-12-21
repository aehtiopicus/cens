package com.aehtiopicus.cens.dto;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExcelInformeConsolidadoDetalleDataDto  implements Comparable<ExcelInformeConsolidadoDetalleDataDto>{
	protected Long id;
	protected String empleado;

	protected String legajo;
	protected String cliente;
	protected String cuil;
	protected String fechaIngresoNovatium;
	
	protected Double sueldoBasico;
	protected Double diasTrabajados;
	
	protected Double basicoMes;
	protected Double asistenciaPuntualidad;

	protected Double nroInasistenciasInjustificadas;
	protected Double importeInasistenciasInjustificadas;
	protected Double nroInasistenciasSinGoceSueldo;
	protected Double importeInasistenciasSinGoceSueldo;
	
	protected Double hsExtras50;
	protected Double hsExtras100;
	protected Double valorHsExtras50;
	protected Double valorHsExtras100;
	
	protected String detalleAdicionales;
	protected List<ExcelInformeConsolidadoDetalleBeneficioDto> beneficios;
	
	protected Double vacacionesDias;
	protected Double vacacionesValor;
	
	protected Double conceptoRemurativoPlus;
	protected Double conceptoNoRemurativo;
	
	protected Double sacBase;
	protected Double sacDias;
	protected Double sacValor;
	
	protected Double sueldoBruto;
	protected Double sueldoBrutoRemunerativo;
	
	protected Double ret11y3;
	protected Double retObraSocial;
	
	protected Double retGanancia;
	
	protected Integer codigoContratacionEmpleado;
	protected Double cont176510;
	protected Double contOS;
	
	protected Double neto;
	
	protected Double celular;
	protected Double prepaga;
	protected Double adelantos;
	protected Double reintegros;
	protected Double embargos;
	
	protected Double cheque;

	protected Double netoADepositar;
	
	protected Boolean usarCheque = false;
	
	protected Boolean hsExtrasConPresentismo = false;
	
	@Override
	public int compareTo(ExcelInformeConsolidadoDetalleDataDto o) {
		return new Integer(this.getLegajo()).compareTo( new Integer(o.getLegajo()));
	}
	
	
	public Integer getCodigoContratacionEmpleado() {
		return codigoContratacionEmpleado;
	}
	public void setCodigoContratacionEmpleado(Integer codigoContratacionEmpleado) {
		this.codigoContratacionEmpleado = codigoContratacionEmpleado;
	}
	public Double getCont176510() {
		return cont176510;
	}
	public void setCont176510(Double cont176510) {
		this.cont176510 = cont176510;
	}
	public Double getContOS() {
		return contOS;
	}
	public void setContOS(Double contOS) {
		this.contOS = contOS;
	}


	public Boolean getHsExtrasConPresentismo() {
		return hsExtrasConPresentismo;
	}
	public void setHsExtrasConPresentismo(Boolean hsExtrasConPresentismo) {
		this.hsExtrasConPresentismo = hsExtrasConPresentismo;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEmpleado() {
		return empleado;
	}
	public void setEmpleado(String empleado) {
		this.empleado = empleado;
	}
	public String getLegajo() {
		return legajo;
	}
	public void setLegajo(String legajo) {
		this.legajo = legajo;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public String getCuil() {
		return cuil;
	}
	public void setCuil(String cuil) {
		this.cuil = cuil;
	}
	public Double getDiasTrabajados() {
		return diasTrabajados;
	}
	public void setDiasTrabajados(Double diasTrabajados) {
		this.diasTrabajados = diasTrabajados;
	}
	public Double getSueldoBasico() {
		return sueldoBasico;
	}
	public void setSueldoBasico(Double sueldoBasico) {
		this.sueldoBasico = sueldoBasico;
	}
	public Double getBasicoMes() {
		return basicoMes;
	}
	public void setBasicoMes(Double basicoMes) {
		this.basicoMes = basicoMes;
	}
	public Double getAsistenciaPuntualidad() {
		return asistenciaPuntualidad;
	}
	public void setAsistenciaPuntualidad(Double asistenciaPuntualidad) {
		this.asistenciaPuntualidad = asistenciaPuntualidad;
	}
	public Double getSueldoBruto() {
		return sueldoBruto;
	}
	public void setSueldoBruto(Double sueldoBruto) {
		this.sueldoBruto = sueldoBruto;
	}
	public String getDetalleAdicionales() {
		if(detalleAdicionales == null || detalleAdicionales.equals("")){
			detalleAdicionales = "<a href='javascript:adicionalesDetalle("+id+")'>VER</a>";
		}
		return detalleAdicionales;
	}
	public void setDetalleAdicionales(String detalleAdicionales) {
		this.detalleAdicionales = detalleAdicionales;
	}
	public Double getHsExtras50() {
		return hsExtras50;
	}
	public void setHsExtras50(Double hsExtras50) {
		this.hsExtras50 = hsExtras50;
	}
	public Double getHsExtras100() {
		return hsExtras100;
	}
	public void setHsExtras100(Double hsExtras100) {
		this.hsExtras100 = hsExtras100;
	}
	public Double getValorHsExtras50() {
		return valorHsExtras50;
	}
	public void setValorHsExtras50(Double valorHsExtras50) {
		this.valorHsExtras50 = valorHsExtras50;
	}
	public Double getValorHsExtras100() {
		return valorHsExtras100;
	}
	public void setValorHsExtras100(Double valorHsExtras100) {
		this.valorHsExtras100 = valorHsExtras100;
	}
	public Double getNetoADepositar() {
		return netoADepositar;
	}
	public void setNetoADepositar(Double netoADepositar) {
		this.netoADepositar = netoADepositar;
	}
	public String getFechaIngresoNovatium() {
		return fechaIngresoNovatium;
	}
	public void setFechaIngresoNovatium(String fechaIngresoNovatium) {
		this.fechaIngresoNovatium = fechaIngresoNovatium;
	}
	public List<ExcelInformeConsolidadoDetalleBeneficioDto> getBeneficios() {
		if(beneficios == null) {
			beneficios = new ArrayList<ExcelInformeConsolidadoDetalleBeneficioDto>();
		}
		return beneficios;
	}
	public void setBeneficios(
			List<ExcelInformeConsolidadoDetalleBeneficioDto> beneficios) {
		this.beneficios = beneficios;
	}
	public Double getVacacionesDias() {
		return vacacionesDias;
	}
	public void setVacacionesDias(Double vacacionesDias) {
		this.vacacionesDias = vacacionesDias;
	}
	public Double getVacacionesValor() {
		return vacacionesValor;
	}
	public void setVacacionesValor(Double vacacionesValor) {
		this.vacacionesValor = vacacionesValor;
	}
	public Double getConceptoRemurativoPlus() {
		return conceptoRemurativoPlus;
	}
	public void setConceptoRemurativoPlus(Double conceptoRemurativoPlus) {
		this.conceptoRemurativoPlus = conceptoRemurativoPlus;
	}
	public Double getConceptoNoRemurativo() {
		return conceptoNoRemurativo;
	}
	public void setConceptoNoRemurativo(Double conceptoNoRemurativo) {
		this.conceptoNoRemurativo = conceptoNoRemurativo;
	}
	public Double getSacBase() {
		return sacBase;
	}
	public void setSacBase(Double sacBase) {
		this.sacBase = sacBase;
	}
	public Double getSacDias() {
		return sacDias;
	}
	public void setSacDias(Double sacDias) {
		this.sacDias = sacDias;
	}
	public Double getSacValor() {
		return sacValor;
	}
	public void setSacValor(Double sacValor) {
		this.sacValor = sacValor;
	}
	public Double getSueldoBrutoRemunerativo() {
		return sueldoBrutoRemunerativo;
	}
	public void setSueldoBrutoRemunerativo(Double sueldoBrutoRemunerativo) {
		this.sueldoBrutoRemunerativo = sueldoBrutoRemunerativo;
	}
	public Double getRet11y3() {
		return ret11y3;
	}
	public void setRet11y3(Double ret11y3) {
		this.ret11y3 = ret11y3;
	}
	public Double getRetObraSocial() {
		return retObraSocial;
	}
	public void setRetObraSocial(Double retObraSocial) {
		this.retObraSocial = retObraSocial;
	}
	public Double getRetGanancia() {
		return retGanancia;
	}
	public void setRetGanancia(Double retGanancia) {
		this.retGanancia = retGanancia;
	}
	public Double getNeto() {
		return neto;
	}
	public void setNeto(Double neto) {
		this.neto = neto;
	}
	public Double getCelular() {
		return celular;
	}
	public void setCelular(Double celular) {
		this.celular = celular;
	}
	public Double getPrepaga() {
		return prepaga;
	}
	public void setPrepaga(Double prepaga) {
		this.prepaga = prepaga;
	}
	public Double getAdelantos() {
		return adelantos;
	}
	public void setAdelantos(Double adelantos) {
		this.adelantos = adelantos;
	}
	public Double getReintegros() {
		return reintegros;
	}
	public void setReintegros(Double reintegros) {
		this.reintegros = reintegros;
	}
	public Double getCheque() {
		return cheque;
	}
	public void setCheque(Double cheque) {
		this.cheque = cheque;
	}
	public Boolean getUsarCheque() {
		return usarCheque;
	}
	public void setUsarCheque(Boolean usarCheque) {
		this.usarCheque = usarCheque;
	}
	public Double getEmbargos() {
		return embargos;
	}
	public void setEmbargos(Double embargos) {
		this.embargos = embargos;
	}
	public Double getNroInasistenciasInjustificadas() {
		return nroInasistenciasInjustificadas;
	}
	public void setNroInasistenciasInjustificadas(
			Double nroInasistenciasInjustificadas) {
		this.nroInasistenciasInjustificadas = nroInasistenciasInjustificadas;
	}
	public Double getImporteInasistenciasInjustificadas() {
		return importeInasistenciasInjustificadas;
	}
	public void setImporteInasistenciasInjustificadas(
			Double importeInasistenciasInjustificadas) {
		this.importeInasistenciasInjustificadas = importeInasistenciasInjustificadas;
	}
	public Double getNroInasistenciasSinGoceSueldo() {
		return nroInasistenciasSinGoceSueldo;
	}
	public void setNroInasistenciasSinGoceSueldo(
			Double nroInasistenciasSinGoceSueldo) {
		this.nroInasistenciasSinGoceSueldo = nroInasistenciasSinGoceSueldo;
	}
	public Double getImporteInasistenciasSinGoceSueldo() {
		return importeInasistenciasSinGoceSueldo;
	}
	public void setImporteInasistenciasSinGoceSueldo(
			Double importeInasistenciasSinGoceSueldo) {
		this.importeInasistenciasSinGoceSueldo = importeInasistenciasSinGoceSueldo;
	}
}
