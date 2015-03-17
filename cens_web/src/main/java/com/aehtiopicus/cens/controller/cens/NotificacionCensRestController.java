package com.aehtiopicus.cens.controller.cens;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aehtiopicus.cens.configuration.UrlConstant;
import com.aehtiopicus.cens.service.cens.NotificacionCensService;
import com.aehtiopicus.cens.utils.CensException;

@Controller
public class NotificacionCensRestController extends AbstractRestController{
	
	@Autowired
	private NotificacionCensService notificacionCensService;

	@RequestMapping(value=UrlConstant.NOTIFICACION_USUARIO_MIEMBRO_REST, method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Map<String,Object> getNotificacioneForUser(@PathVariable(value="miembroId") Long miembroId) throws CensException{
		Map<String, Object> notificacionData = notificacionCensService.getNotificacionesForUser(miembroId);
		return notificacionData;
	}
	
	@Secured("ROLE_ASESOR")
	@RequestMapping(value=UrlConstant.NOTIFICACION_NO_LEIDAS_MIEMBRO_REST, method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Map<String,Object> getNotificacioneNoLeidas(@PathVariable(value="miembroId") Long miembroId) throws CensException{
		Map<String, Object> notificacionData = notificacionCensService.getNotificacionesUnReadForUser(miembroId);
		return notificacionData;
	}
}
