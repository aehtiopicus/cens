package com.aehtiopicus.cens.service.cens;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aehtiopicus.cens.domain.entities.Asignatura;
import com.aehtiopicus.cens.domain.entities.Curso;
import com.aehtiopicus.cens.domain.entities.RestRequest;
import com.aehtiopicus.cens.repository.cens.CursoCensRepository;
import com.aehtiopicus.cens.service.cens.ftp.FtpCursoCensService;
import com.aehtiopicus.cens.specification.cens.CursoSpecification;
import com.aehtiopicus.cens.utils.CensException;
import com.aehtiopicus.cens.utils.Utils;

@Service
public class CursoCensServiceImpl implements CursoCensService{
	private static final Logger logger = LoggerFactory.getLogger(CursoCensServiceImpl.class);

	@Autowired
	private CursoCensRepository cursoCensRepository;
	
	@Autowired
	private FtpCursoCensService ftpCursoCensService;
	
	@Override
	@Transactional(rollbackFor={CensException.class,Exception.class})
	public List<Curso> save(List<Curso> cursoList) throws CensException{
		if(CollectionUtils.isEmpty(cursoList)){
			throw new CensException("No existen datos para guardar");
		}
		List<Curso> cursoResultList = new ArrayList<Curso>();
		for(Curso curso : cursoList){
			validate(curso);
			Curso savedCurso = cursoCensRepository.save(curso);
			cursoResultList.add(savedCurso);
			ftpCursoCensService.createCursoFolder(savedCurso);
		}
		return cursoResultList;
	}
	
	private void validate(Curso curso)throws CensException{
		Curso c = findCursoByYearAndNombre(curso.getYearCurso(),curso.getNombre());
		if(c!=null && (curso.getId()==null || !c.getId().equals(curso.getId()))){
			throw new CensException("No se puede guardar el curso","yearCurso","Existe un curso en este a&ntilde;o con el nombre dado","nombre","Existe un curso en este a&ntilde;o con el nombre dado");
		}
	}
	
	@Override
	public Curso findCursoByYearAndNombre(int year, String nombre){
		return cursoCensRepository.findByYearCursoAndNombre(year,nombre);
	}

	@Override
	@Transactional
	public List<Curso> listCursos(RestRequest restRequest) {
		Page<Curso> requestedPage = null;
		 if(restRequest.getPage() > 0){
			 restRequest.setPage(restRequest.getPage() - 1);
		 }
		 		 
		 if(restRequest.getFilters()==null  || (!restRequest.getFilters().containsKey("year")&& !restRequest.getFilters().containsKey("nombre") )){
			 requestedPage = cursoCensRepository.findAll(Utils.constructPageSpecification(restRequest.getPage(),restRequest.getRow(),sortByNombreAsc()));
			 return requestedPage.getContent();
		 }
		 Specifications<Curso> specifications =getSpecificationCurso(restRequest.getFilters().get("year"),restRequest.getFilters().get("nombre"));
		 requestedPage = cursoCensRepository.findAll(specifications,Utils.constructPageSpecification(restRequest.getPage(),restRequest.getRow(),sortByNombreAsc()));
		 return requestedPage.getContent();
		 
	}
	private Specifications<Curso> getSpecificationCurso(String yearS,String nombre){
		Specifications<Curso> specifications = null;
		 boolean where = false;
		 int year = 0;
		 try{
			 year = Integer.parseInt(yearS);
		 }catch(Exception e){			 
		 }
		 if(year>0){			 
			 specifications = Specifications.where(CursoSpecification.yearEqual(year));
			 where = true;
		 }
		 if(StringUtils.isNotEmpty(nombre)){
			 if(where){
				 specifications = specifications.and(CursoSpecification.nombreLike(nombre));
			 }else{
				 specifications = Specifications.where(CursoSpecification.nombreLike(nombre)); 
			 }
		 }
		return specifications;
	}
	
	/**
     * Retorna  Sort object que ordena usuarios acorde al nombre asociado 
     * @return
     */
    public  Sort sortByNombreAsc() {
        return new Sort(Sort.Direction.ASC, "nombre");
    }

	@Override
	public long getTotalCursos(RestRequest restRequest) {
		logger.info("obteniendo numero de registros de cursos");
    	long cantUsers = 0;   	 	   	 	
   	 if(restRequest.getFilters()==null  ||(!restRequest.getFilters().containsKey("year")||!restRequest.getFilters().containsKey("nombre"))){
   		cantUsers = cursoCensRepository.count();
		
	 }else{
		 Specifications<Curso> specification = getSpecificationCurso(restRequest.getFilters().get("year"),restRequest.getFilters().get("nombre"));
		 cantUsers = cursoCensRepository.count(specification);
	 }
   	 	   	 	
    	return (long) Math.ceil(cantUsers);
	}

	@Override
	public Curso getCurso(Long cursoId) {
		logger.info("Obteniendo curso "+cursoId);
		return cursoCensRepository.findOne(cursoId);
	}

	@Override
	public void deleteCurso(Long cursoId) throws CensException{
		logger.info("Borrando curso "+cursoId);
		try{
			cursoCensRepository.delete(cursoId);
		}catch(Exception e){
			throw new CensException("Error borrando curso. Es posible que este en uso");
		}
		
	}

	@Override
	public Curso findById(Long id) {
		return cursoCensRepository.findOne(id);
	}
	
	class CursoSortAsignaturaName implements Comparator<Asignatura>{

		@Override
		public int compare(Asignatura o1, Asignatura o2) {
			return o1.getNombre().toLowerCase().compareTo(o2.getNombre().toLowerCase());			
		}
		
	}

	@Override
	@Cacheable("cursoAsesor")
	public List<Curso> listCursoAsignatura() {
		List<Curso> cursoList = cursoCensRepository.findDistinctCursoByAsignaturaAndAsignatura();
		if(CollectionUtils.isNotEmpty(cursoList)){
			for(Curso c : cursoList){
				Collections.sort(c.getAsignaturas(), new CursoSortAsignaturaName());
			}
		}
		return cursoList;
	}
}
