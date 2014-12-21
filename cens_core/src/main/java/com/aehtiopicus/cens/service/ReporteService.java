package com.aehtiopicus.cens.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.aehtiopicus.cens.domain.Empleado;

public interface ReporteService {

	
	public HSSFWorkbook getReporte(List<Empleado> empleados) throws FileNotFoundException, IOException;
}
