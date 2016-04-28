package com.aehtiopicus.cens.service.cens;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.aehtiopicus.cens.domain.entities.FileCensInfo;
import com.aehtiopicus.cens.domain.entities.MaterialDidactico;
import com.aehtiopicus.cens.domain.entities.Programa;
import com.aehtiopicus.cens.domain.entities.RestRequest;
import com.aehtiopicus.cens.enumeration.cens.DivisionPeriodoType;
import com.aehtiopicus.cens.enumeration.cens.EstadoRevisionType;
import com.aehtiopicus.cens.enumeration.cens.FileCensInfoType;
import com.aehtiopicus.cens.enumeration.cens.MaterialDidacticoUbicacionType;
import com.aehtiopicus.cens.enumeration.cens.PerfilTrabajadorCensType;
import com.aehtiopicus.cens.repository.cens.MaterialDidacticoCensRepository;
import com.aehtiopicus.cens.repository.cens.ProfesorCensRepository;
import com.aehtiopicus.cens.service.cens.ftp.FTPMaterialDidacticoCensService;
import com.aehtiopicus.cens.specification.cens.MaterialDidacticoSpecification;
import com.aehtiopicus.cens.utils.CensException;
import com.aehtiopicus.cens.utils.Utils;

@Service
public class MaterialDidacticoCensServiceImpl implements MaterialDidacticoCensService{
	private static final Logger logger = LoggerFactory.getLogger(MaterialDidacticoCensServiceImpl.class);

	private static final String DIVISION_PERIODO ="#{censProperties['division_periodo']}";
	
	@Value(DIVISION_PERIODO)
	private String divisionPeriodo;
	
	@Autowired
	private MaterialDidacticoCensRepository materialDidacticoCensRepository;		
			
	@Autowired
	private ProgramaCensService programaCensServiceImpl;
	
	@Autowired
	private ProfesorCensRepository profesorCensRepository;
	
	@Autowired
	private FileCensService fileCensService;
	
	@Autowired
	private FTPMaterialDidacticoCensService ftpMaterialDidacticoCensService;
	
	@Override
	@Transactional
	public List<MaterialDidactico> listMaterialDidactico(RestRequest restRequest) {		
		Page<MaterialDidactico> requestedPage = null;
		 if(restRequest.getPage() > 0){
			 restRequest.setPage(restRequest.getPage() - 1);
		 }
		 Specifications<MaterialDidactico> mdSpecification = Specifications.where(MaterialDidacticoSpecification.programaEquals(Long.valueOf(restRequest.getFilters().get("programaId")))); 
		requestedPage = materialDidacticoCensRepository.findAll(mdSpecification,Utils.constructPageSpecification(restRequest.getPage(),restRequest.getRow(),sortByNroAsc()));
		return requestedPage.getContent();
		 
	}

	
	/**
     * Retorna  Sort object que ordena material acorde al number asociado 
     * @return
     */
    public  Sort sortByNroAsc() {
        return new Sort(Sort.Direction.ASC, "nro");
    }

	@Override
	public long getTotalMaterial(RestRequest restRequest) {
		logger.info("obteniendo numero de registros de cartillas");
    	long cantUsers = 0;
    	Specifications<MaterialDidactico> mdSpecification = Specifications.where(MaterialDidacticoSpecification.programaEquals(Long.valueOf(restRequest.getFilters().get("programaId"))));
   		cantUsers = materialDidacticoCensRepository.count(mdSpecification);				   	 
    	return (long) Math.ceil(cantUsers);
	}


	@Override
	public List<DivisionPeriodoType> listDivisionPeriodo() {
		List<DivisionPeriodoType> returnList = new ArrayList<DivisionPeriodoType>();
		if(StringUtils.isNotEmpty(divisionPeriodo)){
			for(DivisionPeriodoType dpt : DivisionPeriodoType.values()){
				if(dpt.getPeriodoBase().equals(divisionPeriodo.toUpperCase())){
					returnList.add(dpt);
				}
			}
		}
		return returnList;
		
	}


	@Override
	@Transactional(rollbackFor={Exception.class,CensException.class})
	public MaterialDidactico saveMaterialDidactico(MaterialDidactico md,
			MultipartFile file) throws CensException {
		if(md==null || md.getPrograma().getId() == null || md.getProfesor().getId() == null){
			throw new CensException("El material did&aacute;ctico no puede ser nulo");
		}
		logger.info("Guardando cartilla ");
		Programa programa = programaCensServiceImpl.findById(md.getPrograma().getId());
		md.setProfesor(profesorCensRepository.findOne(md.getProfesor().getId()));
		md.setPrograma(programa);
		md = validate(md);		
		md = materialDidacticoCensRepository.save(md);
		if(file!=null){
			md.setFileInfo(handleFtp(file, md));	
			md.setEstadoRevisionType(EstadoRevisionType.LISTO);
			md.getDocumentoModificado().setFechaCambioEstado(new Date());
			return materialDidacticoCensRepository.save(md);
		}else{
			return md;
		}
	}
	
	private FileCensInfo handleFtp(MultipartFile file, MaterialDidactico md) throws CensException{
		FileCensInfo fci = null;
		if(file!=null ){
			
			String filePath =ftpMaterialDidacticoCensService.getRutaMaterialDidactico(md.getPrograma().getAsignatura());
			String fileName = new Date().getTime()+file.getOriginalFilename();
			if(md.getFileInfo()!=null){
				fileCensService.deleteFileCensInfo(md.getFileInfo());
			}
			fci = fileCensService.createNewFileCensService(file,md.getProfesor().getId(),PerfilTrabajadorCensType.PROFESOR,filePath,fileName, MaterialDidacticoUbicacionType.FTP,FileCensInfoType.MATERIAL);
			
			logger.info("iniciando ftp upload de la cartilla");
			ftpMaterialDidacticoCensService.guardarMaterialDidactico(file,(filePath+fileName));
			logger.info("cartilla subida. ruta = "+filePath);						
		}
		return fci;
	}
	private MaterialDidactico validate(MaterialDidactico materialDidactico) throws CensException{
		
		Specifications<MaterialDidactico> mdSpecification = Specifications.where(MaterialDidacticoSpecification.programaEquals(materialDidactico.getPrograma().getId()));
		long cantidad = materialDidacticoCensRepository.count(mdSpecification);		
		MaterialDidactico md = materialDidacticoCensRepository.findByProgramaAndNombre(materialDidactico.getPrograma(),materialDidactico.getNombre());
		if(md!=null && (materialDidactico.getId()==null || !md.getId().equals(materialDidactico.getId()))){
			throw new CensException("Ya existe material did&aacute;ctico para este programa","nombre","Nombre duplicado");
		}
//		materialDidactico.setNro(md!=null ? md.getNro() :(int)cantidad+1);
		if(md!=null && md.getFileInfo()!=null){
			materialDidactico.setFileInfo(md.getFileInfo());
			materialDidactico.setEstadoRevisionType(md.getEstadoRevisionType());
		}
		if(materialDidactico.getId()==null && cantidad == materialDidactico.getPrograma().getCantCartillas()){
			throw new CensException("Cantidad de m&aacute;xima de cartillas superada");
		}
		
		if(md == null){
			materialDidactico.getDocumentoModificado().setFechaCambioEstado(new Date());
		}
		return materialDidactico;
	}


	@Override
	public MaterialDidactico findById(Long materialId) {
		return materialDidacticoCensRepository.findOne(materialId);
	}


	@Override
	public void getArchivoAdjunto(String fileLocationPath, OutputStream os)throws CensException {
		ftpMaterialDidacticoCensService.leerMaterialDidactico(fileLocationPath, os);
		
	}


	@Override
	@Transactional
	public void removeMaterialDidactico(MaterialDidactico material) {
		fileCensService.deleteFileCensInfo(material.getFileInfo());
		materialDidacticoCensRepository.removeFileInfo(material,EstadoRevisionType.NUEVO,new Date());
		
	}


	@Override
	@Transactional
	public void updateMaterialDidacticoStatus(MaterialDidactico material,
			EstadoRevisionType estadoRevisionType) {
		materialDidacticoCensRepository.updateMaterialDidacticoStatus(material.getId(),estadoRevisionType,new Date());
		
	}


	@Override
	public void removeMaterialDidacticoCompleto(MaterialDidactico material) throws CensException{
	
		if(!material.getEstadoRevisionType().equals(EstadoRevisionType.ACEPTADO)){
			fileCensService.deleteFileCensInfo(material.getFileInfo());
			materialDidacticoCensRepository.delete(material);
		}else{
			throw new CensException("No se puede eliminar el Material did&aacute;ctico ya que fue ACEPTADO por asesor&iacute;a");
		}
		
	}
}
