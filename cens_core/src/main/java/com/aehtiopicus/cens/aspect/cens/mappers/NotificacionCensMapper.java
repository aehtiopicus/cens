package com.aehtiopicus.cens.aspect.cens.mappers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
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

	public Map<ComentarioTypeComentarioIdKey, String> mapNotificationSorted(
			Map<ComentarioType, List<NotificacionComentarioFeed>> sortedComentarios) {
		Map<ComentarioTypeComentarioIdKey,String> resultMap = new HashMap<ComentarioTypeComentarioIdKey, String>();
		for(Entry<ComentarioType, List<NotificacionComentarioFeed>> mapEntry : sortedComentarios.entrySet()){
			for(NotificacionComentarioFeed ncf : mapEntry.getValue()){
				resultMap.put(new ComentarioTypeComentarioIdKey(ncf.getComentarioType(), ncf.getTipoId()), "");
			}
		}
		return resultMap;
		

	}
}
