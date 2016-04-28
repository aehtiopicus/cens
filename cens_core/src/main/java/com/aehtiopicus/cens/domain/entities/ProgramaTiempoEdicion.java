package com.aehtiopicus.cens.domain.entities;

import java.util.List;

public class ProgramaTiempoEdicion {

	private Long programaId;
	private Long cartillas;
	private Long asignaturaId;
	private List<MaterialDidacticoTiempoEdicion> material;
	public Long getProgramaId() {
		return programaId;
	}
	public void setProgramaId(Long programaId) {
		this.programaId = programaId;
	}
	public Long getCartillas() {
		return cartillas;
	}
	public void setCartillas(Long cartillas) {
		this.cartillas = cartillas;
	}
	public Long getAsignaturaId() {
		return asignaturaId;
	}
	public void setAsignaturaId(Long asignaturaId) {
		this.asignaturaId = asignaturaId;
	}
	public List<MaterialDidacticoTiempoEdicion> getMaterial() {
		return material;
	}
	public void setMaterial(List<MaterialDidacticoTiempoEdicion> material) {
		this.material = material;
	}
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof ProgramaTiempoEdicion){
			return this.programaId.equals(((ProgramaTiempoEdicion)obj).getProgramaId());
		}
		return false;		
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((programaId == null) ? 0 : programaId.hashCode());
		return result;
	}
	
	
	
}
