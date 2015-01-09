package com.aehtiopicus.cens.domain.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.aehtiopicus.cens.enumeration.cens.PerfilTrabajadorCensType;

@Entity
@Table(name="CENS_PERFIL_USUARIO_CENS")
public class Perfil implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5996732557914028854L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
		
    @Enumerated(EnumType.STRING)     	
	private PerfilTrabajadorCensType perfilType;
	
	@OneToOne	
	@JoinColumn(name="usuario_id")
	private Usuarios usuario;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Usuarios getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuarios usuario) {
		this.usuario = usuario;
	}

	public PerfilTrabajadorCensType getPerfilType() {
		return perfilType;
	}

	public void setPerfilType(PerfilTrabajadorCensType perfilType) {
		this.perfilType = perfilType;
	}
	
	
	
}
