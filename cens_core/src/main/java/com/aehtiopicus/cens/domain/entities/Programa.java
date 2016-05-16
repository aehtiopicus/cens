package com.aehtiopicus.cens.domain.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.aehtiopicus.cens.enumeration.cens.EstadoRevisionType;

@Entity
@Table(name = "CENS_PROGRAMA")
public class Programa implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6048962120392507394L;


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	
	@OneToOne
	private Profesor profesor;
	
	private String nombre;
	
	@OneToOne
	private Asignatura asignatura;
	
	@Column(length=500)
	private String descripcion;
	
	private int cantCartillas;	
	
	@OneToOne(optional=true)
	private FileCensInfo fileInfo;
	
	@Enumerated(EnumType.STRING)
	private EstadoRevisionType estadoRevisionType = EstadoRevisionType.NUEVO;
	
	@OneToMany(mappedBy="programa",fetch = FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
	private List <MaterialDidactico> materialDidactico;
	
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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public int getCantCartillas() {
		return cantCartillas;
	}

	public void setCantCartillas(int cantCartillas) {
		this.cantCartillas = cantCartillas;
	}

	public Asignatura getAsignatura() {
		return asignatura;
	}

	public void setAsignatura(Asignatura asignatura) {
		this.asignatura = asignatura;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
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

	public List<MaterialDidactico> getMaterialDidactico() {
		return materialDidactico;
	}

	public void setMaterialDidactico(List<MaterialDidactico> materialDidactico) {
		this.materialDidactico = materialDidactico;
	}

	public DocumentoModificado getDocumentoModificado() {
		return documentoModificado;
	}

	public void setDocumentoModificado(DocumentoModificado documentoModificado) {
		this.documentoModificado = documentoModificado;
	}

	
		
}
