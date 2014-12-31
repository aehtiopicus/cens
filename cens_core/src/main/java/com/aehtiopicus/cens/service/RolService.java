package com.aehtiopicus.cens.service;

import java.util.List;

import com.aehtiopicus.cens.enumeration.RolType;

public interface RolService {

	public List<RolType> listRol();
	
	public RolType getRolByName(String rolName);
}
