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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.aehtiopicus.cens.enumeration.InformeConsolidadoEstadoEnum;
import com.aehtiopicus.cens.enumeration.InformeConsolidadoTipoEnum;

@Entity
@Table(name="informeconsolidado")
public class InformeConsolidadoStub implements Serializable {

	private static final long serialVersionUID = 4401141901997594833L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	protected Long id;
	
	@Temporal(TemporalType.DATE)
	protected Date periodo;

	@Enumerated(EnumType.STRING)
	protected InformeConsolidadoEstadoEnum estado;

	@Enumerated(EnumType.STRING)
	protected InformeConsolidadoTipoEnum tipo = InformeConsolidadoTipoEnum.COMUN;
	

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

	public InformeConsolidadoEstadoEnum getEstado() {
		return estado;
	}

	public void setEstado(InformeConsolidadoEstadoEnum estado) {
		this.estado = estado;
	}

	public InformeConsolidadoTipoEnum getTipo() {
		return tipo;
	}
	public void setTipo(InformeConsolidadoTipoEnum tipo) {
		this.tipo = tipo;
	}
	
}
