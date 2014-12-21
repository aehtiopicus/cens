package com.aehtiopicus.cens.service;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aehtiopicus.cens.domain.Perfil;
import com.aehtiopicus.cens.domain.Usuario;
import com.aehtiopicus.cens.domain.UsuarioPerfil;
import com.aehtiopicus.cens.repository.PerfilRepository;
import com.aehtiopicus.cens.repository.UsuarioPerfilRepository;
import com.aehtiopicus.cens.repository.UsuarioRepository;
import com.aehtiopicus.cens.specification.UsuarioSpecification;

@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService{

	 private static final Logger logger = Logger.getLogger(InitLoadServiceImpl.class);
	

	@Autowired
	protected UsuarioRepository usuarioRepository;
	
	@Autowired
	protected UsuarioPerfilRepository usuarioPerfilRepository;
	
	@Autowired
	protected PerfilRepository perfilRepository;

	public void setUsuarioRepository(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}
	public void setUsuarioPerfilRepository(
			UsuarioPerfilRepository usuarioPerfilRepository) {
		this.usuarioPerfilRepository = usuarioPerfilRepository;
	}
	public void setPerfilRepository(PerfilRepository perfilRepository) {
		this.perfilRepository = perfilRepository;
	}



	@Override
	public void saveUsuario(Usuario usuario, Perfil perfil) {
		logger.info("Creando/Editando usuario");
		
		UsuarioPerfil up = null;
		
		if(usuario.getId() > 0){
			up = usuarioPerfilRepository.findByUsuario(usuario);
		}
		
		usuario = usuarioRepository.saveAndFlush(usuario);
		
		if(up != null){
			up.setPerfil(perfil);
		}else{
			up = new UsuarioPerfil();
			up.setPerfil(perfil);
			up.setUsuario(usuario);			
		}
		usuarioPerfilRepository.saveAndFlush(up);
		
		logger.info("usuario creado/editado!");
	}
	
	@Override
	public void saveUsuario(Usuario usuario) {
		usuarioRepository.saveAndFlush(usuario);
	}	
	
	@Override
	public List<Perfil> getPerfiles() {
		return perfilRepository.findAll();
	}
	
	@Override
	public UsuarioPerfil getUsuarioPerfilByUsuarioId(Long usuarioId) {
		Usuario u = new Usuario();
		u.setId(usuarioId);		
		return usuarioPerfilRepository.findByUsuario(u);
	}
	
	
	
	
	@Override
	 public List<UsuarioPerfil> search(String perfil, String apellido, int pageIndex, int row) {
		 Page<UsuarioPerfil> requestedPage = null;
		 if(pageIndex > 0){
			 pageIndex = pageIndex - 1;
		 }
		 boolean where = true;
		 Specifications<UsuarioPerfil> specifications = getSpecificationUsuarioPerfil(
				perfil, apellido, where);
		 if(StringUtils.isEmpty(apellido)&&StringUtils.isEmpty(perfil)){
			 requestedPage = usuarioPerfilRepository.findAll(constructPageSpecification(pageIndex,row));
			 return requestedPage.getContent();
		 }
		 requestedPage = usuarioPerfilRepository.findAll(specifications,constructPageSpecification(pageIndex,row));
		 return requestedPage.getContent();
         
    }
	private Specifications<UsuarioPerfil> getSpecificationUsuarioPerfil(
			String perfil, String apellido, boolean where) {
		Specifications<UsuarioPerfil> specifications = null;
		 if(!StringUtils.isEmpty(perfil)){
			 if(where == true){
				 specifications = Specifications.where(UsuarioSpecification.idEquals(Integer.valueOf(perfil)));
				 where = false;
			 }
		 }
		 if(!StringUtils.isEmpty(apellido)){
			 UsuarioSpecification us = new UsuarioSpecification();
			 if(where == true){
				 specifications = Specifications.where(us.apellidoEquals(apellido));
				 where = false;
			 } else{
				 specifications = specifications.and(us.apellidoEquals(apellido));
			 }
		 }
		return specifications;
	}
	
	 /**
     * Retorna una nueva pagina con el especificado tipo de objeto
     * @param pageIndex Numero de pagina que se quiere obtener
     * @return
     */
    private Pageable constructPageSpecification(int pageIndex, int row) {
        Pageable pageSpecification = new PageRequest(pageIndex, row, sortByUsernameAsc());
     
        return pageSpecification;
    }
    
    /**
     * Retorna  Sort object que ordena usuarios acorde al nombre asociado 
     * @return
     */
    private Sort sortByUsernameAsc() {
        return new Sort(Sort.Direction.ASC, "usuario.username");
    }
    
     
    @Override
    public int getTotalUsersFilterByProfile(String id,String apellido){
    	logger.info("obteniendo numero de registros de usuarios");
    	long cantUsers = 0;
   	 	boolean where = true;
   	 	Specifications<UsuarioPerfil> specifications = getSpecificationUsuarioPerfil(
			id, apellido, where);
    	if(StringUtils.isEmpty(id) && StringUtils.isEmpty(apellido)){
    		 cantUsers = usuarioPerfilRepository.count(specifications);
    	 }else{
    		 cantUsers = usuarioRepository.count();
    	 }
    	return (int) Math.ceil(cantUsers);
    }
    
	@Override
	public List<Usuario> getUsuariosByPerfil(Perfil perfil) {		
		
		List<UsuarioPerfil> usuariosPerfil = usuarioPerfilRepository.findByPerfil(perfil);
		List<Usuario> usuarios = new ArrayList<Usuario>();
		
		for (UsuarioPerfil up : usuariosPerfil) {
			usuarios.add(up.getUsuario());
		}
		return usuarios;
		
		
	}
	
	@Override
	public void deleteUsuario(Usuario usuario) {
		UsuarioPerfil usuarioPerfil = usuarioPerfilRepository.findByUsuario(usuario);
		usuarioPerfilRepository.delete(usuarioPerfil);
		usuarioRepository.delete(usuario);
		
	}
	@Override
	public Usuario getUsuarioByUsername(String username) {
		return usuarioRepository.findByUsernameIgnoreCase(username);
	}
	@Override
	public Usuario getById(Long usuarioId) {
		return usuarioRepository.findOne(usuarioId);
	}
	
	

}
