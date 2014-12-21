
package com.aehtiopicus.cens.controller;

import java.security.Principal;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aehtiopicus.cens.configuration.UrlConstant;
import com.aehtiopicus.cens.configuration.VistasConstant;
import com.aehtiopicus.cens.domain.Empleado;
import com.aehtiopicus.cens.dto.HistorialNominaDto;
import com.aehtiopicus.cens.dto.JqGridData;
import com.aehtiopicus.cens.helper.EmpleadoSecurityHelper;
import com.aehtiopicus.cens.mapper.EmpleadoMapper;
import com.aehtiopicus.cens.service.EmpleadoService;
import com.aehtiopicus.cens.util.Utils;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HistorialNominaController extends AbstractController{

	private static final Logger logger = Logger.getLogger(HistorialNominaController.class);

    @Autowired
    protected EmpleadoService empleadoService;
      
    @Autowired
    protected EmpleadoSecurityHelper empleadoSecurityHelper;
    

	public void setEmpleadoService(EmpleadoService empleadoService) {
		this.empleadoService = empleadoService;
	}

	public void setEmpleadoSecurityHelper(
			EmpleadoSecurityHelper empleadoSecurityHelper) {
		this.empleadoSecurityHelper = empleadoSecurityHelper;
	}


	@RequestMapping(value = UrlConstant.EMPLEADO_HISTORIAL_GRILLA_URL , method = RequestMethod.GET)
    public  @ResponseBody Map<String, Object>    getHistorialNomina(HttpServletRequest request,HttpServletResponse response) {
		logger.info("obtener informacion para llenado de grilla historial");
		Long empleadoId = Long.parseLong(request.getParameter("empleadoId"));
		logger.info("Empleado Historial grilla");
		
		Empleado empleado = empleadoService.getEmpleadoByEmpleadoId(empleadoId);
		
		if(!empleadoSecurityHelper.elUsuarioTieneAcceso(request, empleado, EmpleadoSecurityHelper.ACCION_HISTORIAL)) {
			return Utils.getUnauthorizedActionMap();
		}
		
        List<HistorialNominaDto> historialDto = EmpleadoMapper.getHistorialDtoFromEmpleadoo(empleado);
        
        ModelAndView mav = new ModelAndView(VistasConstant.HISTORIAL_VIEW);
      
        mav.addObject("historialNominaDto", historialDto);
        JqGridData<HistorialNominaDto> gridData = new JqGridData<HistorialNominaDto>(1, 1,historialDto.size() , historialDto);
        return gridData.getJsonObject();
    }	
	
	@RequestMapping(value = UrlConstant.EMPLEADO_HISTORIAL_URL + "/{empleadoId}", method = RequestMethod.GET)
    public ModelAndView getGrillaNomina(HttpServletRequest request, Locale locale, Model model, Principal principal, @PathVariable("empleadoId") Long empleadoId) {

		logger.info("Empleado Historial");
		
		Empleado empleado = empleadoService.getEmpleadoByEmpleadoId(empleadoId);
		if(!empleadoSecurityHelper.elUsuarioTieneAcceso(request, empleado, EmpleadoSecurityHelper.ACCION_HISTORIAL)) {
			return Utils.getUnauthorizedActionModelAndView();
		}
		 
		ModelAndView mav = new ModelAndView(VistasConstant.HISTORIAL_VIEW);
		 mav.addObject("empleado",empleado.getApellidos()+", "+empleado.getNombres());
        mav.addObject("empleadoId",empleadoId);
             
        return mav;
    }	
	
	
}
