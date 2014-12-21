package com.aehtiopicus.cens.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.aehtiopicus.cens.domain.Empleado;
import com.aehtiopicus.cens.domain.InformeConsolidadoDetalle;


@Service("liquidacionExcelService")
public class LiquidacionExcelServiceImpl implements LiquidacionService {

	private static final Logger logger = Logger.getLogger(LiquidacionExcelServiceImpl.class);
	
	private static final String CONCEPTO = "01";
	private static final String LIQUIDACION = "Liquidación";
	private HashMap<String, List<InformeConsolidadoDetalle>> convenios;
	
	@Override
	public List<String> generarArchivosLiquidacion(Date fechaAcreditacion,
			List<InformeConsolidadoDetalle> detalle, String referencia,
			String periodo) throws IOException {
		convenios = new HashMap<String, List<InformeConsolidadoDetalle>>();
		logger.info("creando excel liquidacion periodo: "+periodo);
		loadMapByConvenio(detalle);
		
		List<String> fileNames = new ArrayList<String>();
		if(!convenios.isEmpty()){
			int i = 0;
			for(String convenio : convenios.keySet()){
				List<InformeConsolidadoDetalle> informes =  convenios.get(convenio);
					HSSFWorkbook workbook = new HSSFWorkbook();
					String file = LIQUIDACION+ (++i) +".xls";
					HSSFSheet worksheet = workbook.createSheet(LIQUIDACION);
					crearEncabezado(worksheet,workbook);
					cargarDatos(worksheet,informes,workbook);
					FileOutputStream fileOut = new FileOutputStream(file);
					workbook.write(fileOut);
			        fileNames.add(file);
			       			
				}
		}	
		return fileNames;
	}
	
	

	private void loadMapByConvenio(List<InformeConsolidadoDetalle> detalle) {
		for(InformeConsolidadoDetalle ic : detalle){
			if(ic.obtenerEmpleado() != null && !StringUtils.isEmpty(ic.obtenerEmpleado().getConvenio())){
				String convenio = ic.obtenerEmpleado().getConvenio().toLowerCase();
				if(!convenios.containsKey(convenio)){
					List<InformeConsolidadoDetalle> detalles = new ArrayList<InformeConsolidadoDetalle>();
					detalles.add(ic);
					convenios.put(convenio,detalles);
				}else{
					List<InformeConsolidadoDetalle> detalles = convenios.get(convenio);
					detalles.add(ic);
					convenios.put(convenio, detalles);
				}
			}
		}
		
	}



	private void crearEncabezado(HSSFSheet worksheet, HSSFWorkbook workbook) {
		logger.info("creando encabezados excel");
		HSSFRow row1 = worksheet.createRow(0);
		HSSFCell cellA1 = row1.createCell(0);
		cellA1.setCellValue("Cuenta");
		CellStyle style = createStyle(workbook, cellA1);
		style.setAlignment(CellStyle.ALIGN_RIGHT);
		HSSFCell cellB1 = row1.createCell(1);
		cellB1.setCellValue("Nombre");
		CellStyle style1 = createStyle(workbook, cellB1);
		style1.setAlignment(CellStyle.ALIGN_LEFT);
		HSSFCell cellC1 = row1.createCell(2);
		cellC1.setCellValue("Importe");
		createStyle(workbook, cellC1);
		CellStyle style2 = createStyle(workbook, cellC1);
		style2.setAlignment(CellStyle.ALIGN_LEFT);
		HSSFCell cellD1 = row1.createCell(3);
		cellD1.setCellValue("Concepto");
		CellStyle style3 = createStyle(workbook, cellD1);
		style3.setAlignment(CellStyle.ALIGN_LEFT);
		setAutosize(row1, worksheet);
	}
	
	private CellStyle createStyle(HSSFWorkbook workbook,Cell cell ){
		 Font font = workbook.createFont();
		 font.setFontHeightInPoints((short)10);
		 font.setFontName("Arial");
		 CellStyle style = workbook.createCellStyle();
		 style.setFont(font);
		 cell.setCellStyle(style);
		 return style;
	}
	private void cargarDatos(HSSFSheet worksheet,
			List<InformeConsolidadoDetalle> detalles, HSSFWorkbook workbook) {
		DataFormat format2 = workbook.createDataFormat();
		if(!CollectionUtils.isEmpty(detalles)){
			int numeroFila = 1;
			HSSFRow row = null;
			for(InformeConsolidadoDetalle detalle : detalles) {
				if(detalle.obtenerEmpleado() != null){
					Empleado empleado = detalle.obtenerEmpleado();
					row = worksheet.createRow(numeroFila);
					//Nro de cuenta
					HSSFCell cell = row.createCell(0);
					CellStyle style = createStyle(workbook, cell);
					style.setAlignment(CellStyle.ALIGN_RIGHT);
				
					style.setDataFormat(format2.getFormat("###"));
					if(!StringUtils.isEmpty(empleado.getNroCuenta())){
						BigDecimal nroCuenta = new BigDecimal(empleado.getNroCuenta());
						cell.setCellValue((nroCuenta.longValue()));
						cell.setCellType(Cell.CELL_TYPE_NUMERIC);
						cell.setCellStyle(style);
					}
					
					// Nombre
					HSSFCell cell1 = row.createCell(1);
					System.out.print("ROW INDEX"+cell1.getRowIndex());
					System.out.print("COLUMN INDEX INDEX"+cell1.getColumnIndex());
					cell1.setCellType(Cell.CELL_TYPE_STRING);
					String nombre = empleado.getApellidos() + " "
								+ empleado.getNombres();
						cell1.setCellValue(nombre);	
									
					HSSFCell cell2 = row.createCell(2);
					CellStyle style1 = createStyle(workbook, cell2);
					DataFormat format = workbook.createDataFormat();
					style1.setDataFormat(format.getFormat("###0.00"));
					cell2.setCellStyle(style1);
					// neto a depositar
					if(detalle.getNetoADepositar() != null){
						cell2.setCellValue(redondear2Decimales(detalle.getNetoADepositar()));	
						cell2.setCellType(Cell.CELL_TYPE_NUMERIC);
					}
					
					
					HSSFCell cell3 = row.createCell(3);
					cell3.setCellType(Cell.CELL_TYPE_STRING);
					cell3.setCellValue(CONCEPTO);	
					CellStyle style3 = createStyle(workbook, cell3);
					style3.setAlignment(CellStyle.ALIGN_LEFT);
					numeroFila++; 
				 }
				
			}
			setAutosize(row, worksheet);
		}
	}
	
	private void setAutosize(HSSFRow row,HSSFSheet worksheet) {
		Iterator<Cell> cell = row.cellIterator();
		while(cell.hasNext()){
			worksheet.autoSizeColumn(cell.next().getColumnIndex());
		}
	}

	public static Double redondear2Decimales(Double value) {
		if(value == null) {
			return null;
		}
		BigDecimal bigDecimal = new BigDecimal(value);
		BigDecimal bigDecimalRedondeado = bigDecimal.setScale(2, RoundingMode.HALF_UP); 
		
		return bigDecimalRedondeado.doubleValue();
	}
	
}
