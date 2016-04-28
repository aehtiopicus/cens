package com.aehtiopicus.cens.domain.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.aehtiopicus.cens.enumeration.cens.DivisionPeriodoType;
import com.aehtiopicus.cens.enumeration.cens.EstadoRevisionType;

@Entity
@Table(name = "CENS_MATERIAL_DIDACTICO")
public class MaterialDidactico implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3125152027078314823L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	
	@OneToOne
	private Profesor profesor;
	
	private String nombre;
	
	@OneToOne
	private Programa programa;
	
	@Column(length=500)
	private String descripcion;
	
	private int nro;	
	
	@Enumerated(EnumType.STRING)
	private DivisionPeriodoType divisionPeriodoType;
	
	@OneToOne(optional=true)
	private FileCensInfo fileInfo;
	
	@Enumerated(EnumType.STRING)
	private EstadoRevisionType estadoRevisionType = EstadoRevisionType.NUEVO;
	
	@Embedded
	private DocumentoModificado documentoModificado = new DocumentoModificado();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Profesor getProfesor() {
		return profesor;
	}

	public void setProfesor(Profesor profesor) {
		this.profesor = profesor;
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
	

	public FileCensInfo getFileInfo() {
		return fileInfo;
	}

	public void setFileInfo(FileCensInfo fileInfo) {
		this.fileInfo = fileInfo;
	}

	public EstadoRevisionType getEstadoRevisionType() {
		return estadoRevisionType;
	}

	public void setEstadoRevisionType(EstadoRevisionType estadoRevisionType) {
		this.estadoRevisionType = estadoRevisionType;
	}

	public Programa getPrograma() {
		return programa;
	}

	public void setPrograma(Programa programa) {
		this.programa = programa;
	}

	public int getNro() {
		return nro;
	}

	public void setNro(int nro) {
		this.nro = nro;
	}

	public DivisionPeriodoType getDivisionPeriodoType() {
		return divisionPeriodoType;
	}

	public void setDivisionPeriodoType(DivisionPeriodoType divisionPeriodoType) {
		this.divisionPeriodoType = divisionPeriodoType;
	}

	public DocumentoModificado getDocumentoModificado() {
		return documentoModificado;
	}

	public void setDocumentoModificado(DocumentoModificado documentoModificado) {
		this.documentoModificado = documentoModificado;
	}

	

	
}
