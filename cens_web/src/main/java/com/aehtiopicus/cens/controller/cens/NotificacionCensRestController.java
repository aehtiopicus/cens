package com.aehtiopicus.cens.controller.cens;

import java.security.Principal;

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
import com.aehtiopicus.cens.dto.cens.NotificacionConfigDto;
import com.aehtiopicus.cens.dto.cens.NotificacionDto;
import com.aehtiopicus.cens.mapper.cens.NotificacionCensControllerMapper;
import com.aehtiopicus.cens.service.cens.NotificacionCensService;
import com.aehtiopicus.cens.utils.CensException;

@Controller
public class NotificacionCensRestController extends AbstractRestController{
	
    private static final String NOTIFICATION_CHECK_SEC = "#{censProperties['notificacion_check_sec']}";
    
    @Value(NOTIFICATION_CHECK_SEC)
	private int notificationSecCheck;
    
	@Autowired
	private NotificacionCensControllerMapper notificacionCensMapper;
	
	@Autowired
	private NotificacionCensService notificacionCensService;
	
	

	@RequestMapping(value=UrlConstant.NOTIFICACION_USUARIO_MIEMBRO_REST, method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody NotificacionDto getNotificacioneForUser(@PathVariable(value="miembroId") Long miembroId) throws CensException{
		NotificacionDto nDto = notificacionCensMapper.getDtoFromVO(notificacionCensService.getNotificacionesForUser(miembroId));
		return nDto;		
	}
	
	@Secured("ROLE_ASESOR")
	@RequestMapping(value=UrlConstant.NOTIFICACION_NO_LEIDAS_MIEMBRO_REST, method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody NotificacionDto getNotificacioneNoLeidas(@PathVariable(value="miembroId") Long miembroId) throws CensException{
		return notificacionCensMapper.getDtoFromVO(notificacionCensService.getNotificacionesUnReadForUser(miembroId));
	
	}
	
	 @RequestMapping(value={UrlConstant.NOTIFICACION_USUARIO_REST}, method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	    public @ResponseBody NotificacionConfigDto obtainNotificacionConfig(Principal principal){
	    	NotificacionConfigDto ncDto = new NotificacionConfigDto();
	    	ncDto.setUser(principal.getName());
	    	ncDto.setExpireSec(notificationSecCheck);
	    	ncDto.setMiembroId(notificacionCensService.getMiembroByUsername(ncDto.getUser()).getId());
	    	return ncDto;
	    }
}
