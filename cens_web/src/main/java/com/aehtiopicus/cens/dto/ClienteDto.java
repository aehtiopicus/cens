package com.aehtiopicus.cens.dto;



import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ClienteDto {
	protected Long clienteId;
	
	protected Long beneficioId;
	protected Long usuarioGerenteOperadorId;
	
	protected Long usuarioJefeOperadorId;
	
	protected String gerenteName;
	protected String jefeName;
	
	
	@NotNull(message="Debe cargar un nombre de cliente.")
	@Size(min=1, message="Debe cargar un nombre de cliente.")
	protected String nombre;
	
	@NotNull(message="Debe cargar una razón social.")
	@Size(min=1, message="Debe cargar una razón social.")	
	protected String razonSocial;
	
	String estadoCliente;
		
	
	@Pattern(regexp="|^[a-zA-Z0-9._-]{2,100}@[a-zA-Z0-9._-]{2,100}.[a-zA-Z]{2,4}(.[a-zA-Z]{2,4})?$", message="Formato de e-mail incorrecto.")
	protected String email;
	
	@NotNull(message="Debe cargar la fecha de alta.")
	@Size(min=1, message="Debe cargar la fecha de alta")	
	protected String fecha_alta;
	
	protected String fecha_baja;
	

	protected String direccion;
	
	protected String telefono;
	
	protected String nombre_contacto;	
	
	protected Boolean hsExtrasConPresentismo;
	
	protected boolean accionEditar = true;
	protected boolean accionBeneficios = true;
	
	@Pattern(regexp="|^[a-zA-Z0-9._-]{2,100}@[a-zA-Z0-9._-]{2,100}.[a-zA-Z]{2,4}(.[a-zA-Z]{2,4})?$", message="Formato de e-mail incorrecto.")
	protected String emailContacto;
	
	
	public boolean isAccionEditar() {
		return accionEditar;
	}

	public void setAccionEditar(boolean accionEditar) {
		this.accionEditar = accionEditar;
	}

	public boolean isAccionBeneficios() {
		return accionBeneficios;
	}

	public void setAccionBeneficios(boolean accionBeneficios) {
		this.accionBeneficios = accionBeneficios;
	}

	public void setClienteId(Long clienteId) {
		this.clienteId = clienteId;
	}

	public Long getClienteId() {
		return clienteId;
	}
	
	public Long getUsuarioGerenteOperadorId() {
		return usuarioGerenteOperadorId;
	}

	public void setUsuarioGerenteOperadorId(Long usuarioGerenteOperadorId) {
		this.usuarioGerenteOperadorId = usuarioGerenteOperadorId;
	}

	public Long getBeneficioId() {
		return beneficioId;
	}

	public void setBeneficioId(Long beneficioId) {
		this.beneficioId = beneficioId;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getFecha_alta() {
		return fecha_alta;
	}

	public void setFecha_alta(String fecha_alta) {
		this.fecha_alta = fecha_alta;
	}

	public String getFecha_baja() {
		return fecha_baja;
	}

	public void setFecha_baja(String fecha_baja) {
		this.fecha_baja = fecha_baja;
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

	public String getGerenteName() {
		return gerenteName;
	}

	public void setGerenteName(String gerenteName) {
		this.gerenteName = gerenteName;
	}

	public String getEstadoCliente() {
		return estadoCliente;
	}

	public void setEstadoCliente(String estadoCliente) {
		this.estadoCliente = estadoCliente;
	}
	

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getEmailContacto() {
		return emailContacto;
	}

	public void setEmailContacto(String emailContacto) {
		this.emailContacto = emailContacto;
	}

	public Long getUsuarioJefeOperadorId() {
		return usuarioJefeOperadorId;
	}

	public void setUsuarioJefeOperadorId(Long usuarioJefeOperadorId) {
		this.usuarioJefeOperadorId = usuarioJefeOperadorId;
	}

	public String getJefeName() {
		return jefeName;
	}

	public void setJefeName(String jefeName) {
		this.jefeName = jefeName;
	}

	public Boolean getHsExtrasConPresentismo() {
		return hsExtrasConPresentismo;
	}

	public void setHsExtrasConPresentismo(Boolean hsExtrasConPresentismo) {
		this.hsExtrasConPresentismo = hsExtrasConPresentismo;
	}
	
	
}
