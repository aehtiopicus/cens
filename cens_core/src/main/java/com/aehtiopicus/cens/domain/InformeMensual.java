package com.aehtiopicus.cens.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.aehtiopicus.cens.enumeration.InformeMensualEstadoEnum;

@Entity
@Table(name="informemensual")
public class InformeMensual implements Serializable {

	private static final long serialVersionUID = 4401141901997594833L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	protected Long id;
	
	@Temporal(TemporalType.DATE)
	protected Date periodo;
	
	@ManyToOne
	protected Cliente cliente;
	
	@OneToMany(fetch=FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	protected List<InformeMensualDetalle> detalle;
	
	@Enumerated(EnumType.STRING)
	protected InformeMensualEstadoEnum estado;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Date periodo) {
		this.periodo = periodo;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<InformeMensualDetalle> getDetalle() {
		if(detalle == null) {
			detalle = new ArrayList<InformeMensualDetalle>();
		}
		return detalle;
	}

	public void setDetalle(List<InformeMensualDetalle> detalle) {
		this.detalle = detalle;
	}

	public InformeMensualEstadoEnum getEstado() {
		return estado;
	}

	public void setEstado(InformeMensualEstadoEnum estado) {
		this.estado = estado;
	}
}
