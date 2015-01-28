package com.aehtiopicus.cens.domain.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.aehtiopicus.cens.enumeration.cens.ComentarioType;

@Entity
@Table(name="cens_comentario")
public class ComentarioCens implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(length=1000)
	private String comentario;
	
	private Long tipoId;
	
	@Enumerated(EnumType.STRING)
	private ComentarioType tipoComentario;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha;
	
	@OneToOne
	private Profesor profesor;
	
	@OneToOne
	private Asesor asesor;
	
	@OneToOne
	private FileCensInfo fileCensInfo;
	
	@OneToOne
	private ComentarioCens parent;
	
	@OneToMany(mappedBy = "parent", cascade= CascadeType.ALL)
	@Fetch(FetchMode.SELECT)
	private List<ComentarioCens> children;
	
	private Boolean baja = false;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	
	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Profesor getProfesor() {
		return profesor;
	}

	public void setProfesor(Profesor profesor) {
		this.profesor = profesor;
	}

	public Asesor getAsesor() {
		return asesor;
	}

	public void setAsesor(Asesor asesor) {
		this.asesor = asesor;
	}

	public Boolean getBaja() {
		return baja;
	}

	public void setBaja(Boolean baja) {
		this.baja = baja;
	}

	public FileCensInfo getFileCensInfo() {
		return fileCensInfo;
	}

	public void setFileCensInfo(FileCensInfo fileCensInfo) {
		this.fileCensInfo = fileCensInfo;
	}

	public Long getTipoId() {
		return tipoId;
	}

	public void setTipoId(Long tipoId) {
		this.tipoId = tipoId;
	}

	public ComentarioType getTipoComentario() {
		return tipoComentario;
	}

	public void setTipoComentario(ComentarioType tipoComentario) {
		this.tipoComentario = tipoComentario;
	}

	public ComentarioCens getParent() {
		return parent;
	}

	public void setParent(ComentarioCens parent) {
		this.parent = parent;
	}

	public List<ComentarioCens> getChildren() {
		return children;
	}

	public void setChildren(List<ComentarioCens> children) {
		this.children = children;
	}


	

}
