package com.aehtiopicus.cens.service.cens;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aehtiopicus.cens.domain.entities.Asignatura;
import com.aehtiopicus.cens.domain.entities.Curso;
import com.aehtiopicus.cens.domain.entities.Profesor;
import com.aehtiopicus.cens.domain.entities.Programa;
import com.aehtiopicus.cens.domain.entities.RestRequest;
import com.aehtiopicus.cens.repository.cens.AsignaturCensRepository;
import com.aehtiopicus.cens.repository.cens.ProfesorCensRepository;
import com.aehtiopicus.cens.service.cens.ftp.FtpAsignaturaCensService;
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
	private ProfesorCensRepository profesorCensRepository;
	
	@Autowired
	private FtpAsignaturaCensService ftpAsignaturaCensService;
	
	@Autowired
	private ProgramaCensService programaCensService;
	
	@Autowired
	private FileCensService fileCensService;
	
	@Autowired
	private CacheManager cacheManager;
	
	@PersistenceContext
	private EntityManager entityManager;
		
	
	@Override
	@Transactional(rollbackFor={CensException.class,Exception.class})
	public List<Asignatura> saveAsignaturas(List<Asignatura> asignaturaList) throws CensException{
		if(CollectionUtils.isEmpty(asignaturaList)){
			throw new CensException("Debe existir datos para guardar asignaturas");
		}
		logger.info("Guardando asignatura ");
		List<Asignatura> resultList = new ArrayList<Asignatura>();
		Long oldCursoId = null;
		for(Asignatura asignatura : asignaturaList){
			asignatura = validateAsignatura(asignatura);
			if(asignatura.getId()!=null){
				oldCursoId=asignaturaCensRepository.findOne(asignatura.getId()).getCurso().getId();
			}
			Asignatura asignaturaSaved = asignaturaCensRepository.save(asignatura); 	
			handleFtp(asignaturaSaved, oldCursoId);
			resultList.add(asignaturaSaved);
		}
		return resultList;
		
	}
	
	private void handleFtp(Asignatura asignaturaSaved,Long oldCursoId) throws CensException{
		ftpAsignaturaCensService.createAsignaturaFolder(asignaturaSaved);
		if(oldCursoId!=null){		
			if(oldCursoId!= asignaturaSaved.getCurso().getId()){
				List<String> asignaturaPath =ftpAsignaturaCensService.asignaturaPaths(asignaturaSaved);
				fileCensService.updatePath(asignaturaPath,oldCursoId);
				ftpAsignaturaCensService.moveAsignaturaData(oldCursoId,asignaturaSaved);
			}
		}
		
	}
	
	private Asignatura validateAsignatura(Asignatura asignatura)throws CensException{
		Asignatura a = asignaturaCensRepository.findByNombreAndModalidadAndCurso(asignatura.getNombre(),asignatura.getModalidad(),asignatura.getCurso());
		if(a!=null && (asignatura.getId()==null || !a.getId().equals(asignatura.getId()))){
			throw new CensException("No se puede guardar la asignatura");
		}		
		
		if(asignatura.getProfesor()!=null && asignatura.getProfesor().getId()!=null){
			asignatura.setProfesor(profesorCensRepository.findOne(asignatura.getProfesor().getId()));
		}else{
			asignatura.setProfesor(null);
		}
		if(asignatura.getProfesorSuplente()!=null && asignatura.getProfesorSuplente().getId()!=null){
			asignatura.setProfesorSuplente(profesorCensRepository.findOne(asignatura.getProfesorSuplente().getId()));
		}else{
			asignatura.setProfesorSuplente(null);
		}
		if(asignatura.getCurso()!=null && asignatura.getCurso().getId()!=null){
			asignatura.setCurso(cursoCensService.findById(asignatura.getCurso().getId()));
		}else{
			throw new CensException("La asignatura debe pertenecer a un curso");
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

	@Override
	public List<Programa> getProgramasForAsignaturas(Long id) {
		return programaCensService.getProgramasForAsignatura(id);
	}

	@Cacheable(value="cursoProfesor")
	@Override
	public List<Curso> listarAsignaturasByProfesor(Long id) {
		return assembleCursoList(asignaturaCensRepository.findByProfesorId(id));
	
	}
	
	private List<Curso> assembleCursoList(List<Asignatura> asignatura){
		List<Curso> cursoList = null;
		if(CollectionUtils.isNotEmpty(asignatura)){
			Map<Long,Curso> cursos = new HashMap<Long,Curso>();
			for(Asignatura a : asignatura){
				if(cursos.get(a.getCurso().getId())== null){
					Curso c = a.getCurso();
					c.setAsignaturas(new ArrayList<Asignatura>());
					c.getAsignaturas().add(a);
					cursos.put(c.getId(),c);				
				}else{
					Curso c =cursos.get(a.getCurso().getId());
					c.getAsignaturas().add(a);
				}
			}
			cursoList = new ArrayList<Curso>();
			
			for(Entry<Long,Curso> cursoEntry : cursos.entrySet()){				
				cursoList.add(cursoEntry.getValue());
			}
			
		}
		return cursoList;
	}

	 

}
