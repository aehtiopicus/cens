package com.aehtiopicus.cens.aspect.cens.mappers;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.aehtiopicus.cens.domain.entities.ActivityFeed;
import com.aehtiopicus.cens.domain.entities.CambioEstadoCensFeed;
import com.aehtiopicus.cens.domain.entities.MaterialDidactico;
import com.aehtiopicus.cens.domain.entities.Profesor;
import com.aehtiopicus.cens.domain.entities.Programa;
import com.aehtiopicus.cens.enumeration.cens.ComentarioType;
import com.aehtiopicus.cens.enumeration.cens.EstadoRevisionType;
import com.aehtiopicus.cens.enumeration.cens.PerfilTrabajadorCensType;

@Component
public class CambioEstadoFeedMapper {

	public CambioEstadoCensFeed convertProgramaToCambioEstadoFeed(
			Programa programa, EstadoRevisionType estadoRevision) {
		CambioEstadoCensFeed cecf = new CambioEstadoCensFeed();
		cecf.setEstadoRevisionType(estadoRevision);
		cecf.setTipoId(programa.getId());		
		cecf.setActivityFeed(createActivityFeed(programa.getProfesor(),estadoRevision));
		cecf.getActivityFeed().setComentarioType(ComentarioType.PROGRAMA);
		cecf.setEstadoRevisionTypeViejo(programa.getEstadoRevisionType());
		return cecf;
	}
	
	public CambioEstadoCensFeed convertMaterialDidacticoToCambioEstadoFeed(
			MaterialDidactico md, EstadoRevisionType estadoRevision) {
		CambioEstadoCensFeed cecf = new CambioEstadoCensFeed();
		cecf.setEstadoRevisionType(estadoRevision);
		cecf.setTipoId(md.getId());
		cecf.setActivityFeed(createActivityFeed(md.getProfesor(),estadoRevision));
		cecf.getActivityFeed().setComentarioType(ComentarioType.MATERIAL);
		cecf.setEstadoRevisionTypeViejo(md.getEstadoRevisionType());
		return cecf;
	}

	private ActivityFeed createActivityFeed(Profesor profesor,EstadoRevisionType estadoRevision) {
				
		ActivityFeed af = new ActivityFeed();
		af.setDateCreated(new Date());
		switch(estadoRevision){
		case NUEVO:
		case LISTO:
			af.setFromPerfil(PerfilTrabajadorCensType.PROFESOR);	
			af.setFromId(profesor.getMiembroCens().getId());
			af.setToPerfil(PerfilTrabajadorCensType.ASESOR);
			break;
		default:
			af.setFromPerfil(PerfilTrabajadorCensType.ASESOR);
			af.setFromId(-1L);//algun asesor
			af.setToPerfil(PerfilTrabajadorCensType.PROFESOR);
			af.setToId(profesor.getMiembroCens().getId());
			break;
						
		}
		af.setNotificado(false);
		af.setReaded(false);
		return af;
		
	}

}
