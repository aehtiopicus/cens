package com.aehtiopicus.cens.service.cens;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aehtiopicus.cens.domain.entities.MiembroCens;
import com.aehtiopicus.cens.domain.entities.Perfil;
import com.aehtiopicus.cens.domain.entities.RestRequest;
import com.aehtiopicus.cens.domain.entities.Usuarios;
import com.aehtiopicus.cens.enumeration.cens.PerfilTrabajadorCensType;
import com.aehtiopicus.cens.repository.cens.MiembroCensRepository;
import com.aehtiopicus.cens.specification.cens.MiembroCensSpecification;
import com.aehtiopicus.cens.utils.CensException;
import com.aehtiopicus.cens.utils.Utils;

@Service
public class MiembroCensServiceImpl implements MiembroCensService {

	@Autowired
	private UsuarioCensService usuarioCensService;

	@Autowired
	private PerfilCensService perfilCensService;
	
	@Autowired
	private RolCensService rolCensService;

	@Autowired
	private MiembroCensRepository miembroCensRepository;

	private static final Logger logger = LoggerFactory.getLogger(MiembroCensServiceImpl.class);
	

	@Transactional(rollbackFor = CensException.class)
	@Override
	public List<MiembroCens> saveMiembroSens(List<MiembroCens> miembroCensList)
			throws CensException {		
		if (CollectionUtils.isNotEmpty(miembroCensList)) {
			List<MiembroCens> miembroCensListResult = new ArrayList<MiembroCens>();
			for(MiembroCens  miembroCens:miembroCensList){
				miembroCens = validateMiembro(miembroCens);
				List<Perfil> perfilList = new ArrayList<Perfil>(miembroCens.getUsuario().getPerfil());
				miembroCens.setUsuario(usuarioCensService.saveUsuario(miembroCens.getUsuario()));
				miembroCens.getUsuario().setPerfil(perfilList);
				perfilCensService.addPerfilesToUsuarios(miembroCens.getUsuario());
				miembroCens.getUsuario().setPerfil(perfilCensService.listPerfilFromUsuario(miembroCens.getUsuario()));				
				miembroCens = miembroCensRepository.save(miembroCens);	
				rolCensService.assignRolToMiembro(miembroCens);
				miembroCensListResult.add(miembroCens);
			}
			return miembroCensListResult;
		} else {
			throw new CensException("El Usuario miembro no puede ser nulo");
		}
	}

	private MiembroCens validateMiembro(MiembroCens miembroCens)throws CensException{
		MiembroCens mc = miembroCensRepository.findByDni(miembroCens.getDni());
		if(mc!=null && (miembroCens.getId()==null || !mc.getId().equals(miembroCens.getId()))){
			throw new CensException("No se puede guardar el miembro","dni","Existe un miembro con el documento indicado");
		}
		if(mc!=null){
			List<Perfil> perfiles = miembroCens.getUsuario().getPerfil();
			miembroCens.setUsuario(mc.getUsuario());
			
			miembroCens.getUsuario().setPerfil(perfiles);
		}
		return miembroCens;
		
	}
	
	@Override
	public MiembroCens searchMiembroCensByUsuario(Usuarios usuario){
		MiembroCens miembroCens = null;
		if(usuario != null){
			miembroCens = miembroCensRepository.findOneByUsuario(usuario);
		}
		return miembroCens;
	}



	@Override
	@Transactional
	public List<MiembroCens> listMiembrosCens(RestRequest restRequest) {
		
		Page<MiembroCens> requestedPage = null;
		 if(restRequest.getPage() > 0){
			 restRequest.setPage(restRequest.getPage() - 1);
		 }
		 		 
		 if(restRequest.getFilters()==null  ||(!restRequest.getFilters().containsKey("apellido") && !restRequest.getFilters().containsKey("perfil"))){
			 requestedPage = miembroCensRepository.findAll(getSpecificationMiembroCens(null,null,true),Utils.constructPageSpecification(restRequest.getPage(),restRequest.getRow(),sortByApellidoAsc()));
			 return requestedPage.getContent();
		 }
		 Specifications<MiembroCens> specifications = getSpecificationMiembroCens(
				 restRequest.getFilters().get("apellido"), PerfilTrabajadorCensType.getPrefilByName(restRequest.getFilters().get("perfil")), true);
		 requestedPage = miembroCensRepository.findAll(specifications,Utils.constructPageSpecification(restRequest.getPage(),restRequest.getRow(),sortByApellidoAsc()));
		 return requestedPage.getContent();
		 
		
		
	}
	private Specifications<MiembroCens> getSpecificationMiembroCens(String apellido, PerfilTrabajadorCensType ptct, boolean where){
		Specifications<MiembroCens> specifications = null;
		 if(ptct!=null){
			 if(where){
				 specifications = Specifications.where(MiembroCensSpecification.perfilTrabajadorCensEquals(ptct));
				 where = false;
			 }
		 }
		 if(!StringUtils.isEmpty(apellido)){			 
			 if(where ){
				 specifications = Specifications.where(MiembroCensSpecification.apellidoEquals(apellido));
				 where = false;
			 } else{
				 specifications = specifications.and(MiembroCensSpecification.apellidoEquals(apellido));
			 }
		 }
		 if(where){
			 specifications = Specifications.where(MiembroCensSpecification.bajaFalseEnabledTrue());
		 }else{
			 specifications = specifications.and(MiembroCensSpecification.bajaFalseEnabledTrue());
		 }
		return specifications;
	}
	
	@Override
	public MiembroCens getMiembroCens(Long id) throws CensException{
		logger.info("Obteniendo miembro "+id);
		MiembroCens miembroCens = miembroCensRepository.findOne(id);
		if(miembroCens==null){
			throw new CensException("No existe el miembro con identificador "+id);
		}
		return miembroCens;
	}



	@Override
	public Long getTotalUsersFilterByProfile(RestRequest restRequest) {
		logger.info("obteniendo numero de registros de usuarios");
    	long cantUsers = 0;   	 	   	 	
   	 if(restRequest.getFilters()==null  ||(!restRequest.getFilters().containsKey("apellido") && !restRequest.getFilters().containsKey("perfil"))){
   		cantUsers = miembroCensRepository.count(getSpecificationMiembroCens(null,null,true));
		
	 }else{
		 Specifications<MiembroCens> specification = getSpecificationMiembroCens(
				 restRequest.getFilters().get("apellido"), PerfilTrabajadorCensType.getPrefilByName(restRequest.getFilters().get("perfil")), true);
		 cantUsers = miembroCensRepository.count(specification);
	 }
   	 	   	 	
    	return (long) Math.ceil(cantUsers);
	}



	@Override
	@Transactional
	public void deleteMiembro(Long miembroId) {
		usuarioCensService.deleteUsuarioByMiembroId(miembroId);
		miembroCensRepository.softDelete(miembroId);		
	}

	@Override
	public void updateCurrentUserFromMiembro(MiembroCens miembroCensDto) {
		Usuarios u = usuarioCensService.findUsuarioById(miembroCensDto.getUsuario().getId());
		miembroCensDto.getUsuario().setEnabled(u.getEnabled());
		miembroCensDto.getUsuario().setPassword(u.getPassword());
		miembroCensDto.getUsuario().setUsername(u.getUsername());		
		
	}
	
	/**
     * Retorna  Sort object que ordena usuarios acorde al nombre asociado 
     * @return
     */
    public  Sort sortByApellidoAsc() {
        return new Sort(Sort.Direction.ASC, "apellido");
    }

	@Override
	public MiembroCens getMiembroCensByUsername(String username) {
		return miembroCensRepository.findByUsername(username);
	}
	
}
