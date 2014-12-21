package com.aehtiopicus.cens.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.stereotype.Service;

import com.aehtiopicus.cens.domain.Cliente;
import com.aehtiopicus.cens.domain.InformeMensualDetalle;
import com.aehtiopicus.cens.domain.InformeMensualDetalleBeneficio;
import com.aehtiopicus.cens.domain.Sueldo;
import com.aehtiopicus.cens.utils.PeriodoUtils;

@Service
public class InformeMensualReporteServiceImpl implements InformeMensualReporteService {
	private static final Logger logger = Logger.getLogger(InformeMensualReporteServiceImpl.class);
	public final static String DD_MM_YYYY = "dd/MM/yyyy";
	
	@Override
	public HSSFWorkbook getInformeExcel(List<InformeMensualDetalle> detalles, Date periodo, Cliente cliente) throws IOException {
		
		logger.info("informe excel mensual");
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet worksheet = workbook.createSheet(cliente.getNombre() + " - " + PeriodoUtils.getPeriodoFromDate(periodo));
//		loadBeneficiosMap(detalles);
		crearEncabezado(worksheet, workbook, periodo, detalles, cliente);
		cargarDatos(worksheet, workbook, detalles, periodo);
		
		worksheet.setColumnWidth(1, 7*256);
		
		return workbook;

	}


	private void crearEncabezado(HSSFSheet worksheet, HSSFWorkbook workbook, Date periodo, List<InformeMensualDetalle> detalles, Cliente cliente) {
		logger.info("creando encabezados excel");
		HSSFRow row0 = worksheet.createRow(0);
		HSSFCellStyle cellStyle = setEstilosEncabezados(workbook);

		HSSFCell cellA1 = row0.createCell(0);
		cellA1.setCellValue("Periodo");
		HSSFCell cellB1 = row0.createCell(1);
		cellB1.setCellValue(PeriodoUtils.getPeriodoFromDate(periodo));
	
		HSSFRow row1 = worksheet.createRow(1);

		HSSFCell cellA2 = row1.createCell(0);
		cellA2.setCellValue("Cliente");
		HSSFCell cellB2 = row1.createCell(1);
		cellB2.setCellValue(cliente.getNombre());
		
		List<String> headers = getHeaders(detalles);
		int i = 0;
		HSSFRow row2 = worksheet.createRow(3);
		HSSFCell headerCell;
		for (String header : headers) {
			headerCell = row2.createCell(i++);
			headerCell.setCellValue(header);
			headerCell.setCellStyle(cellStyle);
		}
				
		setAutosize(row2, worksheet);
	}
	
	private void cargarDatos(HSSFSheet worksheet, HSSFWorkbook workbook, List<InformeMensualDetalle> detalles, Date periodo) {
		logger.info("cargando datos excel");
		
		if(detalles == null || detalles.size() == 0) {
			logger.info("NO HAY DETALLES EN EL INFORME");
			return;
		}
		
		int currentRow = 4;
		
		HSSFCellStyle cellStyle = setEstilosDatos(workbook);
		HSSFCellStyle cellStyleNumber = setEstilosDatos(workbook);
		HSSFCellStyle cellStyleDate = setEstilosDatos(workbook);
		DataFormat format = workbook.createDataFormat();
		cellStyleNumber.setDataFormat(format.getFormat("###0.00"));
		
		HSSFDataFormat dateFomat = workbook.createDataFormat();
		cellStyleDate.setDataFormat(dateFomat.getFormat("d/m/yyyy"));
		
		double totalBasico = 0d;
		double totalPresentismo = 0d;
		double totalHELiq50 = 0d;
		double totalHELiq100 = 0d;
		double totalHEFac50 = 0d;
		double totalHEFac100 = 0d;
		double[] totalBeneficios = new double[detalles.get(0).getBeneficios().size()];
		
		HSSFRow row;
		int i = 0;
		for (InformeMensualDetalle det : detalles) {
			i = 0;
			row = worksheet.createRow(currentRow++);
			
			//empleado
			setStringDataCell(row, i++, det.getRelacionLaboral().getEmpleado().getApellidos() + ", " + det.getRelacionLaboral().getEmpleado().getNombres(), cellStyle);
			//condicion
			setStringDataCell(row, i++, det.getCondicion(), cellStyle);
			//porcentaje
			setDoubleDataCell(row, i++, det.getPorcentaje(), cellStyleNumber);
			//fecha alta
			setDateDataCell(row, i++, det.getRelacionLaboral().getFechaInicio(), cellStyleDate);
			//fecha baja
			setDateDataCell(row, i++, det.getRelacionLaboral().getFechaFin(), cellStyleDate);
			
			//Sueldo basico y presentismo
			Sueldo sueldo = det.getRelacionLaboral().getEmpleado().getSueldoEnPeriodo(periodo); 			
			if(det.getSueldoBasico() != null){
				totalBasico += setDoubleDataCell(row, i++, det.getSueldoBasico(), cellStyleNumber);
			}else {
				totalBasico += setDoubleDataCell(row, i++, sueldo.getBasico(), cellStyleNumber);
			}
			if(det.getPresentismo() != null) {
				totalPresentismo += setDoubleDataCell(row, i++, det.getPresentismo(), cellStyleNumber);
			}else {
				totalPresentismo += setDoubleDataCell(row, i++, sueldo.getPresentismo(), cellStyleNumber);
			}
			
			//Dias trabajados
			setIntegerDataCell(row, i++, det.getDiasTrabajados().intValue(), cellStyleNumber);
			//Hs extras a liquidar al 50%
			totalHELiq50 += setDoubleDataCell(row, i++, det.getHsExtrasALiquidarAl50(), cellStyleNumber);
			//Hs extras a liquidar al 100%
			totalHELiq100 += setDoubleDataCell(row, i++, det.getHsExtrasALiquidarAl100(), cellStyleNumber);
			//Hs extras a facturar al 50%
			totalHEFac50 += setDoubleDataCell(row, i++, det.getHsExtrasAFacturarAl50(), cellStyleNumber);
			//Hs extras a facturar al 100%
			totalHEFac100 += setDoubleDataCell(row, i++, det.getHsExtrasAFacturarAl100(), cellStyleNumber);

			//Beneficios
			if(det.getBeneficios() != null) {
				Collections.sort(det.getBeneficios());
				int j = 0;
				for(InformeMensualDetalleBeneficio detBen : det.getBeneficios()) {
					totalBeneficios[j++] += setDoubleDataCell(row, i++, detBen.getValor(), cellStyleNumber);
				}
			}
			
			setAutosize(row, worksheet);
		}
		
		//Agrego la fila de los TOTALES
		i = 0;
		row = worksheet.createRow(currentRow++);
		
		//empleado
		setStringDataCell(row, i++, "", cellStyle);
		//condicion
		setStringDataCell(row, i++, "", cellStyle);
		//porcentaje
		setStringDataCell(row, i++, "", cellStyle);
		//fecha alta
		setStringDataCell(row, i++, "", cellStyle);
		//fecha baja
		setStringDataCell(row, i++, "", cellStyle);
		//Sueldo basico
		setDoubleDataCell(row, i++, totalBasico, cellStyleNumber);
		//Sueldo presentismo
		setDoubleDataCell(row, i++, totalPresentismo, cellStyleNumber);
		//Dias trabajados
		setStringDataCell(row, i++, "", cellStyle);
		//Hs extras a liquidar al 50%
		setDoubleDataCell(row, i++, totalHELiq50, cellStyleNumber);
		//Hs extras a liquidar al 100%
		setDoubleDataCell(row, i++, totalHELiq100, cellStyleNumber);
		//Hs extras a facturar al 50%
		setDoubleDataCell(row, i++, totalHEFac50, cellStyleNumber);
		//Hs extras a facturar al 100%
		setDoubleDataCell(row, i++, totalHEFac100, cellStyleNumber);

		//Beneficios
		for (int j = 0; j < totalBeneficios.length; j++) {
			setDoubleDataCell(row, i++, totalBeneficios[j], cellStyleNumber);
		}
		
		setAutosize(row, worksheet);
	}
	

	private void setStringDataCell(HSSFRow row, int indexCol, String value, HSSFCellStyle style) {
		// TODO Auto-generated method stub
		HSSFCell dataCell = row.createCell(indexCol);
		if(value != null) {
			dataCell.setCellType(HSSFCell.CELL_TYPE_STRING);
			dataCell.setCellValue(value);
		}
		dataCell.setCellStyle(style);
	}

	private Double setDoubleDataCell(HSSFRow row, int indexCol, Double value, HSSFCellStyle style) {
		// TODO Auto-generated method stub
		HSSFCell dataCell = row.createCell(indexCol);
		if(value != null) {
			dataCell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			dataCell.setCellValue(value);
		}
		dataCell.setCellStyle(style);
		
		if(value != null) {
			return value;
		}else {
			return 0d;
		}
	}
	private void setIntegerDataCell(HSSFRow row, int indexCol, Integer value, HSSFCellStyle style) {
		// TODO Auto-generated method stub
		HSSFCell dataCell = row.createCell(indexCol);
		if(value != null) {
			dataCell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			dataCell.setCellValue(value);
		}
		dataCell.setCellStyle(style);
	}	
	private void setDateDataCell(HSSFRow row, int indexCol, Date value, HSSFCellStyle style) {
		// TODO Auto-generated method stub
		HSSFCell dataCell = row.createCell(indexCol);
		if(value != null) {
			dataCell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			dataCell.setCellValue(HSSFDateUtil.getExcelDate(value));
		}
		dataCell.setCellStyle(style);
	}
	

	private void setAutosize(HSSFRow row,HSSFSheet worksheet) {
		Iterator<Cell> cell = row.cellIterator();
		while(cell.hasNext()){
			worksheet.autoSizeColumn(cell.next().getColumnIndex());
		}
	}
	private HSSFCellStyle setEstilosEncabezados(HSSFWorkbook workbook) {
		// Aplicar color de fondo
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		
		// Aplicar Bordes
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBottomBorderColor((short) 8);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setLeftBorderColor((short) 8);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setRightBorderColor((short) 8);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setTopBorderColor((short) 8);
		
		// Ajustar texto
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_JUSTIFY);
		return cellStyle;
	}

	private HSSFCellStyle setEstilosDatos(HSSFWorkbook workbook) {
		// Aplicar Bordes

		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBottomBorderColor((short) 8);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setLeftBorderColor((short) 8);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setRightBorderColor((short) 8);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setTopBorderColor((short) 8);
		return cellStyle;
	}
	
	private List<String> getHeaders(List<InformeMensualDetalle> detalles){
		List<String> headers = new ArrayList<String>();
		headers.add("Apellido y Nombre");
		headers.add("Cond");
		headers.add("%");
		headers.add("Fecha Ingreso");
		headers.add("Fecha Egreso");
		headers.add("S. Básico");
		headers.add("Asistencia y Puntualidad");
		headers.add("Nro Días trab.");
		headers.add("HE Liq. 50%");
		headers.add("HE Liq. 100%");
		headers.add("HE Fact. 50%");
		headers.add("HE Fact. 100%");
		
		if(detalles != null && detalles.size() > 0 && detalles.get(0).getBeneficios() != null) {
			Collections.sort(detalles.get(0).getBeneficios());
			for(InformeMensualDetalleBeneficio detBeneficio : detalles.get(0).getBeneficios()) {
				headers.add(detBeneficio.getBeneficio().getBeneficio().getTitulo());
			}
		}
		
		return headers;
	}
}
