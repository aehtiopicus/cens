package com.aehtiopicus.cens.aspect.cens.mappers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Component;

import com.aehtiopicus.cens.domain.entities.AbstractNotificacionFeed;
import com.aehtiopicus.cens.domain.entities.NotificacionTypeComentarioIdKey;
import com.aehtiopicus.cens.enumeration.cens.ComentarioType;
import com.aehtiopicus.cens.enumeration.cens.NotificacionType;
import com.aehtiopicus.cens.service.cens.CensServiceConstant;

@Component
public class NotificacionCensMapper {
	
	private Map<ComentarioType,List<? extends AbstractNotificacionFeed>> genericMapper(List<? extends AbstractNotificacionFeed> notificationList){
		
		List<AbstractNotificacionFeed> ncfPrograma = new ArrayList<>();
		List<AbstractNotificacionFeed> ncfMaterial = new ArrayList<>();
		List<AbstractNotificacionFeed> ncfTEMaterial = new ArrayList<>();
		List<AbstractNotificacionFeed> ncfTEPrograma = new ArrayList<>();
		List<AbstractNotificacionFeed> ncfTEAsignatura = new ArrayList<>();
		
		
		for(AbstractNotificacionFeed ncf : notificationList){			
			switch(ncf.getComentarioType() ){
			case MATERIAL:
				ncfMaterial.add(ncf);
				break;
			case PROGRAMA:
				ncfPrograma.add(ncf);
				break;		
			case TE_ASIGNATURA:
				ncfTEAsignatura.add(ncf);
				break;
			case TE_MATERIAL:
				ncfTEMaterial.add(ncf);
				break;
			case TE_PROGRAMA:
				ncfTEPrograma.add(ncf);
				break;
			}
		}
		Map<ComentarioType,List<? extends AbstractNotificacionFeed>> resultMap  = new HashMap<ComentarioType, List<? extends AbstractNotificacionFeed>>();
		
		putDataToCollection(ncfMaterial, ComentarioType.MATERIAL, resultMap);
		putDataToCollection(ncfPrograma, ComentarioType.PROGRAMA, resultMap);
		putDataToCollection(ncfTEAsignatura, ComentarioType.TE_ASIGNATURA, resultMap);
		putDataToCollection(ncfTEMaterial, ComentarioType.TE_MATERIAL, resultMap);
		putDataToCollection(ncfTEPrograma, ComentarioType.TE_PROGRAMA, resultMap);
				
		
		return resultMap;
		
	}
	
	private void putDataToCollection(List<AbstractNotificacionFeed> listFeed,ComentarioType comentarioType,Map<ComentarioType,List<? extends AbstractNotificacionFeed>> map){
		if(org.apache.commons.collections.CollectionUtils.isNotEmpty(listFeed)){
			Collections.sort(listFeed, new SortComentarioCollectionByDate());
			map.put(comentarioType, listFeed);
		}
	}
	
	class SortComentarioCollectionByDate implements Comparator<AbstractNotificacionFeed>{

		@Override
		public int compare(AbstractNotificacionFeed o1, AbstractNotificacionFeed o2) {
			if( o1.getFechaCreacion().before(o2.getFechaCreacion())){
				return -1;
			}
			if( o1.getFechaCreacion().after(o2.getFechaCreacion())){
				return 1;
			}else{
				return 0;
			}
		}
		
	}

	public Map<NotificacionTypeComentarioIdKey, Map<String,String>> mapNotificationSorted(final 
			Map<ComentarioType, List<? extends AbstractNotificacionFeed>> sortedComentarios,NotificacionType nt) {
		
		Map<NotificacionTypeComentarioIdKey,Map<String,String>> resultMap = new HashMap<NotificacionTypeComentarioIdKey, Map<String,String>>();
		for(Entry<ComentarioType, List<? extends AbstractNotificacionFeed>> mapEntry : sortedComentarios.entrySet()){
			for(AbstractNotificacionFeed ncf : mapEntry.getValue()){
				resultMap.put(new NotificacionTypeComentarioIdKey(ncf.getComentarioType(), ncf.getTipoId(),ncf.getFechaCreacion(),nt,ncf.getToId()), new HashMap<String,String>());
			}
		}
		
		return resultMap;
		

	}
	
	
	
	private Map<NotificacionTypeComentarioIdKey,Integer> countRepeatedValues(Entry<ComentarioType,List<? extends AbstractNotificacionFeed>> sortedComentario,NotificacionType nt){
		Iterator<? extends AbstractNotificacionFeed> ncfIterator  = sortedComentario.getValue().iterator();
		Map<NotificacionTypeComentarioIdKey,Integer> count = new HashMap<>();
		
		while(ncfIterator.hasNext()){
					
			AbstractNotificacionFeed ncf = ncfIterator.next();
			NotificacionTypeComentarioIdKey ctcik = new NotificacionTypeComentarioIdKey(sortedComentario.getKey(), ncf.getTipoId(),ncf.getFechaCreacion(),nt,ncf.getToId());
				
			if(count.containsKey(ctcik)){
				count.put(ctcik, count.get(ctcik)+1);
				ncfIterator.remove();
			
			}else{
				count.put(ctcik, 1);
			
			}
		}
		return count;
	}
	
	
	
	private void assembleMessageData(AbstractNotificacionFeed ncf,Map<String,String> dataMaps){
		Map<String,String> dataMap = new HashMap<String, String>(dataMaps);
		dataMap.put(CensServiceConstant.COMENTARIO_FECHA, new SimpleDateFormat("dd/MM/yyyy").format(ncf.getFechaCreacion()));
		dataMap.put(CensServiceConstant.COMENTARIO_NOTIFICADO, ""+ncf.getNotificado());
		dataMap.put(CensServiceConstant.COMENTARIO_CANTIDAD, ""+(ncf.getCantidad() == 0 ? 1 : ncf.getCantidad()));
		if(ncf.getDaysAgo()!=null){
			dataMap.put(CensServiceConstant.COMENTARIO_DAYS_AGO, ""+ncf.getDaysAgo());
		}
		if(ncf.getFechaNotificacion() !=null){
			dataMap.put(CensServiceConstant.COMENTARIO_FECHA_NOTIFICADO, new SimpleDateFormat("dd/MM/yyyy").format(ncf.getFechaNotificacion()));
		}
		ncf.setDisplayTextMap(dataMap);
	}

	public Map<ComentarioType, List<Map<String,String>>> convertToNotificacionData(
			Map<ComentarioType, List<? extends AbstractNotificacionFeed>> sortedComentarios) {
		Map<ComentarioType, List<Map<String,String>>> result = new HashMap<>();
		List<Map<String,String>> dataTextMaps = null;		
		for(Map.Entry<ComentarioType, List<? extends AbstractNotificacionFeed>> entry : sortedComentarios.entrySet()){
			dataTextMaps = new ArrayList<>();
			for(AbstractNotificacionFeed ncf : entry.getValue()){
				if(MapUtils.isNotEmpty(ncf.getDisplayTextMap())){
					dataTextMaps.add(ncf.getDisplayTextMap());
				}
			}
			if(CollectionUtils.isNotEmpty(dataTextMaps)){
				result.put(entry.getKey(), dataTextMaps);
			}
		}
		return result;
		
	}


	@SuppressWarnings("unchecked")
	public Map<ComentarioType, List<AbstractNotificacionFeed>> generalNotificationMapper(
			List<AbstractNotificacionFeed> list) {
		Map<ComentarioType,List<AbstractNotificacionFeed>> resultMap  = new HashMap<ComentarioType, List<AbstractNotificacionFeed>>();
		
		
		for(Map.Entry<ComentarioType, List <? extends AbstractNotificacionFeed>> dataMap : genericMapper(list).entrySet()){
					resultMap.put(dataMap.getKey(), (List <AbstractNotificacionFeed>)dataMap.getValue());
		}
		
		return resultMap;
	}

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void convertToNotificacion(
			Map<ComentarioType, List<AbstractNotificacionFeed>> sortedActividad,
			Map<NotificacionTypeComentarioIdKey, Map<String, String>> informationToRetrieve,NotificacionType notificacion,NotificacionType notificacionType) {
		for(Map.Entry<ComentarioType, List<AbstractNotificacionFeed>> sortedComentario : sortedActividad.entrySet()){			
					
			
			Map<NotificacionTypeComentarioIdKey,Integer> count = countRepeatedValues((Entry)sortedComentario, notificacion);
			for(AbstractNotificacionFeed ncf : sortedComentario.getValue()){
				NotificacionTypeComentarioIdKey ctcik = new NotificacionTypeComentarioIdKey(sortedComentario.getKey(), ncf.getTipoId(),ncf.getFechaCreacion(),notificacionType,ncf.getToId());
				if(informationToRetrieve.containsKey(ctcik)){
					ncf.setCantidad(count.get(ctcik));	
					assembleMessageData(ncf, informationToRetrieve.get(ctcik));					
					switch(notificacion){
					case ACTIVIDAD:
						ncf.getDisplayTextMap().put(CensServiceConstant.ESTADO_REVISION, ncf.getEstadoRevisionType().toString());
						break;					
					case TIEMPO_EDICION:
						ncf.getDisplayTextMap().put(CensServiceConstant.ESTADO_REVISION, ncf.getEstadoRevisionType().toString());
						break;
					case COMENTARIO:
					case ACTIVIDAD_CREACION:
					default:
						break;
					
					}
					
				}
			}					
		}
		
	}
	
	
}
