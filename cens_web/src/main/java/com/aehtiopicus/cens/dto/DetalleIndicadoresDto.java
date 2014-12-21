package com.aehtiopicus.cens.dto;


public class DetalleIndicadoresDto {

	private String empleado;
	private Double sueldoP1;
	private Double sueldoP2;
	private Double diferencia;
	
	
	public String getEmpleado() {
		return empleado;
	}
	public void setEmpleado(String empleado) {
		this.empleado = empleado;
	}
	public Double getSueldoP1() {
		return sueldoP1;
	}
	public void setSueldoP1(Double sueldoP1) {
		this.sueldoP1 = sueldoP1;
	}
	public Double getSueldoP2() {
		return sueldoP2;
	}
	public void setSueldoP2(Double sueldoP2) {
		this.sueldoP2 = sueldoP2;
	}
	public Double getDiferencia() {
		return diferencia;
	}
	public void setDiferencia(Double diferencia) {
		this.diferencia = diferencia;
	}
	
}
