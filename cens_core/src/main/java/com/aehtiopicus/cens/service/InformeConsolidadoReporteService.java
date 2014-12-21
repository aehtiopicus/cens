package com.aehtiopicus.cens.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

import com.aehtiopicus.cens.domain.InformeConsolidadoDetalle;

@Service
public interface InformeConsolidadoReporteService {

	public HSSFWorkbook getInformeExcel(List<InformeConsolidadoDetalle> detalles, Date periodo) throws IOException;

	void crearEncabezadoEstadoInformesMensuales(HSSFSheet worksheet, HSSFWorkbook workbook);

	void addRegistroEstadoInformesMensuales(HSSFSheet worksheet, HSSFWorkbook workbook, String[] data, int rowIndex);

}
