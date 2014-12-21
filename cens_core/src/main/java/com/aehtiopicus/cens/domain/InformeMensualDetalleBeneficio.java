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
@Table(name="informemensualdetallebeneficio")
public class InformeMensualDetalleBeneficio  implements Serializable, Comparable<InformeMensualDetalleBeneficio> {

	private static final long serialVersionUID = -5157809390109362814L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	protected Long id;
	
	@ManyToOne
	protected BeneficioCliente beneficio;
	
	@Type(type="encryptedDouble")
	protected Double valor;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BeneficioCliente getBeneficio() {
		return beneficio;
	}

	public void setBeneficio(BeneficioCliente beneficio) {
		this.beneficio = beneficio;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	@Override
	public int compareTo(InformeMensualDetalleBeneficio o) {
		return this.getBeneficio().getBeneficio().getTitulo().compareTo(o.getBeneficio().getBeneficio().getTitulo());
	}
}
