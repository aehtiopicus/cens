package com.aehtiopicus.cens.service.cens;

import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.aehtiopicus.cens.domain.entities.Asignatura;
import com.aehtiopicus.cens.domain.entities.FileCensInfo;
import com.aehtiopicus.cens.domain.entities.Programa;
import com.aehtiopicus.cens.enumeration.cens.EstadoRevisionType;
import com.aehtiopicus.cens.enumeration.cens.FileCensInfoType;
import com.aehtiopicus.cens.enumeration.cens.MaterialDidacticoUbicacionType;
import com.aehtiopicus.cens.enumeration.cens.PerfilTrabajadorCensType;
import com.aehtiopicus.cens.repository.cens.AsignaturCensRepository;
import com.aehtiopicus.cens.repository.cens.ProfesorCensRepository;
import com.aehtiopicus.cens.repository.cens.ProgramaCensRepository;
import com.aehtiopicus.cens.service.cens.ftp.FTPProgramaCensService;
import com.aehtiopicus.cens.utils.CensException;

@Service
public class ProgramaCensServiceImpl implements ProgramaCensService {

	private static final Logger logger = LoggerFactory.getLogger(ProgramaCensServiceImpl.class);
	
	@Autowired
	private AsignaturCensRepository asignaturCensRepository;
	
	@Autowired
	private FTPProgramaCensService ftpProgramaCensService;
	
	@Autowired
	private ProgramaCensRepository programaCensRepository;
	
	@Autowired
	private ProfesorCensRepository profesorCensRepository;
	
	@Autowired
	private FileCensService fileCensService;
	
	@Override
	@Transactional(rollbackFor={Exception.class,CensException.class})
	public Programa savePrograma(Programa p, MultipartFile file) throws CensException {
		if(p==null || p.getAsignatura().getId() == null || p.getProfesor().getId() == null){
			throw new CensException("El programa no puede ser nulo");
		}
		logger.info("Guardando programa ");
		Asignatura asignatura = asignaturCensRepository.findOne(p.getAsignatura().getId());
		p.setProfesor(profesorCensRepository.findOne(p.getProfesor().getId()));
		p.setAsignatura(asignatura);
		p = validate(p);		
		p = programaCensRepository.save(p);
		if(file!=null){
			p.setFileInfo(handleFtp(file, p));	
			p.setEstadoRevisionType(EstadoRevisionType.LISTO);
			return programaCensRepository.save(p);
		}else{
			return p;
		}
	}
	
	private FileCensInfo handleFtp(MultipartFile file, Programa p) throws CensException{
		FileCensInfo fci = null;
		if(file!=null ){
			
			String filePath =ftpProgramaCensService.getRutaPrograma(p.getAsignatura());
			String fileName = new Date().getTime()+file.getOriginalFilename();
			if(p.getFileInfo()!=null){
				fileCensService.deleteFileCensInfo(p.getFileInfo());
			}
			fci = fileCensService.createNewFileCensService(file,p.getProfesor().getId(),PerfilTrabajadorCensType.PROFESOR,filePath,fileName, MaterialDidacticoUbicacionType.FTP,FileCensInfoType.PROGRAMA);
			
			logger.info("iniciando ftp upload del programa");
			ftpProgramaCensService.guardarPrograma(file,(filePath+fileName));
			logger.info("programa subido. ruta = "+filePath);						
		}
		return fci;
	}
	private Programa validate(Programa programa) throws CensException{
		Programa p = programaCensRepository.findByAsignatura(programa.getAsignatura());
		if(p!=null && (programa.getId()==null || !p.getId().equals(programa.getId()))){
			throw new CensException("Ya existe un programa para esta asignatura");
		}
		if(p!=null && p.getFileInfo()!=null){
			programa.setFileInfo(p.getFileInfo());
			programa.setEstadoRevisionType(p.getEstadoRevisionType());
		}
		return programa;
	}

	@Override
	public List<Programa> getProgramasForAsignatura(Long id) {
		return programaCensRepository.findProgramaByProfesor(id);
	}

	@Override
	public Programa findById(Long programaId) {
		return programaCensRepository.findOne(programaId);
	}

	@Override
	public void getArchivoAdjunto(String fileLocationPath, OutputStream os)throws CensException {
		ftpProgramaCensService.leerPrograma(fileLocationPath,os);
		
	}

	@Override
	@Transactional
	public void removePrograma(Long programaId) throws CensException {
		Programa p = findById(programaId);
		fileCensService.deleteFileCensInfo(p.getFileInfo());
		programaCensRepository.removeFileInfo(p,EstadoRevisionType.NUEVO);
		
	}

	@Override
	public List<Programa> getProgramas() {
		return programaCensRepository.findProgramaByAsignaturaVigente();
	}

	@Override
	@Transactional
	public void updateProgramaStatus(Long programaId, EstadoRevisionType type) {
		programaCensRepository.updateProgramaStatus(programaId,type);
		
	}
	
}
