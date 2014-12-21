package com.aehtiopicus.cens.service;


import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.aehtiopicus.cens.domain.Empleado;
import com.aehtiopicus.cens.domain.RelacionLaboral;
import com.aehtiopicus.cens.enumeration.EstadoEmpleado;

@Service
public class ReporteServiceImpl implements ReporteService{
	
	private static final Logger logger = Logger.getLogger(ReporteServiceImpl.class);

    public final static String DD_MM_YYYY = "dd/MM/yyyy";
	
	public static SimpleDateFormat sdf = new SimpleDateFormat(DD_MM_YYYY);
	
	public EmpleadoService empleadoService;
	
	
	@Autowired
	public void setEmpleadoService(EmpleadoService empleadoService) {
		this.empleadoService = empleadoService;
	}

	@Override
	public HSSFWorkbook getReporte(List<Empleado> empleados) throws FileNotFoundException {
		logger.info("creando excel empleados");
	
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet worksheet = workbook.createSheet("Empleados");
		
		crearEncabezado(worksheet,workbook);
		cargarDatos(worksheet,empleados,workbook);
				
		return workbook;
	}


	private void cargarDatos(HSSFSheet worksheet, List<Empleado> empleados, HSSFWorkbook workbook) {
		logger.info("cargando empleados");
		HSSFCellStyle cellStyle = setEstilosDatos(workbook);
		
	
		if(!CollectionUtils.isEmpty(empleados)){
			int numeroFila = 1;
			HSSFRow row = null;
			for(Empleado empleado : empleados) {
				logger.info("empleado: "+numeroFila);
				row = worksheet.createRow(numeroFila);
				int rowCellIndex = 0;
				//Legajo
				HSSFCell cell = row.createCell(rowCellIndex++);
				cell.setCellStyle(cellStyle);
				if(empleado.getLegajo() != null){
					cell.setCellValue(empleado.getLegajo());	
				}
				//Fecha Ingreso
				HSSFCell cell1 = row.createCell(rowCellIndex++);
				if(empleado.getFechaIngresoNovatium() != null){
					cell1.setCellValue(sdf.format(empleado.getFechaIngresoNovatium()));	
				}
				cell1.setCellStyle(cellStyle);
				//Fecha Egreso
				HSSFCell cell2 = row.createCell(rowCellIndex++);
				if(empleado.getFechaEgresoNovatium() != null){
					cell2.setCellValue(sdf.format(empleado.getFechaEgresoNovatium()));	
				}
				cell2.setCellStyle(cellStyle);
				
				//Motivo Baja
				HSSFCell cell2a = row.createCell(rowCellIndex++);
				if(empleado.getMotivoBaja() != null){
					if(empleado.getMotivoBaja().getArticuloLct() != null) {
						cell2a.setCellValue(empleado.getMotivoBaja().getMotivo() + " (art: " + empleado.getMotivoBaja().getArticuloLct() + ")");
					}else {
						cell2a.setCellValue(empleado.getMotivoBaja().getMotivo());					
					}
				}
				cell2a.setCellStyle(cellStyle);
				
				//Empresa
				HSSFCell cell3 = row.createCell(rowCellIndex++);
				
				if(empleado.getEstado().equals(EstadoEmpleado.ACTUAL)) {
					RelacionLaboral rlaboral = empleado.getRelacionLaboralVigente();
					if(rlaboral.getCliente() != null && rlaboral.getCliente().getRazonSocial() != null){
						cell3.setCellValue(rlaboral.getCliente().getNombre());	
					}
				}else {
					RelacionLaboral ultimaRLaboral = empleado.getUltimaRelacionLaboral();
					if(ultimaRLaboral != null && ultimaRLaboral.getCliente() != null && ultimaRLaboral.getCliente().getRazonSocial() != null) {
						cell3.setCellValue(ultimaRLaboral.getCliente().getRazonSocial());
					}
				}
				cell3.setCellStyle(cellStyle);
				
				//Apellido
				HSSFCell cell4 = row.createCell(rowCellIndex++);
				if(!StringUtils.isEmpty(empleado.getApellidos())){
					cell4.setCellValue(empleado.getApellidos());	
				}
				cell4.setCellStyle(cellStyle);
				//Nombre
				HSSFCell cell5 = row.createCell(rowCellIndex++);
				if(!StringUtils.isEmpty(empleado.getNombres())){
					cell5.setCellValue(empleado.getNombres());	
				}
				cell5.setCellStyle(cellStyle);
				//DNI
				HSSFCell cell6 = row.createCell(rowCellIndex++);
				if(!StringUtils.isEmpty(empleado.getDni())){
					cell6.setCellValue(empleado.getDni());	
				}
				cell6.setCellStyle(cellStyle);
				//CUIL
				HSSFCell cell7 = row.createCell(rowCellIndex++);
				if(!StringUtils.isEmpty(empleado.getCuil())){
					cell7.setCellValue(empleado.getCuil());	
				}
				cell7.setCellStyle(cellStyle);
				//Fecha de Nacimiento
				HSSFCell cell8 = row.createCell(rowCellIndex++);
				if(empleado.getFechaNacimiento() != null){
					cell8.setCellValue(sdf.format(empleado.getFechaNacimiento()));	
				}
				
				cell8.setCellStyle(cellStyle);
				//Estado Civil
				HSSFCell cell9 = row.createCell(rowCellIndex++);
				if(empleado.getEstadoCivil() != null){
					cell9.setCellValue(empleado.getEstadoCivil().getNombre());	
				}
				cell9.setCellStyle(cellStyle);
				//Nacionalidad
				HSSFCell cell10 = row.createCell(rowCellIndex++);
				if(empleado.getNacionalidad() != null){
					cell10.setCellValue(empleado.getNacionalidad().getNombre());	
				}
				cell10.setCellStyle(cellStyle);
				//Domicilio
				HSSFCell cell11 = row.createCell(rowCellIndex++);
				if(!StringUtils.isEmpty(empleado.getDomicilio())){
					cell11.setCellValue(empleado.getDomicilio());	
				}
				cell11.setCellStyle(cellStyle);
				//Teléfono Fijo
				HSSFCell cell12 = row.createCell(rowCellIndex++);
				if(!StringUtils.isEmpty(empleado.getTelFijo())){
					cell12.setCellValue(empleado.getTelFijo());	
				}
				cell12.setCellStyle(cellStyle);
				//Celular
				HSSFCell cell13 = row.createCell(rowCellIndex++);
				if(!StringUtils.isEmpty(empleado.getCelular())){
					cell13.setCellValue(empleado.getCelular());	
				}
				cell13.setCellStyle(cellStyle);
				//Teléfono Urgencias
				HSSFCell cell14 = row.createCell(rowCellIndex++);
				if(!StringUtils.isEmpty(empleado.getTelUrgencias())){
					cell14.setCellValue(empleado.getTelUrgencias());	
				}
				cell14.setCellStyle(cellStyle);
				//Contacto para Urgencias
				HSSFCell cell15 = row.createCell(rowCellIndex++);
				if(!StringUtils.isEmpty(empleado.getContactoUrgencias())){
					cell15.setCellValue(empleado.getContactoUrgencias());	
				}
				cell15.setCellStyle(cellStyle);
				//Mail Personal
				HSSFCell cell16 = row.createCell(rowCellIndex++);
				if(!StringUtils.isEmpty(empleado.getMailPersonal())){
					cell16.setCellValue(empleado.getMailPersonal());	
				}
				cell16.setCellStyle(cellStyle);
				//Mail Novatium
				HSSFCell cell17 = row.createCell(rowCellIndex++);
				if(!StringUtils.isEmpty(empleado.getMailNovatium())){
					cell17.setCellValue(empleado.getMailNovatium());	
				}
				cell17.setCellStyle(cellStyle);
				//Mail empresa
				HSSFCell cell18 = row.createCell(rowCellIndex++);
				if(!StringUtils.isEmpty(empleado.getMailmpresa())){
					cell18.setCellValue(empleado.getMailmpresa());	
				}
				cell18.setCellStyle(cellStyle);
				//Obra Social
				HSSFCell cell19 = row.createCell(rowCellIndex++);
				if(empleado.getObraSocial()!=null){
					cell19.setCellValue(empleado.getObraSocial().getNombre());	
				}
				cell19.setCellStyle(cellStyle);
				//Prepaga
				HSSFCell cell20 = row.createCell(rowCellIndex++);
				if(empleado.getPrepaga()!=null){
					cell20.setCellValue(empleado.getPrepaga().getNombre());	
				}
				cell20.setCellStyle(cellStyle);
				//Chomba
				HSSFCell cell21 = row.createCell(rowCellIndex++);
				if(empleado.getChomba() != null && empleado.getChomba().equals(Boolean.TRUE)){
					cell21.setCellValue("SI");	
				}else {
					cell21.setCellValue("NO");	
				}
				cell21.setCellStyle(cellStyle);
				//Mochila
				HSSFCell cell22 = row.createCell(rowCellIndex++);
				if(empleado.getMochila() != null && empleado.getMochila().equals(Boolean.TRUE)){
					cell22.setCellValue("SI");	
				}else{
					cell22.setCellValue("NO");	
				}
				cell22.setCellStyle(cellStyle);
				//Fecha de Preocupacional
				HSSFCell cell23 = row.createCell(rowCellIndex++);
				if(empleado.getFechaPreOcupacional() != null){
					cell23.setCellValue(sdf.format(empleado.getFechaPreOcupacional()));	
				}
				cell23.setCellStyle(cellStyle);
				//Resultado Preocupacional
				HSSFCell cell24 = row.createCell(rowCellIndex++);
				if(!StringUtils.isEmpty(empleado.getResultadoPreOcupacional())){
					cell24.setCellValue(empleado.getResultadoPreOcupacional());	
				}
				cell24.setCellStyle(cellStyle);
			
				//Banco
				HSSFCell cell27 = row.createCell(rowCellIndex++);
				if(empleado.getBanco()!=null){
					cell27.setCellValue(empleado.getBanco().getNombre());	
				}
				cell27.setCellStyle(cellStyle);
				//Sucursal 
				HSSFCell cell28 = row.createCell(rowCellIndex++);
				if(!StringUtils.isEmpty(empleado.getSucursal())){
					cell28.setCellValue(empleado.getSucursal());	
				}
				cell28.setCellStyle(cellStyle);
				//Nro Cuenta 
				HSSFCell cell29 = row.createCell(rowCellIndex++);
				if(!StringUtils.isEmpty(empleado.getNroCuenta())){
					cell29.setCellValue(empleado.getNroCuenta());	
				}
				cell29.setCellStyle(cellStyle);
				
				//Convenio 
				HSSFCell cell30 = row.createCell(rowCellIndex++);
				if(!StringUtils.isEmpty(empleado.getConvenio())){
					cell30.setCellValue(empleado.getConvenio());	
				}
				cell30.setCellStyle(cellStyle);
				//cbu
				HSSFCell cell31 = row.createCell(rowCellIndex++);
				if(!StringUtils.isEmpty(empleado.getCbu())){
					cell31.setCellValue(empleado.getCbu());	
				}
				cell31.setCellStyle(cellStyle);
				
				//observaciones
				HSSFCell cell32 = row.createCell(rowCellIndex++);
				if(!StringUtils.isEmpty(empleado.getObservaciones())){
					cell32.setCellValue(empleado.getObservaciones());	
				}
				cell32.setCellStyle(cellStyle);
				
				//estado
				HSSFCell cell33 = row.createCell(rowCellIndex++);
				cell33.setCellValue(empleado.getEstado().getNombre());
				cell33.setCellStyle(cellStyle);
				
				
				numeroFila++;
				
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

	private HSSFCellStyle setEstilosDatos(HSSFWorkbook workbook) {
		//Aplicar Bordes
		
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBottomBorderColor((short)8);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setLeftBorderColor((short)8);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setRightBorderColor((short)8);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setTopBorderColor((short)8);
		return cellStyle;
	}

	private void crearEncabezado(HSSFSheet worksheet, HSSFWorkbook workbook) {
		logger.info("creando encabezados excel");
	
				
		HSSFRow row1 = worksheet.createRow(0);
		HSSFCellStyle cellStyle = setEstilosEncabezados(workbook);
		
		int rowCellIndex = 0;
		HSSFCell cellA1 = row1.createCell(rowCellIndex++);
		cellA1.setCellValue("Legajo");
		cellA1.setCellStyle(cellStyle);
		
		HSSFCell cellB1 = row1.createCell(rowCellIndex++);
		cellB1.setCellValue("Fecha de Ingreso Novatium");
		cellB1.setCellStyle(cellStyle);
		
		HSSFCell cellC1 = row1.createCell(rowCellIndex++);
		cellC1.setCellValue("Fecha de Egreso Novatium");
		cellC1.setCellStyle(cellStyle);

		HSSFCell cellC12 = row1.createCell(rowCellIndex++);
		cellC12.setCellValue("Motivo de Baja");
		cellC12.setCellStyle(cellStyle);
		
		HSSFCell cellD1 = row1.createCell(rowCellIndex++);
		cellD1.setCellValue("Empresa");
		cellD1.setCellStyle(cellStyle);
		
		HSSFCell cellE1 = row1.createCell(rowCellIndex++);
		cellE1.setCellValue("Apellido");
		cellE1.setCellStyle(cellStyle);
		
		HSSFCell cellF1 = row1.createCell(rowCellIndex++);
		cellF1.setCellValue("Nombre");
		cellF1.setCellStyle(cellStyle);
		
		HSSFCell cellG1 = row1.createCell(rowCellIndex++);
		cellG1.setCellValue("DNI");
		cellG1.setCellStyle(cellStyle);
		
		HSSFCell cellH1 = row1.createCell(rowCellIndex++);
		cellH1.setCellValue("CUIL");
		cellH1.setCellStyle(cellStyle);
		
		HSSFCell cellI1 = row1.createCell(rowCellIndex++);
		cellI1.setCellValue("Fecha de Nacimiento");
		cellI1.setCellStyle(cellStyle);
		
		HSSFCell cellJ1 = row1.createCell(rowCellIndex++);
		cellJ1.setCellValue("Estado Civil");
		cellJ1.setCellStyle(cellStyle);
		
		HSSFCell cellK1 = row1.createCell(rowCellIndex++);
		cellK1.setCellValue("Nacionalidad");
		cellK1.setCellStyle(cellStyle);
		
		HSSFCell cellL1 = row1.createCell(rowCellIndex++);
		cellL1.setCellValue("Domicilio");
		cellL1.setCellStyle(cellStyle);
		
		HSSFCell cellM1 = row1.createCell(rowCellIndex++);
		cellM1.setCellValue("Teléfono Fijo");
		cellM1.setCellStyle(cellStyle);
		
		HSSFCell cellN1 = row1.createCell(rowCellIndex++);
		cellN1.setCellValue("Celular");
		cellN1.setCellStyle(cellStyle);
	
		HSSFCell cellO1 = row1.createCell(rowCellIndex++);
		cellO1.setCellValue("Teléfono Urgencias");
		cellO1.setCellStyle(cellStyle);
		
		HSSFCell cellP1 = row1.createCell(rowCellIndex++);
		cellP1.setCellValue("Contacto para Urgencias");
		cellP1.setCellStyle(cellStyle);
		

		HSSFCell cellQ1 = row1.createCell(rowCellIndex++);
		cellQ1.setCellValue("Mail Personal");
		cellQ1.setCellStyle(cellStyle);

		HSSFCell cellR1 = row1.createCell(rowCellIndex++);
		cellR1.setCellValue("Mail Novatium");
		cellR1.setCellStyle(cellStyle);

		HSSFCell cellS1 = row1.createCell(rowCellIndex++);
		cellS1.setCellValue("Mail Empresa");
		cellS1.setCellStyle(cellStyle);
		
		HSSFCell cellT1 = row1.createCell(rowCellIndex++);
		cellT1.setCellValue("Obra Social");
		cellT1.setCellStyle(cellStyle);
		
		HSSFCell cellU1 = row1.createCell(rowCellIndex++);
		cellU1.setCellValue("Prepaga");
		cellU1.setCellStyle(cellStyle);
		
		HSSFCell cellV1 = row1.createCell(rowCellIndex++);
		cellV1.setCellValue("Chomba");
		cellV1.setCellStyle(cellStyle);
		
		HSSFCell cellW1 = row1.createCell(rowCellIndex++);
		cellW1.setCellValue("Mochila");
		cellW1.setCellStyle(cellStyle);
		
		HSSFCell cellX1 = row1.createCell(rowCellIndex++);
		cellX1.setCellValue("Fecha Pre Ocupacional");
		cellX1.setCellStyle(cellStyle);
		
		HSSFCell cellY1 = row1.createCell(rowCellIndex++);
		cellY1.setCellValue("Resultado Pre Ocupacional");
		cellY1.setCellStyle(cellStyle);
		
				
		HSSFCell cellZ1 = row1.createCell(rowCellIndex++);
		cellZ1.setCellValue("Banco");
		cellZ1.setCellStyle(cellStyle);
		
		HSSFCell cellAA1 = row1.createCell(rowCellIndex++);
		cellAA1.setCellValue("Sucursal");
		cellAA1.setCellStyle(cellStyle);
		
		HSSFCell cellBB21 = row1.createCell(rowCellIndex++);
		cellBB21.setCellValue("Nro de Cuenta");
		cellBB21.setCellStyle(cellStyle);
		
		HSSFCell cellBB1 = row1.createCell(rowCellIndex++);
		cellBB1.setCellValue("Convenio");
		cellBB1.setCellStyle(cellStyle);
		
		HSSFCell cellBB11 = row1.createCell(rowCellIndex++);
		cellBB11.setCellValue("CBU");
		cellBB11.setCellStyle(cellStyle);
		
		HSSFCell cellCC1 = row1.createCell(rowCellIndex++);
		cellCC1.setCellValue("Observaciones");
		cellCC1.setCellStyle(cellStyle);
		
		HSSFCell cellCC2 = row1.createCell(rowCellIndex++);
		cellCC2.setCellValue("Estado");
		cellCC2.setCellStyle(cellStyle);
		
		setAutosize(row1, worksheet);
	}

	private HSSFCellStyle setEstilosEncabezados(HSSFWorkbook workbook) {
	
		//Aplicar color de fondo
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		//Aplicar Bordes
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBottomBorderColor((short)8);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setLeftBorderColor((short)8);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setRightBorderColor((short)8);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setTopBorderColor((short)8);
		//Ajustar texto
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); 
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_JUSTIFY);
		return cellStyle;
	}

}
