package com.aehtiopicus.cens.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
@Table(name="informeconsolidadodetallebeneficio")
public class InformeConsolidadoDetalleBeneficio implements Serializable{

	private static final long serialVersionUID = 7318393489491594633L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	protected Long id;

	@ManyToOne
	protected Beneficio beneficio; 	//beneficio asociado al cliente donde trabajo el empleado durante el mes
	
	@Type(type="encryptedDouble")
	protected Double importe;		//importe que se debe abonar por el beneficio
	
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
	public Double getImporte() {
		return importe;
	}
	public void setImporte(Double importe) {
		this.importe = importe;
	}
}
