package com.aehtiopicus.cens.service.cens;

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

import com.aehtiopicus.cens.domain.entities.MiembroCens;
import com.aehtiopicus.cens.domain.entities.Profesor;
import com.aehtiopicus.cens.domain.entities.RestRequest;
import com.aehtiopicus.cens.enumeration.cens.PerfilTrabajadorCensType;
import com.aehtiopicus.cens.repository.cens.ProfesorCensRepository;
import com.aehtiopicus.cens.specification.cens.ProfesorCensSpecification;
import com.aehtiopicus.cens.utils.CensException;
import com.aehtiopicus.cens.utils.Utils;

@Service
public class ProfesorCensServiceImpl implements ProfesorCensService{

	@Autowired
	private ProfesorCensRepository profesorCensRepository;
	
	@Autowired
	private AsignaturaCensService asignaturaCensService;
	
	private static final Logger logger = LoggerFactory.getLogger(ProfesorCensServiceImpl.class);
	private static final PerfilTrabajadorCensType PERFIL_TYPE = PerfilTrabajadorCensType.PROFESOR;

	@Override
	public Profesor saveProfesor(MiembroCens miembroCens) throws CensException{
		if(miembroCens==null  || !Utils.checkIsCensMiembro(miembroCens.getUsuario().getPerfil(), PERFIL_TYPE)){
			throw new CensException("El usuario no puede asignarse como un Profesor");
		}
		
		Profesor p = getProfesor(miembroCens);
		if(p == null || p.getBaja()){
			if(p==null){
				p = new Profesor();
			}
			p.setMiembroCens(miembroCens);
		}else if (p.getBaja()){
			p.setBaja(false);
		}else{
			return p;
		}
		return profesorCensRepository.save(p);

	}
	
	@Override
	public Profesor getProfesor(MiembroCens usuario){
		Profesor p = null;
		if(usuario != null){
			return profesorCensRepository.findOneByMiembroCens(usuario);
		}
		return p;
	}

	@Override
	public void deleteProfesor(MiembroCens miembroCens) throws CensException {
		Profesor p = profesorCensRepository.findOneByMiembroCens(miembroCens);
		if(asignaturaCensService.countAsignaturasActivasByProfesor(p)==0){
			profesorCensRepository.markAsesorAsDisable(miembroCens);
		}else{
			throw new CensException("El profesor posee asignaturas asociadas.","profesorId",""+p.getId());
		}
		
	}
	
	@Override
	public List<Profesor> listProfesores(RestRequest restRequest){
		Page<Profesor> requestedPage = null;
		 if(restRequest.getPage() > 0){
			 restRequest.setPage(restRequest.getPage() - 1);
		 }
		 		 
		 if(restRequest.getFilters()==null  || !restRequest.getFilters().containsKey("data")){
			 requestedPage = profesorCensRepository.findAll(getSpecificationProfesor(null,null),Utils.constructPageSpecification(restRequest.getPage(),restRequest.getRow(),sortByApellidoAsc()));
			 return requestedPage.getContent();
		 }
		 Specifications<Profesor> specifications = getSpecificationProfesor(
				 restRequest.getFilters().get("data"),restRequest.getFilters().containsKey("profesor") ?restRequest.getFilters().get("profesor") : null);
		 requestedPage = profesorCensRepository.findAll(specifications,Utils.constructPageSpecification(restRequest.getPage(),restRequest.getRow(),sortByApellidoAsc()));
		 return requestedPage.getContent();
	}
	
	private Specifications<Profesor> getSpecificationProfesor(String data, String profesor){
		
		Specifications<Profesor> specifications = Specifications.where(ProfesorCensSpecification.perfilTrabajadorCensEquals());		
		
		 if(StringUtils.isNotEmpty(data)){
			 
			 specifications  = specifications.and(ProfesorCensSpecification.nombreApellidoDniLikeNotBaja(data));
		 }else{
			 specifications  = specifications.and(ProfesorCensSpecification.NotBaja());
		 }
		 if(StringUtils.isNotEmpty(profesor)){
			 Long profesorId;
			 try{
				 profesorId = Long.parseLong(profesor);
			 }catch(Exception e){	
				 profesorId = null;
			 }
			 if(profesorId!=null){
				 specifications = specifications.and(ProfesorCensSpecification.notThisOne(profesorId));
			 }
		 }
		return specifications; 
	}
	
	@Override
	public Long getTotalProfesoresFilterByProfile(RestRequest restRequest) {
		logger.info("obteniendo numero de registros de profesores");
    	
		long cantUsers = 0;   	 	   	 	
   	 	if(restRequest.getFilters()==null  || !restRequest.getFilters().containsKey("data")){
   	 		cantUsers = profesorCensRepository.count(getSpecificationProfesor(null,null));
		
   	 	}else{
   	 		Specifications<Profesor> specification = getSpecificationProfesor(restRequest.getFilters().get("data"),restRequest.getFilters().containsKey("profesor") ?restRequest.getFilters().get("profesor") : null);
   	 		cantUsers = profesorCensRepository.count(specification);
   	 	}
   	 	   	 	
    	return (long) Math.ceil(cantUsers);
	}
	
	 public  Sort sortByApellidoAsc() {
	        return new Sort(Sort.Direction.ASC, "miembroCens.apellido");
	    }

	@Override
	public Profesor findById(Long id) {
		return profesorCensRepository.findOne(id);
	}

	@Override
	public void removeAsignaturasProfesor(Long profesorId) {
		
		asignaturaCensService.removeProfesorFromAsignaturas(this.findById(profesorId));
		
	}
}
