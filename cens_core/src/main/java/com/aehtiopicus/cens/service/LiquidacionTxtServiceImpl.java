package com.aehtiopicus.cens.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.aehtiopicus.cens.domain.Empleado;
import com.aehtiopicus.cens.domain.InformeConsolidadoDetalle;


@Service("liquidacionTxtService")
public class LiquidacionTxtServiceImpl implements LiquidacionService {
	
	private static final Logger logger = Logger.getLogger(LiquidacionTxtServiceImpl.class);
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	private static final  String BANCO = "GALICIA";
	private static final  String MONEDA = "1";
	private static final  String TIPO_TX = "1";
	private static final  String TIPO_REGISTRO = "*F";
	@Value("${empresa.registros}")
	private String registros;
	@Value("${empresa.header}")
	private String header;
	@Value("${empresa.prefijocbu}")
	private String prefijoCbu;
	@Value("${empresa.tipocuenta}")
	private String tipoCuenta;
	@Value("${empresa.cuit}")
	private String cuit;
	@Value("${empresa.transaccion}")
	private String codigoTransaccion;
	@Value("${empresa.folio}")
	private String folioEmpresa;
	@Value("${empresa.digito2}")
	private String empresaDigito1;
	@Value("${empresa.digito1}") 
	private String empresaDigito2;
	@Value("${empresa.sucursal}") 
	private String empresaSucursal;
	@Value("${empresa.codigo}")
	private String codigoEmpresa;
	
	List<String> archivos = new ArrayList<String>();
	// id del detalle 
	private static Map<Long,InformeConsolidadoDetalle> galicia = new HashMap<Long,InformeConsolidadoDetalle>(); 
	/**
	 * Genera los archivos de pago txt para todos los empleados
	 * que no tienen cuenta en el banco galicia
	 */
	@Override
	public List<String> generarArchivosLiquidacion(Date fechaAcreditacion,
			List<InformeConsolidadoDetalle> detalle, String referencia, String periodo) {
		List<String> archivos = new ArrayList<String>();
		logger.info("generar archivo pago txt");
		Map<String,String> fileNames = new HashMap<String,String>();
		galicia = new HashMap<Long,InformeConsolidadoDetalle>();
		if (!CollectionUtils.isEmpty(detalle)) {
			for (InformeConsolidadoDetalle informeDetalle : detalle) {
			// el importe a depositar debe ser mayor a cero
			 if(informeDetalle.getNetoADepositar() != null && informeDetalle.getNetoADepositar() > 0){
				 if (isEmpleadoNotNull(informeDetalle)) {
						 if(!isBancoGalicia(informeDetalle) && informeDetalle.obtenerEmpleado().getBanco() != null){	
							createFile(fechaAcreditacion, referencia, periodo,
									archivos, fileNames, informeDetalle); 
						 }
				 	}
			 	}
			}
		}
		return archivos; 
	}

	private void createFile(Date fechaAcreditacion, String referencia,
			String periodo, List<String> archivos,
			Map<String, String> fileNames,
			InformeConsolidadoDetalle informeDetalle) {
		try {
			String fileName = getFileName(informeDetalle,periodo,fileNames)+".txt";
			File pago = new File(fileName);
			archivos.add(fileName);
			String line1 = getLine1(fechaAcreditacion, informeDetalle,referencia);
			FileWriter writer = new FileWriter(pago, false);
			BufferedWriter bw = new BufferedWriter(writer);
			bw.append(getHeader(informeDetalle, fechaAcreditacion));
			bw.newLine();
			bw.append(line1);
			bw.newLine();
			bw.append(getFooter());
			bw.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("hubo un error al escribir el archivo para el detalle "
					+ informeDetalle.getId());
		}
	}

	private boolean isBancoGalicia(InformeConsolidadoDetalle informeDetalle) {
		boolean isGalicia = false;
		if(isEmpleadoNotNull(informeDetalle)){
			Empleado empleado = informeDetalle.obtenerEmpleado();
			if(empleado.getBanco() != null && empleado.getBanco().getNombre() != null && empleado.getBanco().getNombre().toLowerCase().equals(BANCO.toLowerCase())){
				isGalicia = true;
				//Solo se crearan los que tengan un importe mayor a cero
				if(informeDetalle.getNetoADepositar() != null && informeDetalle.getNetoADepositar() > 0){
					galicia.put(informeDetalle.getId(), informeDetalle);	
				}
				
			}
		}
		return isGalicia;
	}

	/**
	 * retorna el nombre del archivo con el siguiente formato
	 * MMM APELLIDO
	 * @param informeDetalle
	 * @param fileNames 
	 * @param per
	 * @return
	 */
	private String getFileName(InformeConsolidadoDetalle informeDetalle,
			String periodo, Map<String, String> fileNames) {
		String apellido = informeDetalle.obtenerEmpleado().getApellidos();
		periodo =periodo.substring(0, periodo.length()-4);
		String fileName = periodo.toUpperCase()+apellido.toUpperCase();
		if(fileNames.containsKey(fileName)){
			while(fileNames.containsKey(fileName)){
				fileName = fileName + "_";
			}
			
		}
		fileNames.put(fileName, fileName);
		return fileName;
	}

	/**
	 * Se crea el footer con el tipo de registro
	 * y la empresa
	 * @return
	 */
	private String getFooter() {
		String footer = new String();
		// filler son 135 espacios en blanco
		footer = TIPO_REGISTRO + codigoEmpresa+registros+getEspacios(135);
		return footer;
	}
	

	/**
	 * se recupera la referencia y se completa con 
	 * los espacios en blanco necesarios
	 * @param referencia
	 * @return
	 */
	private String getReferencia(String referencia) {
		if (referencia.length() > 15) {
			referencia = referencia.substring(0, 14);
		} else if(referencia.length() < 15){
			String espacios = getEspacios(Math.abs(referencia.length() - 15));
			if(espacios.length() >= 2){
			referencia = espacios.substring(0,espacios.length()/2) + referencia +espacios.substring(espacios.length()/2,espacios.length());
		  }
		}
		return referencia;
	}

	/**
	 * se recupera el deposito  y se completan con ceros hasta cumplir
	 * los diez caracteres
	 * @param informeDetalle
	 * @return
	 */
	private String getDeposito(InformeConsolidadoDetalle informeDetalle) {
		String deposito;
		if (informeDetalle.getNetoADepositar() != null) {
			deposito = informeDetalle.getNetoADepositar().toString();
			String[] total = deposito.split("\\.");
			if(total.length == 1){
				deposito = deposito +"00";
			}else if(total.length == 2){
				 String decimal = total[1];
				 if(decimal.length() > 2){
					 decimal = decimal.substring(0,2);
				 }else if(decimal.length() < 2){
					 decimal = decimal + "0";
				 }
				deposito = total[0] + decimal;
			}
			if (deposito.length() < 10) {
				deposito = getCeros(10 - deposito.length()) + deposito;
			}
			if (deposito.length() > 10) {
				deposito = deposito.substring(0, 9);
			}
		} else {
			deposito = getCeros(10);
		}
		return deposito;
	}
	
	private String getHeader(InformeConsolidadoDetalle informeDetalle,Date fechaAcreditacion){
		String fecha = sdf.format(fechaAcreditacion);
		String cabecera =  header + codigoEmpresa + cuit + tipoCuenta + MONEDA + folioEmpresa + empresaDigito1 + empresaSucursal + empresaDigito2 +
				         getCeros(26) + getDeposito(informeDetalle) + fecha + getEspacios(72);
		return cabecera;
	}
	
	private String getTipoCuenta(Empleado empleado) {
		String tipoCuentaBancaria = "C";
		if(!StringUtils.isEmpty(empleado.getCbu())){
			empleado.getCbu();
			String tipoCuenta = getDato(empleado, 8, 9);
			if(tipoCuenta.equals("3")){
				tipoCuentaBancaria = "A";
			}
		}
		return tipoCuentaBancaria;
	}

	private String getFolioSucursalYDigitosVerificadores(Empleado empleado){
		String folio = getDato(empleado,12,19);
		String sucursal = getDato(empleado,4,7);
		String digitoVerificador1 = getDato(empleado,19,20);
		String digitoVerificador2  = getDato(empleado,20,21);
		return folio + sucursal + digitoVerificador1 + digitoVerificador2;
	}
	/**
	 * se arma la linea uno del txt con los datos del empleado
	 * @param fechaAcreditacion
	 * @param informeDetalle
	 * @param referencia
	 * @return
	 * @throws IOException
	 */
	private String getLine1(Date fechaAcreditacion,
			InformeConsolidadoDetalle informeDetalle,
			String referencia) throws IOException {
		logger.info("obteniendo linea 1");
		String fecha = sdf.format(fechaAcreditacion);
		String fechaMovimiento = sdf.format(new Date());
		Empleado empleado = informeDetalle.obtenerEmpleado();
		String line1 = getDatosEmpleado(fechaAcreditacion, empleado);

		String deposito = getDeposito(informeDetalle);
		referencia = getReferencia(referencia);
		String nombre = empleado.getApellidos().toUpperCase() + " " + empleado.getNombres().toUpperCase();
		nombre = completarCaracteresNombre(nombre, 22);
		line1 = line1 + fecha + getTipoCuenta(empleado) + MONEDA + getEspacios(12)+
				getCbu(empleado) + codigoTransaccion + TIPO_TX
				+  deposito + referencia
				// campo filler es un campo en blanco con 17 espacios
				+ nombre + fechaMovimiento + getEspacios(17);
		return line1;
	}
	private String getCbu(Empleado empleado) {
		String cbu = empleado.getCbu();
		if(!StringUtils.isEmpty(cbu)){
			cbu = "0"+cbu.substring(0,8)+"000"+cbu.substring(8,cbu.length());	
		}
		
		return cbu;
	}

	/**
	 * se recuperan los caracteres especificados del cbu
	 * @param empleado
	 * @param desde
	 * @param hasta
	 * @return
	 */
	private String getDato(Empleado empleado,int desde, int hasta) {
		String dato = "";
		if(empleado.getCbu()!= null){
			dato = empleado.getCbu().substring(desde,hasta);
		}
		return dato;
	}
		
	/**
	 * se recupera el nombre y cuil del empleado
	 * @param fechaAcreditacion
	 * @param empleado
	 * @return
	 */
	private String getDatosEmpleado(Date fechaAcreditacion, Empleado empleado) {
	
		String linea1 = new String();
		String nombre = empleado.getApellidos().toUpperCase() + " "
					+ empleado.getNombres().toUpperCase();
		nombre = completarCaracteresNombre(nombre, 16);
		linea1 = nombre + empleado.getCuil();
		return linea1;
	}

	private String completarCaracteresNombre(String nombre, int longitud) {
		if (nombre.length() < longitud) {
			// para completar los caracteres agregamos espacios en blanco
			nombre = nombre + getEspacios(Math.abs(nombre.length()  - longitud));
		} else {
			// si la longitud del nombre es mayor entonces cortamos el nombre
			// solo hasta 16 caracteres
			nombre = nombre.substring(0, longitud);
		}
		return nombre;
	}

	/**
	 * se verifica si el detalle tiene un empleado asignado en su relacion
	 * laboral
	 * @param informeDetalle
	 * @return
	 */
	private boolean isEmpleadoNotNull(InformeConsolidadoDetalle informeDetalle) {
		return informeDetalle.obtenerEmpleado() != null;
	}

	/**
	 * retorna la cantidad de espacios en blanco 
	 * especificados
	 * @param cantidad
	 * @return
	 */
	private String getEspacios(int cantidad) {
		String result = new String();
		for (int i = 0; i < cantidad; i++) {
			result = result + " ";
		}
		return result;
	}

	/**
	 * retorna la cantidad de ceros especificados
	 * @param cantidad
	 * @return
	 */
	private String getCeros(int cantidad) {
		String result = new String();
		for (int i = 0; i < cantidad; i++) {
			result = result + "0";
		}
		return result;
	}

	public void setCuit(String cuit) {
		this.cuit = cuit;
	}

	public void setCodigoTransaccion(String codigoTransaccion) {
		this.codigoTransaccion = codigoTransaccion;
	}

	public void setFolioEmpresa(String folioEmpresa) {
		this.folioEmpresa = folioEmpresa;
	}

	public void setEmpresaDigito1(String empresaDigito1) {
		this.empresaDigito1 = empresaDigito1;
	}

	public void setEmpresaDigito2(String empresaDigito2) {
		this.empresaDigito2 = empresaDigito2;
	}

	public void setEmpresaSucursal(String empresaSucursal) {
		this.empresaSucursal = empresaSucursal;
	}

	public void setCodigoEmpresa(String codigoEmpresa) {
		this.codigoEmpresa = codigoEmpresa;
	}

	public void setRegistros(String registros) {
		this.registros = registros;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public void setPrefijoCbu(String prefijoCbu) {
		this.prefijoCbu = prefijoCbu;
	}

	public void setTipoCuenta(String tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
	}

	public static Map<Long, InformeConsolidadoDetalle> getGalicia() {
		return galicia;
	}
	
	

}
