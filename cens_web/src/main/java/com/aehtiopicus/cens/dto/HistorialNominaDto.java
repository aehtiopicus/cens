package com.aehtiopicus.cens.dto;

import java.text.ParseException;
import java.util.Date;

import com.aehtiopicus.cens.util.Utils;




public class HistorialNominaDto implements  Comparable<HistorialNominaDto>{

	private Long empleadoId;
	private String fecha;
	private String concepto;
	private String cliente;
	private String sueldoBasico;
	private String sueldoPresentismo;
	private String nombrePuesto;
	private String empleado;
	
	public Long getEmpleadoId() {
		return empleadoId;
	}
	public void setEmpleadoId(Long empleadoId) {
		this.empleadoId = empleadoId;
	}
	
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getConcepto() {
		return concepto;
	}
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public String getSueldoBasico() {
		return sueldoBasico;
	}
	public void setSueldoBasico(String sueldoBasico) {
		this.sueldoBasico = sueldoBasico;
	}
	public String getSueldoPresentismo() {
		return sueldoPresentismo;
	}
	public void setSueldoPresentismo(String sueldoPresentismo) {
		this.sueldoPresentismo = sueldoPresentismo;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
		return result;
	}
	
	@Override
	public int compareTo(HistorialNominaDto obj) {
		int result = 0;
		try {
			Date fechaObj = Utils.sdf.parse(obj.getFecha());
			Date fecha = Utils.sdf.parse(this.fecha);
			
			String strObj = String.valueOf(fechaObj.getTime()) + obj.getTipoIndex();
			String str = String.valueOf(fecha.getTime()) + this.getTipoIndex();
			
			result = str.compareTo(strObj);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}
	public String getNombrePuesto() {
		return nombrePuesto;
	}
	public void setNombrePuesto(String nombrePuesto) {
		this.nombrePuesto = nombrePuesto;
	}
	public String getEmpleado() {
		return empleado;
	}
	public void setEmpleado(String empleado) {
		this.empleado = empleado;
	}

	
 	private static final String RELACION_LABORAL = "Cambio de cliente";
	private static final String INGRESO = "Ingreso a Novatium";
	private static final String SUELDO = "Cambio de Sueldo";
	private static final String PUESTO = "Cambio de Puesto";
	private static final String EGRESO = "Egreso de Novatium";
	 
	public String getTipoIndex() {
		if(this.concepto.equals(INGRESO)) { return "1";}
		if(this.concepto.equals(SUELDO)) { return "2";}
		if(this.concepto.equals(RELACION_LABORAL)) { return "3";}
		if(this.concepto.equals(PUESTO)) { return "4";}
		if(this.concepto.equals(EGRESO)) { return "5";}
		return "0";
	}
}
