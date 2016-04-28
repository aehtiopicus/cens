package com.aehtiopicus.cens.domain.entities;

import java.util.Date;

import com.aehtiopicus.cens.enumeration.cens.EstadoRevisionType;

public class AsignaturaTiempoEdicion {

	private Long asignaturaId;
	private Long profesorId;
	private Date fechaAsignacion;
	private Long programaId;
	private EstadoRevisionType estadoRevision;
	private Long cantidadCartillas;
	private Date programaFechaUpdate;
	public Long getAsignaturaId() {
		return asignaturaId;
	}
	public void setAsignaturaId(Long asignaturaId) {
		this.asignaturaId = asignaturaId;
	}
	public Long getProfesorId() {
		return profesorId;
	}
	public void setProfesorId(Long profesorId) {
		this.profesorId = profesorId;
	}
	public Date getFechaAsignacion() {
		return fechaAsignacion;
	}
	public void setFechaAsignacion(Date fechaAsignacion) {
		this.fechaAsignacion = fechaAsignacion;
	}
	public Long getProgramaId() {
		return programaId;
	}
	public void setProgramaId(Long programaId) {
		this.programaId = programaId;
	}
	public EstadoRevisionType getEstadoRevision() {
		return estadoRevision;
	}
	public void setEstadoRevision(EstadoRevisionType estadoRevision) {
		this.estadoRevision = estadoRevision;
	}
	public Long getCantidadCartillas() {
		return cantidadCartillas;
	}
	public void setCantidadCartillas(Long cantidadCartillas) {
		this.cantidadCartillas = cantidadCartillas;
	}
	public Date getProgramaFechaUpdate() {
		return programaFechaUpdate;
	}
	public void setProgramaFechaUpdate(Date programaFechaUpdate) {
		this.programaFechaUpdate = programaFechaUpdate;
	}
	
	
	
	
}
