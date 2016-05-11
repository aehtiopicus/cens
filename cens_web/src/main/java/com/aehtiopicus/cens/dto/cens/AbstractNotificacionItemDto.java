package com.aehtiopicus.cens.dto.cens;

public abstract class AbstractNotificacionItemDto {

	private String nombre;

	private Long id;

	private String fechaCreado;

	private String fechaNotificado;

	private Integer cantidadComnetarios;

	private Boolean notificado;

	private Integer diasNotificado;
	
	private String estadoRevision;
	
	private boolean isTiempoEdicion = false;

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
		return cantidadComnetarios == null ? 0 : cantidadComnetarios;
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
	
	

	public String getEstadoRevision() {
		return estadoRevision;
	}

	public void setEstadoRevision(String estadoRevision) {
		this.estadoRevision = estadoRevision;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((cantidadComnetarios == null) ? 0 : cantidadComnetarios
						.hashCode());
		result = prime * result
				+ ((diasNotificado == null) ? 0 : diasNotificado.hashCode());
		result = prime * result
				+ ((estadoRevision == null) ? 0 : estadoRevision.hashCode());
		result = prime * result
				+ ((fechaCreado == null) ? 0 : fechaCreado.hashCode());
		result = prime * result
				+ ((fechaNotificado == null) ? 0 : fechaNotificado.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result
				+ ((notificado == null) ? 0 : notificado.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		AbstractNotificacionItemDto other = (AbstractNotificacionItemDto) obj;

		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (fechaCreado == null) {
			if (other.fechaCreado != null)
				return false;
		} else if (!fechaCreado.equals(other.fechaCreado)){
			return false;
		}

		
		return true;
	}

	public boolean isTiempoEdicion() {
		return isTiempoEdicion;
	}

	public void setTiempoEdicion(boolean isTiempoEdicion) {
		this.isTiempoEdicion = isTiempoEdicion;
	}



}
