/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.midasconsultores.catalogodefiltros.security;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import ar.com.midasconsultores.catalogodefiltros.configuration.HardwareInformation;
import ar.com.midasconsultores.catalogodefiltros.domain.Users;
import ar.com.midasconsultores.catalogodefiltros.repository.ClienteListaRepository;
import ar.com.midasconsultores.catalogodefiltros.repository.PrecioBaseRepository;
import ar.com.midasconsultores.catalogodefiltros.repository.UsuariosRepository;
import ar.com.midasconsultores.catalogodefiltros.service.UsuarioService;
import ar.com.midasconsultores.catalogodefiltros.validator.CredencialesValidator;

/**
 * 
 * @author bsasschetti
 */
public class CustomAuthenticationProvider implements AuthenticationProvider {

	private static final String ROLE_USER = "ROLE_USER";

	private static final String BAD_CREDENTIALS_MESSAGE = "Usuario deshabilitado. Contacte al Administrador del sistema.";

	@Autowired
	PrecioBaseRepository precioBaseRepository;

	@Autowired
	UsuariosRepository usuariosRepository;

	@Autowired
	ClienteListaRepository clienteListaRepository;

	@Autowired
	UsuarioService usuarioService;

	@Autowired
	HardwareInformation hardwareInformation;

	@Autowired
	CredencialesValidator validator;

	public static final String DEFAULT_USER_NAME = "appuser";

	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {

		if (usuarioService.existeUsuario()) {
			Users usuario = usuarioService.obtenerUsuario();
			if (usuario.isEnabled()) {
				if (validator.validar(usuario)) {
					// PrecioBase pb = new PrecioBase();
					// pb.setPrecio("10");
					// precioBaseRepository.save(pb);
					// ClienteLista cl
					// =clienteListaRepository.findByCodigoCliente(usuario.getCodigoDeUsuario());
					GrantedAuthority ga = new SimpleGrantedAuthority(ROLE_USER);
					Authentication auth = new UsernamePasswordAuthenticationToken(
							usuario.getUsername(), usuario.getPassword(),
							new ArrayList<GrantedAuthority>(Arrays.asList(ga)));					
					return auth;
				}
			}
		}
		throw new BadCredentialsException(BAD_CREDENTIALS_MESSAGE);

	}

	public boolean supports(Class<?> authentication) {
		return (UsernamePasswordAuthenticationToken.class
				.isAssignableFrom(authentication));
	}

}
