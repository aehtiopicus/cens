package com.aehtiopicus.cens.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aehtiopicus.cens.configuration.UrlConstant;
import com.aehtiopicus.cens.configuration.VistasConstant;
import com.aehtiopicus.cens.domain.Banco;
import com.aehtiopicus.cens.domain.MotivoBaja;
import com.aehtiopicus.cens.domain.ObraSocial;
import com.aehtiopicus.cens.domain.Prepaga;
import com.aehtiopicus.cens.dto.JqGridData;
import com.aehtiopicus.cens.dto.MotivoBajaDto;
import com.aehtiopicus.cens.dto.ParametroDto;
import com.aehtiopicus.cens.mapper.ParametrizacionMapper;
import com.aehtiopicus.cens.service.BancoService;
import com.aehtiopicus.cens.service.MotivoBajaService;
import com.aehtiopicus.cens.service.ObraSocialService;
import com.aehtiopicus.cens.service.PrepagaService;
import com.aehtiopicus.cens.util.Utils;

@Controller
public class ParametrizacionController extends AbstractController {
	
	private static final Logger logger = Logger.getLogger(ParametrizacionController.class);
	
    @Autowired
    protected ObraSocialService obraSocialService;
    
    @Autowired
    protected PrepagaService prepagaService;
    
    @Autowired
    protected BancoService bancoService;

    @Autowired
    protected MotivoBajaService motivoBajaService;
    
	public void setObraSocialService(ObraSocialService obraSocialService) {
		this.obraSocialService = obraSocialService;
	}

	public void setPrepagaService(PrepagaService prepagaService) {
		this.prepagaService = prepagaService;
	}

	public void setBancoService(BancoService bancoService) {
		this.bancoService = bancoService;
	}
    
	public void setMotivoBajaService(MotivoBajaService motivoBajaService) {
		this.motivoBajaService = motivoBajaService;
	}

	
	//obras sociales------------------------------------

	@RequestMapping(value = UrlConstant.OBRAS_SOCIALES_URL, method = RequestMethod.GET)
	public ModelAndView obrasSociales(Locale locale, Model model, Principal principal) {
		logger.info("Obras Sociales List Form.");
		ModelAndView mav = new ModelAndView(VistasConstant.OBRAS_SOCIALES_VIEW);

		return mav;
	}
	
	@RequestMapping(value = UrlConstant.OBRAS_SOCIALES_GRILLA_URL, method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> obrasSocialesGrilla(HttpServletRequest request, HttpServletResponse response) throws IOException {

		logger.info("obtener informacion para llenado de grilla obras sociales");

		Integer rows = Integer.parseInt(request.getParameter("rows"));
        Integer page = Integer.parseInt(request.getParameter("page"));
        String nombre = request.getParameter("nombre");
        
		List<ObraSocial> obrasSociales = obraSocialService.search(nombre, page, rows);
		
		int total = obraSocialService.getTotal(nombre);
		int pageTotal = Utils.getNumberOfPages(rows,total);
		int pageNumber = page;

		List<ParametroDto> obrasSocialesDto = ParametrizacionMapper.getParametrosDtoFromObrasSociales(obrasSociales);
		JqGridData<ParametroDto> gridData = new JqGridData<ParametroDto>(pageTotal, pageNumber, total, obrasSocialesDto);
		return gridData.getJsonObject();

	}	

	@RequestMapping(value = UrlConstant.OBRA_SOCIAL_URL, method = RequestMethod.GET)
	public ModelAndView obraSocialForm(Locale locale, Model model, Principal principal) {
		logger.info("ObraSocial Form.");

		ModelAndView mav = new ModelAndView(VistasConstant.OBRA_SOCIAL_VIEW);
		mav.addObject("parametroDto", new ParametroDto());

		return mav;
	}	

	@RequestMapping(value = UrlConstant.OBRA_SOCIAL_URL + "/{obraSocialId}", method = RequestMethod.GET)
	public ModelAndView obraSocialFormEdit(Locale locale, Model model, Principal principal, @PathVariable("obraSocialId") Long obraSocialId) {
		logger.info("ObraSocial Form Edit.");

		ObraSocial entity = obraSocialService.getObraSocialById(obraSocialId);
		
		ParametroDto dto = ParametrizacionMapper.getParametroDtoFromObraSocial(entity);

		ModelAndView mav = new ModelAndView(VistasConstant.OBRA_SOCIAL_VIEW);
		mav.addObject("parametroDto", dto);
		
		return mav;
	}	
	
	@RequestMapping(value = {UrlConstant.OBRA_SOCIAL_URL, UrlConstant.OBRA_SOCIAL_URL + "/{obraSocialId}"}, method = RequestMethod.POST)
	public ModelAndView obraSocialFormSubmit(Locale locale, Model model, Principal principal, @Valid ParametroDto dto, BindingResult result) {
		logger.info("ObraSocial Form Submit.");

		ModelAndView mav;
		
        //SI hay errores,  se muestran al usuario
		if(result.hasErrors()){
			mav = new ModelAndView(VistasConstant.OBRA_SOCIAL_VIEW);
			mav.addObject("parametroDto", dto);

	        return mav;
		}
		
		ObraSocial entity = ParametrizacionMapper.getObraSocialFromParametroDto(dto);
		entity = obraSocialService.saveObraSocial(entity);
		
		mav = new ModelAndView("redirect:" + UrlConstant.OBRAS_SOCIALES_URL);

		return mav;
	}	
	
	@RequestMapping(value = UrlConstant.OBRA_SOCIAL_DELETE_URL, method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> deleteObraSocial(@RequestParam("obraSocialId") Long obraSocialId, HttpServletRequest request) {
        logger.info("Eliminar obraSocial.");
        Map<String, Object> data  = new HashMap<String, Object>();
        
        boolean success = true;
        String message;
        
        if(obraSocialId != null) {
        	try {
            	success = obraSocialService.deleteObraSocial(obraSocialId);
            	message = "La obra social fue eliminada.";
        	}catch(Exception e) {
        		success = false;
        		message = "La obra social no se puede eliminar. Se encuentra asociado al menos a un empleado.";
        	}
        }else {
        	success = false;
        	message = "No se pudo eliminar la obra social.";
        }

        data.put("success", success);
        data.put("message",message);
        
        return data;
    }
	

	//prepagas------------------------------------

	@RequestMapping(value = UrlConstant.PREPAGAS_URL, method = RequestMethod.GET)
	public ModelAndView prepagas(Locale locale, Model model, Principal principal) {
		logger.info("Prepagas List Form.");
		ModelAndView mav = new ModelAndView(VistasConstant.PREPAGAS_VIEW);

		return mav;
	}
	
	@RequestMapping(value = UrlConstant.PREPAGAS_GRILLA_URL, method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> prepagasGrilla(HttpServletRequest request, HttpServletResponse response) throws IOException {

		logger.info("obtener informacion para llenado de grilla prepagas");

		Integer rows = Integer.parseInt(request.getParameter("rows"));
        Integer page = Integer.parseInt(request.getParameter("page"));
        String nombre = request.getParameter("nombre");
        
		List<Prepaga> prepagas = prepagaService.search(nombre, page, rows);
		
		int total = prepagaService.getTotal(nombre);
		int pageTotal = Utils.getNumberOfPages(rows,total);
		int pageNumber = page;

		List<ParametroDto> prepagasDto = ParametrizacionMapper.getParametrosDtoFromPrepagas(prepagas);
		JqGridData<ParametroDto> gridData = new JqGridData<ParametroDto>(pageTotal, pageNumber, total, prepagasDto);
		return gridData.getJsonObject();

	}	

	@RequestMapping(value = UrlConstant.PREPAGA_URL, method = RequestMethod.GET)
	public ModelAndView prepagaForm(Locale locale, Model model, Principal principal) {
		logger.info("Prepaga Form.");

		ModelAndView mav = new ModelAndView(VistasConstant.PREPAGA_VIEW);
		mav.addObject("parametroDto", new ParametroDto());

		return mav;
	}	

	@RequestMapping(value = UrlConstant.PREPAGA_URL + "/{prepagaId}", method = RequestMethod.GET)
	public ModelAndView prepagaFormEdit(Locale locale, Model model, Principal principal, @PathVariable("prepagaId") Long prepagaId) {
		logger.info("Prepaga Form Edit.");

		Prepaga entity = prepagaService.getPrepagaById(prepagaId);
		
		ParametroDto dto = ParametrizacionMapper.getParametroDtoFromPrepaga(entity);

		ModelAndView mav = new ModelAndView(VistasConstant.PREPAGA_VIEW);
		mav.addObject("parametroDto", dto);
		
		return mav;
	}	
	
	@RequestMapping(value = {UrlConstant.PREPAGA_URL, UrlConstant.PREPAGA_URL + "/{prepagaId}"}, method = RequestMethod.POST)
	public ModelAndView prepagaFormSubmit(Locale locale, Model model, Principal principal, @Valid ParametroDto dto, BindingResult result) {
		logger.info("Prepaga Form Submit.");

		ModelAndView mav;
		
        //SI hay errores,  se muestran al usuario
		if(result.hasErrors()){
			mav = new ModelAndView(VistasConstant.PREPAGA_VIEW);
			mav.addObject("parametroDto", dto);

	        return mav;
		}
		
		Prepaga entity = ParametrizacionMapper.getPrepagaFromParametroDto(dto);
		entity = prepagaService.save(entity);
		
		mav = new ModelAndView("redirect:" + UrlConstant.PREPAGAS_URL);

		return mav;
	}	
	
	@RequestMapping(value = UrlConstant.PREPAGA_DELETE_URL, method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> deletePrepaga(@RequestParam("prepagaId") Long prepagaId, HttpServletRequest request) {
        logger.info("Eliminar prepaga.");
        Map<String, Object> data  = new HashMap<String, Object>();
        
        boolean success = true;
        String message;
        
        if(prepagaId != null) {
        	try {
            	success = prepagaService.deletePrepaga(prepagaId);
            	message = "La prepaga fue eliminada.";
        	}catch(Exception e) {
        		success = false;
        		message = "La prepaga no se puede eliminar. Se encuentra asociada al menos a un empleado.";
        	}
        }else {
        	success = false;
        	message = "No se pudo eliminar la prepaga.";
        }

        data.put("success", success);
        data.put("message",message);
        
        return data;
    }
	
	//bancos------------------------------------

	@RequestMapping(value = UrlConstant.BANCOS_URL, method = RequestMethod.GET)
	public ModelAndView bancos(Locale locale, Model model, Principal principal) {
		logger.info("Bancos List Form.");
		ModelAndView mav = new ModelAndView(VistasConstant.BANCOS_VIEW);

		return mav;
	}
	
	@RequestMapping(value = UrlConstant.BANCOS_GRILLA_URL, method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> bancosGrilla(HttpServletRequest request, HttpServletResponse response) throws IOException {

		logger.info("obtener informacion para llenado de grilla bancos");

		Integer rows = Integer.parseInt(request.getParameter("rows"));
        Integer page = Integer.parseInt(request.getParameter("page"));
        String nombre = request.getParameter("nombre");
        
		List<Banco> bancos = bancoService.search(nombre, page, rows);
		
		int total = bancoService.getTotal(nombre);
		int pageTotal = Utils.getNumberOfPages(rows,total);
		int pageNumber = page;

		List<ParametroDto> bancosDto = ParametrizacionMapper.getParametrosDtoFromBancos(bancos);
		JqGridData<ParametroDto> gridData = new JqGridData<ParametroDto>(pageTotal, pageNumber, total, bancosDto);
		return gridData.getJsonObject();

	}	

	@RequestMapping(value = UrlConstant.BANCO_URL, method = RequestMethod.GET)
	public ModelAndView bancoForm(Locale locale, Model model, Principal principal) {
		logger.info("Banco Form.");

		ModelAndView mav = new ModelAndView(VistasConstant.BANCO_VIEW);
		mav.addObject("parametroDto", new ParametroDto());

		return mav;
	}	

	@RequestMapping(value = UrlConstant.BANCO_URL + "/{bancoId}", method = RequestMethod.GET)
	public ModelAndView bancoFormEdit(Locale locale, Model model, Principal principal, @PathVariable("bancoId") Long bancoId) {
		logger.info("Banco Form Edit.");

		Banco entity = bancoService.findById(bancoId);
		
		ParametroDto dto = ParametrizacionMapper.getParametroDtoFromBanco(entity);

		//El banco Galicia tiene un tratamiendo especial y no se puede editar ni eliminar del sistema
		if(dto.getParametro().equals("Galicia")){
			return new ModelAndView("redirect:" + UrlConstant.BANCOS_URL);
		}
		
		ModelAndView mav = new ModelAndView(VistasConstant.BANCO_VIEW);
		mav.addObject("parametroDto", dto);
		
		return mav;
	}	
	
	@RequestMapping(value = {UrlConstant.BANCO_URL, UrlConstant.BANCO_URL + "/{bancoId}"}, method = RequestMethod.POST)
	public ModelAndView bancoFormSubmit(Locale locale, Model model, Principal principal, @Valid ParametroDto dto, BindingResult result) {
		logger.info("Banco Form Submit.");

		ModelAndView mav;
		
		if(result.hasErrors()){
			mav = new ModelAndView(VistasConstant.BANCO_VIEW);
			mav.addObject("parametroDto", dto);

	        return mav;
		}
		
		Banco entity = ParametrizacionMapper.getBancoFromParametroDto(dto);
		entity = bancoService.save(entity);
		
		mav = new ModelAndView("redirect:" + UrlConstant.BANCOS_URL);

		return mav;
	}	
	
	@RequestMapping(value = UrlConstant.BANCO_DELETE_URL, method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> deleteBanco(@RequestParam("bancoId") Long bancoId, HttpServletRequest request) {
        logger.info("Eliminar banco.");
        Map<String, Object> data  = new HashMap<String, Object>();
        
        boolean success = true;
        String message;
        
        if(bancoId != null) {
        	
        	//El banco galicia tiene un tratamiendo especial en el sistema y no puede ser eliminado.
        	Banco b = bancoService.findById(bancoId);
        	if(b.getNombre().equals("Galicia")){
        		success = false;
        		message = "El banco Galicia no puede ser eliminado del sistema.";
                data.put("success", success);
                data.put("message",message);
                
                return data;
        	}
        	
        	try {
            	success = bancoService.deleteBanco(bancoId);
            	message = "El banco fue eliminado.";
        	}catch(Exception e) {
        		success = false;
        		message = "El banco no se puede eliminar. Se encuentra asociado al menos a un empleado.";
        	}
        }else {
        	success = false;
        	message = "No se pudo eliminar el banco.";
        }

        data.put("success", success);
        data.put("message",message);
        
        return data;
    }
	
	//motivo baja---------------------------------------------------------------------
	@RequestMapping(value = UrlConstant.MOTIVOS_BAJA_URL, method = RequestMethod.GET)
	public ModelAndView motivosBaja(Locale locale, Model model, Principal principal) {
		logger.info("Motivos baja List Form.");
		ModelAndView mav = new ModelAndView(VistasConstant.MOTIVOS_BAJA_VIEW);

		return mav;
	}
	
	@RequestMapping(value = UrlConstant.MOTIVOS_BAJA_GRILLA_URL, method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> motivosBajaGrilla(HttpServletRequest request, HttpServletResponse response) throws IOException {

		logger.info("obtener informacion para llenado de grilla motivosbaja");
    
		List<MotivoBaja> motivos = motivoBajaService.getMotivosBaja();
		
		int total = motivos.size();
		int pageTotal = 1;
		int pageNumber = 1;

		List<MotivoBajaDto> dtos = ParametrizacionMapper.getMotivoBajaDtoFromMotivosBaja(motivos);
		JqGridData<MotivoBajaDto> gridData = new JqGridData<MotivoBajaDto>(pageTotal, pageNumber, total, dtos);
		return gridData.getJsonObject();

	}	
	
	@RequestMapping(value = UrlConstant.MOTIVO_BAJA_URL, method = RequestMethod.GET)
	public ModelAndView motivoBajaForm(Locale locale, Model model, Principal principal) {
		logger.info("MotivoBaja Form.");

		ModelAndView mav = new ModelAndView(VistasConstant.MOTIVO_BAJA_VIEW);
		mav.addObject("motivoBajaDto", new MotivoBajaDto());

		return mav;
	}	

	@RequestMapping(value = UrlConstant.MOTIVO_BAJA_URL + "/{motivoBajaId}", method = RequestMethod.GET)
	public ModelAndView motivoBajaFormEdit(Locale locale, Model model, Principal principal, @PathVariable("motivoBajaId") Long motivoBajaId) {
		logger.info("MotivoBaja Form Edit.");

		MotivoBaja entity = motivoBajaService.getMotivoBajaById(motivoBajaId);
		
		MotivoBajaDto dto = ParametrizacionMapper.getMotivoBajaDtoFromMotivoBaja(entity);

		ModelAndView mav = new ModelAndView(VistasConstant.MOTIVO_BAJA_VIEW);
		mav.addObject("motivoBajaDto", dto);
		
		return mav;
	}	
	
	@RequestMapping(value = {UrlConstant.MOTIVO_BAJA_URL, UrlConstant.MOTIVO_BAJA_URL + "/{motivoBajaId}"}, method = RequestMethod.POST)
	public ModelAndView motivoBajaFormSubmit(Locale locale, Model model, Principal principal, @Valid MotivoBajaDto dto, BindingResult result) {
		
		logger.info("MotivoBaja Form Submit.");

		ModelAndView mav;
		
        //SI hay errores,  se muestran al usuario
		if(result.hasErrors()){
			mav = new ModelAndView(VistasConstant.MOTIVO_BAJA_VIEW);
			mav.addObject("motivoBajaDto", dto);

	        return mav;
		}
		
		MotivoBaja entity = ParametrizacionMapper.getMotivoBajaFromMotivoBajaDto(dto);
		entity = motivoBajaService.saveMotivoBaja(entity);
		
		mav = new ModelAndView("redirect:" + UrlConstant.MOTIVOS_BAJA_URL);

		return mav;
	}		
	
	@RequestMapping(value = UrlConstant.MOTIVO_BAJA_DELETE_URL, method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> deleteMotivoBaja(@RequestParam("motivoBajaId") Long motivoBajaId, HttpServletRequest request) {
        logger.info("Eliminar MotivoBaja.");
        Map<String, Object> data  = new HashMap<String, Object>();
        
        boolean success = true;
        String message;
        
        if(motivoBajaId != null) {
        	try {
            	success = motivoBajaService.deleteMotivoBaja(motivoBajaId);
            	message = "El Motivo de baja fue eliminado.";
        	}catch(Exception e) {
        		success = false;
        		message = "El Motivo de baja no se puede eliminar. Se encuentra asociado al menos a un empleado.";
        	}
        }else {
        	success = false;
        	message = "No se pudo eliminar el motivo de baja.";
        }

        data.put("success", success);
        data.put("message",message);
        
        return data;
    }	
	
	//--------------------------------------------------------------------------------
}
