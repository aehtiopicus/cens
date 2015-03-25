package com.aehtiopicus.cens.controller.cens.mvc;

import java.security.Principal;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aehtiopicus.cens.configuration.UrlConstant;
import com.aehtiopicus.cens.controller.cens.enums.HomePageType;
import com.aehtiopicus.cens.domain.entities.PerfilRol;
import com.aehtiopicus.cens.dto.cens.NotificacionConfigDto;

/**
 * Handles requests for the application home page.
 */
@Controller
public class IndexController extends AbstractController{

    private static final Logger logger = LoggerFactory
            .getLogger(IndexController.class);
    
    private static final String NOTIFICATION_CHECK_SEC = "#{censProperties['notificacion_check_sec']}";
    
    @Value(NOTIFICATION_CHECK_SEC)
	private int notificationSecCheck;


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(Locale locale, Model model, Principal principal) {

        return new ModelAndView("login");
    }

    @RequestMapping(value="/loginfailed", method = RequestMethod.GET)
	public ModelAndView loginerror(Locale locale, Model model, Principal principal) {
    	ModelAndView mav = new ModelAndView("login");
		mav.addObject("error", "true");
		return mav;
	}
    
    @RequestMapping(value = {UrlConstant.MAIN_URL}, method = RequestMethod.GET)
    public ModelAndView start(Locale locale, Model model, Principal principal) {
        logger.info("Configuracion correcta.");
        
		PerfilRol higher = getUserInfo();
		HomePageType hpt = HomePageType.getHomePageTypeByRol(higher.getPerfilType());
		ModelAndView mav = new ModelAndView(hpt!=null ? hpt.getHomePage() : HomePageType.DEFAULT.getHomePage());
		mav.addObject(higher.getPerfilType().name().toLowerCase()+"Id",higher.getPerfilId());
        return mav;
    }
	
    @RequestMapping(value={UrlConstant.NOTIFICACION_MVC_URL}, method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody NotificacionConfigDto obtainNotificacionConfig(Principal principal){
    	NotificacionConfigDto ncDto = new NotificacionConfigDto();
    	ncDto.setUser(principal.getName());
    	ncDto.setExpireSec(notificationSecCheck);
    	return ncDto;
    }
       
  }
