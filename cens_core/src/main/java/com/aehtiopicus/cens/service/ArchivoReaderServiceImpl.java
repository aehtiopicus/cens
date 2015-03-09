package com.aehtiopicus.cens.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;



public class ArchivoReaderServiceImpl {

//	private EmpleadoService empleadoService;
//	private ClienteService clienteService;
//	private RelacionLaboralService relacionLaboralService;
//	private NacionalidadService nacionalidadService;
//	private ObraSocialService obraSocialService;
//	private PrepagaService prepagaService;
//	private BancoService bancoService;
//	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//	Pattern pat = Pattern.compile("|^[a-zA-Z0-9._-]{2,100}@[a-zA-Z0-9._-]{2,100}.[a-zA-Z]{2,4}(.[a-zA-Z]{2,4})?$");
//	Pattern patCbu = Pattern.compile("|^[0-9]{22}");
//	Pattern patNroCuenta =Pattern.compile("|^[0-9]{12}");
//	protected ResourceBundle mensajes = ResourceBundle.getBundle("errores_es");
//	String error;
//	@Autowired
//	public void setPrepagaService(PrepagaService prepagaService) {
//		this.prepagaService = prepagaService;
//	}
//
//	
//	@Autowired
//	public void setBancoService(BancoService bancoService) {
//		this.bancoService = bancoService;
//	}
//
//
//
//	@Autowired
//	public void setClienteService(ClienteService clienteService) {
//		this.clienteService = clienteService;
//	}
//	
//	
//	@Autowired
//	public void setObraSocialService(ObraSocialService obraSocialService) {
//		this.obraSocialService = obraSocialService;
//	}
//
//
//
//	@Autowired
//	public void setNacionalidadService(NacionalidadService nacionalidadService) {
//		this.nacionalidadService = nacionalidadService;
//	}
//
//	@Autowired
//	public void setEmpleadoService(EmpleadoService empleadoService) {
//		this.empleadoService = empleadoService;
//	}
//
//	@Autowired
//	public void setRelacionLaboralService(
//			RelacionLaboralService relacionLaboralService) {
//		this.relacionLaboralService = relacionLaboralService;
//	}
//
//	@Override
//	public List<String> readExcelFile(File file)
//			throws IOException {
//		String fileExtn = getFileExtension(file.getPath());
//		InputStream inp = new FileInputStream(file.getPath());
//		Workbook wb_xssf; //Declare XSSF WorkBook
//	    Workbook wb_hssf; //Declare HSSF WorkBook
//	    Sheet sheet = null;
//	    if (fileExtn.equalsIgnoreCase("xlsx"))
//	      {
//		      wb_xssf = new XSSFWorkbook(inp);
//		      sheet = wb_xssf.getSheetAt(0);
//	      }
//	      if (fileExtn.equalsIgnoreCase("xls"))
//	      {
//	    	  POIFSFileSystem fs = new POIFSFileSystem(inp);
//	    	  wb_hssf = new HSSFWorkbook(fs);
//	    
//	    	  sheet = wb_hssf.getSheetAt(0);
//	      }
//		List<List<Cell>> cellDataList = new ArrayList<List<Cell>>();
//		Cell Cell;
//		Iterator<Row> rowIterator = sheet.rowIterator();
//		while (rowIterator.hasNext()) {
//			Row hssfRow = (Row) rowIterator.next();
//			List<Cell> cellTempList = new ArrayList<Cell>();
//			for (int i = 0; i < 35; i++) {
//				if (hssfRow.getCell(i) != null
//						&& hssfRow.getCell(i).getColumnIndex() == i) {
//					Cell = hssfRow.getCell(i);
//					cellTempList.add(Cell);
//				} else {
//					cellTempList.add(null);
//				}
//			}
//			cellDataList.add(cellTempList);
//		}
//		List<String> errores = printToConsole(cellDataList);
//		return errores;
//	}
//
//	private List<String> printToConsole(List<List<Cell>> cellDataList) {
//		List<String> errores = new ArrayList<String>();
//		
//		error = "";
//		for (int i = 1; i < cellDataList.size(); i++) {
//			RelacionLaboral relacionLaboral = null;
//			Sueldo sueldo = null;
//			Banco banco = null;
//			Empleado empleado = new Empleado();
//			List<Cell> cellTempList = (List<Cell>) cellDataList.get(i);
//			error = "";
//			for (int j = 0; j < cellTempList.size(); j++) {
//				switch (j) {
//				case 0:
//					setLegajo(i, empleado, cellTempList, j);
//					break;
//				case 1:
//					setFechaIngreso( i, empleado, cellTempList, j);
//					break;
//				case 2:
//					setFechaEgreso( i, empleado, cellTempList, j);
//					break;
//				case 3:
//					relacionLaboral = setCliente(i, cellTempList, j, relacionLaboral);
//					break;
//				case 4:
//					relacionLaboral = setPuesto( i,  cellTempList, j, relacionLaboral);
//					break;
//				case 5:
//					setFecha(i, empleado, cellTempList, j,relacionLaboral);
//					break;
//				case 6:
//					setApellido( i, empleado, cellTempList, j);
//					break;
//				case 7:
//					setNombre( i, empleado, cellTempList, j);
//					break;
//				case 8:
//					setDNI( i, empleado, cellTempList, j);
//					break;
//				case 9:
//					setCuil( i, empleado, cellTempList, j);
//					break;
//				case 10:
//					setFechaDeNacimiento( i, empleado, cellTempList, j);
//					break;
//				case 11:
//					setEstadoCivil( i, empleado, cellTempList, j);
//					break;
//				case 12:
//					setNacionalidad( i, empleado, cellTempList, j);
//					break;
//				case 13:
//					setDomicilio( i, empleado, cellTempList, j);
//					break;
//				case 14:
//					setTelefonoFijo( i, empleado, cellTempList, j);
//					break;
//				case 15:
//					setCelular( i, empleado, cellTempList, j);
//					break;
//				case 16:
//					setTelUrgencias( i, empleado, cellTempList, j);
//					break;
//				case 17:
//					setContactoUrgencias( i, empleado, cellTempList, j);
//					break;
//				case 18:
//					setMailPersonal( i, empleado, cellTempList, j);
//					break;
//				case 19:
//					setMailNovatium( i, empleado, cellTempList, j);
//					break;
//				case 20:
//					setMailEmpresa( i, empleado, cellTempList, j);
//					break;
//				case 21:
//					setObraSocial( i, empleado, cellTempList, j);
//					break;
//				case 22:
//					setPrepaga( i, empleado, cellTempList, j);
//					break;
//				case 23:
//					setChomba( i, empleado, cellTempList, j);
//					break;
//				case 24:
//					setMochila( i, empleado, cellTempList, j);
//					break;
//				case 25:
//					setFechaPreocupacional( i, empleado, cellTempList, j);
//					break;
//				case 26:
//					setResultadoPreocupacional( i, empleado, cellTempList, j);
//					break;
//				case 27:
//					banco = setBanco( i, empleado, cellTempList, j,banco);
//					break;
//				case 28:
//					setSucursal( i, empleado, cellTempList, j);
//					break;
//				case 29:
//					setNroCuenta( i, empleado, cellTempList, j,banco);
//					break;
//				case 30:
//					setConvenio( i, empleado, cellTempList, j,banco);
//					break;
//				case 31:
//					setCBU( i, empleado, cellTempList, j,banco);
//					break;
//				
//				case 32:
//					setObservaciones( i, empleado, cellTempList, j,banco);
//					break;
//					
//				case 33:
//					sueldo = setBasico( i, empleado, cellTempList, j,sueldo);
//					break;
//				case 34:
//					sueldo = setPresentismo( i, empleado, cellTempList, j,sueldo);
//					break;
//				}
//				
//			}
//			if(error.equals("")){
//				error = null;
//				List<RelacionLaboral> relaciones = empleado.getRelacionLaboral();
//				List<Sueldo> sueldos = empleado.getSueldo();
//				empleado.setSueldo(null);
//				//cargamos las relaciones laborales
//				if(!CollectionUtils.isEmpty(relaciones)){
//					empleado.setRelacionLaboral(null);
//					empleadoService.saveEmpleado(empleado);
//					relaciones.get(0).setEmpleado(empleado);
//					relacionLaboralService.createAndSave(relaciones.get(0));
//					empleado.setRelacionLaboral(relaciones);
//				}
//				//cargamos el sueldo
//				
//				if(!CollectionUtils.isEmpty(sueldos)){
//					empleadoService.saveEmpleado(empleado);
//					sueldos.get(0).setEmpleado(empleado);
//					empleadoService.saveSueldo(sueldos.get(0), empleado.getId());
//					empleado.setSueldo(sueldos);
//				}
//				empleadoService.saveEmpleado(empleado);
//				
//			}
//			if(!StringUtils.isEmpty(error)){
//				errores.add(error);	
//			}
//			
//		}
//		return errores;
//	}
//
//	private void setCBU( int i, Empleado empleado,
//			List<Cell> cellTempList, int j, Banco banco) {
//		
//		if(cellTempList.get(j) == null) {
//			return;
//		}
//
//		cellTempList.get(j).setCellType(Cell.CELL_TYPE_STRING);
//
//		String cbu = getValue(cellTempList, j);
//		if(StringUtils.isEmpty(cbu)){
//			if(banco != null && !banco.getNombre().equalsIgnoreCase("GALICIA")){
//				setCBURequerido(i);
//			}
//		}else{
//			Matcher mat = patCbu.matcher(cbu);
//			if (mat.matches()) {
//				      empleado.setCbu(cbu);
//			} else {
//				      setIncorrectFormatCBU(i);
//			}
//				
//				
//		}
//	}
//
//
//	
//
//	private Sueldo setBasico(int i,Empleado empleado,
//			List<Cell> cellTempList, int j, Sueldo sueldo){
//		
//		cellTempList.get(j).setCellType(Cell.CELL_TYPE_STRING);
//		
//		String basico = getValue(cellTempList, j);
//		if(StringUtils.isEmpty(basico)) {
//			basico = "0";
//		}
//		try{
//			if(!StringUtils.isEmpty(basico)){
//				Double sueldoBasico = Double.valueOf(basico);
//				sueldo = new Sueldo();
//				sueldo.setBasico(sueldoBasico);	
//			}
//			
//		}catch(Exception e){
//			setFormatoSueldoIncorrecto(i);
//		}
//		return sueldo;
//	}
//	
//	
//
//
//	private Sueldo setPresentismo(int i,Empleado empleado,
//			List<Cell> cellTempList, int j, Sueldo sueldo){
//		
//		if(cellTempList.get(j) != null) {
//			cellTempList.get(j).setCellType(Cell.CELL_TYPE_STRING);
//		}
//		
//		
//		String presentismo = getValue(cellTempList, j);
//		if(StringUtils.isEmpty(presentismo)) {
//			presentismo = "0";
//		}
//		try{
//			if(sueldo != null && sueldo.getBasico() != null && !StringUtils.isEmpty(presentismo)){
//				Double presentismoSueldo = Double.valueOf(presentismo);
//				sueldo.setPresentismo(presentismoSueldo);
//				if(empleado.getFechaIngresoNovatium() != null){
//					sueldo.setFechaInicio(empleado.getFechaIngresoNovatium());	
//				}
//				List<Sueldo> sueldos = new ArrayList<Sueldo>();
//				sueldos.add(sueldo);
//				empleado.setSueldo(sueldos);
//			}
//			if(sueldo != null && sueldo.getBasico() != null && StringUtils.isEmpty(presentismo)){
//				setPresentismoObligatorio(i);
//			}
//		}catch(Exception e){
//			setFormatoSueldoIncorrecto(i);
//		}
//		return sueldo;
//	}
//	
//	private void setConvenio(int i, Empleado empleado, List<Cell> cellTempList, int j, Banco banco) {
//		if(empleado.getEstado().equals(EstadoEmpleado.BAJA)) {
//			return;
//		}
//		
//		cellTempList.get(j).setCellType(Cell.CELL_TYPE_STRING);
//		
//		String convenio = getValue(cellTempList, j);
//		if(banco != null && banco.getNombre().equalsIgnoreCase("GALICIA")){
//			if(!StringUtils.isEmpty(convenio)){
//				empleado.setConvenio(convenio);
//			}else{
//				setConvenioRequerido(i);
//			}
//		}
//		
//	}
//
//
//	private void setObservaciones(int i, Empleado empleado,
//			List<Cell> cellTempList, int j, Banco banco) {
//		
//		if(cellTempList.get(j) == null) {
//			return;
//		}
//		
//		String observaciones = getValue(cellTempList, j);
//		empleado.setObservaciones(observaciones);
//		
//		}
//		
//	
//
//
//	private void setNroCuenta( int i, Empleado empleado, List<Cell> cellTempList, int j, Banco banco) {
//		if(empleado.getEstado().equals(EstadoEmpleado.BAJA)) {
//			return;
//		}
//		cellTempList.get(j).setCellType(Cell.CELL_TYPE_STRING);
//		
//		String nroCuenta = getValue(cellTempList, j);
//		if(banco != null && banco.getNombre().equalsIgnoreCase("GALICIA")){
//			if(!StringUtils.isEmpty(nroCuenta)){
//				 Matcher mat = patNroCuenta.matcher(nroCuenta);
//			     if (mat.matches()) {
//			    	 empleado.setNroCuenta(nroCuenta);
//			     } else {
//			         setIncorrectFormatNroCuenta(i);
//			     }
//				
//			}else{
//				setNroCuentaRequerido(i);
//			}
//		}else{
//			if(!StringUtils.isEmpty(nroCuenta)){
//				empleado.setNroCuenta(nroCuenta);
//			}
//		}
//		
//	}
//
//
//	
//
//
//	private void setSucursal(int i, Empleado empleado, List<Cell> cellTempList, int j) {
//		if(empleado.getEstado().equals(EstadoEmpleado.BAJA)) {
//			return;
//		}
//		if (cellTempList.get(j) != null) {
//			cellTempList.get(j).setCellType(Cell.CELL_TYPE_STRING);
//			String sucursal = getValue(cellTempList, j);
//			if (!StringUtils.isEmpty(sucursal)) {
//				empleado.setSucursal(sucursal);
//			}
//		}
//		
//	}
//
//
//	private Banco setBanco( int i, Empleado empleado, List<Cell> cellTempList, int j,Banco banco) {
//		if(empleado.getEstado().equals(EstadoEmpleado.BAJA)) {
//			return null;
//		}
//		if (cellTempList.get(j) != null) {
//			String bancoNombre = getValue(cellTempList, j);
//			if (!StringUtils.isEmpty(bancoNombre)) {
//				banco = bancoService.findByNombre(bancoNombre);
//				if(banco != null){
//					empleado.setBanco(banco);	
//				}else{
//					setBanoNoExiste(i);
//				}
//			}else{
//				setBancoRequerido(i);
//			}
//		}
//		else{
//			setBancoRequerido(i);
//		}
//		return banco;
//	}
//
//	
//	private void setResultadoPreocupacional(int i,
//			Empleado empleado, List<Cell> cellTempList, int j) {
//		if (cellTempList.get(j) != null) {
//			String preocupacional = getValue(cellTempList, j);
//			if (!StringUtils.isEmpty(preocupacional)) {
//				empleado.setResultadoPreOcupacional(preocupacional);
//				
//			}
//		}
//		
//	}
//
//
//	private void setFechaPreocupacional(int i, Empleado empleado, List<Cell> cellTempList, int j) {
//		if (cellTempList.get(j) != null) {
//			
//			if(cellTempList.get(j).getCellType() == Cell.CELL_TYPE_NUMERIC && 
//				HSSFDateUtil.isCellDateFormatted(cellTempList.get(j))) 
//			{
//				Date myDate = HSSFDateUtil.getJavaDate(cellTempList.get(j).getNumericCellValue());
//				if(myDate != null) {
//					empleado.setFechaPreOcupacional(myDate);
//				}else {
//					setFormatoFechaIncorrectoError(i, "Fecha Preocupacional");
//				}
//			}else {
//			
//				String fechaPreocupacional = getValue(cellTempList, j);
//				if (!StringUtils.isEmpty(fechaPreocupacional)) {
//					try {
//						Date fecha = sdf.parse(fechaPreocupacional);
//						empleado.setFechaPreOcupacional(fecha);
//						
//					} catch (Exception e) {
//						setFormatoFechaIncorrectoError( i, "Fecha Preocupacional");
//					}
//				}
//			}
//		}
//		
//	}
//
//
//	private void setMochila( int i, Empleado empleado,
//			List<Cell> cellTempList, int j) {
//		if (cellTempList.get(j) != null) {
//			String mochila = getValue(cellTempList, j);
//			if(!StringUtils.isEmpty(mochila)){
//				try{
//					if(mochila.equalsIgnoreCase("no"))
//						empleado.setMochila(Boolean.FALSE);	
//					else{
//						empleado.setMochila(Boolean.TRUE);
//					}
//				}catch(Exception e){
//					setFormatoIncorrectoBoolean(i);
//				}
//				
//				
//			}else{
//				empleado.setMochila(Boolean.FALSE);
//			}
//		}
//		
//	}
//
//
//	private void setChomba( int i, Empleado empleado,
//			List<Cell> cellTempList, int j) {
//		if (cellTempList.get(j) != null) {
//			String chomba = getValue(cellTempList, j);
//			if(!StringUtils.isEmpty(chomba)){
//				try{
//					if(chomba.equalsIgnoreCase("no")){
//						empleado.setChomba(Boolean.FALSE);	
//					}else{
//						empleado.setChomba(Boolean.TRUE);
//					}
//						
//				}catch(Exception e){
//					setFormatoIncorrectoBoolean(i);
//				}
//				
//				
//			}else{
//				empleado.setChomba(Boolean.FALSE);
//			}
//		}
//		
//		
//	}
//
//
//	
//
//
//	private void setPrepaga(int i, Empleado empleado,
//			List<Cell> cellTempList, int j) {
//		if (cellTempList.get(j) != null) {
//			String prepagaStr = getValue(cellTempList, j);
//			if(!StringUtils.isEmpty(prepagaStr)){
//				Prepaga prapaga = prepagaService.getPrepagaByName(prepagaStr);
//				if(prapaga != null){
//					empleado.setPrepaga(prapaga);
//				}else{
//					setPrepagaNoExistente(i);
//				}
//				
//			}
//		}
//		
//		
//	}
//
//
//	private void setObraSocial( int i, Empleado empleado,
//			List<Cell> cellTempList, int j) {
//		if (cellTempList.get(j) != null) {
//			String obraSocial = getValue(cellTempList, j);
//			if(!StringUtils.isEmpty(obraSocial)){
//				ObraSocial os = obraSocialService.getObraSocialByNombre(obraSocial);
//				if(os != null){
//					empleado.setObraSocial(os);
//				}else{
//					setObraSocialNoExistente(i);
//				}
//				
//			}
//		}
//		
//	}
//
//
//	private void setMailEmpresa( int i, Empleado empleado,
//			List<Cell> cellTempList, int j) {
//		if (cellTempList.get(j) != null) {
//			String mailEmpresa = getValue(cellTempList, j);
//			if (!StringUtils.isEmpty(mailEmpresa)) {
//				mailEmpresa = mailEmpresa.replaceAll(" ", "");
//				
//			     Matcher mat = pat.matcher(mailEmpresa);
//			     if (mat.matches()) {
//			    	 empleado.setMailmpresa(mailEmpresa);
//			     } else {
//			         setIncorrectFormatEmail(i);
//			     }
//					
//			}
//		}
//		
//	}
//
//	private void setMailNovatium(int i, Empleado empleado,
//			List<Cell> cellTempList, int j) {
//		if (cellTempList.get(j) != null) {
//			String mailNovatium = getValue(cellTempList, j);
//			if (!StringUtils.isEmpty(mailNovatium)) {
//				mailNovatium = mailNovatium.replaceAll(" ", "");
//				
//			     Matcher mat = pat.matcher(mailNovatium);
//			     if (mat.matches()) {
//			    	 empleado.setMailNovatium(mailNovatium);
//			     } else {
//			         setIncorrectFormatEmail(i);
//			     }
//					
//			}
//		}
//		
//	}
//
//
//	private void setMailPersonal( int i, Empleado empleado,
//			List<Cell> cellTempList, int j) {
//		if (cellTempList.get(j) != null) {
//			String mailPersonal = getValue(cellTempList, j);
//			if (!StringUtils.isEmpty(mailPersonal)) {
//				mailPersonal = mailPersonal.replaceAll(" ", "");
//				
//			     Matcher mat = pat.matcher(mailPersonal);
//			     if (mat.matches()) {
//			    	 empleado.setMailPersonal(mailPersonal);
//			     } else {
//			         setIncorrectFormatEmail(i);
//			     }
//					
//			}
//		}
//		
//	}
//
//
//	
//	private void setContactoUrgencias(int i, Empleado empleado,	List<Cell> cellTempList, int j) {
//		if (cellTempList.get(j) != null) {
//			
//			cellTempList.get(j).setCellType(Cell.CELL_TYPE_STRING);
//			
//			String contactoUrgencias = getValue(cellTempList, j);
//			if (!StringUtils.isEmpty(contactoUrgencias)) {
//					empleado.setContactoUrgencias(contactoUrgencias);
//			}
//		}
//		
//	}
//
//
//	private void setTelUrgencias(int i, Empleado empleado,List<Cell> cellTempList, int j) {
//		
//		if (cellTempList.get(j) != null) {
//			
//			cellTempList.get(j).setCellType(Cell.CELL_TYPE_STRING);
//			
//			String urgencias = getValue(cellTempList, j);
//			if (!StringUtils.isEmpty(urgencias)) {
//				urgencias = urgencias.replaceAll(" ", "");
//				try{
//					new Double(urgencias);
//					empleado.setTelUrgencias(urgencias);
//					
//				}catch(Exception e){
//					setFormatoNumeroIncorrecto(i);
//				}
//					
//			}
//		}
//		
//	}
//
//
//	private void setCelular(int i, Empleado empleado,
//			List<Cell> cellTempList, int j) {
//		if (cellTempList.get(j) != null) {
//			
//			cellTempList.get(j).setCellType(Cell.CELL_TYPE_STRING);
//			
//			String celular = getValue(cellTempList, j);
//			if (!StringUtils.isEmpty(celular)) {
//				celular = celular.replaceAll(" ", "");
//				try{
//					new Double(celular);
//					empleado.setCelular(celular);
//				}catch(Exception e){
//					setFormatoNumeroIncorrecto(i);
//				}
//					
//			}
//		}
//		
//	}
//
//	
//	private void setTelefonoFijo(int i, Empleado empleado,
//			List<Cell> cellTempList, int j) {
//		if (cellTempList.get(j) != null) {
//			
//			cellTempList.get(j).setCellType(Cell.CELL_TYPE_STRING);
//			
//			String telefono = getValue(cellTempList, j);
//			if (!StringUtils.isEmpty(telefono)) {
//				telefono = telefono.replaceAll(" ", "");
//				try{
//					new Double(telefono);
//					empleado.setTelFijo(telefono);
//				}catch(Exception e){
//					setFormatoNumeroIncorrecto(i);
//				}
//					
//			}
//		}
//		
//	}
//
//
//	private void setDomicilio(int i, Empleado empleado,
//			List<Cell> cellTempList, int j) {
//		if (cellTempList.get(j) != null) {
//			String domicilio = getValue(cellTempList, j);
//			if (!StringUtils.isEmpty(domicilio)) {
//				empleado.setDomicilio(domicilio);
//				
//			}
//		}
//		
//	}
//
//	
//	private void setNacionalidad( int i, Empleado empleado,
//			List<Cell> cellTempList, int j) {
//		if (cellTempList.get(j) != null) {
//			String nacionalidadStr = getValue(cellTempList, j);
//			if(!StringUtils.isEmpty(nacionalidadStr)){
//				if(nacionalidadStr == "") {
//					nacionalidadStr = " ";
//				}
//				Nacionalidad nacionalidad = nacionalidadService.getNacionalidadByName(nacionalidadStr);
//				if(nacionalidad != null){
//					empleado.setNacionalidad(nacionalidad);
//				}else{
//					setNacionalidadNoExistente(i);
//				}
//				
//			}else{
//				//setNacionalidadRequerida(i);
//				Nacionalidad nacionalidad = nacionalidadService.getNacionalidadByName(" ");
//				if(nacionalidad != null){
//					empleado.setNacionalidad(nacionalidad);
//				}else{
//					setNacionalidadNoExistente(i);
//				}
//			}
//			
//		}
//		
//	}
//
//	
//
//
//	
//
//
//	private void setEstadoCivil( int i, Empleado empleado,
//			List<Cell> cellTempList, int j) {
//		if (cellTempList.get(j) != null) {
//			String estadoCivilStr = getValue(cellTempList, j);
//			EstadoCivil estadoCivil = EstadoCivil.getEstadoCivilFromString(estadoCivilStr);
//			if(estadoCivil != null){
//				empleado.setEstadoCivil(estadoCivil);
//			}else{
//				setEstadoCivilNoExistente( i);
//				empleado.setEstadoCivil(EstadoCivil.NO_INFORMADO);
//			}
//		}else{
//			//setEstadoCivilRequerido( i);
//			empleado.setEstadoCivil(EstadoCivil.NO_INFORMADO);
//		}
//		
//	}
//
//	
//
//	
//
//	private void setFechaDeNacimiento(int i, Empleado empleado,	List<Cell> cellTempList, int j) {
//		if (cellTempList.get(j) != null) {
//			
//			if(cellTempList.get(j).getCellType() == Cell.CELL_TYPE_NUMERIC && 
//				HSSFDateUtil.isCellDateFormatted(cellTempList.get(j))) 
//			{
//				Date myDate = HSSFDateUtil.getJavaDate(cellTempList.get(j).getNumericCellValue());
//				if(myDate != null) {
//					empleado.setFechaNacimiento(myDate);
//				}else {
//					setFormatoFechaIncorrectoError(i, "Fecha Nacimiento");
//				}
//			}else {
//			
//				String fechaNacimiento = getValue(cellTempList, j);
//				if (!StringUtils.isEmpty(fechaNacimiento)) {
//					try {
//						Date fecha = sdf.parse(fechaNacimiento);
//						empleado.setFechaNacimiento(fecha);
//						
//					} catch (Exception e) {
//						setFormatoFechaIncorrectoError(i, "Fecha Nacimiento");
//					}
//				}
//			}
//		}
//		
//	}
//
//	private void setCuil(int i, Empleado empleado,
//			List<Cell> cellTempList, int j) {
//		if (cellTempList.get(j) != null) {
//			
//			cellTempList.get(j).setCellType(Cell.CELL_TYPE_STRING);
//			
//			String cuilStr = getValue(cellTempList, j);
//			
//			if(!StringUtils.isEmpty(cuilStr)){
//				try{
//					Long.valueOf(cuilStr);
//					if(esCUILValido(cuilStr)){
//						empleado.setCuil(cuilStr);
//					}else{
//						setCUILIncorrectoFormatoError(i);
//					}
//					
//				}
//				catch(Exception e){
//					setCUILIncorrectoFormatoError(i);
//				}
//					
//			}else{
//				setcCUILObligatorio(i);
//			}
//			
//		}
//		
//	}
//
//	private void setDNI( int i, Empleado empleado,
//			List<Cell> cellTempList, int j) {
//		if (cellTempList.get(j) != null) {
//			
//			cellTempList.get(j).setCellType(Cell.CELL_TYPE_STRING);
//			
//			String dniStr = getValue(cellTempList, j);
//			if(!StringUtils.isEmpty(dniStr)){
//				try{
//					new BigDecimal(dniStr);
//					if(dniStr.length() == 8){
//						empleado.setDni(dniStr);	
//					}else{
//						setDniIncorrectoFormatoError(i);
//					}
//					
//				}catch(Exception e){
//					setDniIncorrectoFormatoError(i);
//				}
//			}else{
//				setDNIObligatorio(i);
//			}
//			
//		}
//		
//	}
//
//
//	private void setApellido( int i, Empleado empleado,
//			List<Cell> cellTempList, int j) {
//		if (cellTempList.get(j) != null) {
//			String apellido = getValue(cellTempList, j);
//			if (!StringUtils.isEmpty(apellido)) {
//				empleado.setApellidos(apellido);
//				
//			} else {
//				setApellidoObligatorioError( i);
//
//			}
//		}
//		
//	}
//
//	private void setNombre(int i, Empleado empleado,
//			List<Cell> cellTempList, int j) {
//		if (cellTempList.get(j) != null) {
//			String nombre = getValue(cellTempList, j);
//			if (!StringUtils.isEmpty(nombre)) {
//				empleado.setNombres(nombre);
//				
//			} else {
//				setNombreObligatorioError( i);
//
//			}
//		}
//		
//	}
//
//	
//
//	private void setFecha(int i, Empleado empleado,	List<Cell> cellTempList, int j, RelacionLaboral relacionLaboral) {
//		if(relacionLaboral == null || relacionLaboral.getCliente() == null) {
//			return;
//		}
//		
//		if (cellTempList.get(j) != null) {
//			
//			if(cellTempList.get(j).getCellType() == Cell.CELL_TYPE_NUMERIC && 
//					HSSFDateUtil.isCellDateFormatted(cellTempList.get(j))) 
//			{
//				Date myDate = HSSFDateUtil.getJavaDate(cellTempList.get(j).getNumericCellValue());
//				if(myDate != null) {
//					relacionLaboral.setFechaInicio(myDate);
//					List<RelacionLaboral> relaciones = new ArrayList<RelacionLaboral>();
//					relaciones.add(relacionLaboral);
//					empleado.setRelacionLaboral(relaciones);
//				}else {
//					setFormatoFechaIncorrectoError(i, "Fecha Inicio relacion labural");
//				}
//			}else {
//		
//				String fechaRelacion = getValue(cellTempList, j);
//				if (!StringUtils.isEmpty(fechaRelacion)) {
//					try {
//						Date fecha = sdf.parse(fechaRelacion);
//						if(relacionLaboral != null && relacionLaboral.getCliente() != null && relacionLaboral.getPuesto() != null){
//								Date fechaIngreso = empleado.getFechaIngresoNovatium();
//								if(fechaIngreso != null){
//									if(fecha.before(fechaIngreso)){
//										setFechaRelacionError(i);
//										return;
//									}	
//								}
//							relacionLaboral.setFechaInicio(fecha);
//							List<RelacionLaboral> relaciones = new ArrayList<RelacionLaboral>();
//							relaciones.add(relacionLaboral);
//							empleado.setRelacionLaboral(relaciones);
//						}else{
//							setClienteYPuestoError(i);
//						}
//					} catch (Exception e) {
//						setFormatoFechaIncorrectoError( i, "Fecha Inicio relacion labural");
//					}
//				}else {
//					setFechaRequerida(i);
//				}
//			}
//		}
//		
//	}
//
//
//
//
//
//
//
//
//	private RelacionLaboral setPuesto(int i,	List<Cell> cellTempList, int j, RelacionLaboral relacionLaboral) {
//		if(relacionLaboral == null || relacionLaboral.getCliente() == null) {
//			return relacionLaboral;
//		}
//		
//		String puesto = "";
//		if(cellTempList.get(j) != null){
//			puesto = getValue(cellTempList, j);
//		}
//		
//		if(StringUtils.isEmpty(puesto)){
//			puesto = "no definido";
//		}
//		
//		if(StringUtils.isNotEmpty(puesto)) {
//			if(relacionLaboral == null && !StringUtils.isEmpty(puesto)){
//				setClienteObligatoriaError(i);
//				
//			}else if(!StringUtils.isEmpty(puesto)){
//				Puesto puestoRL = relacionLaboralService.getPuestoByNombre(puesto);
//				relacionLaboral.setPuesto(puestoRL);
//				if(puestoRL == null){
//					setPuestoError(i);
//				}
//			}else if(relacionLaboral != null && relacionLaboral.getCliente() != null && StringUtils.isEmpty(puesto)){
//				setPuestoObligatorio(i);
//			}
//		}
//		return relacionLaboral;
//	}
//
//	
//
//
//	private RelacionLaboral setCliente(int i,
//			List<Cell> cellTempList, int j, RelacionLaboral relacionLaboral) {
//		if (cellTempList.get(j) != null) {
//			String cliente = getValue(cellTempList, j);
//			Cliente clienteRZ = clienteService.getClienteByNombre(cliente);
//			if (clienteRZ != null) {
//
//				relacionLaboral = new RelacionLaboral();
//				relacionLaboral.setCliente(clienteRZ);
//				
//			}
//			if (!StringUtils.isEmpty(cliente) && clienteRZ == null) {
//				setClienteNoExisteError( i);
//			}
//		}
//		return relacionLaboral;	
//	}
//
//	private void setFechaEgreso(int i, Empleado empleado, List<Cell> cellTempList, int j) {
//		if (cellTempList.get(j) != null) {
//			
//			if(cellTempList.get(j).getCellType() == Cell.CELL_TYPE_NUMERIC && 
//				HSSFDateUtil.isCellDateFormatted(cellTempList.get(j))) 
//			{
//				Date myDate = HSSFDateUtil.getJavaDate(cellTempList.get(j).getNumericCellValue());
//				if(myDate != null) {
//					empleado.setFechaEgresoNovatium(myDate);
//					empleado.setEstado(EstadoEmpleado.BAJA);
//				}else {
//					setFormatoFechaIncorrectoError(i, "Fecha Egreso Novatium");
//				}
//			}else {
//			
//				String fechaEgreso = getValue(cellTempList, j);
//				if (!StringUtils.isEmpty(fechaEgreso)) {
//					try {
//						Date fecha = sdf.parse(fechaEgreso);
//						empleado.setFechaEgresoNovatium(fecha);
//						empleado.setEstado(EstadoEmpleado.BAJA);
//					} catch (Exception e) {
//						setFormatoFechaIncorrectoError(i, "Fecha Egreso Novatium");
//					}
//				}
//			}
//		}
//	}
//
//	private void setFechaIngreso(int i, Empleado empleado, List<Cell> cellTempList, int j) {
//		if (cellTempList.get(j) != null) {
//			
//			if(cellTempList.get(j).getCellType() == Cell.CELL_TYPE_NUMERIC && 
//				HSSFDateUtil.isCellDateFormatted(cellTempList.get(j))) 
//			{	
//				Date myDate = HSSFDateUtil.getJavaDate(cellTempList.get(j).getNumericCellValue());
//				if(myDate != null) {
//					empleado.setFechaIngresoNovatium(myDate);
//					empleado.setEstado(EstadoEmpleado.ACTUAL);
//				}else {
//					setFormatoFechaIncorrectoError(i, "Fecha Ingreso Novatium");
//				}
//			}else {
//			
//				String fechaIngresoNovatium = getValue(cellTempList, j);
//				if (!StringUtils.isEmpty(fechaIngresoNovatium)) {
//					try {
//						Date fecha = sdf.parse(fechaIngresoNovatium);
//						empleado.setFechaIngresoNovatium(fecha);
//						empleado.setEstado(EstadoEmpleado.ACTUAL);
//					} catch (Exception e) {
//						setFormatoFechaIncorrectoError(i, "Fecha Ingreso Novatium");
//					}
//				} else {
//					setFechaIngresoObligatoriaError(i);
//	
//				}
//			}
//		}
//	}
//
//	private String getValue(List<Cell> cellTempList, int j) {
//		Cell hssfCell =  cellTempList.get(j);
//		
//		if(hssfCell == null) {
//			return "";
//		}
//		
//		String result = null;
//		if(hssfCell != null){
//			
//			try{
//				result = hssfCell.getStringCellValue();	
//			}catch(Exception e){
//				result = String.valueOf(hssfCell.getNumericCellValue());
//			}
//				
//		}
//		
//		return result.trim();
//	}
//
//	private void setLegajo(int i, Empleado empleado,
//			List<Cell> cellTempList, int j) {
//		if (cellTempList.get(j) != null) {
//			
//			cellTempList.get(j).setCellType(Cell.CELL_TYPE_STRING);
//			
//			String legajo = getValue(cellTempList, j);
//			if (!StringUtils.isEmpty(legajo)) {
//				empleado.setLegajo(Integer.valueOf(legajo));
//				if (empleadoService.getCountEmpleadoByLegajo(Integer.valueOf(legajo), null) > 0) {
//					setDuplicadoLegajoError(i);
//				}
//			} else {
//				setLegajoObligatorioError(i);
//
//			}
//		}
//	}
//
//	private void setClienteNoExisteError(int i) {
//		if (error.equals("")) {
//			error = "Registro " + i + ":";
//		}
//		error = error + mensajes.getString("ERR036");
//
//	}
//	
//	private void setNacionalidadRequerida( int i) {
//		if (error.equals("")) {
//			error = "Registro " + i + ":";
//		}
//		error = error + mensajes.getString("ERR037");
//		
//	}
//	
//	private void setIncorrectFormatEmail(int i) {
//		if (error.equals("")) {
//			error = "Registro " + i + ":";
//		}
//		error = error +  mensajes.getString("ERR038");
//		
//	}
//	private void setIncorrectFormatNroCuenta(int i) {
//		if (error.equals("")) {
//			error = "Registro " + i + ":";
//		}
//		error = error +  mensajes.getString("ERR039");
//		
//	}
//
//	
//	private void setEstadoCivilNoExistente( int i) {
//		if (error.equals("")) {
//			error = "Registro " + i + ":";
//		}
//		error = error + mensajes.getString("ERR040");
//
//		
//	}
//	
//	private void setFormatoNumeroIncorrecto( int i) {
//		if (error.equals("")) {
//			error = "Registro " + i + ":";
//		}
//		error = error + mensajes.getString("ERR041");
//		
//	}
//	
//	private void setFormatoIncorrectoBoolean(int i) {
//		if (error.equals("")) {
//			error = "Registro " + i + ":";
//		}
//		error = error + mensajes.getString("ERR042");
//		
//	}
//	private void setNacionalidadNoExistente(int i) {
//		if (error.equals("")) {
//			error = "Registro " + i + ":";
//		}
//		error = error + mensajes.getString("ERR043");
//		
//	}
//	
//	private void setObraSocialNoExistente(int i) {
//		if (error.equals("")) {
//			error = "Registro " + i + ":";
//		}
//		error = error + mensajes.getString("ERR044");
//		
//	}
//	
//	private void setPrepagaNoExistente(int i) {
//		if (error.equals("")) {
//			error = "Registro " + i + ":";
//		}
//		error = error +  mensajes.getString("ERR045");
//		
//	}
//	
//	private void setDniIncorrectoFormatoError(int i) {
//		if (error.equals("")) {
//			error = "Registro " + i + ":";
//		}
//		error = error +mensajes.getString("ERR046");
//		
//	}
//	
//	private void setEstadoCivilRequerido(int i) {
//		if (error.equals("")) {
//			error = "Registro " + i + ":";
//		}
//		error = error +mensajes.getString("ERR047"); 
//		
//	}
//	private void setCUILIncorrectoFormatoError( int i) {
//		if (error.equals("")) {
//			error = "Registro " + i + ":";
//		}
//		error = error + mensajes.getString("ERR048");
//		
//	}
//	
//	private void setPuestoError(int i) {
//		if (error.equals("")) {
//			error = "Registro " + i + ":";
//		}
//		error = error +mensajes.getString("ERR049"); 
//
//	}
//	private void setClienteObligatoriaError(int i) {
//		if (error.equals("")) {
//			error = "Registro " + i + ":";
//		}
//		error = error + mensajes.getString("ERR050");
//
//	}
//
//	private void setLegajoObligatorioError( int i) {
//		if (error.equals("")) {
//			error = "Registro " + i + ":";
//		}
//		error = error + mensajes.getString("ERR051");
//	}
//	private void setNombreObligatorioError( int i) {
//		if (error.equals("")) {
//			error = "Registro " + i + ":";
//		}
//		error = error +mensajes.getString("ERR052");
//	}
//	
//	private void setDNIObligatorio(int i) {
//		
//		if (error.equals("")) {
//			error = "Registro " + i + ":";
//		}
//		error = error + mensajes.getString("ERR053");
//		
//	}
//	
//	private void setcCUILObligatorio( int i) {
//		
//		if (error.equals("")) {
//			error = "Registro " + i + ":";
//		}
//		error = error + mensajes.getString("ERR054");
//		
//	}
//	private void setApellidoObligatorioError( int i) {
//		if (error.equals("")) {
//			error = "Registro " + i + ":";
//		}
//		error = error + "El apellido es obligatorio. ";
//	}
//	private void setFechaIngresoObligatoriaError(int i) {
//		if (error.equals("")) {
//			error = "Registro " + i + ":";
//		}
//		error = error +  mensajes.getString("ERR055");
//	}
//
//	private void setFormatoFechaIncorrectoError( int i, String campo) {
//		if (error.equals("")) {
//			error = "Registro " + i + ":";
//		}
//		String myError = "";
//		if(campo != null) {
//			myError = "(" + campo + ")";
//		}
//		myError += mensajes.getString("ERR056");
//		error = error + myError;
//	}
//
//	private void setDuplicadoLegajoError( int i) {
//		if (error.equals("")) {
//			error = "Registro " + i + ":";
//		}
//		//error = error +  mensajes.getString("ERR057");
//	}
//	
//	private void setClienteYPuestoError(int i) {
//		if (error.equals("")) {
//			error = "Registro " + i + ":";
//		}
//		error = error + mensajes.getString("ERR058");
//		
//	}
//	
//	private void setBancoRequerido( int i) {
//		if (error.equals("")) {
//			error = "Registro " + i + ":";
//		}
//		error = error + mensajes.getString("ERR059"); 
//		
//	}
//
//
//	private void setBanoNoExiste( int i) {
//		if (error.equals("")) {
//			error = "Registro " + i + ":";
//		}
//		error = error + mensajes.getString("ERR060"); 
//		
//	}
//	
//	private void setNroCuentaRequerido( int i) {
//		if (error.equals("")) {
//			error = "Registro " + i + ":";
//		}
//		error = error + mensajes.getString("ERR061"); 
//	}
//	
//	private void setConvenioRequerido( int i) {
//		if (error.equals("")) {
//			error = "Registro " + i + ":";
//		}
//		error = error +  mensajes.getString("ERR062"); 
//	}
//	
//	private void setIncorrectFormatCBU(int i) {
//		if (error.equals("")) {
//			error = "Registro " + i + ":";
//		}
//		error = error +  mensajes.getString("ERR063");
//		
//	}
//
//
//	private void setCBURequerido( int i) {
//		if (error.equals("")) {
//			error = "Registro " + i + ":";
//		}
//		error = error + mensajes.getString("ERR064");
//		
//	}
//	
//	private void setPuestoObligatorio( int i) {
//		if (error.equals("")) {
//			error = "Registro " + i + ":";
//		}
//		error = error + mensajes.getString("ERR065");
//		
//	}
//	
//	private void setFechaRequerida(int i) {
//		if (error.equals("")) {
//			error = "Registro " + i + ":";
//		}
//		error = error + mensajes.getString("ERR066");
//		
//	}
//	private void setFechaRelacionError(int i) {
//		if (error.equals("")) {
//			error = "Registro " + i + ":";
//		}
//		error = error + mensajes.getString("ERR067");
//		
//		
//	}
//	
//	private void setFormatoSueldoIncorrecto(int i) {
//		if (error.equals("")) {
//			error = "Registro " + i + ":";
//		}
//		error = error + mensajes.getString("ERR068");
//		
//	}
//	private void setPresentismoObligatorio(int i) {
//		if (error.equals("")) {
//			error = "Registro " + i + ":";
//		}
//		error = error + mensajes.getString("ERR069");
//		
//	}
//
//
//	private static String getFileExtension(String fname2)
//	{
//	      String fileName = fname2;
//	      String ext="";
//	      int mid= fileName.lastIndexOf(".");
//	      ext=fileName.substring(mid+1,fileName.length());
//	      return ext;
//	  }
//	  
//	 public static boolean  esCUILValido(String inputValor) {
//		    String inputString = inputValor.toString();
//		    if (inputString.length() == 11) {
//		    	String chartA = String.valueOf((inputString.charAt(0)));
//		    	String chartB = String.valueOf((inputString.charAt(1)));
//		    	
//		        String caracters_1_2 = chartA + chartB;
//		        if (caracters_1_2.equals("20") || caracters_1_2.equals("23") || caracters_1_2.equals("24") || caracters_1_2.equals("27") || caracters_1_2.equals("30") || caracters_1_2.equals("33") || caracters_1_2.equals("34")) {
//		            int count = inputString.charAt(0) * 5 + inputString.charAt(1) * 4 + inputString.charAt(2) * 3 + inputString.charAt(3) * 2 + inputString.charAt(4) * 7 + inputString.charAt(5) * 6 + inputString.charAt(6) * 5 + inputString.charAt(7) * 4 + inputString.charAt(8) * 3 + inputString.charAt(9) * 2 + inputString.charAt(10) * 1;
//		            double division = count / 11;
//		            if (division == Math.floor(division)) {
//		                return true;
//		            }
//		        }
//		    }
//		    return false;
//		}
}
