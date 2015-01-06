package com.aehtiopicus.cens.service.cens;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aehtiopicus.cens.domain.entities.Perfil;
import com.aehtiopicus.cens.domain.entities.Usuarios;
import com.aehtiopicus.cens.enumeration.PerfilTrabajadorCensType;
import com.aehtiopicus.cens.repository.cens.PerfilCensRepository;
import com.aehtiopicus.cens.utils.CensException;

@Service
public class PerfilCensServiceImpl implements PerfilCensService{

	@Autowired
	private PerfilCensRepository perfilCensRepository;
	
	@Override
	public Perfil addPerfilToUser(Usuarios usuario, PerfilTrabajadorCensType perfilType)throws CensException{
		List<Perfil> perfilList = this.listPerfilFromUsuario(usuario);
		if(CollectionUtils.isNotEmpty(perfilList)){
			for(Perfil perfil : perfilList){
				if(perfil.getPerfilType().equals(perfilType)){
					throw new CensException("El perfil ya existe");
				}
			}
		}
		Perfil perfil = createPerfil(usuario, perfilType);
		return perfilCensRepository.save(perfil);
		
	}
	
	private Perfil createPerfil(Usuarios usuario, PerfilTrabajadorCensType perfilType){
		Perfil perfil = new Perfil();
		perfil.setPerfilType(perfilType);
		perfil.setUsuario(usuario);
		return perfil;
	}
	
	@Override
	public List<Perfil> listPerfilFromUsuario(Usuarios usuario){
		List<Perfil> perfilList = new ArrayList<Perfil>();
		if(usuario!=null){
			perfilList = perfilCensRepository.findByUsuario(usuario);
		}
		return perfilList;
	}
}
