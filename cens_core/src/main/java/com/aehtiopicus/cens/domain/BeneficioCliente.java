package com.aehtiopicus.cens.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.aehtiopicus.cens.enumeration.FrecuenciaBeneficioEnum;
import com.aehtiopicus.cens.enumeration.TipoBeneficioEnum;

@Entity
@Table(name="beneficiocliente")
public class BeneficioCliente implements Serializable{

	private static final long serialVersionUID = 2004431747826932383L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	protected Long id;
	
	@ManyToOne
	protected Beneficio beneficio;
	
	@ManyToOne
	protected Cliente cliente;
	
	protected Float valor;
	
	@Enumerated(EnumType.STRING)
	protected TipoBeneficioEnum tipo; 

	protected Boolean vigente;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Beneficio getBeneficio() {
		return beneficio;
	}

	public void setBeneficio(Beneficio beneficio) {
		this.beneficio = beneficio;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Float getValor() {
		return valor;
	}

	public void setValor(Float valor) {
		this.valor = valor;
	}

	public TipoBeneficioEnum getTipo() {
		return tipo;
	}

	public void setTipo(TipoBeneficioEnum tipo) {
		this.tipo = tipo;
	}


	public Boolean getVigente() {
		return vigente;
	}

	public void setVigente(Boolean vigente) {
		this.vigente = vigente;
	}
}
