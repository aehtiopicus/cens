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

import com.aehtiopicus.cens.domain.entities.Asignatura;
import com.aehtiopicus.cens.domain.entities.Profesor;
import com.aehtiopicus.cens.domain.entities.RestRequest;
import com.aehtiopicus.cens.repository.cens.AsignaturCensRepository;
import com.aehtiopicus.cens.specification.cens.AsignaturaCensSpecification;
import com.aehtiopicus.cens.utils.CensException;
import com.aehtiopicus.cens.utils.Utils;

@Service
public class AsignaturaCensServiceImpl implements AsignaturaCensService{
	
	private static final Logger logger = LoggerFactory.getLogger(AsignaturaCensServiceImpl.class);
	
	@Autowired
	private AsignaturCensRepository asignaturaCensRepository;
	
	@Autowired
	private CursoCensService cursoCensService;
	
	@Autowired
	private ProfesorCensService profesorCensService;
	
	@Override
	public List<Asignatura> saveAsignaturas(List<Asignatura> asignaturaList) throws CensException{
		if(CollectionUtils.isEmpty(asignaturaList)){
			throw new CensException("Debe existir datos para guardar asignaturas");
		}
		logger.info("Guardando asignatura ");
		List<Asignatura> resultList = new ArrayList<Asignatura>();
		for(Asignatura asignatura : asignaturaList){
			asignatura = validateAsignatura(asignatura);			
			resultList.add(asignaturaCensRepository.save(asignatura));
		}
		return resultList;
		
	}
	
	private Asignatura validateAsignatura(Asignatura asignatura)throws CensException{
		Asignatura a = asignaturaCensRepository.findByNombreAndModalidadAndCurso(asignatura.getNombre(),asignatura.getModalidad(),asignatura.getCurso());
		if(a!=null && (asignatura.getId()==null || !a.getId().equals(asignatura.getId()))){
			throw new CensException("No se puede guardar el miembro","dni","Existe un miembro con el documento indicado");
		}		
		
		if(asignatura.getProfesor()!=null && asignatura.getProfesor().getId()!=null){
			asignatura.setProfesor(profesorCensService.findById(asignatura.getProfesor().getId()));
		}else{
			asignatura.setProfesor(null);
		}
		if(asignatura.getProfesorSuplente()!=null && asignatura.getProfesorSuplente().getId()!=null){
			asignatura.setProfesorSuplente(profesorCensService.findById(asignatura.getProfesorSuplente().getId()));
		}else{
			asignatura.setProfesorSuplente(null);
		}
		if(asignatura.getCurso()!=null && asignatura.getCurso().getId()!=null){
			asignatura.setCurso(cursoCensService.findById(asignatura.getCurso().getId()));
		}else{
			asignatura.setCurso(null);
		}
		
		return asignatura;
	}
	
	@Override
	@Transactional
	public List<Asignatura> listAsignaturas(RestRequest restRequest) {
		
		Page<Asignatura> requestedPage = null;
		 if(restRequest.getPage() > 0){
			 restRequest.setPage(restRequest.getPage() - 1);
		 }
		 		 
		 if(restRequest.getFilters()==null  || (!restRequest.getFilters().containsKey("year") &&!restRequest.getFilters().containsKey("nombre") && !restRequest.getFilters().containsKey("modalidad"))){
			 requestedPage = asignaturaCensRepository.findAll(Utils.constructPageSpecification(restRequest.getPage(),restRequest.getRow(),sortByNombreAsc()));
			 return requestedPage.getContent();
		 }
		 //hack
		 Integer year = null;
		 try{
			 year =Integer.parseInt(restRequest.getFilters().get("year"));
		 }catch(Exception e){
			 logger.error("year validacion fallida");
		 }
		 Specifications<Asignatura> specifications = getSpecificationAsignatura(year,
				 restRequest.getFilters().get("nombre"), restRequest.getFilters().get("modalidad"), true);
		 requestedPage = asignaturaCensRepository.findAll(specifications,Utils.constructPageSpecification(restRequest.getPage(),restRequest.getRow(),sortByNombreAsc()));
		 return requestedPage.getContent();
		 
		
		
	}
	private Specifications<Asignatura> getSpecificationAsignatura(Integer year,String nombre, String modalidad, boolean where){
		Specifications<Asignatura> specifications = null;
		 if(year!=null){
			 if(where){
				 specifications = Specifications.where(AsignaturaCensSpecification.cursoYearEquals(year));
				 where = false;
			 }
		 }
		 if(!StringUtils.isEmpty(nombre)){			 
			 if(where ){
				 specifications = Specifications.where(AsignaturaCensSpecification.modalidadLike(modalidad));
				 where = false;
			 } else{
				 specifications = specifications.and(AsignaturaCensSpecification.modalidadLike(modalidad));
			 }
		 }
		
		 if(!StringUtils.isEmpty(nombre)){			 
			 if(where ){
				 specifications = Specifications.where(AsignaturaCensSpecification.modalidadLike(nombre));
				 where = false;
			 } else{
				 specifications = specifications.and(AsignaturaCensSpecification.nombreLike(nombre));
			 }
		 }
		return specifications;
	}
	
	
	@Override
	public Long getTotalAsignaturasFilterByProfile(RestRequest restRequest) {
		logger.info("obteniendo numero de registros de usuarios");
    	long cantUsers = 0;   	 	   	 	
   	 if(restRequest.getFilters()==null  ||(!restRequest.getFilters().containsKey("year") &&!restRequest.getFilters().containsKey("nombre") && !restRequest.getFilters().containsKey("modalidad"))){
   		cantUsers = asignaturaCensRepository.count();
		
	 }else{
		 //hack
		 Integer year = null;
		 try{
			 year =Integer.parseInt(restRequest.getFilters().get("year"));
		 }catch(Exception e){
			logger.error("year validacion fallida"); 
		 }
		 Specifications<Asignatura> specification = getSpecificationAsignatura(year,
				 restRequest.getFilters().get("nombre"), restRequest.getFilters().get("modalidad"), true);
		 cantUsers = asignaturaCensRepository.count(specification);
	 }
   	 	   	 	
    	return (long) Math.ceil(cantUsers);
	}
	
	@Override
	public void deleteAsignatura(Long asignaturaID) throws CensException{
		logger.info("Borrando asignatura "+asignaturaID);
		try{
			asignaturaCensRepository.delete(asignaturaID);
		}catch(Exception e){
			throw new CensException("Error borrando Asignatura. Es posible que este en uso");
		}
		
	}
	
	 public  Sort sortByNombreAsc() {
	        return new Sort(Sort.Direction.ASC, "nombre");
	    }

	@Override
	public Asignatura getAsignatura(Long asignaturaId) {
		return asignaturaCensRepository.findOne(asignaturaId);
	}

	@Override
	public List<Asignatura> findAsignaturasActivasByProfesor(
			Profesor profesor) {
		return asignaturaCensRepository.findAsignaturaByProfesor(profesor);
	}
	
	@Override
	public Long countAsignaturasActivasByProfesor(Profesor profesor){
		return asignaturaCensRepository.countAsignaturaByProfesor(profesor.getId()).longValue();
	}

	@Override
	@Transactional
	public void removeProfesorFromAsignaturas(Profesor profesor) {
		asignaturaCensRepository.removeProfesor(profesor.getId());
		
	}
	 
	 

}
