package com.aehtiopicus.cens.domain;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
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
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.util.CollectionUtils;

import com.aehtiopicus.cens.enumeration.EstadoCivil;
import com.aehtiopicus.cens.enumeration.EstadoEmpleado;

@Entity
@Table(name="empleado")
public class Empleado implements Serializable{

	private static final long serialVersionUID = 711632002055752336L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	protected Long id;
	
	@OneToMany(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
	private List<RelacionLaboral> relacionLaboral;
	
	@OneToMany(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
	private List<RelacionPuestoEmpleado> puestos;
	
	@OneToMany(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
	private	List<Sueldo> sueldo;
	
	
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL, mappedBy="empleado")
	@Fetch(FetchMode.SUBSELECT)
	private List<EmpleadoViejoFecha> empleadoViejoFechas; 
	
	@OneToMany
	private List<Vacaciones> vacaciones;
	
	@ManyToOne
	private MotivoBaja motivoBaja;
	
	@ManyToOne
	private Nacionalidad nacionalidad;
	@ManyToOne
	private ObraSocial	obraSocial;
	@ManyToOne
	private Prepaga	prepaga;
	private Integer capitas;
	@ManyToOne
	private Banco banco;
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaIngresoNovatium;
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaEgresoNovatium;
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaNacimiento;
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaPreOcupacional;
	/*
	@Id 
	@SequenceGenerator(name="pk_sequence",sequenceName="entity_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	@Column(name="id", unique=true, nullable=false)
	 */
//	@SequenceGenerator(name="legajo_sequence",sequenceName="legajoSec", allocationSize=1)
//	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="legajo_sequence")
//	@Column(unique=true, nullable=false)
	private Integer legajo;	
	
	private String apellidos;
	private String nombres;
	private String	dni;
	private String	cuil;
	
	@Enumerated(EnumType.STRING)
	private EstadoCivil	estadoCivil;
	private String	domicilio;
	private String	telFijo;
	private String celular;
	private String telUrgencias;
	private String 	contactoUrgencias;
	private String mailPersonal;
	private String mailNovatium;
	private String mailmpresa;
	private Boolean chomba;
	private Boolean	mochila;
	private String resultadoPreOcupacional;
	private String sucursal;
	private String nroCuenta;
	private String convenio;
	private String cbu;
	private String observaciones;
	@Enumerated(EnumType.STRING)
	private EstadoEmpleado estado;
	
	private Integer codigoContratacion;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<RelacionLaboral> getRelacionLaboral() {
		return relacionLaboral;
	}
	public void setRelacionLaboral(List<RelacionLaboral> relacionLaboral) {
		this.relacionLaboral = relacionLaboral;
	}
	public Nacionalidad getNacionalidad() {
		return nacionalidad;
	}
	public void setNacionalidad(Nacionalidad nacionalidad) {
		this.nacionalidad = nacionalidad;
	}
	public ObraSocial getObraSocial() {
		return obraSocial;
	}
	public void setObraSocial(ObraSocial obraSocial) {
		this.obraSocial = obraSocial;
	}
	public Prepaga getPrepaga() {
		return prepaga;
	}
	public void setPrepaga(Prepaga prepaga) {
		this.prepaga = prepaga;
	}
	public Date getFechaIngresoNovatium() {
		return fechaIngresoNovatium;
	}
	public void setFechaIngresoNovatium(Date fechaIngresoNovatium) {
		this.fechaIngresoNovatium = fechaIngresoNovatium;
	}
	public Date getFechaEgresoNovatium() {
		return fechaEgresoNovatium;
	}
	public void setFechaEgresoNovatium(Date fechaEgresoNovatium) {
		this.fechaEgresoNovatium = fechaEgresoNovatium;
	}
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	public Date getFechaPreOcupacional() {
		return fechaPreOcupacional;
	}
	public void setFechaPreOcupacional(Date fechaPreOcupacional) {
		this.fechaPreOcupacional = fechaPreOcupacional;
	}
	public Integer getLegajo() {
		return legajo;
	}
	public void setLegajo(Integer legajo) {
		this.legajo = legajo;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public String getNombres() {
		return nombres;
	}
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public String getCuil() {
		return cuil;
	}
	public void setCuil(String cuil) {
		this.cuil = cuil;
	}
	public EstadoCivil getEstadoCivil() {
		return estadoCivil;
	}
	public void setEstadoCivil(EstadoCivil estadoCivil) {
		this.estadoCivil = estadoCivil;
	}
	public String getDomicilio() {
		return domicilio;
	}
	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}
	public String getTelFijo() {
		return telFijo;
	}
	public void setTelFijo(String telFijo) {
		this.telFijo = telFijo;
	}
	public String getCelular() {
		return celular;
	}
	public void setCelular(String celular) {
		this.celular = celular;
	}
	public String getTelUrgencias() {
		return telUrgencias;
	}
	public void setTelUrgencias(String telUrgencias) {
		this.telUrgencias = telUrgencias;
	}
	public String getContactoUrgencias() {
		return contactoUrgencias;
	}
	public void setContactoUrgencias(String contactoUrgencias) {
		this.contactoUrgencias = contactoUrgencias;
	}
	public String getMailPersonal() {
		return mailPersonal;
	}
	public void setMailPersonal(String mailPersonal) {
		this.mailPersonal = mailPersonal;
	}
	public String getMailNovatium() {
		return mailNovatium;
	}
	public void setMailNovatium(String mailNovatium) {
		this.mailNovatium = mailNovatium;
	}
	public String getMailmpresa() {
		return mailmpresa;
	}
	public void setMailmpresa(String mailmpresa) {
		this.mailmpresa = mailmpresa;
	}
	public Boolean getChomba() {
		return chomba;
	}
	public void setChomba(Boolean chomba) {
		this.chomba = chomba;
	}
	public Boolean getMochila() {
		return mochila;
	}
	public void setMochila(Boolean mochila) {
		this.mochila = mochila;
	}
	public String getResultadoPreOcupacional() {
		return resultadoPreOcupacional;
	}
	public void setResultadoPreOcupacional(String resultadoPreOcupacional) {
		this.resultadoPreOcupacional = resultadoPreOcupacional;
	}
	
	public List<Sueldo> getSueldo() {
		return sueldo;
	}
	public void setSueldo(List<Sueldo> sueldo) {
		this.sueldo = sueldo;
	}
	public Banco getBanco() {
		return banco;
	}
	public void setBanco(Banco banco) {
		this.banco = banco;
	}
	public String getSucursal() {
		return sucursal;
	}
	public void setSucursal(String sucursal) {
		this.sucursal = sucursal;
	}
	public String getNroCuenta() {
		return nroCuenta;
	}
	public void setNroCuenta(String nroCuenta) {
		this.nroCuenta = nroCuenta;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public EstadoEmpleado getEstado() {
		return estado;
	}
	public void setEstado(EstadoEmpleado estado) {
		this.estado = estado;
	}
	public List<EmpleadoViejoFecha> getEmpleadoViejoFechas() {
		return empleadoViejoFechas;
	}
	public void setEmpleadoViejoFechas(List<EmpleadoViejoFecha> empleadoViejoFechas) {
		this.empleadoViejoFechas = empleadoViejoFechas;
	}
	public Integer getCodigoContratacion() {
		return codigoContratacion;
	}
	public void setCodigoContratacion(Integer codigoContratacion) {
		this.codigoContratacion = codigoContratacion;
	}
	public Integer getCapitas() {
		return capitas;
	}
	public void setCapitas(Integer capitas) {
		this.capitas = capitas;
	}
	public MotivoBaja getMotivoBaja() {
		return motivoBaja;
	}
	public void setMotivoBaja(MotivoBaja motivoBaja) {
		this.motivoBaja = motivoBaja;
	}
	
	public void addRelacionLaboral(RelacionLaboral relacion){
		this.setMailmpresa(relacion.getEmpleado().getMailmpresa());
		relacion.setEmpleado(this);
		
		if(this.relacionLaboral == null){
			this.relacionLaboral = new ArrayList<RelacionLaboral>();
		}
		this.relacionLaboral.add(relacion);
	}
	
	@Transient
	public RelacionLaboral getRelacionLaboralVigente() {
		RelacionLaboral relacionVigente = null;
		
		if(this.getRelacionLaboral()!= null && this.getRelacionLaboral().size() > 0){
			for (int i = this.getRelacionLaboral().size()-1; i >= 0; i--) {
				if(this.getRelacionLaboral().get(i).getFechaFin() == null){
					relacionVigente = this.getRelacionLaboral().get(i);
					break;
				}
			}
		}
		
		if(relacionVigente == null){
			relacionVigente = new RelacionLaboral();
			relacionVigente.setEmpleado(this);
		}
		return relacionVigente;
	}
	
	@Transient
	public RelacionLaboral getUltimaRelacionLaboral() {
		RelacionLaboral ultimaRL = null;
		
		if(!CollectionUtils.isEmpty(this.getRelacionLaboral())) {
			for (RelacionLaboral rl : this.getRelacionLaboral()) {
				if(rl.getFechaFin() != null) {
					if(ultimaRL == null || rl.getFechaFin().after(ultimaRL.getFechaFin())) {
						ultimaRL = rl;
					}
				}
			}
		}
		
		return ultimaRL;
	}

	
	@Transient
	public Sueldo getSueldoVigente() {
		Sueldo sueldoVigente = null;
		
		if(this.getSueldo()!= null && this.getSueldo().size() > 0){
			for (int i = this.getSueldo().size()-1; i >= 0; i--) {
				if(this.getSueldo().get(i).getFechaFin() == null){
					sueldoVigente = this.getSueldo().get(i);
					break;
				}
			}
		}
		
		if(sueldoVigente == null){
			sueldoVigente = new Sueldo();
			sueldoVigente.setBasico(0d);
			sueldoVigente.setPresentismo(0d);
		}
		return sueldoVigente;
	}
	
	@Transient
	public Sueldo getSueldoEnPeriodo(Date periodo){
		Sueldo sueldoEnPeriodo = null;
		
		if(!CollectionUtils.isEmpty(this.getSueldo())) {
			for (int i = this.getSueldo().size()-1; i >= 0; i--) {
				if(this.getSueldo().get(i).getFechaInicio().getTime() <= periodo.getTime()){
					sueldoEnPeriodo = this.getSueldo().get(i);
					break;
				}
			}
		}
		
		if(sueldoEnPeriodo == null) {
			sueldoEnPeriodo = this.getSueldoVigente();
		}
		
		return sueldoEnPeriodo;
	}
	
	@Transient
	public Sueldo getMejorSueldoAnual(int dateYear, long longTimeLimit) {
		Sueldo mejorSueldo = null;
		Sueldo s = null;
		if(this.getSueldo()!= null && this.getSueldo().size() > 0){
			for (int i = this.getSueldo().size()-1; i >= 0; i--) {
				s = this.getSueldo().get(i);
				if((s.getFechaFin() == null || s.getFechaFin().getYear() == dateYear) &&
				   (s.getFechaInicio().getTime() <= longTimeLimit)){
					
					if(mejorSueldo == null || mejorSueldo.getBasico() < this.getSueldo().get(i).getBasico()) {
						mejorSueldo = this.getSueldo().get(i);						
					}
				}
			}
		}
		
		return mejorSueldo;
	}
	
	public List<RelacionPuestoEmpleado> getPuestos() {
		return puestos;
	}
	public void setPuestos(List<RelacionPuestoEmpleado> puestos) {
		this.puestos = puestos;
	}
	public List<Vacaciones> getVacaciones() {
		if(vacaciones == null) {
			vacaciones = new ArrayList<Vacaciones>();
		}
		return vacaciones;
	}
	public void setVacaciones(List<Vacaciones> vacaciones) {
		this.vacaciones = vacaciones;
	}
	public String getConvenio() {
		return convenio;
	}
	public void setConvenio(String convenio) {
		this.convenio = convenio;
	}
	public String getCbu() {
		return cbu;
	}
	public void setCbu(String cbu) {
		this.cbu = cbu;
	}
	public int compareTo(Empleado empleado) {
		String nombres = this.getApellidos()+", "+this.getNombres();
		return nombres.compareTo(empleado.getApellidos()+", "+empleado.getNombres());
		
	}
	
	public void addEmpleadoViejoFecha(EmpleadoViejoFecha historico) {
		if(this.empleadoViejoFechas == null) {
			empleadoViejoFechas = new ArrayList<EmpleadoViejoFecha>();
		}else {
			for (EmpleadoViejoFecha evf : this.empleadoViejoFechas) {
				if(evf.getKey().equals(historico.getKey())) {
					//ya se encuentra cargado.. no cargar registros duplicados
					return;
				}
			}
		}
		historico.setEmpleado(this);
		this.empleadoViejoFechas.add(historico);
	}
	
	
	
}
