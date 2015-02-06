package com.aehtiopicus.cens.service.cens;

import java.io.OutputStream;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.aehtiopicus.cens.domain.entities.MaterialDidactico;
import com.aehtiopicus.cens.domain.entities.RestRequest;
import com.aehtiopicus.cens.enumeration.cens.DivisionPeriodoType;
import com.aehtiopicus.cens.enumeration.cens.EstadoRevisionType;
import com.aehtiopicus.cens.utils.CensException;

public interface MaterialDidacticoCensService {

	public List<MaterialDidactico> listMaterialDidactico(RestRequest restRequest);

	public long getTotalMaterial(RestRequest restRequest);

	public List<DivisionPeriodoType> listDivisionPeriodo();

	public MaterialDidactico saveMaterialDidactico(MaterialDidactico md,
			MultipartFile file) throws CensException;

	public MaterialDidactico findById(Long materialId);

	public void getArchivoAdjunto(String string, OutputStream baos) throws CensException;

	public void removeMaterialDidactico(Long materialId);

	public void updateMaterialDidacticoStatus(Long materialId,
			EstadoRevisionType estadoRevisionType);

}
