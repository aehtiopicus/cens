package com.aehtiopicus.cens.mapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;

import com.aehtiopicus.cens.domain.Beneficio;
import com.aehtiopicus.cens.domain.BeneficioCliente;
import com.aehtiopicus.cens.domain.Cliente;
import com.aehtiopicus.cens.domain.Usuario;
import com.aehtiopicus.cens.dto.BeneficioClienteDto;
import com.aehtiopicus.cens.dto.BeneficioDto;
import com.aehtiopicus.cens.dto.ClienteDto;
import com.aehtiopicus.cens.dto.ComboDto;
import com.aehtiopicus.cens.dto.ComboStrDto;
import com.aehtiopicus.cens.enumeration.EstadoClienteEnum;
import com.aehtiopicus.cens.enumeration.FrecuenciaBeneficioEnum;
import com.aehtiopicus.cens.enumeration.TipoBeneficioEnum;
import com.aehtiopicus.cens.util.Utils;

public class ClienteMapper {
	
	private static final Logger logger = Logger.getLogger(ClienteMapper.class);
	static SimpleDateFormat sdf = new SimpleDateFormat(Utils.DD_MM_YYYY);
	
	public static List<ComboDto> getCombosDtoFromUsuarios(List<Usuario> usuarios){
		List<ComboDto> comboData = new ArrayList<ComboDto>();
		
		if(usuarios != null){
			ComboDto data;
			for (Usuario usuarioGerenteOp : usuarios) {
				logger.info("US: "+usuarioGerenteOp.getId());
				data = new ComboDto();
				data.setId(usuarioGerenteOp.getId());
				data.setValue(usuarioGerenteOp.getApellido() + ", " +usuarioGerenteOp.getNombre());
				comboData.add(data);
			}
		}
		
		return comboData;
	}
	
	public static List<ComboDto> getCombosDtoFromBeneficios(List<Beneficio> beneficios){
		List<ComboDto> comboData = new ArrayList<ComboDto>();
		
		if(beneficios != null){
			ComboDto data;
			for (Beneficio beneficio : beneficios) {
				logger.info("Armando combo beneficios: "+beneficio.getId());
				data = new ComboDto();
				data.setId(beneficio.getId());
				data.setValue(beneficio.getTitulo());
				comboData.add(data);
			}
		}
		
		return comboData;
	}
	
	public static Cliente getClienteFromClienteDto(ClienteDto dto) {
		Cliente cliente = new Cliente();

		if(dto != null){
			if(StringUtils.isNotEmpty(dto.getNombre())){
				cliente.setNombre(dto.getNombre());
			}
			if(StringUtils.isNotEmpty(dto.getRazonSocial())){
				cliente.setRazonSocial(dto.getRazonSocial());
			}

			if(StringUtils.isNotEmpty(dto.getEmail())){
				cliente.setEmail(dto.getEmail());
			}
			

			if(StringUtils.isNotEmpty(dto.getEmailContacto())){
				cliente.setEmailContacto((dto.getEmailContacto()));
			}
			if(StringUtils.isNotEmpty(dto.getFecha_alta())){
				Date date;
				try {
					date = sdf.parse(dto.getFecha_alta());
					cliente.setFecha_alta(date);
					cliente.setEstadoClienteEnum(EstadoClienteEnum.Vigente);
				} catch (ParseException e) {
					logger.error("no se pudo parsera fecha egreso novatium");
				}
				
				
			}
			if(StringUtils.isNotEmpty(dto.getFecha_baja())){
				Date date;
				try {
					date = sdf.parse(dto.getFecha_baja());
					cliente.setFechaBaja(date);
					cliente.setEstadoClienteEnum(EstadoClienteEnum.DadoDeBaja);
				} catch (ParseException e) {
					logger.error("no se pudo parsera fecha egreso novatium");
				}				
			}
			
			if(StringUtils.isNotEmpty(dto.getDireccion())){
				cliente.setDireccion(dto.getDireccion());
			}
			if(StringUtils.isNotEmpty(dto.getTelefono())){
				cliente.setTelefono(dto.getTelefono());
			}
			if(StringUtils.isNotEmpty(dto.getNombre_contacto())){
				cliente.setNombre_contacto(dto.getNombre_contacto());
			}
			
			
			if(dto.getClienteId() != null){
				cliente.setId(dto.getClienteId());
			}
			
						
			if(dto.getUsuarioGerenteOperadorId() != null){
				Usuario usuario = new Usuario(); 
				usuario.setId(dto.getUsuarioGerenteOperadorId());
				cliente.setGerenteOperacion(usuario);
			}
			
			if(dto.getUsuarioJefeOperadorId() != null){
				Usuario usuario = new Usuario(); 
				usuario.setId(dto.getUsuarioJefeOperadorId());
				cliente.setJefeOperacion(usuario);
			}else {
				cliente.setJefeOperacion(null);
			}
			
			if(dto.getHsExtrasConPresentismo() != null) {
				cliente.setHsExtrasConPresentismo(dto.getHsExtrasConPresentismo());
			}
		}

		
		return cliente;
	
	}
	
	public static Beneficio getBeneficioFromClienteDto(ClienteDto dto) {
		Beneficio beneficio = new Beneficio(); 
		
		if(dto != null && dto.getBeneficioId() != null){
			beneficio.setId(dto.getBeneficioId());
		}
		
		return beneficio;
	}

	public static BeneficioDto getBeneficioDtoFromBeneficio(Beneficio entity) {
		BeneficioDto dto = new BeneficioDto(); 
		
		if(entity.getId() != null){
			dto.setBeneficioId(entity.getId());
		}
		if(StringUtils.isNotEmpty(entity.getTitulo())){
			dto.setTitulo(entity.getTitulo());
		}
		if(StringUtils.isNotEmpty(entity.getDescripcion())){
			dto.setDescripcion(entity.getDescripcion());
		}

		dto.setRemunerativo(entity.isRemunerativo());
		
		return dto;
	}
	
	public static List <BeneficioDto> getBeneficiosDtoFromBeneficios(List <Beneficio> beneficios) {
		List <BeneficioDto> beneficiosDto = new ArrayList<BeneficioDto>();
		
		if(beneficios != null){
			for (Beneficio beneficio : beneficios) {
				beneficiosDto.add(getBeneficioDtoFromBeneficio(beneficio));
				
			}
		}
		return beneficiosDto;
	}
	
	public static Usuario getGerenteOpFromClienteDto(ClienteDto dto) {
		Usuario usuario = new Usuario(); 
		
		if(dto != null && dto.getUsuarioGerenteOperadorId() != null){
			usuario.setId(dto.getUsuarioGerenteOperadorId());
		}
		
		return usuario;
	}
	
	
	public static ClienteDto getClienteDtoFromCliente(Cliente cliente) {
		//Beneficio beneficio = cliente.getBeneficio();

		ClienteDto dto = new ClienteDto();
		
		if(cliente != null){
			if(cliente.getId() > 0){
				dto.setClienteId(cliente.getId());
			}
			if(StringUtils.isNotEmpty(cliente.getNombre())){
				dto.setNombre(cliente.getNombre());
			}
			if(StringUtils.isNotEmpty(cliente.getRazonSocial())){
				dto.setRazonSocial(cliente.getRazonSocial());
			}
			if(StringUtils.isNotEmpty(cliente.getEmail())){
				dto.setEmail(cliente.getEmail());
			}
			if((cliente.getFecha_alta())!=null){
				dto.setFecha_alta(sdf.format(cliente.getFecha_alta()));
				dto.setEstadoCliente(EstadoClienteEnum.Vigente.toString());
			}
			if((cliente.getFechaBaja()) !=null){
				dto.setFecha_baja(sdf.format(cliente.getFechaBaja()));
				dto.setEstadoCliente(EstadoClienteEnum.DadoDeBaja.toString());
			}
			
			if(StringUtils.isNotEmpty(cliente.getDireccion())){
				dto.setDireccion(cliente.getDireccion());
			}
			
			if(StringUtils.isNotEmpty(cliente.getTelefono())){
				dto.setTelefono(cliente.getTelefono());
			}
			
			if(StringUtils.isNotEmpty(cliente.getNombre_contacto())){
				dto.setNombre_contacto(cliente.getNombre_contacto());
			}
			
			if(StringUtils.isNotEmpty(cliente.getEmailContacto())){
				dto.setEmailContacto(cliente.getEmailContacto());
			}
			
			if(cliente.getGerenteOperacion() != null){
				String gerente = cliente.getGerenteOperacion().getApellido() + ", " + cliente.getGerenteOperacion().getNombre();
				dto.setGerenteName(gerente);
				dto.setUsuarioGerenteOperadorId(cliente.getGerenteOperacion().getId());
			}
			
			if(cliente.getJefeOperacion() != null){
				String jefe = cliente.getJefeOperacion().getApellido() + ", " + cliente.getJefeOperacion().getNombre();
				dto.setJefeName(jefe);
				dto.setUsuarioJefeOperadorId(cliente.getJefeOperacion().getId());
			}
			
			if(cliente.getHsExtrasConPresentismo() != null) {
				dto.setHsExtrasConPresentismo(cliente.getHsExtrasConPresentismo());
			}
		}
		
		return dto;
	}
	
	public static List<ClienteDto> getClientesDtoFromClientes(List<Cliente> clientes){
		List<ClienteDto> clientesDto = new ArrayList<ClienteDto>();
		if(!CollectionUtils.isEmpty(clientes)){
			for(Cliente cliente : clientes){
				clientesDto.add(getClienteDtoFromCliente(cliente));
			}
		}
		return clientesDto;
	}

	public static List<ComboDto> getComboDtoFromCliente(List<Cliente> clientes) {
		List<ComboDto> comboData = new ArrayList<ComboDto>();
		logger.info("cargando combo empleado");
		if(clientes != null){
			ComboDto data;
			for (Cliente cliente : clientes) {
				data = new ComboDto();
				data.setId(cliente.getId());
				data.setValue(cliente.getNombre());
				comboData.add(data);
			}
		}
		
		return comboData;
	}
	
	public static List<ComboDto> getComboEstadoCliente() {
		List<ComboDto> comboData = new ArrayList<ComboDto>();
		Long i = 0l;
		for (EstadoClienteEnum b : EstadoClienteEnum.values()) {
			i += 1;
			ComboDto data = new ComboDto();
			data.setId(i);
			data.setValue(b.getNombre());
			comboData.add(data);
		}
		return comboData;

	}

	
	public static Beneficio getBeneficioFromBeneficioDto(BeneficioDto dto) {
		Beneficio entity = new Beneficio();
		
		if(dto != null) {
			if(dto.getBeneficioId() != null) entity.setId(dto.getBeneficioId());
			if(StringUtils.isNotEmpty(dto.getDescripcion())) entity.setDescripcion(dto.getDescripcion());
			if(StringUtils.isNotEmpty(dto.getTitulo())) entity.setTitulo(dto.getTitulo());
			entity.setRemunerativo(dto.getRemunerativo());
		}
		
		return entity;
	}

	public static List<ComboStrDto> getCombosDtoFromTiposBeneficio(TipoBeneficioEnum[] values) {
		List<ComboStrDto> dtos = new ArrayList<ComboStrDto>();
		
		ComboStrDto dto;
		for (TipoBeneficioEnum tipoBeneficioEnum : values) {
			dto = new ComboStrDto();
			dto.setId(tipoBeneficioEnum.getNombre());
			dto.setValue(tipoBeneficioEnum.getNombre());
			dtos.add(dto);
		}
		
		return dtos;
	}

	public static Object getCombosDtoFromFrecuenciasBeneficio(FrecuenciaBeneficioEnum[] values) {
		List<ComboStrDto> dtos = new ArrayList<ComboStrDto>();
		
		ComboStrDto dto;
		for (FrecuenciaBeneficioEnum frecuenciaBeneficioEnum : values) {
			dto = new ComboStrDto();
			dto.setId(frecuenciaBeneficioEnum.getNombre());
			dto.setValue(frecuenciaBeneficioEnum.getNombre());
			dtos.add(dto);

		}
		
		return dtos;
	}

	public static List<BeneficioClienteDto> getBeneficiosClienteDtoFromBeneficiosCliente(List<BeneficioCliente> entities) {
		List<BeneficioClienteDto> dtos = new ArrayList<BeneficioClienteDto>();
		if(entities != null) {
			for (BeneficioCliente entity : entities) {
				dtos.add(getBeneficioClienteDtoFromBeneficioCliente(entity));
			}
		}
		return dtos;
	}

	public static BeneficioClienteDto getBeneficioClienteDtoFromBeneficioCliente(BeneficioCliente entity) {
		BeneficioClienteDto dto = new BeneficioClienteDto();
		if(entity != null) {
			dto.setBeneficioClienteId(entity.getId());
			if(entity.getBeneficio() != null) {
				dto.setBeneficioId(entity.getBeneficio().getId());	
				dto.setDescripcion(entity.getBeneficio().getDescripcion());
				dto.setTitulo(entity.getBeneficio().getTitulo());
			}
			if(entity.getCliente() != null) {
				dto.setClienteId(entity.getCliente().getId());
			}
			
			if(entity.getVigente() != null) {
				dto.setHabilitado(entity.getVigente());				
			}
			
			dto.setTipo(entity.getTipo().getNombre());
			if(entity.getValor() != null) {
				dto.setValor(entity.getValor().toString());				
			}
		}
		return dto;
	}

	public static BeneficioCliente getBeneficioClienteFromBeneficioClienteDto(BeneficioClienteDto dto) {
		BeneficioCliente entity = new BeneficioCliente();
		if(dto != null) {
			if(dto.getClienteId() != null) {
				Cliente c = new Cliente();
				c.setId(dto.getClienteId());
				entity.setCliente(c);
			}
			
			if(dto.getBeneficioId() != null) {
				Beneficio b = new Beneficio();
				b.setId(dto.getBeneficioId());
				entity.setBeneficio(b);
			}
			
			if(dto.getBeneficioClienteId() != null) {
				entity.setId(dto.getBeneficioClienteId());
			}
			
			if(dto.getHabilitado() != null) {
				entity.setVigente(dto.getHabilitado());
			}else {
				entity.setVigente(true);
			}
		
			
			if(StringUtils.isNotEmpty(dto.getTipo())) {
				entity.setTipo(TipoBeneficioEnum.getTipoBeneficioEnumFromNombre(dto.getTipo()));
			}
			
			if(StringUtils.isNotEmpty(dto.getValor())) {
				entity.setValor(Float.valueOf(dto.getValor()));
			}
		}
		return entity;
	}


}
