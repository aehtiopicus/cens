package com.aehtiopicus.cens.aspect.cens.mappers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.aehtiopicus.cens.domain.entities.ActivityFeed;
import com.aehtiopicus.cens.domain.entities.ComentarioCens;
import com.aehtiopicus.cens.domain.entities.ComentarioCensFeed;
import com.aehtiopicus.cens.enumeration.cens.PerfilTrabajadorCensType;
import com.aehtiopicus.cens.service.cens.CensServiceConstant;
import com.aehtiopicus.cens.utils.CensException;

@Component
public class ComentarioCensFeedMapper {

	
	public ComentarioCensFeed convertComentarioToFeed(ComentarioCens comentarioCens,Long originalInitializer, PerfilTrabajadorCensType originalPtct) throws CensException{
		ComentarioCensFeed feed = null;
		if(comentarioCens==null ){
			throw new CensException("Comentario nulo feed no creado");
		}
		feed = new ComentarioCensFeed();		
		feed.setComentarioCensId(comentarioCens.getId());
		ActivityFeed aFeed = new ActivityFeed();
		aFeed.setDateCreated(new Date());
		aFeed.setComentarioType(comentarioCens.getTipoComentario());
		
		Map<String,Object> activityFeedData = new HashMap<String, Object>();
		userData(comentarioCens, activityFeedData, originalInitializer, originalPtct);
		aFeed.setFromId(activityFeedData.get(CensServiceConstant.FROM_ID) != null ? (Long)activityFeedData.get(CensServiceConstant.FROM_ID) : null);
		aFeed.setFromPerfil(activityFeedData.get(CensServiceConstant.FROM_PERFIL) != null ? (PerfilTrabajadorCensType)activityFeedData.get(CensServiceConstant.FROM_PERFIL) : null);
		aFeed.setToId(activityFeedData.get(CensServiceConstant.TO_ID) != null ? (Long)activityFeedData.get(CensServiceConstant.TO_ID) : null);
		aFeed.setToPerfil(activityFeedData.get(CensServiceConstant.TO_PERFIL) != null ? (PerfilTrabajadorCensType)activityFeedData.get(CensServiceConstant.TO_PERFIL) : null);
		feed.setActivityFeed(aFeed);
		return feed;
	}
	
	private void userData(ComentarioCens comentarioCens,Map<String,Object> activityFeedData,Long originalInitializer, PerfilTrabajadorCensType originalPtct){
		
		loadCurrentData(CensServiceConstant.FROM_ID,CensServiceConstant.FROM_PERFIL,comentarioCens,activityFeedData);
		if(comentarioCens.getParent()!=null){
			loadCurrentData(CensServiceConstant.TO_ID,CensServiceConstant.TO_PERFIL,comentarioCens.getParent(),activityFeedData);
		}else{
			if(((PerfilTrabajadorCensType)activityFeedData.get(CensServiceConstant.FROM_PERFIL)).equals(PerfilTrabajadorCensType.PROFESOR)){
				activityFeedData.put(CensServiceConstant.TO_PERFIL, PerfilTrabajadorCensType.ASESOR);//broadcast message
			}else{
				activityFeedData.put(CensServiceConstant.TO_ID,originalInitializer);
				activityFeedData.put(CensServiceConstant.TO_PERFIL,originalPtct);
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
