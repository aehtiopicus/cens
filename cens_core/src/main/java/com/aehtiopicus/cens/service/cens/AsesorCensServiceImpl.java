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

import com.aehtiopicus.cens.domain.entities.Asesor;
import com.aehtiopicus.cens.domain.entities.Curso;
import com.aehtiopicus.cens.domain.entities.MiembroCens;
import com.aehtiopicus.cens.domain.entities.Programa;
import com.aehtiopicus.cens.domain.entities.RestRequest;
import com.aehtiopicus.cens.enumeration.cens.PerfilTrabajadorCensType;
import com.aehtiopicus.cens.repository.cens.AsesorCensRepository;
import com.aehtiopicus.cens.specification.cens.AsesorCensSpecification;
import com.aehtiopicus.cens.utils.CensException;
import com.aehtiopicus.cens.utils.Utils;

@Service
public class AsesorCensServiceImpl implements AsesorCensService{

	@Autowired
	private AsesorCensRepository asesorCensRepository;
	
	@Autowired
	private CursoCensService cursoCensService;
	
	@Autowired
	private ProgramaCensService programaCensService;
	
	private static final Logger logger = LoggerFactory.getLogger(AsesorCensServiceImpl.class);
	
	private static final PerfilTrabajadorCensType PERFIL_TYPE = PerfilTrabajadorCensType.ASESOR;

	@Override
	public Asesor saveAsesor(MiembroCens miembroCens) throws CensException{
		if(miembroCens==null  || !Utils.checkIsCensMiembro(miembroCens.getUsuario().getPerfil(), PERFIL_TYPE)){
			throw new CensException("El usuario no puede asignarse como un Asesor");
		}
		
		Asesor a = getAsesor(miembroCens);
		if(a == null ){
			a = new Asesor();
			a.setMiembroCens(miembroCens);
		}else if (a.getBaja()){
			a.setBaja(false);
		}else{
			return a;
		}
		return asesorCensRepository.save(a);

	}
	
	@Override
	public Asesor getAsesor(MiembroCens usuario){
		Asesor a = null;
		if(usuario != null){
			return asesorCensRepository.findOneByMiembroCens(usuario);
		}
		return a;
	}

	@Override
	public void deleteAsesor(MiembroCens miembroCens) {
		asesorCensRepository.markAsesorAsDisable(miembroCens);
		
	}

	@Override
	public Asesor findById(Long asesorId) {
		return asesorCensRepository.findOne(asesorId);
	}

	@Override
	public List<Curso> listCursos() {
		return cursoCensService.listCursoAsignatura();
	}

	@Override
	public List<Programa> listProgramas() {
		return programaCensService.getProgramas();
	}

	@Override
	public List<Asesor> listAsesores(RestRequest restRequest){
		Page<Asesor> requestedPage = null;
		 if(restRequest.getPage() > 0){
			 restRequest.setPage(restRequest.getPage() - 1);
		 }
		 		 
		 if(restRequest.getFilters()==null  || !restRequest.getFilters().containsKey("data")){
			 requestedPage = asesorCensRepository.findAll(getSpecificationAsesor(null,null),Utils.constructPageSpecification(restRequest.getPage(),restRequest.getRow(),sortByApellidoAsc()));
			 return requestedPage.getContent();
		 }
		 Specifications<Asesor> specifications = getSpecificationAsesor(
				 restRequest.getFilters().get("data"),restRequest.getFilters().containsKey("profesor") ?restRequest.getFilters().get("profesor") : null);
		 requestedPage = asesorCensRepository.findAll(specifications,Utils.constructPageSpecification(restRequest.getPage(),restRequest.getRow(),sortByApellidoAsc()));
		 return requestedPage.getContent();
	}
	
	private Specifications<Asesor> getSpecificationAsesor(String data, String asesor){
		
		Specifications<Asesor> specifications = Specifications.where(AsesorCensSpecification.perfilTrabajadorCensEquals());		
		
		 if(StringUtils.isNotEmpty(data)){
			 
			 specifications  = specifications.and(AsesorCensSpecification.nombreApellidoDniLikeNotBaja(data));
		 }else{
			 specifications  = specifications.and(AsesorCensSpecification.NotBaja());
		 }
		 if(StringUtils.isNotEmpty(asesor)){
			 Long asesorId;
			 try{
				 asesorId = Long.parseLong(asesor);
			 }catch(Exception e){	
				 asesorId = null;
			 }
			 if(asesorId!=null){
				 specifications = specifications.and(AsesorCensSpecification.notThisOne(asesorId));
			 }
		 }
		return specifications; 
	}
	
	@Override
	public Long getTotalProfesoresFilterByProfile(RestRequest restRequest) {
		logger.info("obteniendo numero de registros de asesor");
    	
		long cantUsers = 0;   	 	   	 	
   	 	if(restRequest.getFilters()==null  || !restRequest.getFilters().containsKey("data")){
   	 		cantUsers = asesorCensRepository.count(getSpecificationAsesor(null,null));
		
   	 	}else{
   	 		Specifications<Asesor> specification = getSpecificationAsesor(restRequest.getFilters().get("data"),restRequest.getFilters().containsKey("profesor") ?restRequest.getFilters().get("profesor") : null);
   	 		cantUsers = asesorCensRepository.count(specification);
   	 	}
   	 	   	 	
    	return (long) Math.ceil(cantUsers);
	}
	
	 public  Sort sortByApellidoAsc() {
	        return new Sort(Sort.Direction.ASC, "miembroCens.apellido");
	    }
	
	
}
