package com.aehtiopicus.cens.mapper.cens;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import com.aehtiopicus.cens.domain.entities.Notificacion;
import com.aehtiopicus.cens.dto.cens.AbstractNotificacionItemDto;
import com.aehtiopicus.cens.dto.cens.ActividadNotificacionDto;
import com.aehtiopicus.cens.dto.cens.AsignaturaNotificacionDto;
import com.aehtiopicus.cens.dto.cens.ComentarioNotificacionDto;
import com.aehtiopicus.cens.dto.cens.CursoNotificacionDto;
import com.aehtiopicus.cens.dto.cens.MaterialNotificacionDto;
import com.aehtiopicus.cens.dto.cens.NotificacionDto;
import com.aehtiopicus.cens.dto.cens.ProgramaNotificacionDto;
import com.aehtiopicus.cens.enumeration.cens.ComentarioType;
import com.aehtiopicus.cens.enumeration.cens.NotificacionType;
import com.aehtiopicus.cens.service.cens.CensServiceConstant;

@Component
public class NotificacionCensControllerMapper {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public NotificacionDto getDtoFromVO(Notificacion notificacionesForUser) {
		NotificacionDto nDto = null;
		if(notificacionesForUser != null){
			nDto = new NotificacionDto();
			nDto.setPerfilRol(notificacionesForUser.getPerfilRol());
			if(notificacionesForUser.getData()!=null ){
				Set<CursoNotificacionDto> cnDtoSet = null;
				if(notificacionesForUser.getData().containsKey(NotificacionType.COMENTARIO.name())){											
					cnDtoSet = convertCursoList((Map)notificacionesForUser.getData().get(NotificacionType.COMENTARIO.name()));
					
					if(CollectionUtils.isNotEmpty(cnDtoSet)){
						ComentarioNotificacionDto cnDto = new ComentarioNotificacionDto();
						cnDto.setCurso(cnDtoSet);
						nDto.setComentario(cnDto);
					}
				}
				if(notificacionesForUser.getData().containsKey(NotificacionType.ACTIVIDAD.name())){
					cnDtoSet = convertCursoList((Map)notificacionesForUser.getData().get(NotificacionType.ACTIVIDAD.name()));
					
					if(CollectionUtils.isNotEmpty(cnDtoSet)){
						ActividadNotificacionDto anDto = new ActividadNotificacionDto();
						anDto.setCurso(cnDtoSet);
						nDto.setActividad(anDto);
					}
				}
								
			}
		}
		nDto.setCantidadNotificaciones();
		return nDto;
	}

	
	private Set<CursoNotificacionDto> convertCursoList(
			Map<ComentarioType,List<Map<String,String>>> mapData) {
		//comentarios
		Set<CursoNotificacionDto> cnDtoSet = new HashSet<>();			
		if(mapData!=null && !mapData.isEmpty()){
						
			
			CursoNotificacionDto cnDto = null;
			AsignaturaNotificacionDto aDto = null;
			ProgramaNotificacionDto pDto = null;
			MaterialNotificacionDto mDto = null;
			
			for(Map.Entry<ComentarioType, List<Map<String,String>>> comentarioListDataMap : mapData.entrySet()){
				
				for(Map<String,String> comentarioData : comentarioListDataMap.getValue()){
//					for(Map.Entry<String, String> comentarioData : comentarioListData.entrySet() ){
					cnDto = new CursoNotificacionDto();
					cnDto.setId(Long.valueOf(comentarioData.get(CensServiceConstant.COMENTARIO_CURSO_ID)));
					cnDto.setNombre(comentarioData.get(CensServiceConstant.COMENTARIO_CURSO)+", ("+comentarioData.get(CensServiceConstant.COMENTARIO_CURSO_YEAR)+")");
					
					if(!cnDtoSet.contains(cnDto)){
						cnDtoSet.add(cnDto);
					}else{
						for(CursoNotificacionDto aux : cnDtoSet){
							if(aux.equals(cnDto)){
								cnDto = aux;
								break;
							}
						}						
					}
				
					aDto = new AsignaturaNotificacionDto();
					aDto.setId(Long.valueOf(comentarioData.get(CensServiceConstant.COMENTARIO_ASIGNATURA_ID)));
					aDto.setNombre(comentarioData.get(CensServiceConstant.COMENTARIO_ASIGNATURA));
					
					if(!cnDto.getAsignatura().contains(aDto)){
						cnDto.getAsignatura().add(aDto);
					}else{
						for(AsignaturaNotificacionDto aux : cnDto.getAsignatura()){
							if(aux.equals(aDto)){
								aDto = aux;
								break;
							}
						}						
					}
					
					pDto = new ProgramaNotificacionDto();
					pDto.setId(Long.valueOf(comentarioData.get(CensServiceConstant.COMENTARIO_PROGRAMA_ID)));
					pDto.setNombre(comentarioData.get(CensServiceConstant.COMENTARIO_PROGRAMA));
					//removeLater if not needed after assemble all:S
					pDto.setFechaCreado(comentarioData.get(CensServiceConstant.COMENTARIO_FECHA));
					
					boolean old = false;
					
					if(!aDto.getPrograma().contains(pDto)){
						aDto.getPrograma().add(pDto);
					}else{
						for(ProgramaNotificacionDto aux : aDto.getPrograma()){
							if(aux.equals(pDto)){
								if(CollectionUtils.isEmpty(aux.getMaterial()) && comentarioData.containsKey(CensServiceConstant.COMENTARIO_MATERIAL_ID)){
									aDto.getPrograma().add(pDto);
									break;
								}
								if(!comentarioListDataMap.getKey().equals(ComentarioType.MATERIAL)){
									aux.setCantidadComnetarios(aux.getCantidadComnetarios()+Integer.parseInt(comentarioData.get(CensServiceConstant.COMENTARIO_CANTIDAD)));
								}
								
								pDto = aux;
								old = true;
								break;
							}
						}						
					}
					if(comentarioListDataMap.getKey().equals(ComentarioType.MATERIAL)){
						mDto = new MaterialNotificacionDto();
						mDto.setId(Long.valueOf(comentarioData.get(CensServiceConstant.COMENTARIO_MATERIAL_ID)));
						mDto.setNombre(comentarioData.get(CensServiceConstant.COMENTARIO_MATERIAL));						
						setSpecificNotificationData(mDto,comentarioData,false);
						mDto.setNro(Integer.parseInt(comentarioData.get(CensServiceConstant.COMENTARIO_MATERIAL_NRO).toString()));
							
						
						
						if(!pDto.getMaterial().contains(mDto)){
							pDto.getMaterial().add(mDto);
						}else{
							for(MaterialNotificacionDto aux : pDto.getMaterial()){
								if(aux.equals(mDto)){
									aux.setCantidadComnetarios(aux.getCantidadComnetarios()+mDto.getCantidadComnetarios());
									break;
								}
							}
						}
						
					}else{
						
						setSpecificNotificationData(pDto,comentarioData,old);
					}
					}
//				}	
			}
		}
		return cnDtoSet;
	}
	
	private void setSpecificNotificationData(AbstractNotificacionItemDto pDto,Map<String,String> comentarioData,boolean oldProgram){
		pDto.setFechaCreado(comentarioData.get(CensServiceConstant.COMENTARIO_FECHA));
		if(!oldProgram){
			pDto.setCantidadComnetarios(Integer.parseInt(comentarioData.get(CensServiceConstant.COMENTARIO_CANTIDAD)));
		}
		pDto.setNotificado(Boolean.valueOf(comentarioData.get(CensServiceConstant.COMENTARIO_NOTIFICADO)));
		if(comentarioData.containsKey(CensServiceConstant.COMENTARIO_DAYS_AGO)){
			pDto.setDiasNotificado(Integer.parseInt(comentarioData.get(CensServiceConstant.COMENTARIO_DAYS_AGO)));
		}
		if(comentarioData.containsKey(CensServiceConstant.COMENTARIO_FECHA_NOTIFICADO)){
			pDto.setFechaNotificado(comentarioData.get(CensServiceConstant.COMENTARIO_FECHA_NOTIFICADO));
		}
		if(comentarioData.containsKey(CensServiceConstant.ESTADO_REVISION)){
			pDto.setEstadoRevision(comentarioData.get(CensServiceConstant.ESTADO_REVISION));
		}
		
	}

}
