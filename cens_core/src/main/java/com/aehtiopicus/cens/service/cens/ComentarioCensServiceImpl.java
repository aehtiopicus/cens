package com.aehtiopicus.cens.service.cens;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aehtiopicus.cens.domain.entities.Alumno;
import com.aehtiopicus.cens.domain.entities.Asesor;
import com.aehtiopicus.cens.domain.entities.MiembroCens;
import com.aehtiopicus.cens.domain.entities.Preceptor;
import com.aehtiopicus.cens.domain.entities.Profesor;
import com.aehtiopicus.cens.enumeration.cens.PerfilTrabajadorCensType;
import com.aehtiopicus.cens.utils.CensException;

@Service
public class ComentarioCensServiceImpl implements ComentarioCensService{

	@Autowired
	private AsesorCensService asesorCensService;
	
	@Autowired
	private ProfesorCensService profesorCensService;
	
	@Autowired
	private PreceptorCensService preceptorCensService;
	
	@Autowired
	private AlumnoCensService alumnoCensService;
	
	
	@Override
	public MiembroCens getMiembroCensByPerfilTypeAndRealId(PerfilTrabajadorCensType type, Long id) throws CensException{
		
		MiembroCens miembroCens = null;
		switch(type){
		
		case ALUMNO:
			Alumno alumno = alumnoCensService.findById(id);
			if(alumno!=null){
				miembroCens = alumno.getMiembroCens();
			}
			break;
		case ASESOR:
			Asesor a = asesorCensService.findById(id);
			if(a!=null){
				miembroCens = a.getMiembroCens();
			}
			break;
		case PRECEPTOR:
			Preceptor preceptor = preceptorCensService.findById(id);
			if(preceptor!=null){
				miembroCens = preceptor.getMiembroCens();
			}
			break;
		case PROFESOR:
			Profesor p = profesorCensService.findById(id);
			if(p!=null){
				miembroCens = p.getMiembroCens();
			}
			break;
		default:
			break;
		
		}
		return miembroCens;
	}
}
