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
import com.aehtiopicus.cens.domain.Usuario;
import com.aehtiopicus.cens.dto.ClienteDto;
import com.aehtiopicus.cens.enumeration.EstadoClienteEnum;
import com.aehtiopicus.cens.enumeration.PerfilEnum;
import com.aehtiopicus.cens.service.ClienteService;
import com.aehtiopicus.cens.service.UsuarioService;

@Component
public class ClienteSecurityHelper {

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
	public boolean elUsuarioTieneAcceso(HttpServletRequest request, Cliente cliente) {
		if(cliente == null || request == null) {
			return false;
		}
		//Si el usuario logueado es un gerente de operaciones solo puede editar los clientes asignados a si mismo.
		if(request.isUserInRole(PerfilEnum.GTE_OPERACION.getNombre())) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication(); 
	        String username = auth.getName();
	        Usuario usuario = usuarioService.getUsuarioByUsername(username);
		
	        List<Cliente> clientesUsuario = clienteService.getClientesVigentesByGteOperacionOrderByRazonSocial(EstadoClienteEnum.Vigente, usuario);
	        if(!clientesUsuario.contains(cliente)) {
	        	return false;
	        }
		}
		//Si el usuario logueado es un jefe de operaciones solo puede editar los clientes asignados a si mismo.
		if(request.isUserInRole(PerfilEnum.JEFE_OPERACION.getNombre())) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication(); 
	        String username = auth.getName();
	        Usuario usuario = usuarioService.getUsuarioByUsername(username);
		
	        List<Cliente> clientesUsuario = clienteService.getClientesVigentesByJefeOperacionOrderByRazonSocial(EstadoClienteEnum.Vigente, usuario);
	        if(!clientesUsuario.contains(cliente)) {
	        	return false;
	        }
		}
		
		return true;
    }

	/**
	 * Este metodo agrega informacion al dto para indicar al js que carga las acciones en la grilla si debe mostrar u ocultar
	 * las acciones posibles de cada emplead en base al usuario logueado y su peril
	 */
	public List<ClienteDto> addSecurityToActionsGrillaClientes(HttpServletRequest request, List<ClienteDto> dtos){
		if(dtos == null || request == null) {
			return dtos;
		}
		
		if(request.isUserInRole(PerfilEnum.GTE_OPERACION.getNombre()) || request.isUserInRole(PerfilEnum.JEFE_OPERACION.getNombre())) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication(); 
	        String username = auth.getName();
	        Usuario usuario = usuarioService.getUsuarioByUsername(username);
		
	        List<Cliente> clientesUsuario =  null;
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
	        	
	        	for (ClienteDto dto : dtos) {
					if(!idClientes.contains(dto.getClienteId())) {
						dto.setAccionEditar(false);
						dto.setAccionBeneficios(false);
					}
				}
	        }
		}else if(request.isUserInRole(PerfilEnum.RRHH.getNombre())) {
			for (ClienteDto dto : dtos) {
				dto.setAccionEditar(false);
				dto.setAccionBeneficios(false);
			}
		}
		
		return dtos;
	}
}
