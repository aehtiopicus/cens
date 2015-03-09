package com.aehtiopicus.cens.aspect.cens.mappers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.aehtiopicus.cens.domain.entities.ActivityFeed;
import com.aehtiopicus.cens.domain.entities.ComentarioCens;
import com.aehtiopicus.cens.domain.entities.ComentarioCensFeed;
import com.aehtiopicus.cens.enumeration.cens.PerfilTrabajadorCensType;
import com.aehtiopicus.cens.utils.CensException;

@Component
public class ComentarioCensFeedMapper {

	private static final String FROM_ID ="id_from";
	private static final String FROM_PERFIL ="perfil_from";
	private static final String TO_ID ="id_to";
	private static final String TO_PERFIL ="perfil_to";
	
	public ComentarioCensFeed convertComentarioToFeed(ComentarioCens comentarioCens,Long originalInitializer, PerfilTrabajadorCensType originalPtct) throws CensException{
		ComentarioCensFeed feed = null;
		if(comentarioCens==null ){
			throw new CensException("Comentario nulo feed no creado");
		}
		feed = new ComentarioCensFeed();
		feed.setComentarioType(comentarioCens.getTipoComentario());
		feed.setComentarioCensId(comentarioCens.getId());
		ActivityFeed aFeed = new ActivityFeed();
		aFeed.setDateCreated(new Date());
		
		Map<String,Object> activityFeedData = new HashMap<String, Object>();
		userData(comentarioCens, activityFeedData, originalInitializer, originalPtct);
		aFeed.setFromId(activityFeedData.get(FROM_ID) != null ? (Long)activityFeedData.get(FROM_ID) : null);
		aFeed.setFromPerfil(activityFeedData.get(FROM_PERFIL) != null ? (PerfilTrabajadorCensType)activityFeedData.get(FROM_PERFIL) : null);
		aFeed.setToId(activityFeedData.get(TO_ID) != null ? (Long)activityFeedData.get(TO_ID) : null);
		aFeed.setToPerfil(activityFeedData.get(TO_PERFIL) != null ? (PerfilTrabajadorCensType)activityFeedData.get(TO_PERFIL) : null);
		feed.setActivityFeed(aFeed);
		return feed;
	}
	
	private void userData(ComentarioCens comentarioCens,Map<String,Object> activityFeedData,Long originalInitializer, PerfilTrabajadorCensType originalPtct){
		
		loadCurrentData(FROM_ID,FROM_PERFIL,comentarioCens,activityFeedData);
		if(comentarioCens.getParent()!=null){
			loadCurrentData(TO_ID,TO_PERFIL,comentarioCens.getParent(),activityFeedData);
		}else{
			if(((PerfilTrabajadorCensType)activityFeedData.get(FROM_PERFIL)).equals(PerfilTrabajadorCensType.PROFESOR)){
				activityFeedData.put(TO_PERFIL, PerfilTrabajadorCensType.ASESOR);//broadcast message
			}else{
				activityFeedData.put(TO_ID,originalInitializer);
				activityFeedData.put(TO_PERFIL,originalPtct);
			}
		}
			
	}
	
	private void loadCurrentData(String id,String perfil,ComentarioCens comentarioCens, Map<String,Object> activityFeedData){
		if(comentarioCens.getProfesor()!=null){
			activityFeedData.put(id,comentarioCens.getProfesor().getMiembroCens().getId());
			activityFeedData.put(perfil,PerfilTrabajadorCensType.PROFESOR);
		}else if(comentarioCens.getAsesor()!=null){
			activityFeedData.put(id,comentarioCens.getAsesor().getMiembroCens().getId());
			activityFeedData.put(perfil,PerfilTrabajadorCensType.ASESOR);
		}
	}
}
