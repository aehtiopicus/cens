package com.aehtiopicus.cens.dto;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExcelInformeMensualDetalleDataDto  implements Comparable<ExcelInformeMensualDetalleDataDto>{
	protected Long id;
	protected String empleado;
	protected String condicion;
	protected Double porcentaje;
	protected String fechaIngreso;
	protected String fechaEgreso;
	protected Double sueldoBasico;
	protected Double sueldoPresentismo;
	protected Double diasTrabajados;
	protected Double hsExtrasLiquidar50;		
	protected Double hsExtrasLiquidar100;
	protected Double hsExtrasFacturar50;		
	protected Double hsExtrasFacturar100;
	protected List<ExcelInformeMensualBeneficioDetalleDataDto> beneficios;
	
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
	public String getCondicion() {
		return condicion;
	}
	public void setCondicion(String condicion) {
		this.condicion = condicion;
	}
	public Double getPorcentaje() {
		return porcentaje;
	}
	public void setPorcentaje(Double porcentaje) {
		this.porcentaje = porcentaje;
	}
	public String getFechaIngreso() {
		return fechaIngreso;
	}
	public void setFechaIngreso(String fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}
	public String getFechaEgreso() {
		return fechaEgreso;
	}
	public void setFechaEgreso(String fechaEgreso) {
		this.fechaEgreso = fechaEgreso;
	}
	public Double getSueldoBasico() {
		return sueldoBasico;
	}
	public void setSueldoBasico(Double sueldoBasico) {
		this.sueldoBasico = sueldoBasico;
	}
	public Double getSueldoPresentismo() {
		return sueldoPresentismo;
	}
	public void setSueldoPresentismo(Double sueldoPresentismo) {
		this.sueldoPresentismo = sueldoPresentismo;
	}
	public Double getDiasTrabajados() {
		return diasTrabajados;
	}
	public void setDiasTrabajados(Double diasTrabajados) {
		this.diasTrabajados = diasTrabajados;
	}
	public Double getHsExtrasLiquidar50() {
		return hsExtrasLiquidar50;
	}
	public void setHsExtrasLiquidar50(Double hsExtrasLiquidar50) {
		this.hsExtrasLiquidar50 = hsExtrasLiquidar50;
	}
	public Double getHsExtrasLiquidar100() {
		return hsExtrasLiquidar100;
	}
	public void setHsExtrasLiquidar100(Double hsExtrasLiquidar100) {
		this.hsExtrasLiquidar100 = hsExtrasLiquidar100;
	}
	public Double getHsExtrasFacturar50() {
		return hsExtrasFacturar50;
	}
	public void setHsExtrasFacturar50(Double hsExtrasFacturar50) {
		this.hsExtrasFacturar50 = hsExtrasFacturar50;
	}
	public Double getHsExtrasFacturar100() {
		return hsExtrasFacturar100;
	}
	public void setHsExtrasFacturar100(Double hsExtrasFacturar100) {
		this.hsExtrasFacturar100 = hsExtrasFacturar100;
	}
	public List<ExcelInformeMensualBeneficioDetalleDataDto> getBeneficios() {
		if(beneficios == null) {
			beneficios = new ArrayList<ExcelInformeMensualBeneficioDetalleDataDto>();
		}
		return beneficios;
	}
	public void setBeneficios(
			List<ExcelInformeMensualBeneficioDetalleDataDto> beneficios) {
		this.beneficios = beneficios;
	}
	
	@Override
	public int compareTo(ExcelInformeMensualDetalleDataDto o) {
		return this.getEmpleado().compareTo(o.getEmpleado());
	}
}
