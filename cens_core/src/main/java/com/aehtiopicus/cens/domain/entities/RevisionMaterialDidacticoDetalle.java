package com.aehtiopicus.cens.domain.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
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

import com.aehtiopicus.cens.enumeration.EstadoRevisionType;

@Entity
@Table(name = "REVISION_MATERIAL_DIDACTICO_DETALLE")
public class RevisionMaterialDidacticoDetalle implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4235044043678454051L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@OneToOne
	private Asesor asesor;
	
	@Temporal(TemporalType.DATE)
	private Date fechaRevision;
	
	@Column(length=500)
	private String descripcion;
	
	@Enumerated(EnumType.STRING)
	private EstadoRevisionType estadoRevisionType;
	
	@OneToOne
	private ContactoRevision contactoRevision;
	
	@OneToOne
	private RevisionMaterialDidactico revisionMaterialDidactico;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Asesor getAsesor() {
		return asesor;
	}

	public void setAsesor(Asesor asesor) {
		this.asesor = asesor;
	}

	public Date getFechaRevision() {
		return fechaRevision;
	}

	public void setFechaRevision(Date fechaRevision) {
		this.fechaRevision = fechaRevision;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public EstadoRevisionType getEstadoRevisionType() {
		return estadoRevisionType;
	}

	public void setEstadoRevisionType(EstadoRevisionType estadoRevisionType) {
		this.estadoRevisionType = estadoRevisionType;
	}

	public ContactoRevision getContactoRevision() {
		return contactoRevision;
	}

	public void setContactoRevision(ContactoRevision contactoRevision) {
		this.contactoRevision = contactoRevision;
	}

	public RevisionMaterialDidactico getRevisionMaterialDidactico() {
		return revisionMaterialDidactico;
	}

	public void setRevisionMaterialDidactico(
			RevisionMaterialDidactico revisionMaterialDidactico) {
		this.revisionMaterialDidactico = revisionMaterialDidactico;
	}
	
	
	
	
	
	
	
	
	
	
}
