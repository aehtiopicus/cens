package com.aehtiopicus.cens.controller.cens.mvc;


import java.security.Principal;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.aehtiopicus.cens.configuration.UrlConstant;
import com.aehtiopicus.cens.configuration.VistasConstant;
import com.aehtiopicus.cens.domain.Usuario;
import com.aehtiopicus.cens.dto.ResetPasswordDto;
import com.aehtiopicus.cens.mapper.UsuarioMapper;
import com.aehtiopicus.cens.service.cens.UsuarioCensService;

@Controller
public class UsuarioCensController extends AbstractController {

	private static final Logger log = LoggerFactory.getLogger(UsuarioCensController.class);
	private static final String DEFAUL_PASSWORD = "#{censProperties['password']}";
	
	@Autowired
	private UsuarioCensService usuarioCensService;	
	
	@Value(DEFAUL_PASSWORD)
	private String password;
		
	@RequestMapping(value = UrlConstant.RESET_PASSWORD_MVC + "/{usuarioId}", method = RequestMethod.GET)
    public ModelAndView resetPasswordForm(Locale locale, Model model, Principal principal, @PathVariable("usuarioId") Long usuarioId) {

		log.info("Reset password");		
//		Usuario usuario = usuarioService.getById(usuarioId);		
//        ResetPasswordDto dto = UsuarioMapper.getResetPasswordDtoFromUsuario(usuario);
        
        ModelAndView mav = new ModelAndView(VistasConstant.RESETEAR_PASSWORD_VIEW);
//        mav.addObject("resetPasswordDto", dto);
        
        return mav;
    }
}
