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

import com.aehtiopicus.cens.domain.entities.ComentarioTypeComentarioIdKey;
import com.aehtiopicus.cens.domain.entities.NotificacionComentarioFeed;
import com.aehtiopicus.cens.enumeration.cens.ComentarioType;

@Component
public class NotificacionCensMapper {

	public Map<ComentarioType,List<NotificacionComentarioFeed>> comentarioMapper(List<NotificacionComentarioFeed> notificationList){
		List<NotificacionComentarioFeed> ncfPrograma = new ArrayList<NotificacionComentarioFeed>();
		List<NotificacionComentarioFeed> ncfMaterial = new ArrayList<NotificacionComentarioFeed>();
		Map<ComentarioType,List<NotificacionComentarioFeed>> resultMap  = new HashMap<ComentarioType, List<NotificacionComentarioFeed>>();
		
		for(NotificacionComentarioFeed ncf : notificationList){
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
	
	class SortComentarioCollectionByDate implements Comparator<NotificacionComentarioFeed>{

		@Override
		public int compare(NotificacionComentarioFeed o1, NotificacionComentarioFeed o2) {
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

	public Map<ComentarioTypeComentarioIdKey, Map<String,String>> mapNotificationSorted(
			Map<ComentarioType, List<NotificacionComentarioFeed>> sortedComentarios) {
		Map<ComentarioTypeComentarioIdKey,Map<String,String>> resultMap = new HashMap<ComentarioTypeComentarioIdKey, Map<String,String>>();
		for(Entry<ComentarioType, List<NotificacionComentarioFeed>> mapEntry : sortedComentarios.entrySet()){
			for(NotificacionComentarioFeed ncf : mapEntry.getValue()){
				resultMap.put(new ComentarioTypeComentarioIdKey(ncf.getComentarioType(), ncf.getTipoId(),ncf.getFechaCreacion()), new HashMap<String,String>());
			}
		}
		return resultMap;
		

	}

	public void convertToNotificacion(
			Map<ComentarioType, List<NotificacionComentarioFeed>> sortedComentarios,
			Map<ComentarioTypeComentarioIdKey, Map<String,String>> informationToRetrieve) {
		for(Map.Entry<ComentarioType, List<NotificacionComentarioFeed>> sortedComentario : sortedComentarios.entrySet()){			
					
			Iterator<NotificacionComentarioFeed> ncfIterator  = sortedComentario.getValue().iterator();
			Map<ComentarioTypeComentarioIdKey,Integer> count = new HashMap<>();
			while(ncfIterator.hasNext()){
						
				NotificacionComentarioFeed ncf = ncfIterator.next();
				ComentarioTypeComentarioIdKey ctcik = new ComentarioTypeComentarioIdKey(sortedComentario.getKey(), ncf.getTipoId(),ncf.getFechaCreacion());
					
				if(count.containsKey(ctcik)){
					count.put(ctcik, count.get(ctcik)+1);
					ncfIterator.remove();
				}else{
					count.put(ctcik, 1);
				}
			}
			for(NotificacionComentarioFeed ncf : sortedComentario.getValue()){
				ComentarioTypeComentarioIdKey ctcik = new ComentarioTypeComentarioIdKey(sortedComentario.getKey(), ncf.getTipoId(),ncf.getFechaCreacion());
				if(informationToRetrieve.containsKey(ctcik)){
					ncf.setCantidad(count.get(ctcik));	
					assembleMessageData(ncf,informationToRetrieve.get(ctcik));
				}
			}					
		}
		
	}
	
	private void assembleMessageData(NotificacionComentarioFeed ncf,Map<String,String> dataMap){	
		dataMap.put("Fecha", new SimpleDateFormat("dd/MM/yyyy").format(ncf.getFechaCreacion()));
		dataMap.put("Notificado", ""+ncf.getNotificado());
		dataMap.put("Cantidad", ""+(ncf.getCantidad() == 0 ? 1 : ncf.getCantidad()));
		ncf.setDisplayTextMap(dataMap);
	}

	public Map<ComentarioType, List<Map<String,String>>> convertToNotificacionData(
			Map<ComentarioType, List<NotificacionComentarioFeed>> sortedComentarios) {
		Map<ComentarioType, List<Map<String,String>>> result = new HashMap<>();
		List<Map<String,String>> dataTextMaps = null;		
		for(Map.Entry<ComentarioType, List<NotificacionComentarioFeed>> entry : sortedComentarios.entrySet()){
			dataTextMaps = new ArrayList<>();
			for(NotificacionComentarioFeed ncf : entry.getValue()){
				dataTextMaps.add(ncf.getDisplayTextMap());
			}
			result.put(entry.getKey(), dataTextMaps);
		}
		return result;
		
	}
}
