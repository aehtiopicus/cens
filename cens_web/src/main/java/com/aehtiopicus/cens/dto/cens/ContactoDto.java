package com.aehtiopicus.cens.dto.cens;

import com.aehtiopicus.cens.enumeration.ContactoType;

public class ContactoDto {

	private Long id;	
	private ContactoType tipoContacto;
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
	
	
}
