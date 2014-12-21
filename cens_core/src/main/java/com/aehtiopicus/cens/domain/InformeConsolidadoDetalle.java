package com.aehtiopicus.cens.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
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
@Table(name="informeconsolidadodetalle")
public class InformeConsolidadoDetalle implements Serializable, Comparable<InformeConsolidadoDetalle>  {

	private static final long serialVersionUID = 130285735731636551L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	protected Long id;
	
	//----
	/* Esto deberia haberse hecho con una herencia.... si hubieran sido mas claros los requerimientos desde el principio */
	@ManyToOne(optional = true)
	protected InformeMensualDetalle informeMensualDetalle; //el informe consolidado comun requiere los informes mensuales del periodo en cuestion
	
	@ManyToOne(optional = true)
	protected Empleado empleado; //el informe de SAC no tiene los informesMensuales por lo que necesita el empelado
	//------
	
	@Type(type="encryptedDouble")
	protected Double sueldoBasico; //sueldo basico del empleado
	protected Double diasTrabajados;	//por defecto 30 salvo que haya ingresado a novation durante el mes, o haya salido de novatium durante el mes.
	
	@Type(type="encryptedDouble")
	protected Double basicoMes; //se calcula en base a los dias trabajaddos y el sueldobasico del empleado
	@Type(type="encryptedDouble")
	protected Double asistenciaPuntualidad; //presentismo

	//Inasistencias injustificas y sin goce de sueldo.
	protected Double nroInasistenciasInjustificadas;
	@Type(type="encryptedDouble")
	protected Double importeInasistenciasInjustificadas;
	protected Double nroInasistenciasSinGoceDeSueldo;
	@Type(type="encryptedDouble")
	protected Double importeInasistenciasSinGoceDeSueldo;
	
	protected Double hsExtras50; //hs extras a facturar al 50%
	protected Double hsExtras100; //hs extras a facturar al 100%
	@Type(type="encryptedDouble")
	protected Double valorHsExtras50; //valor total por las hs extras al 50%
	@Type(type="encryptedDouble")
	protected Double valorHsExtras100; //valor total por las hs extras al 100%
	
	@OneToMany(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
	protected List<InformeConsolidadoDetalleBeneficio> beneficios; //beeneficios asociados a los clientes donde trabajo el empleado
	
	protected Double vacacionesDias;	//numero de dias de vacaciones
	@Type(type="encryptedDouble")
	protected Double vacacionesValor;	//pago extra correspondiente a las vacaciones
	
	@Type(type="encryptedDouble")
	protected Double conceptoRemunerativoPlus;	//cualquier concepto extra que se deba agregar se carga aca.. manual
	@Type(type="encryptedDouble")
	protected Double conceptoNoRemunerativo; //cualquier concepto que no es remunerativo se agrega aca.. manual
	
	@Type(type="encryptedDouble")
	protected Double sacBase;	//mejor sueldoBruto de los ultimos 6 meses
	protected Double sacDias;	//dias trabajados (180 si trabajo los 6 meses)
	@Type(type="encryptedDouble")
	protected Double sacValor;	//pago que se realiza en concepto de SAC (aguinaldo)

	@Type(type="encryptedDouble")
	protected Double sueldoBruto; //suma de sueldo, beneficios, hsExtras, adicionales, vacaiones, sac, asistencia, conceptoRemPlus y conceptNoRem
	@Type(type="encryptedDouble")
	protected Double sueldoBrutoRemunerativo; //es sueldoBruto menos conecptoNoRemunerativo

	@Type(type="encryptedDouble")
	protected Double ret11y3;		//retenciones 11 + 3 % del bruto (el nro del porcentaje estara en archivo de properties, el tope debe estar en properties)
	@Type(type="encryptedDouble")
	protected Double retObraSocial;	//retenciones 3% obra social (el nro del porcentaje estara en archivo de properties, el tope debe estar en properties)
	
	@Type(type="encryptedDouble")
	protected Double retGanancia; //retencion de ganancias ..carga manual
	
	
	protected Integer codigoContratacionEmpleado; //es el codigo de contratacion de la AFIP (cdo el empleado cambie de codigo lo debo actualizar en los informes que esten abiertos aun)
	@Type(type="encryptedDouble")
	protected Double cont176510; //cont 17-6.5-10% -> el calculo depende del codigo de contratacion del empleado	
	@Type(type="encryptedDouble")
	protected Double contOS; // cont OS 6%
	
	@Type(type="encryptedDouble")
	protected Double neto; //sueldoBruto - (ret11y3 + retOS + retGanancia)
	
	@Type(type="encryptedDouble")
	protected Double celular;
	@Type(type="encryptedDouble")
	protected Double prepaga;
	@Type(type="encryptedDouble")
	protected Double adelantos;
	@Type(type="encryptedDouble")
	protected Double reintegros;
	@Type(type="encryptedDouble")
	protected Double embargos;
	
	@Type(type="encryptedDouble")
	protected Double cheque; //si el empleado entro despues del 19 del mes se le paga por cheque.. cargar un valor igual al netoADepositar en este campo.
	
	@Type(type="encryptedDouble")
	protected Double netoADepositar; //neto - (celular + prepaga + adelantos + reintegros + cheque) 
	
	protected Boolean usarCheque = false; //indica si se le pagara con un cheque o con una deposito a su cuenta.
	
	@Temporal(TemporalType.DATE)
	protected Date periodo;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public InformeMensualDetalle getInformeMensualDetalle() {
		return informeMensualDetalle;
	}
	public void setInformeMensualDetalle(InformeMensualDetalle informeMensualDetalle) {
		this.informeMensualDetalle = informeMensualDetalle;
	}
	public Double getSueldoBasico() {
		return sueldoBasico;
	}
	public void setSueldoBasico(Double sueldoBasico) {
		this.sueldoBasico = sueldoBasico;
	}
	public Double getDiasTrabajados() {
		return diasTrabajados;
	}
	public void setDiasTrabajados(Double diasTrabajados) {
		this.diasTrabajados = diasTrabajados;
	}
	public Double getBasicoMes() {
		return basicoMes;
	}
	public void setBasicoMes(Double basicoMes) {
		this.basicoMes = basicoMes;
	}
	public Double getAsistenciaPuntualidad() {
		return asistenciaPuntualidad;
	}
	public void setAsistenciaPuntualidad(Double asistenciaPuntualidad) {
		this.asistenciaPuntualidad = asistenciaPuntualidad;
	}

	public Double getSueldoBruto() {
		return sueldoBruto;
	}
	public void setSueldoBruto(Double sueldoBruto) {
		this.sueldoBruto = sueldoBruto;
	}

	public Date getPeriodo() {
		return periodo;
	}
	public void setPeriodo(Date periodo) {
		this.periodo = periodo;
	}
	public Double getNetoADepositar() {
		return netoADepositar;
	}
	public void setNetoADepositar(Double netoADepositar) {
		this.netoADepositar = netoADepositar;
	}
	public Double getHsExtras50() {
		return hsExtras50;
	}
	public void setHsExtras50(Double hsExtras50) {
		this.hsExtras50 = hsExtras50;
	}
	public Double getHsExtras100() {
		return hsExtras100;
	}
	public void setHsExtras100(Double hsExtras100) {
		this.hsExtras100 = hsExtras100;
	}
	public Double getValorHsExtras50() {
		return valorHsExtras50;
	}
	public void setValorHsExtras50(Double valorHsExtras50) {
		this.valorHsExtras50 = valorHsExtras50;
	}
	public Double getValorHsExtras100() {
		return valorHsExtras100;
	}
	public void setValorHsExtras100(Double valorHsExtras100) {
		this.valorHsExtras100 = valorHsExtras100;
	}
	public List<InformeConsolidadoDetalleBeneficio> getBeneficios() {
		if(beneficios == null) {
			beneficios = new ArrayList<InformeConsolidadoDetalleBeneficio>();
		}
		return beneficios;
	}
	public void setBeneficios(List<InformeConsolidadoDetalleBeneficio> beneficios) {
		this.beneficios = beneficios;
	}
	public Double getVacacionesDias() {
		return vacacionesDias;
	}
	public void setVacacionesDias(Double vacacionesDias) {
		this.vacacionesDias = vacacionesDias;
	}
	public Double getVacacionesValor() {
		return vacacionesValor;
	}
	public void setVacacionesValor(Double vacacionesValor) {
		this.vacacionesValor = vacacionesValor;
	}
	public Double getConceptoRemunerativoPlus() {
		return conceptoRemunerativoPlus;
	}
	public void setConceptoRemunerativoPlus(Double conceptoRemunerativoPlus) {
		this.conceptoRemunerativoPlus = conceptoRemunerativoPlus;
	}
	public Double getConceptoNoRemunerativo() {
		return conceptoNoRemunerativo;
	}
	public void setConceptoNoRemunerativo(Double conceptoNoRemunerativo) {
		this.conceptoNoRemunerativo = conceptoNoRemunerativo;
	}
	public Double getSacBase() {
		return sacBase;
	}
	public void setSacBase(Double sacBase) {
		this.sacBase = sacBase;
	}
	public Double getSacDias() {
		return sacDias;
	}
	public void setSacDias(Double sacDias) {
		this.sacDias = sacDias;
	}
	public Double getSacValor() {
		return sacValor;
	}
	public void setSacValor(Double sacValor) {
		this.sacValor = sacValor;
	}
	public Double getSueldoBrutoRemunerativo() {
		return sueldoBrutoRemunerativo;
	}
	public void setSueldoBrutoRemunerativo(Double sueldoBrutoRemunerativo) {
		this.sueldoBrutoRemunerativo = sueldoBrutoRemunerativo;
	}
	public Double getRet11y3() {
		return ret11y3;
	}
	public void setRet11y3(Double ret11y3) {
		this.ret11y3 = ret11y3;
	}
	public Double getRetObraSocial() {
		return retObraSocial;
	}
	public void setRetObraSocial(Double retObraSocial) {
		this.retObraSocial = retObraSocial;
	}
	public Double getRetGanancia() {
		return retGanancia;
	}
	public void setRetGanancia(Double retGanancia) {
		this.retGanancia = retGanancia;
	}
	public Double getNeto() {
		return neto;
	}
	public void setNeto(Double neto) {
		this.neto = neto;
	}
	public Double getCelular() {
		return celular;
	}
	public void setCelular(Double celular) {
		this.celular = celular;
	}
	public Double getPrepaga() {
		return prepaga;
	}
	public void setPrepaga(Double prepaga) {
		this.prepaga = prepaga;
	}
	public Double getAdelantos() {
		return adelantos;
	}
	public void setAdelantos(Double adelantos) {
		this.adelantos = adelantos;
	}
	public Double getReintegros() {
		return reintegros;
	}
	public void setReintegros(Double reintegros) {
		this.reintegros = reintegros;
	}
	public Double getCheque() {
		return cheque;
	}
	public void setCheque(Double cheque) {
		this.cheque = cheque;
	}
	public Boolean getUsarCheque() {
		return usarCheque;
	}
	public void setUsarCheque(Boolean usarCheque) {
		this.usarCheque = usarCheque;
	}
	public Empleado getEmpleado() {
		return empleado;
	}
	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}
	public Double getCont176510() {
		return cont176510;
	}
	public void setCont176510(Double cont176510) {
		this.cont176510 = cont176510;
	}
	public Double getContOS() {
		return contOS;
	}
	public void setContOS(Double contOS) {
		this.contOS = contOS;
	}
	public Integer getCodigoContratacionEmpleado() {
		return codigoContratacionEmpleado;
	}
	public void setCodigoContratacionEmpleado(Integer codigoContratacionEmpleado) {
		this.codigoContratacionEmpleado = codigoContratacionEmpleado;
	}
	public Double getEmbargos() {
		return embargos;
	}
	public void setEmbargos(Double embargos) {
		this.embargos = embargos;
	}
	public Double getNroInasistenciasInjustificadas() {
		return nroInasistenciasInjustificadas;
	}
	public void setNroInasistenciasInjustificadas(
			Double nroInasistenciasInjustificadas) {
		this.nroInasistenciasInjustificadas = nroInasistenciasInjustificadas;
	}
	public Double getImporteInasistenciasInjustificadas() {
		return importeInasistenciasInjustificadas;
	}
	public void setImporteInasistenciasInjustificadas(
			Double importeInasistenciasInjustificadas) {
		this.importeInasistenciasInjustificadas = importeInasistenciasInjustificadas;
	}
	public Double getNroInasistenciasSinGoceDeSueldo() {
		return nroInasistenciasSinGoceDeSueldo;
	}
	public void setNroInasistenciasSinGoceDeSueldo(
			Double nroInasistenciasSinGoceDeSueldo) {
		this.nroInasistenciasSinGoceDeSueldo = nroInasistenciasSinGoceDeSueldo;
	}
	public Double getImporteInasistenciasSinGoceDeSueldo() {
		return importeInasistenciasSinGoceDeSueldo;
	}
	public void setImporteInasistenciasSinGoceDeSueldo(
			Double importeInasistenciasSinGoceDeSueldo) {
		this.importeInasistenciasSinGoceDeSueldo = importeInasistenciasSinGoceDeSueldo;
	}
	
	@Override
	public int compareTo(InformeConsolidadoDetalle o) {
		if(this.getInformeMensualDetalle() != null && o.getInformeMensualDetalle() != null) {
			return this.getInformeMensualDetalle().getRelacionLaboral().getEmpleado().getLegajo().compareTo(o.getInformeMensualDetalle().getRelacionLaboral().getEmpleado().getLegajo());
			//return (this.getInformeMensualDetalle().getRelacionLaboral().getEmpleado().getFechaIngresoNovatium()).compareTo(o.getInformeMensualDetalle().getRelacionLaboral().getEmpleado().getFechaIngresoNovatium());
		}else {
			return this.getEmpleado().getLegajo().compareTo(o.getEmpleado().getLegajo());
			//return (this.getEmpleado().getFechaIngresoNovatium().compareTo(o.getEmpleado().getFechaIngresoNovatium()));
		}
	}
	
	@Transient
	public Double getTotalBeneficios() {
		Double totalBeneficios = 0d;
		for (InformeConsolidadoDetalleBeneficio detBeneficio : this.beneficios) {
			if(detBeneficio != null && detBeneficio.getImporte() != null){				
				totalBeneficios += detBeneficio.getImporte();
			}
		}
		return totalBeneficios;
	}
	
	
	@Transient
	public String getNombreEmpleado() {
		if(this.empleado != null) {
			return this.getEmpleado().getApellidos() + ", " + this.getEmpleado().getNombres();
		}else {
			return this.getInformeMensualDetalle().getRelacionLaboral().getEmpleado().getApellidos() + ", " + this.getInformeMensualDetalle().getRelacionLaboral().getEmpleado().getNombres();
		}
	}
	@Transient
	public String getLegajoEmpleado() {
		if(this.empleado != null) {
			return this.getEmpleado().getLegajo().toString();
		}else {
			return this.getInformeMensualDetalle().getRelacionLaboral().getEmpleado().getLegajo().toString();
		}		
	}
	@Transient
	public String getCuilEmpleado() {
		if(this.empleado != null) {
			return this.getEmpleado().getCuil();
		}else {
			return this.getInformeMensualDetalle().getRelacionLaboral().getEmpleado().getCuil();
		}		
	}
	@Transient
	public Date getFechaIngresoNovatiumEmpleado() {
		if(this.empleado != null) {
			return this.getEmpleado().getFechaIngresoNovatium();
		}else {
			return this.getInformeMensualDetalle().getRelacionLaboral().getEmpleado().getFechaIngresoNovatium();
		}		
	}
	@Transient
	public String getNombreCliente() {
		RelacionLaboral rl;
		if(this.empleado != null) {
			rl = this.getEmpleado().getRelacionLaboralVigente();
		}else {
			rl = this.getInformeMensualDetalle().getRelacionLaboral();
		}		
		
		if(rl != null && rl.getCliente() != null) {
			return rl.getCliente().getNombre();
		}else {
			return null;
		}
	}
	
	@Transient
	public Empleado obtenerEmpleado() {
		try {
			if(this.empleado != null) {
				return this.getEmpleado();
			}else {
				return this.getInformeMensualDetalle().getRelacionLaboral().getEmpleado();
			}		
		}catch(Exception e) {
			return null;
		}
	}
}
