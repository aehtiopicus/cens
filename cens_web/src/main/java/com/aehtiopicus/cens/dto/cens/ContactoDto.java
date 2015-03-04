package com.aehtiopicus.cens.dto.cens;

import com.aehtiopicus.cens.enumeration.ContactoType;

public class ContactoDto {

	private Long id;	
	private ContactoType tipoContacto;
	private String datoContacto;
	private MiembroCensDto miembro;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public ContactoType getTipoContacto() {
		return tipoContacto;
	}
	public void setTipoContacto(ContactoType tipoContacto) {
		this.tipoContacto = tipoContacto;
	}
	public String getDatoContacto() {
		return datoContacto;
	}
	public void setDatoContacto(String datoContacto) {
		this.datoContacto = datoContacto;
	}
	public MiembroCensDto getMiembro() {
		return miembro;
	}
	public void setMiembro(MiembroCensDto miembro) {
		this.miembro = miembro;
	}
	
	
	
}
