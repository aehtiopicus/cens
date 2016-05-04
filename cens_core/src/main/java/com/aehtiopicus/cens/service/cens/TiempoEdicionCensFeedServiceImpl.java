package com.aehtiopicus.cens.service.cens;

import java.util.List;

import org.springframework.stereotype.Service;

import com.aehtiopicus.cens.domain.entities.NotificacionTiempoEdicionFeed;
import com.aehtiopicus.cens.enumeration.cens.ComentarioType;
import com.aehtiopicus.cens.enumeration.cens.TiempoEdicionReporteType;
import com.aehtiopicus.cens.utils.CensException;

@Service
public class TiempoEdicionCensFeedServiceImpl implements TiempoEdicionCensFeedService{

	@Override
	public List<NotificacionTiempoEdicionFeed> getGeneratedFeeds(String username, boolean email) throws CensException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<NotificacionTiempoEdicionFeed> getUnReadFeeds() throws CensException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int markCommentsAsIgnored(Long tipoId, ComentarioType tipoType) throws CensException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public TiempoEdicionReporteType save(TiempoEdicionReporteType mValue) throws CensException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void markAllFeedsForUserAsNotified(String username) throws CensException {
		// TODO Auto-generated method stub
		
	}

}
