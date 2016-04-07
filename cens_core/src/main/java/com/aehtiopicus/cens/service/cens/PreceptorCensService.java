package com.aehtiopicus.cens.service.cens;

import com.aehtiopicus.cens.domain.entities.MiembroCens;
import com.aehtiopicus.cens.domain.entities.Preceptor;
import com.aehtiopicus.cens.utils.CensException;

public interface PreceptorCensService {

	public Preceptor savePreceptor(MiembroCens miembroCens) throws CensException;

	public Preceptor getPreceptor(MiembroCens usuario);

	public void deletePreceptor(MiembroCens miembroCens);

	public Preceptor findById(Long id);

}
