package com.aehtiopicus.cens.controller.cens;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aehtiopicus.cens.configuration.UrlConstant;
import com.aehtiopicus.cens.domain.entities.MiembroCens;
import com.aehtiopicus.cens.dto.cens.NotificacionConfigDto;
import com.aehtiopicus.cens.dto.cens.NotificacionDto;
import com.aehtiopicus.cens.dto.cens.RestSingleResponseDto;
import com.aehtiopicus.cens.enumeration.cens.ComentarioType;
import com.aehtiopicus.cens.enumeration.cens.NotificacionType;
import com.aehtiopicus.cens.enumeration.cens.PerfilTrabajadorCensType;
import com.aehtiopicus.cens.mapper.cens.NotificacionCensControllerMapper;
import com.aehtiopicus.cens.service.cens.NotificacionCensService;
import com.aehtiopicus.cens.utils.CensException;

@Controller
public class NotificacionCensRestController extends AbstractRestController{
	
	private static final Logger logger = LoggerFactory.getLogger(NotificacionCensRestController.class);
    private static final String NOTIFICATION_CHECK_SEC = "#{censProperties['notificacion_check_sec']}";
    
    @Value(NOTIFICATION_CHECK_SEC)
	private int notificationSecCheck;
    
	@Autowired
	private NotificacionCensControllerMapper notificacionCensMapper;
	
	@Autowired
	private NotificacionCensService notificacionCensService;
	
	

	@RequestMapping(value=UrlConstant.NOTIFICACION_USUARIO_MIEMBRO_REST, method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<NotificacionDto> getNotificacioneForUser(@PathVariable(value="miembroId") Long miembroId) throws CensException{
		NotificacionDto nDto = notificacionCensMapper.getDtoFromVO(notificacionCensService.getNotificacionesForUser(miembroId));
		logger.info("Getting notificacion data");
		List<NotificacionDto> resultList = new ArrayList<NotificacionDto>();
		
		resultList.add(nDto);
		if(nDto.getPerfilRol().getPerfilType().equals(PerfilTrabajadorCensType.ASESOR)){
			resultList.add(getNotificacioneNoLeidas(miembroId));
		}
		return resultList;		
	}
	
	@Secured("ROLE_ASESOR")
	@RequestMapping(value=UrlConstant.NOTIFICACION_NO_LEIDAS_MIEMBRO_REST, method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody NotificacionDto getNotificacioneNoLeidas(@PathVariable(value="miembroId") Long miembroId) throws CensException{
		logger.info("Getting notificacion data for a asesor");
		return notificacionCensMapper.getDtoFromVO(notificacionCensService.getNotificacionesUnReadForUser(miembroId));
	
	}
	@Secured("ROLE_ASESOR")
	@RequestMapping(value=UrlConstant.NOTIFICACION_NO_LEIDAS_IGNORAR_MIEMBRO_REST, method=RequestMethod.PUT, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody RestSingleResponseDto ignorarNotificacionNoLeida(@PathVariable(value="tipoId") Long tipoId, @PathVariable(value="tipoType") ComentarioType tipoType,@PathVariable(value="notificacionType") NotificacionType notificacionType) throws CensException{
		logger.info("Marcar como no ignoradas");
		int cantIgnorados = notificacionCensService.markFeedsAsIgnored(tipoId,tipoType,notificacionType);
		RestSingleResponseDto rsDto = new RestSingleResponseDto();
		rsDto.setId(new Long(cantIgnorados));
		rsDto.setMessage("Cantidad comentarios Ignorados");
		return rsDto;
	
	}
	
	 @RequestMapping(value={UrlConstant.NOTIFICACION_USUARIO_REST}, method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	    public @ResponseBody NotificacionConfigDto obtainNotificacionConfig(Principal principal){
	    	NotificacionConfigDto ncDto = new NotificacionConfigDto();
	    	ncDto.setUser(principal.getName());
	    	ncDto.setExpireSec(notificationSecCheck);
	    	MiembroCens mc = notificacionCensService.getMiembroByUsername(ncDto.getUser());
	    	ncDto.setMiembroId(mc.getId());	    	
	    	return ncDto;
	    }
}
