package com.aehtiopicus.cens.dto;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

public class EmpleadoDto{
	
	protected boolean oldEmpleado = false;
	
	protected Long empleadoId;
	//@Null(message="Debe cargar un legajo")
	protected Integer legajo;
	@NotBlank(message="Debe cargar fecha de ingreso")
	protected String fechaIngresoNovatium;
	protected String fechaEgresoNovatium;
	protected Long motivoBajaId;
	protected Integer codigoContratacion;
	
	@NotBlank(message="Debe cargar apellido")
	protected String apellidos;
	@NotBlank(message="Debe cargar nombre")
	protected String nombres;
	@NotBlank(message="Debe cargar un dni")
	protected String dni;
	@NotBlank(message="Debe cargar un cuil")
	protected String cuil;
	//@NotBlank(message="Debe ingresar su estado civil")
	protected String estadoCivil;
	protected String fechaNacimiento;
	protected Long nacionalidadId;
	protected String domicilio;
	protected String telefonoFijo;
	protected String celular;
	protected String telefonoUrgencias;
	protected String contactoParaUrgencias;
	@Pattern(regexp="|^[a-zA-Z0-9._-]{2,100}@[a-zA-Z0-9._-]{2,100}.[a-zA-Z]{2,4}(.[a-zA-Z]{2,4})?$", message="Formato de e-mail incorrecto.")
	protected String emailPersonal;
	@Pattern(regexp="|^[a-zA-Z0-9._-]{2,100}@[a-zA-Z0-9._-]{2,100}.[a-zA-Z]{2,4}(.[a-zA-Z]{2,4})?$", message="Formato de e-mail incorrecto.")
	protected String emailNovatium;
	@Pattern(regexp="|^[a-zA-Z0-9._-]{2,100}@[a-zA-Z0-9._-]{2,100}.[a-zA-Z]{2,4}(.[a-zA-Z]{2,4})?$", message="Formato de e-mail incorrecto.")
	protected String emailEmpresa;
	protected Long obraSocialId;
	protected Long prepagaId;
	protected Integer capitas;
	protected Boolean chomba;
	protected Boolean mochila;
	protected String fechaPreocupacional;
	protected String resultadoPreocupacional;
	protected Long bancoId;
	protected String sucursal;
	protected String nroCuenta;
	protected String observaciones;
	protected String clienteNombre;
	protected String estado;
	protected String convenio;
	@Pattern(regexp ="|^[0-9]{22}", message="Debe contener 22 digitos númericos.")
	protected String cbu;
	
	protected String fechaInicio;
	protected Long puestoId;
	protected Long clienteId;
	@Pattern(regexp="|^[a-zA-Z0-9._-]{2,100}@[a-zA-Z0-9._-]{2,100}.[a-zA-Z]{2,4}(.[a-zA-Z]{2,4})?$", message="Formato de e-mail incorrecto.")
	protected String mailEmpresa;
	
	protected String basico;
	protected String presentismo;
	
	protected boolean accionEditar = true;
	protected boolean accionHistorial = true;
	protected boolean accionSueldo = true;
	protected boolean accionVacacion = true;
	protected boolean existRelacion = false;
	
	
	
	public Integer getCodigoContratacion() {
		return codigoContratacion;
	}
	public void setCodigoContratacion(Integer codigoContratacion) {
		this.codigoContratacion = codigoContratacion;
	}
	public boolean isExistRelacion() {
		return existRelacion;
	}
	public void setExistRelacion(boolean existRelacion) {
		this.existRelacion = existRelacion;
	}
	public boolean isAccionEditar() {
		return accionEditar;
	}
	public void setAccionEditar(boolean accionEditar) {
		this.accionEditar = accionEditar;
	}
	public boolean isAccionHistorial() {
		return accionHistorial;
	}
	public void setAccionHistorial(boolean accionHistorial) {
		this.accionHistorial = accionHistorial;
	}
	public boolean isAccionSueldo() {
		return accionSueldo;
	}
	public void setAccionSueldo(boolean accionSueldo) {
		this.accionSueldo = accionSueldo;
	}
	public boolean isAccionVacacion() {
		return accionVacacion;
	}
	public void setAccionVacacion(boolean accionVacacion) {
		this.accionVacacion = accionVacacion;
	}
	public Long getEmpleadoId() {
		return empleadoId;
	}
	public void setEmpleadoId(Long empleadoId) {
		this.empleadoId = empleadoId;
	}
	public Integer getLegajo() {
		return legajo;
	}
	public void setLegajo(Integer legajo) {
		this.legajo = legajo;
	}
	public String getFechaIngresoNovatium() {
		return fechaIngresoNovatium;
	}
	public void setFechaIngresoNovatium(String fechaIngresoNovatium) {
		this.fechaIngresoNovatium = fechaIngresoNovatium;
	}
	public String getFechaEgresoNovatium() {
		return fechaEgresoNovatium;
	}
	public void setFechaEgresoNovatium(String fechaEgresoNovatium) {
		this.fechaEgresoNovatium = fechaEgresoNovatium;
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
	public String getEstadoCivil() {
		return estadoCivil;
	}
	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
	}
	public String getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	
	public Long getNacionalidadId() {
		return nacionalidadId;
	}
	public void setNacionalidadId(Long nacionalidadId) {
		this.nacionalidadId = nacionalidadId;
	}
	public String getDomicilio() {
		return domicilio;
	}
	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}
	public String getTelefonoFijo() {
		return telefonoFijo;
	}
	public void setTelefonoFijo(String telefonoFijo) {
		this.telefonoFijo = telefonoFijo;
	}
	public String getCelular() {
		return celular;
	}
	public void setCelular(String celular) {
		this.celular = celular;
	}
	public String getTelefonoUrgencias() {
		return telefonoUrgencias;
	}
	public void setTelefonoUrgencias(String telefonoUrgencias) {
		this.telefonoUrgencias = telefonoUrgencias;
	}
	public String getContactoParaUrgencias() {
		return contactoParaUrgencias;
	}
	public void setContactoParaUrgencias(String contactoParaUrgencias) {
		this.contactoParaUrgencias = contactoParaUrgencias;
	}
	public String getEmailPersonal() {
		return emailPersonal;
	}
	public void setEmailPersonal(String emailPersonal) {
		this.emailPersonal = emailPersonal;
	}
	public String getEmailNovatium() {
		return emailNovatium;
	}
	public void setEmailNovatium(String emailNovatium) {
		this.emailNovatium = emailNovatium;
	}
	public String getEmailEmpresa() {
		return emailEmpresa;
	}
	public void setEmailEmpresa(String emailEmpresa) {
		this.emailEmpresa = emailEmpresa;
	}
	public Long getObraSocialId() {
		return obraSocialId;
	}
	public void setObraSocialId(Long obraSocialId) {
		this.obraSocialId = obraSocialId;
	}
	public Long getPrepagaId() {
		return prepagaId;
	}
	public void setPrepagaId(Long prepagaId) {
		this.prepagaId = prepagaId;
	}
	
	public String getFechaPreocupacional() {
		return fechaPreocupacional;
	}
	public void setFechaPreocupacional(String fechaPreocupacional) {
		this.fechaPreocupacional = fechaPreocupacional;
	}
	public String getResultadoPreocupacional() {
		return resultadoPreocupacional;
	}
	public void setResultadoPreocupacional(String resultadoPreocupacional) {
		this.resultadoPreocupacional = resultadoPreocupacional;
	}
	public Long getBancoId() {
		return bancoId;
	}
	public void setBancoId(Long bancoId) {
		this.bancoId = bancoId;
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
	public String getClienteNombre() {
		return clienteNombre;
	}
	public void setClienteNombre(String clienteNombre) {
		this.clienteNombre = clienteNombre;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
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
	public String getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public Long getPuestoId() {
		return puestoId;
	}
	public void setPuestoId(Long puestoId) {
		this.puestoId = puestoId;
	}
	public Long getClienteId() {
		return clienteId;
	}
	public void setClienteId(Long clienteId) {
		this.clienteId = clienteId;
	}
	public String getMailEmpresa() {
		return mailEmpresa;
	}
	public void setMailEmpresa(String mailEmpresa) {
		this.mailEmpresa = mailEmpresa;
	}
	public String getBasico() {
		return basico;
	}
	public void setBasico(String basico) {
		this.basico = basico;
	}
	public String getPresentismo() {
		return presentismo;
	}
	public void setPresentismo(String presentismo) {
		this.presentismo = presentismo;
	}
	public boolean isOldEmpleado() {
		return oldEmpleado;
	}
	public void setOldEmpleado(boolean oldEmpleado) {
		this.oldEmpleado = oldEmpleado;
	}
	public Integer getCapitas() {
		return capitas;
	}
	public void setCapitas(Integer capitas) {
		this.capitas = capitas;
	}
	public Long getMotivoBajaId() {
		return motivoBajaId;
	}
	public void setMotivoBajaId(Long motivoBajaId) {
		this.motivoBajaId = motivoBajaId;
	}
	
//	@Override
//	public int compareTo(EmpleadoDto o) {
//		String name = this.apellidos + ", " + this.nombres;
//		String oName = o.getApellidos() + ", " + o.getNombres(); 
//		return name.compareTo(oName);
//	}
	
}
