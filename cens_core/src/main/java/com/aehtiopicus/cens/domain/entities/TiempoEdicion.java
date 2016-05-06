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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.aehtiopicus.cens.enumeration.cens.ComentarioType;
import com.aehtiopicus.cens.enumeration.cens.EstadoRevisionType;
import com.aehtiopicus.cens.enumeration.cens.PerfilTrabajadorCensType;

@Entity
@Table(name="cens_tiempo_edicion_vencido")
public class TiempoEdicion implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6460303441253447892L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name="tipo_id")
	private Long tipoId;
	
	@Enumerated(EnumType.STRING)	
	@Column(name="comentario_type")
	private ComentarioType comentarioType;
	
	@Column(name="from_id")
	private Long fromId;
	
	@Column(name="to_id")
	private Long toId;

	
	@Column(name="fecha_vencido")
	@Temporal(TemporalType.DATE)
	private Date fechaVencido;
	
	@Column(name="estado_revision_type")
	@Enumerated(EnumType.STRING)
	private EstadoRevisionType estadoRevisionType;
	
	private Long cantidadCartillas;
	
	private Long cartillasMissing;
	
	@Column(name="resulto",nullable=false,columnDefinition="BOOLEAN DEFAULT FALSE")
	private Boolean resuelto = false;
	
	@Column(name="cancelado",nullable=false,columnDefinition="BOOLEAN DEFAULT FALSE")
	private Boolean cancelado = false;
	
	@Column(name="asignatura_id")
	private Long asignaturaId;
	
	@Column(name="programa_id")
	private Long programaId;
	
	@Enumerated(EnumType.STRING)
	@Column(name="prefil_dirigido")
	private PerfilTrabajadorCensType perfilDirigido;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTipoId() {
		return tipoId;
	}

	public void setTipoId(Long tipoId) {
		this.tipoId = tipoId;
	}

	public ComentarioType getComentarioType() {
		return comentarioType;
	}

	public void setComentarioType(ComentarioType comentarioType) {
		this.comentarioType = comentarioType;
	}

	public Long getFromId() {
		return fromId;
	}

	public void setFromId(Long fromId) {
		this.fromId = fromId;
	}

	public Long getToId() {
		return toId;
	}

	public void setToId(Long toId) {
		this.toId = toId;
	}

	public Date getFechaVencido() {
		return fechaVencido;
	}

	public void setFechaVencido(Date fechaVencido) {
		this.fechaVencido = fechaVencido;
	}

	public EstadoRevisionType getEstadoRevisionType() {
		return estadoRevisionType;
	}

	public void setEstadoRevisionType(EstadoRevisionType estadoRevisionType) {
		this.estadoRevisionType = estadoRevisionType;
	}

	public Long getCantidadCartillas() {
		return cantidadCartillas;
	}

	public void setCantidadCartillas(Long cantidadCartillas) {
		this.cantidadCartillas = cantidadCartillas;
	}

	public Long getCartillasMissing() {
		return cartillasMissing;
	}

	public void setCartillasMissing(Long cartillasMissing) {
		this.cartillasMissing = cartillasMissing;
	}

	public Boolean getResuelto() {
		return resuelto;
	}

	public void setResuelto(Boolean resuelto) {
		this.resuelto = resuelto;
	}

	public Boolean getCancelado() {
		return cancelado;
	}

	public void setCancelado(Boolean cancelado) {
		this.cancelado = cancelado;
	}

	public Long getAsignaturaId() {
		return asignaturaId;
	}

	public void setAsignaturaId(Long asignaturaId) {
		this.asignaturaId = asignaturaId;
	}

	public Long getProgramaId() {
		return programaId;
	}

	public void setProgramaId(Long programaId) {
		this.programaId = programaId;
	}

	public PerfilTrabajadorCensType getPerfilDirigido() {
		return perfilDirigido;
	}

	public void setPerfilDirigido(PerfilTrabajadorCensType perfilDirigido) {
		this.perfilDirigido = perfilDirigido;
	}
	
	
	
}
