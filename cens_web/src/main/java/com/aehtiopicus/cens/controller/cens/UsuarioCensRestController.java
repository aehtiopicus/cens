package com.aehtiopicus.cens.controller.cens;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.aehtiopicus.cens.configuration.UrlConstant;
import com.aehtiopicus.cens.controller.cens.mvc.UsuarioCensController;
import com.aehtiopicus.cens.controller.cens.validator.UsuarioCensValidator;
import com.aehtiopicus.cens.domain.entities.Usuarios;
import com.aehtiopicus.cens.dto.cens.PasswordChangeDto;
import com.aehtiopicus.cens.dto.cens.RestSingleResponseDto;
import com.aehtiopicus.cens.service.cens.UsuarioCensService;

@Controller
public class UsuarioCensRestController extends AbstractRestController{

	private static final Logger log = LoggerFactory.getLogger(UsuarioCensController.class);
	private static final String DEFAUL_PASSWORD = "#{censProperties['password']}";
	
	@Autowired
	private UsuarioCensService usuarioCensService;	
	
	@Autowired
	private UsuarioCensValidator validator;
	
	@Value(DEFAUL_PASSWORD)
	private String password;
		
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = UrlConstant.USUARIO_CENS_REST_PASSWORD, method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody RestSingleResponseDto resetPasswordForm( @PathVariable("id") Long usuarioId) throws Exception{

		log.info("Reset password");	
		usuarioCensService.resetPassword(usuarioId,password);
		RestSingleResponseDto dto = new RestSingleResponseDto();
		dto.setId(usuarioId);
		dto.setMessage("Contrase&ntilde;a actualizada por defecto");
		return dto;
    }
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = UrlConstant.USUARIO_CENS_REST_CHANGE_PASSWORD, method = RequestMethod.PUT, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody RestSingleResponseDto changePassword(@PathVariable("id") Long usuario, @RequestBody PasswordChangeDto passChangeDto) throws Exception{
		Usuarios user = usuarioCensService.findUsuarioById(usuario);
		validator.validateChangePass(user, passChangeDto);
		usuarioCensService.saveUsuario(user);
		RestSingleResponseDto dto = new RestSingleResponseDto();
		dto.setId(usuario);
		dto.setMessage("Contrase&ntilde;a actualizada");
		return dto;
	}
}
