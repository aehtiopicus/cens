package com.aehtiopicus.cens.service.cens;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aehtiopicus.cens.domain.entities.Perfil;
import com.aehtiopicus.cens.domain.entities.Usuarios;
import com.aehtiopicus.cens.enumeration.cens.PerfilTrabajadorCensType;
import com.aehtiopicus.cens.repository.cens.PerfilCensRepository;
import com.aehtiopicus.cens.utils.CensException;

@Service
public class PerfilCensServiceImpl implements PerfilCensService{

	private static final Logger log = LoggerFactory.getLogger(PerfilCensServiceImpl.class);
	
	@Autowired
	private PerfilCensRepository perfilCensRepository;
	
	@Autowired
	private RolCensService rolCensService;
	
	@Override
	public Perfil addPerfilToUser(Usuarios usuario, PerfilTrabajadorCensType perfilType){

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

	@Override
	@Transactional
	public List<Perfil> addPerfilesToUsuarios(Usuarios usuario) throws CensException {
		List<PerfilTrabajadorCensType> perfilTypeList = assembleListFromEntity(usuario);
		List<Perfil> perfilList = null;
		if(CollectionUtils.isNotEmpty(perfilTypeList)){
			checkPerfilTypeIncompatibility(perfilTypeList);
			removePerfiles(perfilTypeList,usuario);
			perfilList = new ArrayList<Perfil>();
			for(PerfilTrabajadorCensType ptct : perfilTypeList){
				perfilList.add(addPerfilToUser( usuario,ptct));				
			}
		}
		return perfilList;		
	}
	
	private List<PerfilTrabajadorCensType> assembleListFromEntity(Usuarios usuario){
		List<PerfilTrabajadorCensType> ptctList = null;
		if(usuario != null && CollectionUtils.isNotEmpty(usuario.getPerfil())){
			ptctList = new ArrayList<PerfilTrabajadorCensType>();
			for(Perfil perfil : usuario.getPerfil()){
				ptctList.add(perfil.getPerfilType());
			}
		}
		return ptctList;
	}
	
	@Override
	@Transactional
	public void removePerfiles(List<PerfilTrabajadorCensType> perfilTypeList,
			Usuarios usuario) throws CensException{
		List<Perfil> perfilList = listPerfilFromUsuario(usuario);
		if(CollectionUtils.isNotEmpty(perfilList)){
			for(Perfil perfil : perfilList){
				if(!perfilTypeList.contains(perfil.getPerfilType())){
					log.info("Removiendo perfil para el usuario "+ perfil.getPerfilType().name());
					
					rolCensService.removeRolToMiembro(perfil.getPerfilType(), usuario);
					perfilCensRepository.delete(perfil);
				}else{
					log.info("Removiendo tipo de perfil existente");
					removeAllDuplicatedPerfiles(perfilTypeList,perfil.getPerfilType());
				}
			}
		}
		
	}
	
	private void removeAllDuplicatedPerfiles(List<PerfilTrabajadorCensType> perfilTypeList,PerfilTrabajadorCensType ptct){
		while(perfilTypeList.contains(ptct)){
			perfilTypeList.remove(ptct);
		}
	}
	

	private void checkPerfilTypeIncompatibility(List<PerfilTrabajadorCensType> newPerfiles) throws CensException{
		List<PerfilTrabajadorCensType> checkList = new ArrayList<PerfilTrabajadorCensType>();
		
		for(PerfilTrabajadorCensType ptct : newPerfiles)
			switch(ptct){			
			case ALUMNO:
				if(checkList.isEmpty()){
					checkList.add(ptct);
				}else{
					throw new CensException("Conflicto con perfiles",assembleException(PerfilTrabajadorCensType.ALUMNO,checkList));
				}				
				break;
			case ASESOR:
				if(checkList.isEmpty() || checkList.contains(PerfilTrabajadorCensType.PROFESOR)){
					checkList.add(ptct);
				}else{
					throw new CensException("Conflicto con perfiles",assembleException(PerfilTrabajadorCensType.ASESOR,Arrays.asList(ptct)));
				}
				break;
			case PRECEPTOR:
				if(checkList.isEmpty()){
					checkList.add(ptct);
				}else{
					throw new CensException("Conflicto con perfiles",assembleException(PerfilTrabajadorCensType.PRECEPTOR,checkList));
				}		
				break;
			case PROFESOR:
				if(checkList.isEmpty() || checkList.contains(PerfilTrabajadorCensType.ASESOR)){
					checkList.add(ptct);
				}else{
					throw new CensException("Conflicto con perfiles",assembleException(PerfilTrabajadorCensType.PROFESOR,Arrays.asList(ptct)));
				}
				break;
			default:
				break;
			
			}
	}
	
	private Map<String,String> assembleException(PerfilTrabajadorCensType perfilError, List<PerfilTrabajadorCensType> incompatible){
		StringBuilder sb = new StringBuilder();	
		for(PerfilTrabajadorCensType ptct : incompatible){
			sb.append(ptct.name());
			sb.append(", ");
		}
		String perfilesIncompatibles = sb.toString();
		perfilesIncompatibles =perfilesIncompatibles.substring(0, perfilesIncompatibles.length()-2);
		
		 Map<String,String> map = new HashMap<String, String>();
		 map.put("perfil", "El rol "+perfilError.name() +" no se puede asignarse con : "+perfilesIncompatibles);
		 return map;
	}
}
