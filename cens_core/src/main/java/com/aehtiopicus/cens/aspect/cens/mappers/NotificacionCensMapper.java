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

import org.springframework.stereotype.Component;

import com.aehtiopicus.cens.domain.entities.AbstractNotificacionFeed;
import com.aehtiopicus.cens.domain.entities.NotificacionCambioEstadoFeed;
import com.aehtiopicus.cens.domain.entities.NotificacionComentarioFeed;
import com.aehtiopicus.cens.domain.entities.NotificacionTypeComentarioIdKey;
import com.aehtiopicus.cens.enumeration.cens.ComentarioType;
import com.aehtiopicus.cens.enumeration.cens.NotificacionType;
import com.aehtiopicus.cens.service.cens.CensServiceConstant;

@Component
public class NotificacionCensMapper {

	@SuppressWarnings("unchecked")
	public Map<ComentarioType,List<NotificacionComentarioFeed>> comentarioMapper(List<NotificacionComentarioFeed> notificationList){
		
		Map<ComentarioType,List<NotificacionComentarioFeed>> resultMap  = new HashMap<ComentarioType, List<NotificacionComentarioFeed>>();
		
		
		for(Map.Entry<ComentarioType, List <? extends AbstractNotificacionFeed>> dataMap : genericMapper(notificationList).entrySet()){
					resultMap.put(dataMap.getKey(), (List <NotificacionComentarioFeed>)dataMap.getValue());
		}
		
		return resultMap;
		
	}
	
	private Map<ComentarioType,List<? extends AbstractNotificacionFeed>> genericMapper(List<? extends AbstractNotificacionFeed> notificationList){
		
		List<AbstractNotificacionFeed> ncfPrograma = new ArrayList<AbstractNotificacionFeed>();
		List<AbstractNotificacionFeed> ncfMaterial = new ArrayList<AbstractNotificacionFeed>();
		Map<ComentarioType,List<? extends AbstractNotificacionFeed>> resultMap  = new HashMap<ComentarioType, List<? extends AbstractNotificacionFeed>>();
		
		for(AbstractNotificacionFeed ncf : notificationList){
			switch(ncf.getComentarioType()){
			case MATERIAL:
				ncfMaterial.add(ncf);
				break;
			case PROGRAMA:
				ncfPrograma.add(ncf);
				break;		
			
			}
		}
		if(!ncfMaterial.isEmpty()){
			Collections.sort(ncfMaterial, new SortComentarioCollectionByDate());
			resultMap.put(ComentarioType.MATERIAL, ncfMaterial);
		}
		
		if(!ncfPrograma.isEmpty()){
			Collections.sort(ncfPrograma, new SortComentarioCollectionByDate());
			resultMap.put(ComentarioType.PROGRAMA, ncfPrograma);
		}
		
		return resultMap;
		
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

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void convertToComentarioNotificacion(
			Map<ComentarioType, List<NotificacionComentarioFeed>> sortedComentarios,
			Map<NotificacionTypeComentarioIdKey, Map<String,String>> informationToRetrieve) {
		for(Map.Entry<ComentarioType, List<NotificacionComentarioFeed>> sortedComentario : sortedComentarios.entrySet()){			
					
			
			Map<NotificacionTypeComentarioIdKey,Integer> count = countRepeatedValues((Entry)sortedComentario, NotificacionType.COMENTARIO);
			for(NotificacionComentarioFeed ncf : sortedComentario.getValue()){
				NotificacionTypeComentarioIdKey ctcik = new NotificacionTypeComentarioIdKey(sortedComentario.getKey(), ncf.getTipoId(),ncf.getFechaCreacion(),NotificacionType.COMENTARIO,ncf.getToId());
				if(informationToRetrieve.containsKey(ctcik)){
					ncf.setCantidad(count.get(ctcik));	
					assembleMessageData(ncf, informationToRetrieve.get(ctcik));
//					ncf.getDisplayTextMap().put(CensServiceConstant.ESTADO_REVISION, ncf.getEstadoRevisionType().toString());
				}
			}					
		}
		
	}
	
	private Map<NotificacionTypeComentarioIdKey,Integer> countRepeatedValues(Entry<ComentarioType,List<? extends AbstractNotificacionFeed>> sortedComentario,NotificacionType nt){
		Iterator<? extends AbstractNotificacionFeed> ncfIterator  = sortedComentario.getValue().iterator();
		Map<NotificacionTypeComentarioIdKey,Integer> count = new HashMap<>();
		int suma = 0;
		while(ncfIterator.hasNext()){
					
			AbstractNotificacionFeed ncf = ncfIterator.next();
			NotificacionTypeComentarioIdKey ctcik = new NotificacionTypeComentarioIdKey(sortedComentario.getKey(), ncf.getTipoId(),ncf.getFechaCreacion(),nt,ncf.getToId());
				
			if(count.containsKey(ctcik)){
				count.put(ctcik, count.get(ctcik)+1);
				ncfIterator.remove();
				suma++;
			}else{
				count.put(ctcik, 1);
				suma++;
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
				dataTextMaps.add(ncf.getDisplayTextMap());
			}
			result.put(entry.getKey(), dataTextMaps);
		}
		return result;
		
	}


	@SuppressWarnings("unchecked")
	public Map<ComentarioType, List<NotificacionCambioEstadoFeed>> actividadMapper(
			List<NotificacionCambioEstadoFeed> list) {
		Map<ComentarioType,List<NotificacionCambioEstadoFeed>> resultMap  = new HashMap<ComentarioType, List<NotificacionCambioEstadoFeed>>();
		
		
		for(Map.Entry<ComentarioType, List <? extends AbstractNotificacionFeed>> dataMap : genericMapper(list).entrySet()){
					resultMap.put(dataMap.getKey(), (List <NotificacionCambioEstadoFeed>)dataMap.getValue());
		}
		
		return resultMap;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void convertToActividadNotificacion(
			Map<ComentarioType, List<NotificacionCambioEstadoFeed>> sortedActividad,
			Map<NotificacionTypeComentarioIdKey, Map<String, String>> informationToRetrieve) {
		for(Map.Entry<ComentarioType, List<NotificacionCambioEstadoFeed>> sortedComentario : sortedActividad.entrySet()){			
					
			
			Map<NotificacionTypeComentarioIdKey,Integer> count = countRepeatedValues((Entry)sortedComentario, NotificacionType.ACTIVIDAD);
			for(NotificacionCambioEstadoFeed ncf : sortedComentario.getValue()){
				NotificacionTypeComentarioIdKey ctcik = new NotificacionTypeComentarioIdKey(sortedComentario.getKey(), ncf.getTipoId(),ncf.getFechaCreacion(),NotificacionType.ACTIVIDAD,ncf.getToId());
				if(informationToRetrieve.containsKey(ctcik)){
					ncf.setCantidad(count.get(ctcik));	
					assembleMessageData(ncf, informationToRetrieve.get(ctcik));
					ncf.getDisplayTextMap().put(CensServiceConstant.ESTADO_REVISION, ncf.getEstadoRevisionType().toString());
				}
			}					
		}
		
	}
	
	
}
