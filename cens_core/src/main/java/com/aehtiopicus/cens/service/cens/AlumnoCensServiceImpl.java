package com.aehtiopicus.cens.service.cens;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import com.aehtiopicus.cens.domain.entities.Alumno;
import com.aehtiopicus.cens.domain.entities.MiembroCens;
import com.aehtiopicus.cens.domain.entities.RestRequest;
import com.aehtiopicus.cens.enumeration.cens.PerfilTrabajadorCensType;
import com.aehtiopicus.cens.repository.cens.AlumnoCensRepository;
import com.aehtiopicus.cens.specification.cens.AlumnoCensSpecification;
import com.aehtiopicus.cens.utils.CensException;
import com.aehtiopicus.cens.utils.Utils;

@Service
public class AlumnoCensServiceImpl implements AlumnoCensService{

	@Autowired
	private AlumnoCensRepository alumnoCensRepository;
	
	private static final PerfilTrabajadorCensType PERFIL_TYPE = PerfilTrabajadorCensType.ALUMNO;
	
	private static final Logger logger = LoggerFactory.getLogger(AlumnoCensServiceImpl.class);

	@Override
	public Alumno saveAlumno(MiembroCens miembroCens) throws CensException{
		if(miembroCens==null  || !Utils.checkIsCensMiembro(miembroCens.getUsuario().getPerfil(), PERFIL_TYPE)){
			throw new CensException("El usuario no puede asignarse como un Alumno");
		}
		
		Alumno p = getAlumno(miembroCens);
		if(p == null ){
				p = new Alumno();		
			p.setMiembroCens(miembroCens);
		}else if (p.getBaja()){
			p.setBaja(false);
		}else{
			return p;
		}
		return alumnoCensRepository.save(p);

	}
	
	@Override
	public Alumno getAlumno(MiembroCens usuario){
		Alumno p = null;
		if(usuario != null){
			return alumnoCensRepository.findOneByMiembroCens(usuario);
		}
		return p;
	}

	@Override
	public void deleteAlumno(MiembroCens miembroCens) {
		alumnoCensRepository.markAsesorAsDisable(miembroCens);
		
	}

	@Override
	public Alumno findById(Long id) {
		return alumnoCensRepository.findOne(id);
	}
	
	@Override
	public List<Alumno> listAlumnos(RestRequest restRequest){
		Page<Alumno> requestedPage = null;
		 if(restRequest.getPage() > 0){
			 restRequest.setPage(restRequest.getPage() - 1);
		 }
		 		 
		 if(restRequest.getFilters()==null  || !restRequest.getFilters().containsKey("data")){
			 requestedPage = alumnoCensRepository.findAll(getSpecificationProfesor(null,null,null),Utils.constructPageSpecification(restRequest.getPage(),restRequest.getRow(),sortByApellidoAsc()));
			 return requestedPage.getContent();
		 }
		 Specifications<Alumno> specifications = getSpecificationProfesor(
				 restRequest.getFilters().get("data"),
				 restRequest.getFilters().containsKey("asignaturaRemoveId") ?restRequest.getFilters().get("asignaturaRemoveId") : null,
				 restRequest.getFilters().containsKey("asignaturaId") ?restRequest.getFilters().get("asignaturaId") : null);
		 requestedPage = alumnoCensRepository.findAll(specifications,Utils.constructPageSpecification(restRequest.getPage(),restRequest.getRow(),sortByApellidoAsc()));
		 return requestedPage.getContent();
	}
	
	private Specifications<Alumno> getSpecificationProfesor(String data, String asignaturaRemove,String asignatura){
		
		Specifications<Alumno> specifications = Specifications.where(AlumnoCensSpecification.perfilTrabajadorCensEquals());		
		
		 if(StringUtils.isNotEmpty(data)){
			 
			 specifications  = specifications.and(AlumnoCensSpecification.nombreApellidoDniLikeNotBaja(data));
		 }else{
			 specifications  = specifications.and(AlumnoCensSpecification.notBaja());
		 }
		 if(StringUtils.isNotEmpty(asignaturaRemove)){
			 Long asignaturaRemoveId;
			 try{
				 asignaturaRemoveId = Long.parseLong(asignaturaRemove);
				 specifications = excludeAlumnos(asignaturaRemoveId);
			 }catch(Exception e){	
				 asignaturaRemoveId = null;
			 }

		 }else{
			 if(StringUtils.isNotEmpty(asignatura)){
				 Long asignaturaId;
				 try{
					 asignaturaId = Long.parseLong(asignatura);
					 specifications = specifications.and(AlumnoCensSpecification.inThisAsignatura(asignaturaId));
				 }catch(Exception e){
					 asignaturaId = null;
				 }
			}
			 
		 }
		return specifications; 
	}
	
	private Specifications<Alumno> excludeAlumnos(Long asignaturaId) {
	
		return Specifications.where(AlumnoCensSpecification.innerQueryToFilter(asignaturaId));

		
	}

	@Override
	public Long getTotalAlumnoFilterByProfile(RestRequest restRequest) {
		logger.info("obteniendo numero de registros de alumnos");
    	
		long cantUsers = 0;   	 	   	 	
   	 	if(restRequest.getFilters()==null  || !restRequest.getFilters().containsKey("data")){
   	 		cantUsers = alumnoCensRepository.count(getSpecificationProfesor(null,null,null));
		
   	 	}else{
   	 		Specifications<Alumno> specification = getSpecificationProfesor(restRequest.getFilters().get("data"),
   	 				restRequest.getFilters().containsKey("asignaturaRemoveId") ?restRequest.getFilters().get("asignaturaRemoveId") : null,
   	 					restRequest.getFilters().containsKey("asignaturaId") ?restRequest.getFilters().get("asignaturaId") : null);
   	 		cantUsers = alumnoCensRepository.count(specification);
   	 	}
   	 	   	 	
    	return (long) Math.ceil(cantUsers);
	}
	
	 public  Sort sortByApellidoAsc() {
	        return new Sort(Sort.Direction.ASC, "miembroCens.apellido");
	    }

}
