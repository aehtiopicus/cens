package com.aehtiopicus.cens.controller;

import java.security.Principal;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.aehtiopicus.cens.service.InitLoadService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class InitLoadController {

    private static final Logger logger = Logger.getLogger(InitLoadController.class);
    
    @Autowired
    protected InitLoadService initLoadService;

	public void setInitLoadService(InitLoadService initLoadService) {
		this.initLoadService = initLoadService;
	}



	@RequestMapping(value = "/inicializar/basededatos", method = RequestMethod.GET)
    public ModelAndView start(Locale locale, Model model, Principal principal) {
        logger.info("Creando usuario.");

        initLoadService.inicializarDataBase();
        
        return new ModelAndView("inicializado");
    }
	
	//@RequestMapping(value = "/inicializar/datosprueba", method = RequestMethod.GET)
    public ModelAndView cargarDatosPrueba(Locale locale, Model model, Principal principal) {
        logger.info("Creando datos de prueba .");

        initLoadService.cargarDatosPrueba();
        
        return new ModelAndView("inicializado");
    }
	
    @RequestMapping(value = "/inicializar/updatehistoriales", method = RequestMethod.GET)
    public ModelAndView updateHistorialEmpleados(Locale locale, Model model, Principal principal) {
    	logger.info("updateHistorialEmpleados()");

        initLoadService.updateHistorialEmpleados();
        
        return new ModelAndView("inicializado");
    }
  }
