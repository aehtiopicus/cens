package com.aehtiopicus.cens.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.aehtiopicus.cens.domain.InformeConsolidadoDetalle;

@Service
public interface LiquidacionService {

	public List<String> generarArchivosLiquidacion(Date fechaAcreditacion,List<InformeConsolidadoDetalle> detalle,String referencia,String periodo) throws FileNotFoundException, IOException;

	
	
}
