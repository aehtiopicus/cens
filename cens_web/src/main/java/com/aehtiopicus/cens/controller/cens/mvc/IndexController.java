package com.aehtiopicus.cens.controller.cens.mvc;

import java.security.Principal;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.aehtiopicus.cens.configuration.UrlConstant;
import com.aehtiopicus.cens.controller.cens.enums.HomePageType;
import com.aehtiopicus.cens.domain.entities.PerfilRol;

/**
 * Handles requests for the application home page.
 */
@Controller
public class IndexController extends AbstractController{

    private static final Logger logger = LoggerFactory
            .getLogger(IndexController.class);
    

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
    public ModelAndView start(Locale locale, Model model, Principal principal) throws Exception {
        logger.info("Configuracion correcta.");
        
		PerfilRol higher = getUserInfo();
		HomePageType hpt = HomePageType.getHomePageTypeByRol(higher.getPerfilType());
		ModelAndView mav = new ModelAndView(hpt!=null ? hpt.getHomePage() : HomePageType.DEFAULT.getHomePage());
		mav.addObject(higher.getPerfilType().name().toLowerCase()+"Id",higher.getPerfilId());
		mav.addObject("userId",getUsuarioId());
        return mav;
    }
	
   
       
  }
