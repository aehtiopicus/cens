package com.aehtiopicus.cens.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

import com.aehtiopicus.cens.domain.Cliente;
import com.aehtiopicus.cens.domain.InformeMensualDetalle;

@Service
public interface InformeMensualReporteService {

	public HSSFWorkbook getInformeExcel(List<InformeMensualDetalle> detalles, Date periodo, Cliente cliente) throws IOException;


}
