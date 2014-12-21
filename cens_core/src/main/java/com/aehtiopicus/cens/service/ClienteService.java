package com.aehtiopicus.cens.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.aehtiopicus.cens.domain.Beneficio;
import com.aehtiopicus.cens.domain.BeneficioCliente;
import com.aehtiopicus.cens.domain.Cliente;
import com.aehtiopicus.cens.domain.Usuario;
import com.aehtiopicus.cens.enumeration.EstadoClienteEnum;

@Service
public interface ClienteService {

	public void saveCliente(Cliente cliente);
	
	
	public Cliente getClienteByNombre(String nombre);
	
	public Cliente getClienteById(Long clienteId);
	
	//Métodos de la grilla
	
	public List<Cliente> search(String searchTerm, String nombre, int pageIndex, int row);



	public int getNumberOfPages(int numberOfClientsPerPage, Integer cantClients);


	public List<Cliente> getClientes();


	public int getTotalClientes(String estado, String nombre);


	public List<Cliente> getClientesVigentesOrderByNombre();


	public List<Cliente> getClientesVigentesByGteOperacionOrderByRazonSocial(EstadoClienteEnum estado, Usuario gerente);

	public List<Cliente> getClientesVigentesByJefeOperacionOrderByRazonSocial(EstadoClienteEnum estado, Usuario jefe);

	
	public Beneficio getBeneficioById(Long beneficioId);


	public List<Beneficio> getBeneficios();


	public Beneficio saveBeneficio(Beneficio beneficio);


	public boolean deleteBeneficio(Long beneficioId) throws Exception;


	public List<Beneficio> searchBeneficios(Integer page, Integer rows);


	public int getTotalBeneficios(Integer page, Integer rows);


	public BeneficioCliente getBeneficioClienteById(Long beneficioClienteId);


	public BeneficioCliente saveBeneficioCliente(BeneficioCliente beneficioCliente);


	public BeneficioCliente cambiarEstadoBeneficioCliente(Long beneficioClienteId)  throws Exception ;


	public List<Cliente> getClientesFixed();


	public List<Cliente> getClientesVigentesHastaPeriodo(String periodo);
	
}
