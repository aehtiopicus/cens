package com.aehtiopicus.cens.mapper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.aehtiopicus.cens.domain.Banco;
import com.aehtiopicus.cens.domain.MotivoBaja;
import com.aehtiopicus.cens.domain.ObraSocial;
import com.aehtiopicus.cens.domain.Prepaga;
import com.aehtiopicus.cens.dto.MotivoBajaDto;
import com.aehtiopicus.cens.dto.ParametroDto;
import com.aehtiopicus.cens.util.Utils;

public class ParametrizacionMapper {
	
	private static final Logger logger = Logger.getLogger(ParametrizacionMapper.class);
	static SimpleDateFormat sdf = new SimpleDateFormat(Utils.DD_MM_YYYY);
	
	//Obra social---------------------------
	public static List<ParametroDto> getParametrosDtoFromObrasSociales(List<ObraSocial> entities) {
		List<ParametroDto> dtos = new ArrayList<ParametroDto>();
		if(entities != null) {
			for (ObraSocial entity : entities) {
				dtos.add(getParametroDtoFromObraSocial(entity));
			}
		}
		return dtos;
	}

	public static ParametroDto getParametroDtoFromObraSocial(ObraSocial entity) {
		ParametroDto dto = new ParametroDto();
		if(entity != null) {
			dto.setEntityId(entity.getId());
			if(entity.getNombre() != null) {
				dto.setParametro(entity.getNombre());				
			}
		}
		return dto;
	}
	
	public static ObraSocial getObraSocialFromParametroDto(ParametroDto dto) {
		ObraSocial entity = new ObraSocial();
		if(dto != null) {
			if(dto.getEntityId() != null){
				entity.setId(dto.getEntityId());
			}
			if(StringUtils.isNotEmpty(dto.getParametro())){
				entity.setNombre(dto.getParametro());
			}
		}
		return entity;
	}
	//--------------------------------------
	
	//Prepaga-------------------------------
	public static List<ParametroDto> getParametrosDtoFromPrepagas(List<Prepaga> entities) {
		List<ParametroDto> dtos = new ArrayList<ParametroDto>();
		if(entities != null) {
			for (Prepaga entity : entities) {
				dtos.add(getParametroDtoFromPrepaga(entity));
			}
		}
		return dtos;
	}

	public static ParametroDto getParametroDtoFromPrepaga(Prepaga entity) {
		ParametroDto dto = new ParametroDto();
		if(entity != null) {
			dto.setEntityId(entity.getId());
			if(entity.getNombre() != null) {
				dto.setParametro(entity.getNombre());				
			}
		}
		return dto;
	}
	
	public static Prepaga getPrepagaFromParametroDto(ParametroDto dto) {
		Prepaga entity = new Prepaga();
		if(dto != null) {
			if(dto.getEntityId() != null){
				entity.setId(dto.getEntityId());
			}
			if(StringUtils.isNotEmpty(dto.getParametro())){
				entity.setNombre(dto.getParametro());
			}
		}
		return entity;
	}
	//--------------------------------------
	
	//Banco---------------------------------
	public static List<ParametroDto> getParametrosDtoFromBancos(List<Banco> entities) {
		List<ParametroDto> dtos = new ArrayList<ParametroDto>();
		if(entities != null) {
			for (Banco entity : entities) {
				dtos.add(getParametroDtoFromBanco(entity));
			}
		}
		return dtos;
	}

	public static ParametroDto getParametroDtoFromBanco(Banco entity) {
		ParametroDto dto = new ParametroDto();
		if(entity != null) {
			dto.setEntityId(entity.getId());
			if(entity.getNombre() != null) {
				dto.setParametro(entity.getNombre());				
			}
		}
		return dto;
	}
	
	public static Banco getBancoFromParametroDto(ParametroDto dto) {
		Banco entity = new Banco();
		if(dto != null) {
			if(dto.getEntityId() != null){
				entity.setId(dto.getEntityId());
			}
			if(StringUtils.isNotEmpty(dto.getParametro())){
				entity.setNombre(dto.getParametro());
			}
		}
		return entity;
	}
	//--------------------------------------


	//Motivo Baja---------------------------
	public static List<MotivoBajaDto> getMotivoBajaDtoFromMotivosBaja(List<MotivoBaja> entities) {
		List<MotivoBajaDto> dtos =  new ArrayList<MotivoBajaDto>();
		if(entities != null) {
			for (MotivoBaja entity : entities) {
				dtos.add(getMotivoBajaDtoFromMotivoBaja(entity));				
			}
		}
		return dtos;
	}
	
	public static MotivoBajaDto getMotivoBajaDtoFromMotivoBaja(MotivoBaja entity) {
		MotivoBajaDto dto = new MotivoBajaDto();
		if(entity != null) {
			dto.setMotivoBajaId(entity.getId());
			dto.setMotivo(entity.getMotivo());				
			dto.setArticuloLct(entity.getArticuloLct());
		}
		return dto;
	}
	
	public static MotivoBaja getMotivoBajaFromMotivoBajaDto(MotivoBajaDto dto) {
		MotivoBaja entity = new MotivoBaja();
		if(dto != null) {
			if(dto.getMotivoBajaId() != null){
				entity.setId(dto.getMotivoBajaId());
			}
			entity.setMotivo(dto.getMotivo());
			entity.setArticuloLct(dto.getArticuloLct());
		}
		return entity;
	}
	//--------------------------------------
}
