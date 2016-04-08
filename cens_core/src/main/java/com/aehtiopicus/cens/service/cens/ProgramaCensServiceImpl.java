package com.aehtiopicus.cens.service.cens;

import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.aehtiopicus.cens.domain.entities.Asignatura;
import com.aehtiopicus.cens.domain.entities.FileCensInfo;
import com.aehtiopicus.cens.domain.entities.Programa;
import com.aehtiopicus.cens.enumeration.cens.EstadoRevisionType;
import com.aehtiopicus.cens.enumeration.cens.FileCensInfoType;
import com.aehtiopicus.cens.enumeration.cens.MaterialDidacticoUbicacionType;
import com.aehtiopicus.cens.enumeration.cens.PerfilTrabajadorCensType;
import com.aehtiopicus.cens.enumeration.cens.SocialType;
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

	@PersistenceContext
	private EntityManager em;

	@Override
	@Transactional(rollbackFor = { Exception.class, CensException.class })
	public Programa savePrograma(Programa p, MultipartFile file) throws CensException {
		if (p == null || p.getAsignatura().getId() == null || p.getProfesor().getId() == null) {
			throw new CensException("El programa no puede ser nulo");
		}
		logger.info("Guardando programa ");
		Asignatura asignatura = asignaturCensRepository.findOne(p.getAsignatura().getId());
		p.setProfesor(profesorCensRepository.findOne(p.getProfesor().getId()));
		p.setAsignatura(asignatura);
		p = validate(p);
		p = programaCensRepository.save(p);
		if (file != null) {
			p.setFileInfo(handleFtp(file, p));
			p.setEstadoRevisionType(EstadoRevisionType.LISTO);
			return programaCensRepository.save(p);
		} else {
			return p;
		}
	}

	private FileCensInfo handleFtp(MultipartFile file, Programa p) throws CensException {
		FileCensInfo fci = null;
		if (file != null) {

			String filePath = ftpProgramaCensService.getRutaPrograma(p.getAsignatura());
			String fileName = new Date().getTime() + file.getOriginalFilename();
			if (p.getFileInfo() != null) {
				fileCensService.deleteFileCensInfo(p.getFileInfo());
			}
			fci = fileCensService.createNewFileCensService(file, p.getProfesor().getId(),
					PerfilTrabajadorCensType.PROFESOR, filePath, fileName, MaterialDidacticoUbicacionType.FTP,
					FileCensInfoType.PROGRAMA);

			logger.info("iniciando ftp upload del programa");
			ftpProgramaCensService.guardarPrograma(file, (filePath + fileName));
			logger.info("programa subido. ruta = " + filePath);
		}
		return fci;
	}

	private Programa validate(Programa programa) throws CensException {
		Programa p = programaCensRepository.findByAsignatura(programa.getAsignatura());
		if (p != null && (programa.getId() == null || !p.getId().equals(programa.getId()))) {
			throw new CensException("Ya existe un programa para esta asignatura");
		}
		if (p != null && p.getFileInfo() != null) {
			if (CollectionUtils.isNotEmpty(p.getMaterialDidactico())) {
				if (p.getCantCartillas() > programa.getCantCartillas()
						&& p.getMaterialDidactico().size() > programa.getCantCartillas()) {
					throw new CensException(
							"La cantiad de cartillas asociadas al programa es mayor a la cantidad de cartillas indicadas.");
				}
			}
			programa.setFileInfo(p.getFileInfo());
			programa.setEstadoRevisionType(p.getEstadoRevisionType());
		}
		return programa;
	}

	@Override
	@Cacheable(value = "programaProfesor", key = "#id")
	public List<Programa> getProgramasForAsignatura(Long id) {
		return programaCensRepository.findProgramaByProfesor(id);
	}

	@Override
	public Programa findById(Long programaId) {
		return programaCensRepository.findOne(programaId);
	}

	@Override
	public void getArchivoAdjunto(String fileLocationPath, OutputStream os) throws CensException {
		ftpProgramaCensService.leerPrograma(fileLocationPath, os);

	}

	@Override
	@Transactional
	public void removePrograma(Programa p) throws CensException {
		if (!p.getEstadoRevisionType().equals(EstadoRevisionType.ACEPTADO)) {
			fileCensService.deleteFileCensInfo(p.getFileInfo());
			programaCensRepository.removeFileInfo(p, EstadoRevisionType.NUEVO);
		} else {
			throw new CensException(
					"No se puede eliminar el Programa did&aacute;ctico ya que fue ACEPTADO por asesor&iacute;a");
		}

	}

	@Override
	@Transactional(rollbackFor = CensException.class)
	public void fullRemovePrograma(Programa p) throws CensException {

		try {
			fileCensService.deleteFileCensInfo(p.getFileInfo());

			em.createNativeQuery("delete from cens_comentario_feed as ccomf  " + "where ccomf.comentariocensid in "
					+ "(select ccom.id from cens_material_didactico as cmd "
					+ "inner join cens_programa as cp on cmd.programa_id = cp.id and cp.id = :cpID "
					+ "inner join cens_comentario as ccom on (ccom.tipoid = cmd.id OR ccom.tipoid = cp.id))")
					.setParameter("cpID", p.getId()).executeUpdate();

			em.createNativeQuery("delete from cens_comentario as ccom  " + "where ccom.tipoid in "
					+ "(select cmd.id from cens_material_didactico as cmd "
					+ "inner join cens_programa as cp on cmd.programa_id = cp.id where cp.id = :cpID) ")
					.setParameter("cpID", p.getId()).executeUpdate();

			em.createNativeQuery("delete from cens_cambio_estado_feed as ccef  " + "where ccef.tipoid in "
					+ "(select cmd.id from cens_material_didactico as cmd "
					+ "inner join cens_programa as cp on cmd.programa_id = cp.id where cp.id = :cpID) ")
					.setParameter("cpID", p.getId()).executeUpdate();

			em.createNativeQuery("delete from cens_cambio_estado_feed as ccef  " + "where ccef.tipoid = :cpID ")
					.setParameter("cpID", p.getId()).executeUpdate();

			em.createNativeQuery("delete from cens_comentario as ccom  " + "where ccom.tipoid in "
					+ "(select cp.id from cens_programa as cp where cp.id = :cpID) ").setParameter("cpID", p.getId())
					.executeUpdate();

			em.createNativeQuery("delete from cens_material_didactico as cmd  " + "where cmd.programa_id = :cpID ")
					.setParameter("cpID", p.getId()).executeUpdate();

			em.createNativeQuery("delete from cens_social_postted_data as cspd  " + "where cspd.programa_id = :cpID ")
					.setParameter("cpID", p.getId()).executeUpdate();

			em.createNativeQuery("delete from cens_programa as cp  " + "where cp.id = :cpID ")
					.setParameter("cpID", p.getId()).executeUpdate();

		} catch (Exception e) {
			throw new CensException("Error al eliminar la información completa del programa", e);
		}
	}

	@Override
	// @Cacheable("programaAsesor")
	public List<Programa> getProgramas() {
		return programaCensRepository.findProgramaByAsignaturaVigente();
	}

	@Override
	@Transactional
	public void updateProgramaStatus(Programa programa, EstadoRevisionType type) {
		programaCensRepository.updateProgramaStatus(programa.getId(), type);

	}

	@Override
	public Programa getProgramasForAsignatura(Asignatura asignatura) {
		return programaCensRepository.findByAsignatura(asignatura);
	}

	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	@Override
	public void removeSocialShare(String path, Long programaId, String sessionId) throws CensException {

		RestTemplate rs = new RestTemplate();

		for (SocialType st : SocialType.values()) {
			UriComponentsBuilder builder = UriComponentsBuilder
					.fromHttpUrl(UriComponentsBuilder.fromUriString(path + "/api/social/oauth2").toUriString())
					.queryParam("provider", st).queryParam("comentarioTypeId", programaId);

			HttpHeaders requestHeaders = new HttpHeaders();
			requestHeaders.add("Cookie", "JSESSIONID=" + sessionId);

			try {
				ResponseEntity<Map> rssResponse = rs.exchange(builder.build().encode().toUri(), HttpMethod.DELETE,
						new HttpEntity(null, requestHeaders), Map.class);
				Map result = rssResponse.getBody();
			} catch (Exception e) {
				logger.info(((org.springframework.web.client.HttpClientErrorException)e).getResponseBodyAsString(),e);
			}
		}

	}

}

/*
 * String authURL = asignaturaId+"/j_spring_security_check";
 * MultiValueMap<String, String> map = new LinkedMultiValueMap<String,
 * String>(); map.add("j_username", "censadmin"); map.add("j_password",
 * "1234Qwer");
 * 
 * FormHttpMessageConverter formHttpMessageConverter = new
 * FormHttpMessageConverter();
 * 
 * HttpMessageConverter stringHttpMessageConverternew = new
 * StringHttpMessageConverter();
 * 
 * List<HttpMessageConverter<?>> messageConverters = new
 * LinkedList<HttpMessageConverter<?>>();
 * 
 * messageConverters.add(formHttpMessageConverter);
 * messageConverters.add(stringHttpMessageConverternew);
 * rs.setMessageConverters(messageConverters); HttpHeaders requestHeaders = new
 * HttpHeaders();
 * requestHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
 * 
 * HttpEntity<MultiValueMap> entity = new HttpEntity<MultiValueMap>(map,
 * requestHeaders);
 * 
 * ResponseEntity result = rs.exchange(authURL, HttpMethod.POST, entity,
 * String.class); HttpHeaders respHeaders = result.getHeaders();
 * System.out.println(respHeaders.toString());
 * 
 * System.out.println(result.getStatusCode());
 * 
 * String cookies = respHeaders.getFirst("Set-Cookie");
 */
