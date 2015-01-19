package com.aehtiopicus.cens.controller.cens.mvc;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aehtiopicus.cens.configuration.UrlConstant;
import com.aehtiopicus.cens.configuration.VistasConstant;
import com.aehtiopicus.cens.dto.InformeDto;
import com.aehtiopicus.cens.dto.InformesDto;

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
    
    @RequestMapping(value = UrlConstant.MAIN_URL, method = RequestMethod.GET)
    public ModelAndView start(Locale locale, Model model, Principal principal) {
        logger.info("Configuracion correcta.");

        return new ModelAndView("main");
    }
	
    @RequestMapping(value = "/informes", method = RequestMethod.GET)
    public @ResponseBody List<InformeDto> getData() {
	  logger.info("Recuperando informacion para llenar grilla excel :) ");
	  List<InformeDto> informes = new ArrayList<InformeDto>();
	  for(int i=0; i<15; i++){
		  InformeDto informe = new InformeDto();
		  //informe.setId(String.valueOf(i));
		  informe.setDepartamento("Departamento"+i);
		  informe.setDireccion("Direccion"+i);
		  informe.setEmpleado("Empleado"+i);
		  informe.setEmpresa("Empresa"+i);
		  informes.add(informe);
	  }
     return informes;
    }
    
    @RequestMapping(value = "/save", method = RequestMethod.POST,headers = { "content-type=application/json" })
    public  @ResponseBody InformesDto  save(@RequestBody InformesDto informes) {
	  logger.info("guardando grillas :) ");
	  return informes;
   
    }
    
    
   

    @RequestMapping(value = UrlConstant.UNAUTHORIZED_URL, method = RequestMethod.GET)
    public ModelAndView accesoRestringido(Locale locale, Model model, Principal principal) {
        logger.info("accion no permitida.");

        return new ModelAndView(VistasConstant.UNAUTHORIZED_VIEW);
    }
    @RequestMapping(value = "/grilla", method = RequestMethod.GET)
    public ModelAndView showgrilla(Locale locale, Model model, Principal principal) {
        logger.info("Configuracion correcta.");

        return new ModelAndView("excel");
    }

  }
