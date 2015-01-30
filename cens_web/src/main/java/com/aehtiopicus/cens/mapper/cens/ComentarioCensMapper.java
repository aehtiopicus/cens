package com.aehtiopicus.cens.mapper.cens;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import com.aehtiopicus.cens.domain.entities.Alumno;
import com.aehtiopicus.cens.domain.entities.Asesor;
import com.aehtiopicus.cens.domain.entities.ComentarioCens;
import com.aehtiopicus.cens.domain.entities.MiembroCens;
import com.aehtiopicus.cens.domain.entities.Preceptor;
import com.aehtiopicus.cens.domain.entities.Profesor;
import com.aehtiopicus.cens.dto.cens.ComentarioDescriptionDto;
import com.aehtiopicus.cens.dto.cens.ComentarioDto;
import com.aehtiopicus.cens.dto.cens.ComentarioRequestDto;
import com.aehtiopicus.cens.dto.cens.ComentarioUserDto;
import com.aehtiopicus.cens.dto.cens.ComentariosDto;

@Component
public class ComentarioCensMapper {

	public ComentariosDto createCommentariosDto(Long usuarioId, MiembroCens mc) {
		ComentariosDto dto = new ComentariosDto();		
		dto.setResults(createComentarioDto(usuarioId,mc));
		return dto;
	}

	private ComentarioDto createComentarioDto(Long usuarioId, MiembroCens mc) {
		ComentarioDto dto = new ComentarioDto();
		dto.setUser(createComentarioUserDto(usuarioId,mc));
		return dto;
	}

	private ComentarioUserDto createComentarioUserDto(Long usuarioId,
			MiembroCens mc) {
		ComentarioUserDto dto = new ComentarioUserDto();
		dto.setFullname(mc.getApellido().toUpperCase()+", "+mc.getNombre()+" ("+mc.getDni()+")");
		dto.setUser_id(usuarioId);
		dto.setPicture("/css/midasUI-theme/images/user_blank_picture.png");
		dto.setIs_add_allowed(true);
		dto.setIs_edit_allowed(true);		
		return dto;
	}

	public ComentarioCens getDataForComentarioCens(
			ComentarioRequestDto comentarioRequestDto) {
		ComentarioCens cc = new ComentarioCens();
		
		switch(comentarioRequestDto.getUsuarioTipo()){
		case ADMINISTRADOR:
			break;
		case ALUMNO:
			Alumno alumno = new Alumno();
			alumno.setId(comentarioRequestDto.getUsuarioId());
			break;
		case ASESOR:
			Asesor a = new Asesor();
			a.setId(comentarioRequestDto.getUsuarioId());
			cc.setAsesor(a);
			break;
		case PROFESOR:
			Profesor p = new Profesor();
			p.setId(comentarioRequestDto.getUsuarioId());
			cc.setProfesor(p);
			break;
		case PRECEPTOR:
			Preceptor preceptor = new Preceptor();
			preceptor.setId(comentarioRequestDto.getUsuarioId());			
			break;
		default:
			break;
		
		}
	
		cc.setBaja(false);
		cc.setComentario(comentarioRequestDto.getMensaje());
		cc.setFecha(new java.util.Date());
		if(comentarioRequestDto.getParentId()!=null){
			ComentarioCens ccp = new ComentarioCens();
			ccp.setId(comentarioRequestDto.getParentId());
			cc.setParent(ccp);
		}
		cc.setPrimero(cc.getParent()==null);
		cc.setTipoComentario(comentarioRequestDto.getTipoType());		
		cc.setTipoId(comentarioRequestDto.getTipoId());
		return cc;
	}

	public ComentarioDescriptionDto mapSingleComentario(ComentarioCens cc){
		ComentarioDescriptionDto dto =comentarioDescriptionDto(cc);
		dto.setParent_id(cc.getParent()!=null ? cc.getParent().getId() : 0l);
		dto.setSuccess(true);
		dto.setIn_reply_to(cc.getParent()!=null ? cc.getParent().getFullName() : "");
		return dto;
	}
	public void addComments(ComentariosDto dto, ComentarioCens cc) {
		ComentarioDto cDto =dto.getResults();
		cDto.setComments(Arrays.asList(createInternalComentarios(cc)));				
	}
	
	public void addComments(ComentariosDto dto, List<ComentarioCens> ccList){
		
		if(CollectionUtils.isNotEmpty(ccList)){
			Iterator<ComentarioCens> ccIterator = ccList.iterator();
			while(ccIterator.hasNext()){
				
				if(ccIterator.next().getParent()!=null){
					ccIterator.remove();
				}
			}
			if(CollectionUtils.isNotEmpty(ccList)){
				List<ComentarioDescriptionDto> cddList = new ArrayList<ComentarioDescriptionDto>();
				for(ComentarioCens cc: ccList){
					cddList.add(createInternalComentarios(cc));					
				}
				dto.getResults().setComments(cddList);
			}
		}
	}
	private ComentarioDescriptionDto createInternalComentarios(ComentarioCens cc){
		ComentarioDescriptionDto dto = null;		
		if(cc!=null){
			dto = comentarioDescriptionDto(cc);
			if(dto!=null){
				List<ComentarioDescriptionDto> childrens = null;
				if(CollectionUtils.isNotEmpty(cc.getChildrens())){
					childrens = new ArrayList<ComentarioDescriptionDto>();
					for(ComentarioCens ccInterno : cc.getChildrens()){
						if(!ccInterno.getBaja()){
							ComentarioDescriptionDto ccChildren = createInternalComentarios(ccInterno);
							ccChildren.setParent_id(ccInterno.getId());
							ccChildren.setIn_reply_to(cc.getFullName());
							childrens.add(ccChildren);
						}
					}
					dto.setChildrens(childrens);
				
				}
				dto.setChildrens(childrens);
			}
		}else{
			dto = comentarioDescriptionDto(cc);
		}
		return dto;
	}
	
	
	
	private  ComentarioDescriptionDto comentarioDescriptionDto(ComentarioCens cc){
		ComentarioDescriptionDto dto = null;
		if(cc!=null){
			dto = new ComentarioDescriptionDto();
			dto.setComment_id(cc.getId());
			if(cc.getAsesor()!=null){
				dto.setCreated_by(cc.getAsesor().getId());
			}else if(cc.getProfesor()!=null){
				dto.setCreated_by(cc.getProfesor().getId());
			}
			dto.setElement_id(cc.getId());
			dto.setFullname(cc.getFullName());
			dto.setIn_reply_to(null);
			dto.setParent_id(null);
			dto.setPicture("/css/midasUI-theme/images/user_blank_picture.png");
			dto.setPosted_date(new SimpleDateFormat("yyyy-MM-dd kk:mm:ss").format(cc.getFecha()));
			dto.setText(cc.getComentario());
			if(cc.getFileCensInfo()!=null){
				
				dto.setAttachments(cc.getFileCensInfo().getFileName());
			}
			
		}
		return dto;
	}
}
	



