package com.aehtiopicus.cens.service.cens;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.aehtiopicus.cens.domain.entities.Asignatura;
import com.aehtiopicus.cens.domain.entities.Programa;
import com.aehtiopicus.cens.enumeration.MaterialDidacticoUbicacionType;
import com.aehtiopicus.cens.repository.cens.ProgramaCensRepository;
import com.aehtiopicus.cens.service.cens.ftp.FTPProgramaCensService;
import com.aehtiopicus.cens.utils.CensException;

@Service
public class ProgramaCensServiceImpl implements ProgramaCensService {

	private static final Logger logger = LoggerFactory.getLogger(ProgramaCensServiceImpl.class);
	
	@Autowired
	private AsignaturaCensService asignaturaCensService;
	
	@Autowired
	private FTPProgramaCensService ftpProgramaCensService;
	
	@Autowired
	private ProgramaCensRepository programaCensRepository;
	
	@Autowired
	private ProfesorCensService profesorCensService;
	
	@Autowired
	private FileCensService fileCensService;
	
	@Override
	@Transactional(rollbackFor={Exception.class,CensException.class})
	public Programa savePrograma(Programa p, MultipartFile file) throws CensException {
		if(p==null || p.getAsignatura().getId() == null || p.getProfesor().getId() == null){
			throw new CensException("El programa no puede ser nulo");
		}
		logger.info("Guardando programa ");
		Asignatura asignatura = asignaturaCensService.getAsignatura(p.getAsignatura().getId());
		p.setProfesor(profesorCensService.findById(p.getProfesor().getId()));
		p.setAsignatura(asignatura);
		p = validate(p);
		p = programaCensRepository.save(p);
		if(file!=null && p.getFileInfo()!=null){
			logger.info("iniciando ftp upload del programa");
			String filePath = ftpProgramaCensService.guardarPrograma(asignatura, file);
			logger.info("programa subido. ruta = "+filePath);
			p.getFileInfo().setFileLocation(MaterialDidacticoUbicacionType.FTP);
			p.getFileInfo().setFileLocationPath(filePath);			
			p.setFileInfo(fileCensService.updateFileInfo(p.getFileInfo()));
		}
		return p;
	}
	
	private Programa validate(Programa programa) throws CensException{
		Programa p = programaCensRepository.findByAsignatura(programa.getAsignatura());
		if(p!=null && (programa.getId()==null || !p.getId().equals(programa.getId()))){
			throw new CensException("Ya existe un programa para esta asignatura");
		}
		if(p!=null){
			if(p.getAsignatura().getId()!= programa.getAsignatura().getId()){
				throw new CensException("La asignatura no puede modificarse");
			}
			programa.setFileInfo(fileCensService.copyData(programa.getFileInfo(), p.getFileInfo()));		
		}
		return programa;
	}
	
}
