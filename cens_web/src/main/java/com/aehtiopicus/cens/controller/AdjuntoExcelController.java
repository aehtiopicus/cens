package com.aehtiopicus.cens.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.aehtiopicus.cens.configuration.UrlConstant;
import com.aehtiopicus.cens.configuration.VistasConstant;
import com.aehtiopicus.cens.dto.EmpleadoExcelDto;
import com.aehtiopicus.cens.service.ArchivoReaderService;
import com.aehtiopicus.cens.validator.EmpleadoExcelValidator;

@Controller
public class AdjuntoExcelController {

	private static final Logger logger = Logger.getLogger(AdjuntoExcelController.class);
	
	private ArchivoReaderService archivoService;
	private EmpleadoExcelValidator empleadoExcelValidator;
	protected ResourceBundle mensajes = ResourceBundle.getBundle("errores_es");
	
	@Autowired
	public void setEmpleadoExcelValidator(
			EmpleadoExcelValidator empleadoExcelValidator) {
		this.empleadoExcelValidator = empleadoExcelValidator;
	}

	@Autowired
	public void setArchivoService(ArchivoReaderService archivoService) {
		this.archivoService = archivoService;
	}

	@RequestMapping(value = UrlConstant.ADJUNTO_URL, method = RequestMethod.GET)
    public ModelAndView getAdjuntoExcelEmpleado(Locale locale, Model model, Principal principal) {

		logger.info("Empleado Adjuntar Excel");
		ModelAndView mdl = new ModelAndView(VistasConstant.EMPLEADO_EXCEL_VIEW);
		mdl.addObject("empleadoExcelDto", new EmpleadoExcelDto());
		return mdl; 

	}
	
	
	@RequestMapping(value = UrlConstant.ADJUNTO_URL, method = RequestMethod.POST)//@ModelAttribute("adjuntoExcelDto")
	public ModelAndView save(@Valid EmpleadoExcelDto uploadForm,Model map, HttpServletRequest request,BindingResult result)  throws IOException {
		logger.info("Cargar empleados a traves de excel");
		empleadoExcelValidator.validate(uploadForm, result);
		if(result.hasErrors()){
			ModelAndView mdl =  new ModelAndView(VistasConstant.EMPLEADO_EXCEL_VIEW);
			mdl.addObject("empleadoExcelDto", uploadForm);
			return mdl;
		}
		MultipartFile file = uploadForm.getArchivo();
		File arch = new File(file.getOriginalFilename());
		file.transferTo(arch);
		List<String> errores  = archivoService.readExcelFile(arch);
		Boolean error= false;
		if(!CollectionUtils.isEmpty(errores)){
			 error=armarLogErrores(errores);	
		}
		if(error){
			uploadForm.setError("Cargado");
		}
		arch.delete();
		if(errores.size() > 0){
			result.rejectValue( "archivo", "archivo.errores",mensajes.getString("ERR035"));
		}
		else{
			result.rejectValue( "archivo", "archivo.guardado",mensajes.getString("ERR034"));
			
		}
		ModelAndView mdl = new ModelAndView(VistasConstant.EMPLEADO_EXCEL_VIEW);
		mdl.addObject("empleadoExcelDto", uploadForm);	
		return mdl;
		
    }
	
	private boolean armarLogErrores(List<String> errores){
		// Validamos si existe el fichero
		String path = AdjuntoExcelController.class.getResource("/").getPath() + "../../adjuntos/errores.txt";
		path = path.replaceAll("%20", " ");
		try{
			  BufferedWriter bw = new BufferedWriter(new FileWriter(path,false));
			  for (int x=0;x<errores.size();x++){
			 	bw.write(errores.get(x)+"\n");
			  }
			  bw.close();
			  return true;
			} catch (IOException ioe){
				ioe.printStackTrace();
				return false;
			}
		}

}
