package com.aehtiopicus.cens.service.cens;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aehtiopicus.cens.domain.entities.Alumno;
import com.aehtiopicus.cens.domain.entities.AlumnoInscripcion;
import com.aehtiopicus.cens.domain.entities.AsignaturaInscripcion;
import com.aehtiopicus.cens.domain.entities.AsignaturaInscripcionResult;
import com.aehtiopicus.cens.domain.entities.InscripcionAlumnos;
import com.aehtiopicus.cens.repository.cens.InscripcionAlumnoCensRepository;
import com.aehtiopicus.cens.utils.CensException;

@Service
public class InscripcionAlumnoCensServiceImpl implements InscripcionAlumnoCensService{

	@Autowired
	private AsignaturaCensService asignaturaCensService;
	
	@Autowired
	private InscripcionAlumnoCensRepository inscripcionAlumnoCensRepository;
	
	@Override	
	public AsignaturaInscripcionResult inscribirAlumnos(AsignaturaInscripcion ai) throws Exception{
			
		
		InscripcionAlumnos ia = null;
		AsignaturaInscripcionResult air = new AsignaturaInscripcionResult();
		air.setInscripcionStatus(false);
		
		if(CollectionUtils.isNotEmpty(ai.getAlumnos())){
			
			air.setAlumnosInscriptos(new ArrayList<AlumnoInscripcion>());
			air.setAsignaturaId(ai.getAsignatura().getId());
			
			for(Alumno a : ai.getAlumnos()){
				ia = new InscripcionAlumnos();
				ia.setAlumno(a);
				ia.setAsignatura(ai.getAsignatura());
				ia.setFecha(new java.util.Date());
				air.getAlumnosInscriptos().add(inscribir(ia));
			}
		}
		
		return air;
		
	}
	
	private AlumnoInscripcion inscribir(InscripcionAlumnos ia) throws Exception {
		AlumnoInscripcion ai = new AlumnoInscripcion();
		ai.setAlumnoId(ia.getAlumno().getId());
		
		try{
			inscripcionAlumnoCensRepository.save(ia);
			inscripcionAlumnoCensRepository.flush();
			ai.getMessage().put("status", "success");
			ai.getMessage().put("alumnoId", ia.getAlumno().getId());
			ai.getMessage().put("message", "alumno inscripto");
			ai.setStatus(true);
		}catch(DataIntegrityViolationException e){
			ai.getMessage().put("status", "info");
			ai.getMessage().put("alumnoId", ia.getAlumno().getId());
			ai.getMessage().put("message", "alumno  previamente inscripto");		
			ai.setStatus(false);
		}catch(Exception e){
			ai.getMessage().put("status", "error");
			ai.getMessage().put("alumnoId", ai.getAlumnoId());
			ai.getMessage().put("message", "No se puede inscribir el alumno");		
			ai.setStatus(false);
		}
		return ai;
		
	}

	@Override
	@Transactional(rollbackFor= CensException.class)
	public int eliminarInscripcion(Long asignaturaId, Long alumnoId) throws CensException {
		try{
			return inscripcionAlumnoCensRepository.eliminarInscripcion(asignaturaId, alumnoId);
		}catch(Exception e){
			throw new CensException("No se puede eliminar la inscripci&oacute;n del alumno");
		}
		
	}
	
	@Override
	public List<InscripcionAlumnos> listInscripcionAlumno(Long alumnoId){
		
		return inscripcionAlumnoCensRepository.findByAlumnoIdEqualsAndAsignaturaVigenteEquals( alumnoId,true);
	}

	@Override
	public void eliminarInscripcionAsignatura(Long asignaturaID)
			throws CensException {
		try{
			inscripcionAlumnoCensRepository.eliminarInscripcion(asignaturaID);
		}catch(Exception e){
			throw new CensException("No se puede borrar todas las inscripciones de la asignatura",e);
		}
		
	}

}
