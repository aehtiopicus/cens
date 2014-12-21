package com.aehtiopicus.cens.helper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.aehtiopicus.cens.domain.Cliente;
import com.aehtiopicus.cens.domain.RelacionLaboral;
import com.aehtiopicus.cens.domain.Usuario;
import com.aehtiopicus.cens.dto.EmpleadoDto;
import com.aehtiopicus.cens.dto.RelacionLaboralDto;
import com.aehtiopicus.cens.enumeration.EstadoClienteEnum;
import com.aehtiopicus.cens.enumeration.PerfilEnum;
import com.aehtiopicus.cens.service.ClienteService;
import com.aehtiopicus.cens.service.UsuarioService;

@Component
public class RelacionLaboralSecurityHelper {

	public static final String ACCION_DESASIGNAR = "desasignar"; 
	public static final String ACCION_HISTORIAL = "historial"; 
	public static final String ACCION_PUESTO = "puesto"; 
	public static final String ACCION_ASIGNAR = "asignar"; 
	
	@Autowired
	protected UsuarioService usuarioService;
	public void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	@Autowired
	protected ClienteService clienteService;
	public void setClienteService(ClienteService clienteService) {
		this.clienteService = clienteService;
	}

	/**
	 * El metodo verifica si el usuario logueado tiene permisos para acceder a la acciones seleccionada.
	 * return true : permitir acceso
	 * return false: no permitir acceso
	 */
	public boolean elUsuarioTieneAcceso(HttpServletRequest request, RelacionLaboral relacionlaboral, String accion) {
		if(relacionlaboral == null || relacionlaboral.getCliente() == null || request == null) {
			return false;
		}
		
		if(request.isUserInRole(PerfilEnum.ADMINISTRADOR.getNombre())) {
			return true;
		}
		
		if(request.isUserInRole(PerfilEnum.GTE_OPERACION.getNombre()) || request.isUserInRole(PerfilEnum.JEFE_OPERACION.getNombre())) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication(); 
	        String username = auth.getName();
	        Usuario usuario = usuarioService.getUsuarioByUsername(username);
	        List<Cliente> clientesUsuario = null;
			
	        if(request.isUserInRole(PerfilEnum.GTE_OPERACION.getNombre())) {
		        clientesUsuario = clienteService.getClientesVigentesByGteOperacionOrderByRazonSocial(EstadoClienteEnum.Vigente, usuario);
			}else if(request.isUserInRole(PerfilEnum.JEFE_OPERACION.getNombre())) {
		        clientesUsuario = clienteService.getClientesVigentesByJefeOperacionOrderByRazonSocial(EstadoClienteEnum.Vigente, usuario);
			}

			if(clientesUsuario != null && clientesUsuario.contains(relacionlaboral.getCliente())) {
	        	return true;
	        }
		}
		
		if(request.isUserInRole(PerfilEnum.RRHH.getNombre()) || request.isUserInRole(PerfilEnum.ADMINISTRACION.getNombre())) {
			if(accion.equals(ACCION_HISTORIAL) || accion.equals(ACCION_DESASIGNAR)) {
				if(relacionlaboral != null && relacionlaboral.getCliente() != null && relacionlaboral.getCliente().getFixed() == true) {
					return false;
				}else {
					return true;
				}
			}else {
				return true;
			}
		}

		
		return false;
    }

	/**
	 * Este metodo agrega informacion al dto para indicar al js que carga las acciones en la grilla si debe mostrar u ocultar
	 * las acciones posibles de cada emplead en base al usuario logueado y su peril
	 */
	public List<RelacionLaboralDto> addSecurityToActionsGrillaRelacionesLaborales(HttpServletRequest request, List<RelacionLaboralDto> dtos){
		if(dtos == null || request == null) {
			return dtos;
		}
		
		if(request.isUserInRole(PerfilEnum.GTE_OPERACION.getNombre()) || request.isUserInRole(PerfilEnum.JEFE_OPERACION.getNombre())) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication(); 
	        String username = auth.getName();
	        Usuario usuario = usuarioService.getUsuarioByUsername(username);
	        
	        List<Cliente> clientesUsuario = null;
			if(request.isUserInRole(PerfilEnum.GTE_OPERACION.getNombre())) {
		        clientesUsuario = clienteService.getClientesVigentesByGteOperacionOrderByRazonSocial(EstadoClienteEnum.Vigente, usuario);
			}else if(request.isUserInRole(PerfilEnum.JEFE_OPERACION.getNombre())) {
		        clientesUsuario = clienteService.getClientesVigentesByJefeOperacionOrderByRazonSocial(EstadoClienteEnum.Vigente, usuario);
			}
			
	        Set<Long> idClientes = new HashSet<Long>();
	        if(clientesUsuario != null) {
	        	for (Cliente cliente : clientesUsuario) {
					idClientes.add(cliente.getId());
				}
	        	
	        	for (RelacionLaboralDto dto : dtos) {
					if(!idClientes.contains(dto.getClienteId())) {
						dto.setAccionCambiarPuesto(false);
						dto.setAccionDesasignar(false);
						dto.setAccionHistorial(false);
					}
				}
	        }
		}else if(request.isUserInRole(PerfilEnum.RRHH.getNombre()) || request.isUserInRole(PerfilEnum.ADMINISTRACION.getNombre())) {
			List<Cliente> clientesFixed = clienteService.getClientesFixed();
			Set<Long> idClientes = new HashSet<Long>();
			if(clientesFixed != null) {
	        	for (Cliente cliente : clientesFixed) {
					idClientes.add(cliente.getId());
				}
	        	
	        	for (RelacionLaboralDto dto : dtos) {
					if(idClientes.contains(dto.getClienteId())) {
						dto.setAccionHistorial(false);
						dto.setAccionDesasignar(false);
					}
				}
	        }
		}
		 
		
		return dtos;
	}
}
