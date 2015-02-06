package com.aehtiopicus.cens.dto.cens;

import com.aehtiopicus.cens.enumeration.cens.DivisionPeriodoType;
import com.aehtiopicus.cens.enumeration.cens.EstadoRevisionType;

public class MaterialDidacticoDto {

	private Long id;	
	private Long profesorId;
	private Long programaId;
	private String nombre;		
	private String descripcion;
	private int nro;		
	private EstadoRevisionType estadoRevisionType = EstadoRevisionType.NUEVO;
	private DivisionPeriodoType divisionPeriodoType;
	private String divisionPeriodoName;
	private String cartillaAdjunta;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getProfesorId() {
		return profesorId;
	}
	public void setProfesorId(Long profesorId) {
		this.profesorId = profesorId;
	}
	public Long getProgramaId() {
		return programaId;
	}
	public void setProgramaId(Long programaId) {
		this.programaId = programaId;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public int getNro() {
		return nro;
	}
	public void setNro(int nro) {
		this.nro = nro;
	}
	public EstadoRevisionType getEstadoRevisionType() {
		return estadoRevisionType;
	}
	public void setEstadoRevisionType(EstadoRevisionType estadoRevisionType) {
		this.estadoRevisionType = estadoRevisionType;
	}
	public DivisionPeriodoType getDivisionPeriodoType() {
		return divisionPeriodoType;
	}
	public void setDivisionPeriodoType(DivisionPeriodoType divisionPeriodoType) {
		this.divisionPeriodoType = divisionPeriodoType;
		if(divisionPeriodoType!=null){
			divisionPeriodoName = divisionPeriodoType.getPeriodoName();
		}
		
	}
	public String getDivisionPeriodoName() {
		return divisionPeriodoName;
	}
	public void setDivisionPeriodoName(String divisionPeriodoName) {
		this.divisionPeriodoName = divisionPeriodoName;
	}
	public String getCartillaAdjunta() {
		return cartillaAdjunta;
	}
	public void setCartillaAdjunta(String cartillaAdjunta) {
		this.cartillaAdjunta = cartillaAdjunta;
	}
	
	
	
	
	
}
