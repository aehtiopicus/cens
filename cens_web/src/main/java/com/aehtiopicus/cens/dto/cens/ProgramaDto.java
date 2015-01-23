package com.aehtiopicus.cens.dto.cens;


public class ProgramaDto {

	private Long id;	
	private String nombre;
	private String descripcion;
	private Integer cantCartillas;
	private Long profesorId;
	private Long asignaturaId;
	
	private FileCensInfoDto fileInfo;
	
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
	public Integer getCantCartillas() {
		return cantCartillas;
	}
	public void setCantCartillas(Integer cantCartillas) {
		this.cantCartillas = cantCartillas;
	}
	public FileCensInfoDto getFileInfo() {
		return fileInfo;
	}
	public void setFileInfo(FileCensInfoDto fileInfo) {
		this.fileInfo = fileInfo;
	}
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
	public Long getAsignaturaId() {
		return asignaturaId;
	}
	public void setAsignaturaId(Long asignaturaId) {
		this.asignaturaId = asignaturaId;
	}
	
	
}
