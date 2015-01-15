package com.aehtiopicus.cens.service.cens;

import java.util.List;

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
	public void deleteProfesor(MiembroCens miembroCens) {
		profesorCensRepository.markAsesorAsDisable(miembroCens);
		
	}
	
	@Override
	public List<Profesor> listProfesores(RestRequest restRequest){
		Page<Profesor> requestedPage = null;
		 if(restRequest.getPage() > 0){
			 restRequest.setPage(restRequest.getPage() - 1);
		 }
		 		 
		 if(restRequest.getFilters()==null  || !restRequest.getFilters().containsKey("data")){
			 requestedPage = profesorCensRepository.findAll(getSpecificationProfesor(null),Utils.constructPageSpecification(restRequest.getPage(),restRequest.getRow(),sortByApellidoAsc()));
			 return requestedPage.getContent();
		 }
		 Specifications<Profesor> specifications = getSpecificationProfesor(
				 restRequest.getFilters().get("data"));
		 requestedPage = profesorCensRepository.findAll(specifications,Utils.constructPageSpecification(restRequest.getPage(),restRequest.getRow(),sortByApellidoAsc()));
		 return requestedPage.getContent();
	}
	
	private Specifications<Profesor> getSpecificationProfesor(String data){
		
		Specifications<Profesor> specifications = Specifications.where(ProfesorCensSpecification.miembroBajaFalse());		
		specifications = specifications.and(ProfesorCensSpecification.profesorNotBaja());
		 if(StringUtils.isNotEmpty(data)){
			 specifications  = (specifications.or(ProfesorCensSpecification.apellidoLike(data)).or(ProfesorCensSpecification.nombreLike(data)).or(ProfesorCensSpecification.dniLike(data)));
//			 specifications = specifications.or(ProfesorCensSpecification.apellidoLike(data));
//			 specifications = specifications.or(ProfesorCensSpecification.nombreLike(data));
//			 specifications = specifications.or(ProfesorCensSpecification.dniLike(data));
		 } 
		return specifications = specifications.and(ProfesorCensSpecification.perfilTrabajadorCensEquals());
	}
	
	@Override
	public Long getTotalProfesoresFilterByProfile(RestRequest restRequest) {
		logger.info("obteniendo numero de registros de profesores");
    	
		long cantUsers = 0;   	 	   	 	
   	 	if(restRequest.getFilters()==null  || !restRequest.getFilters().containsKey("data")){
   	 		cantUsers = profesorCensRepository.count(getSpecificationProfesor(null));
		
   	 	}else{
   	 		Specifications<Profesor> specification = getSpecificationProfesor(restRequest.getFilters().get("data"));
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
}
