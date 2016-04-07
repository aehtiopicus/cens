package ar.com.midasconsultores.catalogodefiltros.service;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import ar.com.midasconsultores.catalogodefiltros.domain.ClienteLista;
import ar.com.midasconsultores.catalogodefiltros.domain.Users;
import ar.com.midasconsultores.catalogodefiltros.repository.ClienteListaRepository;

@Service
public class PrecioAjusteServiceImpl implements PrecioAjusteService {

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private ClienteListaRepository clienteListaRepository;
	
	@Override
	public double obtenerPorcentajeAjuste() {
		double result = 0;
		try{
			Object principal = null;
			
			if (principal == null) {
				principal = SecurityContextHolder.getContext()
						.getAuthentication().getPrincipal();
			}
			
			String userName = null;
			if (principal != null) {
			 userName = (principal instanceof Principal) ? ((Principal)principal).getName() : (String) principal;
			}
			
			if (userName != null) {
				Users user = usuarioService.getUsuario(userName);
	
				ClienteLista cl = clienteListaRepository.findByCodigoClienteIgnoreCase(user.getCodigoDeUsuario());
	
				if (cl != null) {
					if(cl.getLista() != null){
						result = Double.parseDouble(cl.getLista().getPorcentaje());
					}
				} 
			}
		}catch(Exception e){
			result = 0;
		}
		return result;
	}
	
	@Override
	public double obtenerPorcentajeAjusteByCliente(String codigoCliente) {
		double result = 0;
		ClienteLista cl = clienteListaRepository.findByCodigoClienteIgnoreCase(codigoCliente);

		if (cl != null) {
			if(cl.getLista() != null){
				result = Double.parseDouble(cl.getLista().getPorcentaje());
			} 
		} else {
			result = obtenerPorcentajeAjuste();
		}
		return result;
	}
	
	@Override
	public double calcularPrecioAjustado(double precio,double ajuste){
		return precio* (1 + ajuste / 100);
	}

}
