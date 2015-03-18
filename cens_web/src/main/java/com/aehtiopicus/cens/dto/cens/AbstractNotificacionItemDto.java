package com.aehtiopicus.cens.dto.cens;

public abstract class AbstractNotificacionItemDto {

	private String nombre;
	
	private Long id;
	
	private String fechaCreado;
	
	private String fechaNotificado;
	
	private int cantidadComnetarios;
	
	private boolean notificado;
	
	private int diasNotificado;
		
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFechaCreado() {
		return fechaCreado;
	}
	public void setFechaCreado(String fechaCreado) {
		this.fechaCreado = fechaCreado;
	}
	public String getFechaNotificado() {
		return fechaNotificado;
	}
	public void setFechaNotificado(String fechaNotificado) {
		this.fechaNotificado = fechaNotificado;
	}
	public int getCantidadComnetarios() {
		return cantidadComnetarios;
	}
	public void setCantidadComnetarios(int cantidadComnetarios) {
		this.cantidadComnetarios = cantidadComnetarios;
	}
		
	public boolean isNotificado() {
		return notificado;
	}
	public void setNotificado(boolean notificado) {
		this.notificado = notificado;
	}
	
	public int getDiasNotificado() {
		return diasNotificado;
	}
	public void setDiasNotificado(int diasNotificado) {
		this.diasNotificado = diasNotificado;
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
		AbstractNotificacionItemDto other = (AbstractNotificacionItemDto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
}
