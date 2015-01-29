package com.aehtiopicus.cens.service.cens;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.aehtiopicus.cens.domain.entities.Alumno;
import com.aehtiopicus.cens.domain.entities.Asesor;
import com.aehtiopicus.cens.domain.entities.ComentarioCens;
import com.aehtiopicus.cens.domain.entities.MiembroCens;
import com.aehtiopicus.cens.domain.entities.Preceptor;
import com.aehtiopicus.cens.domain.entities.Profesor;
import com.aehtiopicus.cens.enumeration.cens.ComentarioType;
import com.aehtiopicus.cens.enumeration.cens.PerfilTrabajadorCensType;
import com.aehtiopicus.cens.repository.cens.ComentarioCensRepository;
import com.aehtiopicus.cens.utils.CensException;

@Service
public class ComentarioCensServiceImpl implements ComentarioCensService{

	private static final Logger logger = LoggerFactory.getLogger(ComentarioCensServiceImpl.class);
	@Autowired
	private AsesorCensService asesorCensService;
	
	@Autowired
	private ProfesorCensService profesorCensService;
	
	@Autowired
	private PreceptorCensService preceptorCensService;
	
	@Autowired
	private AlumnoCensService alumnoCensService;
	
	@Autowired
	private ComentarioCensRepository comentarioCensRepository;
	
	
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


	@Override
	@Transactional(rollbackFor={Exception.class, CensException.class})
	public ComentarioCens saveComentario(ComentarioCens cc, MultipartFile file) throws CensException {
		
		if(cc==null || (cc.getAsesor() == null && cc.getProfesor()==null) || StringUtils.isEmpty(cc.getComentario())){
			throw new CensException("El Comentario no puede ser nulo");
		}
		logger.info("Guardando comentario");
		cc = validateData(cc);
		if(cc.getParent()!=null){
			comentarioCensRepository.save(cc.getParent());
		}else{
			cc = comentarioCensRepository.save(cc);
		}
		if(file!=null){
//			p.setFileInfo(handleFtp(file, p));	
//			p.setEstadoRevisionType(EstadoRevisionType.LISTO);
//			return programaCensRepository.save(p);
			return cc;
		}else{
			return cc;
		}
	}
	
	private ComentarioCens validateData(ComentarioCens cc){
		if(cc.getId()!=null){
			String newComment = cc.getComentario();
			cc = comentarioCensRepository.findOne(cc.getId());
			cc.setComentario(newComment);
		}else{
			if(cc.getAsesor()!=null){
				cc.setAsesor(asesorCensService.findById(cc.getAsesor().getId()));
			}else if(cc.getProfesor()!=null){
				cc.setProfesor(profesorCensService.findById(cc.getProfesor().getId()));
			}
			if(cc.getParent()!=null){
				cc.setParent(comentarioCensRepository.findOne(cc.getParent().getId()));
				if(CollectionUtils.isEmpty(cc.getParent().getChildrens())){
					cc.getParent().setChildrens(new ArrayList<ComentarioCens>());
				}
				
				cc.getParent().getChildrens().add(cc);
			}
		}
		return cc;
	}


	@Override
	public List<ComentarioCens> findAllParentcomments(Long tipoId,
			ComentarioType tipoType) {
		return comentarioCensRepository.findByTipoIdAndTipoComentarioAndBajaFalseAndParentIsNull(tipoId,tipoType,sortByFechaAsc());
	}
	
	 public  Sort sortByFechaAsc() {
	        return new Sort(Sort.Direction.ASC, "fecha");
	    }
}
