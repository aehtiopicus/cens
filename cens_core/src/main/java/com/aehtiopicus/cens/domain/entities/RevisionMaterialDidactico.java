package com.aehtiopicus.cens.domain.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class RevisionMaterialDidactico implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8141787489529865654L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;	
	
	@OneToOne
	private Profesor profesor;
		
	@Temporal(TemporalType.DATE)
	private Date fechaEntrega;
	
	@Temporal(TemporalType.DATE)
	private Date fechaFinal;	
	
	@OneToMany(mappedBy="revisionMaterialDidactico")
	private List<RevisionMaterialDidacticoDetalle> detalles;

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

	public Date getFechaEntrega() {
		return fechaEntrega;
	}

	public void setFechaEntrega(Date fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

	public Date getFechaFinal() {
		return fechaFinal;
	}

	public void setFechaFinal(Date fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	public List<RevisionMaterialDidacticoDetalle> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<RevisionMaterialDidacticoDetalle> detalles) {
		this.detalles = detalles;
	}
	
	
	


}
