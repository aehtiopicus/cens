package com.aehtiopicus.cens.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
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

import com.aehtiopicus.cens.enumeration.EstadoClienteEnum;

@Entity
@Table(name="cliente")
public class Cliente implements Serializable{
	
	private static final long serialVersionUID = -2593132666510879385L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	protected Long id;
	
	protected String nombre;
	
	protected String razonSocial;
	
	@Enumerated(EnumType.STRING)
	protected EstadoClienteEnum estadoClienteEnum; //Vigente, Dado de baja
	
	
	@ManyToOne
	protected Usuario gerenteOperacion; //

	@ManyToOne(optional=true)
	protected Usuario jefeOperacion;
	
	protected String email;
	
	
	@Temporal(TemporalType.TIMESTAMP)
	protected Date fecha_alta;
	
	@Column(name="fecha_baja")
	@Temporal(TemporalType.TIMESTAMP)
	protected Date fechaBaja;

	@Column(nullable=true)
	protected String direccion;

	@Column(nullable=true)
	protected String telefono;

	@Column(nullable=true)
	protected String nombre_contacto;		
	
	@OneToMany(fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	protected List<BeneficioCliente> beneficios;
	
	protected String emailContacto;

	protected Boolean hsExtrasConPresentismo = false; //bandera que indica si las horas extras de los empleados asociados al cliente en cuestion se deben calcular teniendo en cuenta el presentismo o no.
	
	protected Boolean fixed = false; //bandera que indica si el cliente tiene que ser tratado con maxima seguridad (solo Novatium debe tener esta bandera en true)
	public Boolean getFixed() {
		return fixed;
	}

	public void setFixed(Boolean fixed) {
		this.fixed = fixed;
	}

	public Boolean getHsExtrasConPresentismo() {
		return hsExtrasConPresentismo;
	}

	public void setHsExtrasConPresentismo(Boolean hsExtrasConPresentismo) {
		this.hsExtrasConPresentismo = hsExtrasConPresentismo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public EstadoClienteEnum getEstadoClienteEnum() {
		return estadoClienteEnum;
	}

	public void setEstadoClienteEnum(EstadoClienteEnum estadoClienteEnum) {
		this.estadoClienteEnum = estadoClienteEnum;
	}

	public Usuario getGerenteOperacion() {
		return gerenteOperacion;
	}

	public void setGerenteOperacion(Usuario gerenteOperacion) {
		this.gerenteOperacion = gerenteOperacion;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getFecha_alta() {
		return fecha_alta;
	}

	public void setFecha_alta(Date fecha_alta) {
		this.fecha_alta = fecha_alta;
	}

	public Date getFechaBaja() {
		return fechaBaja;
	}

	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getNombre_contacto() {
		return nombre_contacto;
	}

	public void setNombre_contacto(String nombre_contacto) {
		this.nombre_contacto = nombre_contacto;
	}

	public List<BeneficioCliente> getBeneficios() {
		if(beneficios == null) {
			beneficios = new ArrayList<BeneficioCliente>();
		}
		return beneficios;
	}

	public void setBeneficios(List<BeneficioCliente> beneficios) {
		this.beneficios = beneficios;
	}
	
	public Usuario getJefeOperacion() {
		return jefeOperacion;
	}

	public void setJefeOperacion(Usuario jefeOperacion) {
		this.jefeOperacion = jefeOperacion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getEmailContacto() {
		return emailContacto;
	}

	public void setEmailContacto(String emailContacto) {
		this.emailContacto = emailContacto;
	}

	
}
