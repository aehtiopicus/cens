package com.aehtiopicus.cens.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name="informemensualdetalle")
public class InformeMensualDetalle implements Serializable, Comparable<InformeMensualDetalle> {

	private static final long serialVersionUID = 2687718105548451226L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	protected Long id;

	@ManyToOne
	protected RelacionLaboral relacionLaboral;
	
	protected String condicion;
	
	protected Double porcentaje;
	
	protected Double diasTrabajados;
	
	protected Double hsExtrasALiquidarAl50;
	protected Double hsExtrasALiquidarAl100;
	protected Double hsExtrasAFacturarAl50;
	protected Double hsExtrasAFacturarAl100;
	
	protected Double presentismo;
	protected Double sueldoBasico;
	
	@OneToMany(fetch=FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	protected List<InformeMensualDetalleBeneficio> beneficios;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RelacionLaboral getRelacionLaboral() {
		return relacionLaboral;
	}

	public void setRelacionLaboral(RelacionLaboral relacionLaboral) {
		this.relacionLaboral = relacionLaboral;
	}

	public String getCondicion() {
		return condicion;
	}

	public void setCondicion(String condicion) {
		this.condicion = condicion;
	}

	public Double getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(Double porcentaje) {
		this.porcentaje = porcentaje;
	}

	public Double getDiasTrabajados() {
		if(diasTrabajados == null) {
			diasTrabajados = 0d;
		}
		return diasTrabajados;
	}

	public void setDiasTrabajados(Double diasTrabajados) {
		this.diasTrabajados = diasTrabajados;
	}

	public Double getHsExtrasALiquidarAl50() {
		return hsExtrasALiquidarAl50;
	}

	public void setHsExtrasALiquidarAl50(Double hsExtrasALiquidarAl50) {
		this.hsExtrasALiquidarAl50 = hsExtrasALiquidarAl50;
	}

	public Double getHsExtrasALiquidarAl100() {
		return hsExtrasALiquidarAl100;
	}

	public void setHsExtrasALiquidarAl100(Double hsExtrasALiquidarAl100) {
		this.hsExtrasALiquidarAl100 = hsExtrasALiquidarAl100;
	}

	public Double getHsExtrasAFacturarAl50() {
		return hsExtrasAFacturarAl50;
	}

	public void setHsExtrasAFacturarAl50(Double hsExtrasAFacturarAl50) {
		this.hsExtrasAFacturarAl50 = hsExtrasAFacturarAl50;
	}

	public Double getHsExtrasAFacturarAl100() {
		return hsExtrasAFacturarAl100;
	}

	public void setHsExtrasAFacturarAl100(Double hsExtrasAFacturarAl100) {
		this.hsExtrasAFacturarAl100 = hsExtrasAFacturarAl100;
	}

	public List<InformeMensualDetalleBeneficio> getBeneficios() {
		if(beneficios == null) {
			beneficios = new ArrayList<InformeMensualDetalleBeneficio>();
		}
		return beneficios;
	}

	public void setBeneficios(List<InformeMensualDetalleBeneficio> beneficios) {
		this.beneficios = beneficios;
	}
	
	public Double getPresentismo() {
		return presentismo;
	}

	public void setPresentismo(Double presentismo) {
		this.presentismo = presentismo;
	}

	public Double getSueldoBasico() {
		return sueldoBasico;
	}

	public void setSueldoBasico(Double sueldoBasico) {
		this.sueldoBasico = sueldoBasico;
	}

	@Transient
	public Double getTotalBeneficios() {
		Double d = 0d;
		
		for (InformeMensualDetalleBeneficio beneficio : this.getBeneficios()) {
			d += beneficio.getValor();
		}
		
		return d;
	}

	@Override
	public int compareTo(InformeMensualDetalle o) {
		String thisName = this.getRelacionLaboral().getEmpleado().getApellidos() + this.getRelacionLaboral().getEmpleado().getNombres();
		String oName = o.getRelacionLaboral().getEmpleado().getApellidos() + o.getRelacionLaboral().getEmpleado().getNombres();
		
//		return (this.getRelacionLaboral().getEmpleado().getFechaIngresoNovatium()).compareTo(o.getRelacionLaboral().getEmpleado().getFechaIngresoNovatium());
		return (thisName.compareTo(oName));
	}
}
