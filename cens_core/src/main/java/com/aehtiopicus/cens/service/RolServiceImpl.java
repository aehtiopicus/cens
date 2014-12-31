package com.aehtiopicus.cens.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.aehtiopicus.cens.enumeration.RolType;

@Service
public class RolServiceImpl implements RolService{

	@Override
	public List<RolType> listRol() {
		return Arrays.asList(RolType.values());
		
	}

	@Override
	public RolType getRolByName(String rolName) {
		return RolType.getRolByName(rolName);
	}
	

}
