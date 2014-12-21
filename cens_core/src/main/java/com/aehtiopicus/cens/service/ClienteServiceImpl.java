package com.aehtiopicus.cens.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aehtiopicus.cens.domain.Beneficio;
import com.aehtiopicus.cens.domain.BeneficioCliente;
import com.aehtiopicus.cens.domain.Cliente;
import com.aehtiopicus.cens.domain.Usuario;
import com.aehtiopicus.cens.enumeration.EstadoClienteEnum;
import com.aehtiopicus.cens.repository.BeneficioClienteRepository;
import com.aehtiopicus.cens.repository.BeneficioRepository;
import com.aehtiopicus.cens.repository.ClienteRepository;
import com.aehtiopicus.cens.repository.UsuarioRepository;
import com.aehtiopicus.cens.specification.ClienteSpecification;
import com.aehtiopicus.cens.utils.PeriodoUtils;

@Service
@Transactional
public class ClienteServiceImpl implements ClienteService{
	
	private static final Logger logger = Logger.getLogger(InitLoadServiceImpl.class);
	

	@Autowired
	protected UsuarioRepository usuarioRepository;
	
	@Autowired
	protected BeneficioRepository beneficioRepository;
	
	@Autowired
	protected ClienteRepository clienteRepository;
	
	@Autowired
	protected BeneficioClienteRepository beneficioClienteRepository;
	
	public void setClienteRepository(ClienteRepository clienteRepository) {
		this.clienteRepository = clienteRepository;
	}


	public void setUsuarioRepository(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	
	public void setBeneficioRepository(BeneficioRepository beneficioRepository) {
		this.beneficioRepository = beneficioRepository;
	}

	

	public void setBeneficioClienteRepository(
			BeneficioClienteRepository beneficioClienteRepository) {
		this.beneficioClienteRepository = beneficioClienteRepository;
	}


	@Override
	public void saveCliente(Cliente cliente) {
		
		if(cliente.getId() != null) {
			Cliente clienteDB = clienteRepository.findById(cliente.getId());
			if(clienteDB != null) {
				cliente.setFixed(clienteDB.getFixed());
			}
		}
		
		cliente = clienteRepository.saveAndFlush(cliente);	
		
	}
	
	@Override
	public Cliente getClienteByNombre(String razon_social) {
		return clienteRepository.findByNombreIgnoreCase(razon_social);
	}


	@Override
	public Cliente getClienteById(Long clienteId) {
		return clienteRepository.findById(clienteId);
	}
	
	//Beneficios
	
	@Override
	public Beneficio getBeneficioById(Long beneficioId) {
		return beneficioRepository.findById(beneficioId);
	}
	
	@Override
	public List<Beneficio> getBeneficios() {
		return beneficioRepository.findAll();
	}
	
	
	//Metodos de la grilla:
	//////////////////////////////////////////////////////////////////////////////
	
	@Override
	public List<Cliente> search(String estado, String nombre, int pageIndex, int row) {
		 Page<Cliente> requestedPage = null;
		 if(pageIndex > 0){
			 pageIndex = pageIndex - 1;
		 }
		 
		 boolean where = true;
		 Specifications<Cliente> clienteSpecif = null;
		 if(StringUtils.isNotEmpty(estado)){
			 if(where == true){
				 clienteSpecif = Specifications.where(ClienteSpecification.estadoEquals(EstadoClienteEnum.getEstadoClienteEnumFromString(estado)));
				 where = false;
			 }
		 }
		 if(StringUtils.isNotEmpty(nombre)){
			if(where == false){
				 clienteSpecif = clienteSpecif.and(ClienteSpecification.nombreEquals(nombre));
			}else{
				 clienteSpecif = Specifications.where(ClienteSpecification.nombreEquals(nombre));
			}
		 }
		 requestedPage = clienteRepository.findAll(clienteSpecif,constructPageSpecification(pageIndex,row));
		 return requestedPage.getContent();
	}
	
	/**
    * Retorna una nueva pagina con el especificado tipo de objeto
    * @param pageIndex Numero de pagina que se quiere obtener
    * @return
    */
   private Pageable constructPageSpecification(int pageIndex, int row) {
       Pageable pageSpecification = new PageRequest(pageIndex, row, sortByUsernameAsc());
    
       return pageSpecification;
   }
   
   /**
    * Retorna  Sort object que ordena usuarios acorde al nombre asociado 
    * @return
    */
   private Sort sortByUsernameAsc() {
       return new Sort(Sort.Direction.ASC, "nombre");
   }
   
   @Override
   public int getNumberOfPages(int numberOfClientesPerPage, Integer cantClientes){
   
   	double totalPages = 0;
   	if(cantClientes > 0){
   		totalPages = Double.valueOf(cantClientes) / numberOfClientesPerPage;
   	}
   	return (int) Math.ceil(totalPages);
   }


	@Override
	public List<Cliente> getClientes() {
		return clienteRepository.findAll(new Sort("nombre"));
	}

	
	@Override
	public List<Cliente> getClientesVigentesHastaPeriodo(String periodo){
		Date periodoActual = PeriodoUtils.getDateFormPeriodo(periodo);
		
		return clienteRepository.findByFechaBajaIsNullOrFechaBajaAfterOrFechaBajaOrderByNombreAsc(periodoActual, periodoActual);
	}
	
	@Override
	public List<Cliente> getClientesVigentesOrderByNombre() {
		
		return clienteRepository.findAll(ClienteSpecification.estadoEquals(EstadoClienteEnum.Vigente),new Sort(Sort.Direction.ASC, "nombre"));
	}

	@Override
	public List<Cliente> getClientesVigentesByGteOperacionOrderByRazonSocial(EstadoClienteEnum estado, Usuario gteOperaciones) {
		return clienteRepository.findByEstadoClienteEnumAndGerenteOperacionOrderByRazonSocialAsc(estado, gteOperaciones);
	}
	
	
	@Override
	public int getTotalClientes(String estado, String nombre) {
		 ClienteSpecification clienteSpecification = new ClienteSpecification() ;
		
		 Specifications<Cliente> spec = null ;
		 Boolean where = true;
		
		 spec = getSpecification(estado,nombre, clienteSpecification,spec, where);
		return  (int) Math.ceil(clienteRepository.count(spec));
	}



	private Specifications<Cliente> getSpecification(String estado, String nombre, ClienteSpecification clienteSpecification,Specifications<Cliente> spec, Boolean where) {

		 if(!StringUtils.isEmpty(estado)){
			 EstadoClienteEnum estadoCliente = EstadoClienteEnum.getEstadoClienteEnumFromString(estado);
			 	 spec = Specifications.where(clienteSpecification.estadoEquals(estadoCliente));
				 where = false;
		 }
		 if(!StringUtils.isEmpty(nombre)){
			 if(where == true){
				 spec = Specifications.where(clienteSpecification.nombreEquals(nombre));
			 }else{
				 spec = spec.and(clienteSpecification.nombreEquals(nombre));
			 }
		 }
		return spec;
	}


	@Override
	public Beneficio saveBeneficio(Beneficio beneficio) {
		return beneficioRepository.save(beneficio);			
	}


	@Override
	public boolean deleteBeneficio(Long beneficioId) throws Exception {
		try {
			beneficioRepository.delete(beneficioId);
			return true;
		}catch(Exception e) {
			logger.info(e.getMessage());
			throw e;
		}

	}


	@Override
	public List<Beneficio> searchBeneficios(Integer page, Integer rows) {
		 Page<Beneficio> requestedPage = null;
		 if(page > 0){
			 page = page - 1;
		 }
		 
		 requestedPage = beneficioRepository.findAll(constructPageSpecification(page,rows, new Sort(Sort.Direction.ASC, "titulo")));	 

		 return requestedPage.getContent();
	}


	@Override
	public int getTotalBeneficios(Integer page, Integer rows) {

		return  (int) Math.ceil(beneficioRepository.count());
	}


	private Pageable constructPageSpecification(int pageIndex, int row,
			Sort sort) {
		if (sort != null) {
			return new PageRequest(pageIndex, row, sort);
		} else {
			return new PageRequest(pageIndex, row);
		}
	}


	@Override
	public BeneficioCliente getBeneficioClienteById(Long beneficioClienteId) {
		return beneficioClienteRepository.findOne(beneficioClienteId);
	}


	@Override
	public BeneficioCliente saveBeneficioCliente(BeneficioCliente beneficioCliente) {
		if(beneficioCliente.getId() != null) {
			return beneficioClienteRepository.save(beneficioCliente);			
		}else {
			Cliente cliente = clienteRepository.findById(beneficioCliente.getCliente().getId());
			cliente.getBeneficios().add(beneficioCliente);
			beneficioCliente = beneficioClienteRepository.save(beneficioCliente);
			clienteRepository.save(cliente);
			return beneficioCliente;
		}
	}


	@Override
	public BeneficioCliente cambiarEstadoBeneficioCliente(Long beneficioClienteId) throws Exception {
		BeneficioCliente bc = beneficioClienteRepository.findOne(beneficioClienteId);
		
		if(bc == null) {
			throw new Exception("Error, no se pudo modificar el beneficio.");
		}
		
		bc.setVigente(!bc.getVigente());
		return beneficioClienteRepository.save(bc);
	}


	@Override
	public List<Cliente> getClientesFixed() {
		return clienteRepository.findByFixed(true);
	}


	@Override
	public List<Cliente> getClientesVigentesByJefeOperacionOrderByRazonSocial(EstadoClienteEnum estado, Usuario jefeOperaciones) {
		return clienteRepository.findByEstadoClienteEnumAndJefeOperacionOrderByRazonSocialAsc(estado, jefeOperaciones);
	}


	   


}
