package com.aehtiopicus.cens.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.jasypt.hibernate3.type.EncryptedDoubleAsStringType;

@TypeDef(name="encryptedDouble", 
	typeClass=EncryptedDoubleAsStringType.class,
	parameters= { 
		@Parameter(name="encryptorRegisteredName", value="hibernateStringEncryptor")
	}
)
@Entity
@Table(name="sueldo")
public class Sueldo implements Serializable{

	private static final long serialVersionUID = 313611183802995921L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Type(type="encryptedDouble")
	private Double basico;
	@Type(type="encryptedDouble")
	private Double presentismo;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaInicio;
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaFin;
	@ManyToOne
	private Empleado empleado;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Empleado getEmpleado() {
		return empleado;
	}
	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}
	public Double getBasico() {
		return basico;
	}
	public void setBasico(Double basico) {
		this.basico = basico;
	}
	public Double getPresentismo() {
		return presentismo;
	}
	public void setPresentismo(Double presentismo) {
		this.presentismo = presentismo;
	}
	public Date getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public Date getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	
}
