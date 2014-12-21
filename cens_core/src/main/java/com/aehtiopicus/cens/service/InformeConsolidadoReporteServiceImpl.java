package com.aehtiopicus.cens.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.aehtiopicus.cens.domain.Cliente;
import com.aehtiopicus.cens.domain.Empleado;
import com.aehtiopicus.cens.domain.InformeConsolidadoDetalle;
import com.aehtiopicus.cens.domain.InformeConsolidadoDetalleBeneficio;
import com.aehtiopicus.cens.domain.RelacionLaboral;
import com.aehtiopicus.cens.enumeration.InformeIntermedioEstadoEnum;
import com.aehtiopicus.cens.utils.PeriodoUtils;
import com.aehtiopicus.cens.utils.Utils;

@Service
@Transactional
public class InformeConsolidadoReporteServiceImpl implements
		InformeConsolidadoReporteService {

	private static final Logger logger = Logger
			.getLogger(InformeConsolidadoReporteServiceImpl.class);

	public final static String DD_MM_YYYY = "dd/MM/yyyy";
	private int countBenef = 0;
	public static SimpleDateFormat sdf = new SimpleDateFormat(DD_MM_YYYY);
	Map<Long, String> mapBeneficiosNombre = new HashMap<Long, String>();
	Map<Long, Map<String, Double>> mapBeneficiosValor = new HashMap<Long, Map<String,Double>>();
	Map<Long, List<String>> mapBeneficiosDetalle = new HashMap<Long, List<String>>();
	Map<String, Integer> mapBeneficiosColumna = new HashMap<String, Integer>();
	@Value("${mes.liquidacion.vacaciones}") 
	protected Integer mesLiquidacionVacaciones;
	
	@Override
	public HSSFWorkbook getInformeExcel(List<InformeConsolidadoDetalle> detalles, Date periodo) throws IOException {
		
		logger.info("informe excel consolidado");
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet worksheet = workbook.createSheet("Informe Consolidado");
		loadBeneficiosMap(detalles);
		crearEncabezado(worksheet, workbook, periodo);
		cargarDatos(worksheet, workbook, detalles);
		return workbook;

	}

	@SuppressWarnings("deprecation")
	private void cargarDatos(HSSFSheet worksheet, HSSFWorkbook workbook,
			List<InformeConsolidadoDetalle> detalles) {
		int numeroFila = 3;
		HSSFCellStyle cellStyle = setEstilosDatos(workbook);
		HSSFCellStyle cellStyleNumber = setEstilosDatos(workbook);
		DataFormat format = workbook.createDataFormat();
		cellStyleNumber.setDataFormat(format.getFormat("###0.00"));
		double totalDatoBasico = 0;
		double totalBasico = 0;
		double totalAsistenciaPuntualidad = 0;
		double totalC50 = 0;
		double totalC100 = 0;
		double totalHE50 = 0;
		double totalHE100 = 0;
		double totalNeto = 0;
		double totalCheque = 0;
		double totalReintegros = 0;
		double totalValueAdelantos = 0;
		double totalPrepaga = 0;
		double totalNetoDeposito = 0;
		double totalGanancia = 0;
		double totalCont176510 = 0;
		double totalContOs = 0;
		double totalConceptoNoRemunerativo = 0;
		double totalConceptoRemunerativo = 0;
		double totalVacaciones = 0;
		double totalVacacionesDias = 0;
		double totalSacBase = 0;
		double totalRet11y3 = 0;
		double totalBrutoRem = 0;
		double totalBruto = 0;
		double totalSac = 0;
		double totalObraSocial = 0;
		double totalSacDias = 0;
		double totalEmbargos = 0;
		
		double totalImpInasistI = 0;
		double totalImpInasistSGS = 0;
		
		//numero de columna, valor total
		Map<Integer,Double> totalBeneficios = new HashMap<Integer,Double>();
		
		if (!CollectionUtils.isEmpty(detalles)) {
			HSSFRow row = null;
			Date periodo = detalles.get(0).getPeriodo();
			int columna = 0;
			int lastColumn = 0;
			for (InformeConsolidadoDetalle detalle : detalles) {
				row = worksheet.createRow(numeroFila);
				numeroFila++;
				lastColumn = 0;
				HSSFCell cell = row.createCell(lastColumn++);
				Empleado empleado = getEmpleado(detalle);
				Cliente cliente = getCliente(detalle);
				setNombre(worksheet, cellStyle, cell, empleado);
				
				HSSFCell cell1 = setLegajo(worksheet, row, empleado, lastColumn++);
				cell1.setCellStyle(cellStyle);
				
				setCliente(worksheet, cellStyle, row, cliente, lastColumn++);
				
				setCuil(worksheet, cellStyle, row, empleado, lastColumn++);
				totalDatoBasico = setDatoBasico(worksheet, cellStyle, cellStyleNumber, totalDatoBasico, row, detalle, lastColumn++);
				setDiasTrabajados(worksheet, cellStyle, row, detalle, lastColumn++);
				totalBasico = setBasicoMes(worksheet, cellStyleNumber,	totalBasico, row, detalle, lastColumn++);
				totalAsistenciaPuntualidad = setAsistenciaPuntualidad(worksheet, cellStyleNumber, totalAsistenciaPuntualidad, row, detalle, lastColumn++);
				
				setNroInasistenciasI(worksheet, cellStyleNumber, row, detalle, lastColumn++);
				totalImpInasistI = setImpInasistenciasI(worksheet, cellStyleNumber, totalImpInasistI, row, detalle, lastColumn++);
				setNroInasistenciasSGS(worksheet, cellStyleNumber, row, detalle, lastColumn++);
				totalImpInasistSGS = setImpInasistenciasSGS(worksheet, cellStyleNumber, totalImpInasistSGS, row, detalle, lastColumn++);
				
				totalC50 = setC50(worksheet, cellStyleNumber, totalC50, row, detalle, lastColumn++);
				totalC100 = setC100(worksheet, cellStyleNumber, totalC100, row, detalle, lastColumn++);
				totalHE50 = setHE50(worksheet, cellStyleNumber, totalHE50, row,	detalle, lastColumn++);
				totalHE100 = setHE100(worksheet, cellStyleNumber, totalHE100, row, detalle, lastColumn++);
				
				// Beneficios
				
				for (int i = 0; i < countBenef; i++) {
					HSSFCell celly = row.createCell(lastColumn + i);
					celly.setCellStyle(cellStyle);
				}
				List<String> beneficios = mapBeneficiosDetalle.get(detalle.getId());
				setBeneficios(worksheet, cellStyleNumber, totalBeneficios, row,	detalle, beneficios);
				columna = lastColumn + countBenef;
				
				//Vacaciones
				if(isVacaciones(detalle.getPeriodo())){
					// Vacaciones dias
					HSSFCell vac = row.createCell(columna++);
					if (detalle.getVacacionesDias() != null) {
						vac.setCellValue(detalle.getVacacionesDias());
						totalVacacionesDias = totalVacacionesDias + detalle.getVacacionesDias();
					}
					vac.setCellStyle(cellStyle);
					// Vacaciones 
					HSSFCell vacs = row.createCell(columna++);
					if (detalle.getVacacionesValor() != null) {
						double vacacionesValor = Utils.redondear2Decimales(detalle.getVacacionesValor());
						vacs.setCellValue(vacacionesValor);
						totalVacaciones = totalVacaciones + vacacionesValor;
					}
					vacs.setCellStyle(cellStyleNumber);
				}
				//Concepto remunerativo plus
				HSSFCell rem = row.createCell(columna++);
				if (detalle.getConceptoRemunerativoPlus() != null) {
					double conceptoRemunerativo = Utils.redondear2Decimales(detalle.getConceptoRemunerativoPlus());
					rem.setCellValue(conceptoRemunerativo);
					totalConceptoRemunerativo = totalConceptoRemunerativo + conceptoRemunerativo;
			
				}
				rem.setCellStyle(cellStyleNumber);
				
				//Concepto no remunerativo plus
				HSSFCell remn = row.createCell(columna++);
				if (detalle.getConceptoNoRemunerativo() != null) {
					Double conceptoNoRemunerativo = Utils.redondear2Decimales(detalle.getConceptoNoRemunerativo());
					remn.setCellValue(conceptoNoRemunerativo);
					totalConceptoNoRemunerativo = totalConceptoNoRemunerativo + conceptoNoRemunerativo;
					
				}
				remn.setCellStyle(cellStyleNumber);
				
				//SAC
				//SAC Base
				HSSFCell sacBase = row.createCell(columna++);
				if (detalle.getSacBase() != null) {
					Double sacBaseValue = Utils.redondear2Decimales(detalle.getSacBase());
					sacBase.setCellValue(sacBaseValue);
					totalSacBase = totalSacBase + sacBaseValue;
				}
				sacBase.setCellStyle(cellStyleNumber);
				
				//SAC Dias
				HSSFCell sacDias = row.createCell(columna++);
				if (detalle.getSacDias() != null) {
					sacDias.setCellValue(detalle.getSacDias());
					totalSacDias = totalSacDias + detalle.getSacDias();
				}
				sacDias.setCellStyle(cellStyleNumber);
				//SAC 
				HSSFCell sac = row.createCell(columna++);
				if (detalle.getSacValor() != null) {
					double sacValue = Utils.redondear2Decimales(detalle.getSacValor());
					sac.setCellValue(sacValue);
					totalSac = totalSac + sacValue;
				}
				sac.setCellStyle(cellStyleNumber);


				// Bruto
				HSSFCell bruto = row.createCell(columna++);
				if (detalle.getSueldoBruto() != null) {
					Double brutoValue = Utils.redondear2Decimales(detalle.getSueldoBruto());
					bruto.setCellValue(brutoValue);
					totalBruto = totalBruto + brutoValue;
				}
				bruto.setCellStyle(cellStyleNumber);
				// Bruto Rem
				HSSFCell brutoRem = row.createCell(columna++);
				if (detalle.getSueldoBrutoRemunerativo() != null) {
					Double brutoRemun = Utils.redondear2Decimales(detalle.getSueldoBrutoRemunerativo());
					brutoRem.setCellValue(brutoRemun);
					totalBrutoRem = totalBrutoRem + brutoRemun;
			
				}
				brutoRem.setCellStyle(cellStyleNumber);
				// Ret(11+3)
				HSSFCell  ret = row.createCell(columna++);
				if (detalle.getRet11y3() != null) {
					Double ret11y3 = Utils.redondear2Decimales(detalle.getRet11y3());
					ret.setCellValue(ret11y3);
					totalRet11y3 = totalRet11y3 + ret11y3;
				
				}
				ret.setCellStyle(cellStyleNumber);
				//Aportes OS
				HSSFCell  os = row.createCell(columna++);
				if (detalle.getRetObraSocial() != null) {
					double obraSocial = Utils.redondear2Decimales(detalle.getRetObraSocial());
					os.setCellValue(obraSocial);
					totalObraSocial = totalObraSocial + obraSocial;
				}
				os.setCellStyle(cellStyleNumber);
				//Ret Ganancias
				HSSFCell  gan = row.createCell(columna++);
				if (detalle.getRetGanancia() != null) {
					double ganancia = Utils.redondear2Decimales(detalle.getRetGanancia());
					gan.setCellValue(ganancia);
					totalGanancia = totalGanancia + ganancia; 
				}
				gan.setCellStyle(cellStyleNumber);
				
				//Cont 17 - 6.5 - 10 %
				HSSFCell  ct176510 = row.createCell(columna++);
				if (detalle.getCont176510() != null) {
					double contrib = Utils.redondear2Decimales(detalle.getCont176510());
					ct176510.setCellValue(contrib);
					totalCont176510 += contrib; 
				}
				ct176510.setCellStyle(cellStyleNumber);
				
				//Cont OS 6 %
				HSSFCell  ctOs = row.createCell(columna++);
				if (detalle.getContOS() != null) {
					double contribOs = Utils.redondear2Decimales(detalle.getContOS());
					ctOs.setCellValue(contribOs);
					totalContOs += contribOs; 
				}
				ctOs.setCellStyle(cellStyleNumber);

				
				
				//Neto
				HSSFCell  neto = row.createCell(columna++);
				if (detalle.getNeto() != null) {
					double netoValue = Utils.redondear2Decimales(detalle.getNeto());
					neto.setCellValue(netoValue);
					totalNeto = totalNeto + netoValue ;
				}
				neto.setCellStyle(cellStyleNumber);
				
				//Celular
				
				HSSFCell  cel = row.createCell(columna++);
				if (detalle.getCelular() != null) {
					cel.setCellValue(detalle.getCelular());
					
				}
				cel.setCellStyle(cellStyle);
				//Prepaga
				HSSFCell  prep = row.createCell(columna++);
				if (detalle.getPrepaga() != null) {
					Double prepaga = Utils.redondear2Decimales(detalle.getPrepaga());
					prep.setCellValue(prepaga);
					totalPrepaga = totalPrepaga + prepaga;
				
				}
				prep.setCellStyle(cellStyle);
				//Adelantos
				HSSFCell  adelantos = row.createCell(columna++);
				if (detalle.getAdelantos() != null) {
					Double valueAdelantos = Utils.redondear2Decimales(detalle.getAdelantos());
					adelantos.setCellValue(valueAdelantos);
				
					totalValueAdelantos = totalValueAdelantos + valueAdelantos;
				}
				adelantos.setCellStyle(cellStyleNumber);
				
				//Reintegros
				HSSFCell  rein = row.createCell(columna++);
				if (detalle.getReintegros() != null) {
					Double valueReintegros = Utils.redondear2Decimales(detalle.getReintegros());
					rein.setCellValue(valueReintegros);
				
					totalReintegros = totalReintegros + valueReintegros;
				}
				rein.setCellStyle(cellStyleNumber);

				//Embargos
				HSSFCell  embargos = row.createCell(columna++);
				if (detalle.getEmbargos() != null) {
					Double valueEmbargos = Utils.redondear2Decimales(detalle.getEmbargos());
					embargos.setCellValue(valueEmbargos);
				
					totalEmbargos += valueEmbargos;
				}
				embargos.setCellStyle(cellStyleNumber);
				
				//Cheque
				HSSFCell  cheque = row.createCell(columna++);
				if (detalle.getCheque() != null) {
					Double valueCheque = Utils.redondear2Decimales(detalle.getCheque());
					cheque.setCellValue(valueCheque);
					totalCheque = totalCheque + valueCheque;
				
				}
				cheque.setCellStyle(cellStyleNumber);
				
				//Neto a Depositar
				HSSFCell  deposito1 = row.createCell(columna++);
				if (detalle.getNetoADepositar() != null) {
					Double netoDeposito = Utils.redondear2Decimales(detalle.getNetoADepositar());
					deposito1.setCellValue(netoDeposito);
					totalNetoDeposito = totalNetoDeposito + netoDeposito;
			
				}
				deposito1.setCellStyle(cellStyleNumber);
				//Fecha de Ingreso
				HSSFCell  fecha = row.createCell(columna++);
				if (detalle.getInformeMensualDetalle() != null && detalle.getInformeMensualDetalle().getRelacionLaboral() != null) {
					fecha.setCellValue(sdf.format(detalle.getInformeMensualDetalle().getRelacionLaboral().getEmpleado().getFechaIngresoNovatium()));
			
				}
				fecha.setCellStyle(cellStyle);
			}
			
			row = worksheet.createRow(numeroFila);
			int columnLast = 0;
			// NOMBRE
			HSSFCell  nombre = row.createCell(columnLast++);
			nombre.setCellStyle(cellStyleNumber);
			// LEGAJO
			HSSFCell  legajo = row.createCell(columnLast++);
			legajo.setCellStyle(cellStyleNumber);
			// CLIENTE
			HSSFCell  cliente = row.createCell(columnLast++);
			cliente.setCellStyle(cellStyleNumber);
			// CLIENTE
			HSSFCell  cuil = row.createCell(columnLast++);
			cuil.setCellStyle(cellStyleNumber);
			
			// DATO BASICO SET TOTAL
			HSSFCell  totalDatoBasicoForm = row.createCell(columnLast++);
			totalDatoBasicoForm.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			totalDatoBasicoForm.setCellValue(totalDatoBasico);
			totalDatoBasicoForm.setCellStyle(cellStyleNumber);
			// DIAS
			HSSFCell  dias = row.createCell(columnLast++);
			dias.setCellStyle(cellStyleNumber);
			// BASICO SET TOTAL
			HSSFCell  totalBasicoForm = row.createCell(columnLast++);
			totalBasicoForm.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			totalBasicoForm.setCellValue(totalBasico);
			totalBasicoForm.setCellStyle(cellStyleNumber);
			// ASISTENCIA Y PUNTUALIDAD SET TOTAL
			HSSFCell  totalAsistencia = row.createCell(columnLast++);
			totalAsistencia.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			totalAsistencia.setCellValue(totalAsistenciaPuntualidad);
			totalAsistencia.setCellStyle(cellStyleNumber);
			
			// INASISTENCIAS INJUSTIF
			HSSFCell  totalNII = row.createCell(columnLast++);
			totalNII.setCellStyle(cellStyleNumber);
			// IMP. INASISTENCIAS INJUSTIF
			HSSFCell  totalIII = row.createCell(columnLast++);
			totalIII.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			totalIII.setCellValue(totalImpInasistI);
			totalIII.setCellStyle(cellStyleNumber);
			// INASISTENCIAS SIN GOCE SUELDO
			HSSFCell  totalNISGS = row.createCell(columnLast++);
			totalNISGS.setCellStyle(cellStyleNumber);
			// IMP. INASISTENCIAS SIN GOCE SUELDO
			HSSFCell  totalIISGS = row.createCell(columnLast++);
			totalIISGS.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			totalIISGS.setCellValue(totalImpInasistSGS);
			totalIISGS.setCellStyle(cellStyleNumber);
			
			// C50 SET TOTAL
			HSSFCell  c50Total = row.createCell(columnLast++);
			c50Total.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			c50Total.setCellValue(totalC50);
			c50Total.setCellStyle(cellStyleNumber);
			// C100 SET TOTAL
			HSSFCell  c100Total = row.createCell(columnLast++);
			c100Total.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			c100Total.setCellValue(totalC100);
	    	c100Total.setCellStyle(cellStyleNumber);
			// HE50 SET TOTAL
			HSSFCell  he50Total = row.createCell(columnLast++);
			he50Total.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			he50Total.setCellValue(totalHE50);
			he50Total.setCellStyle(cellStyleNumber);
			// HE100 SET TOTAL
			HSSFCell  he100Total = row.createCell(columnLast++);
			he100Total.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			he100Total.setCellValue(totalHE100);
			he100Total.setCellStyle(cellStyleNumber);
			// BENEFICIOS
			for(Integer column : totalBeneficios.keySet()){
				HSSFCell  beneficioTotal = row.createCell(column);
				beneficioTotal.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				beneficioTotal.setCellValue(totalBeneficios.get(column));	
				beneficioTotal.setCellStyle(cellStyleNumber);
			}
			
			columnLast += countBenef;
			//SET TOTAL VACACIONES
			if(isVacaciones(periodo)){
				HSSFCell  vacDias = row.createCell(columnLast++);
				vacDias.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				vacDias.setCellValue(totalVacacionesDias);	
				HSSFCell  vacValor = row.createCell(columnLast++);
				vacValor.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				vacValor.setCellValue(totalVacaciones);	
				vacValor.setCellStyle(cellStyleNumber);
			}
			//SET TOTAL CONCEPTO REMUNERATIVO
			HSSFCell  rem = row.createCell(columnLast++);
			rem.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			rem.setCellValue(totalConceptoRemunerativo);
			rem.setCellStyle(cellStyleNumber);
			//SET TOTAL CONCEPTO REMUNERATIVO
			HSSFCell  noRem = row.createCell(columnLast++);
			noRem.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			noRem.setCellValue(totalConceptoNoRemunerativo);
			noRem.setCellStyle(cellStyleNumber);

			//SET TOTAL SAC
			HSSFCell  sacBase = row.createCell(columnLast++);
			sacBase.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			sacBase.setCellValue(totalSacBase);	
			sacBase.setCellStyle(cellStyleNumber);
			HSSFCell  sacDias = row.createCell(columnLast++);
			sacDias.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			sacDias.setCellValue(totalSacDias);	
			sacDias.setCellStyle(cellStyleNumber);
			HSSFCell  sac = row.createCell(columnLast++);
			sac.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			sac.setCellValue(totalSac);	
			sac.setCellStyle(cellStyleNumber);
				
			//SET BRUTO
			HSSFCell  bruto = row.createCell(columnLast++);
			bruto.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			bruto.setCellValue(totalBruto);	
			bruto.setCellStyle(cellStyleNumber);
			//SET BRUTO REM
			HSSFCell  brutoRem = row.createCell(columnLast++);
			brutoRem.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			brutoRem.setCellValue(totalBrutoRem);
			brutoRem.setCellStyle(cellStyleNumber);
			//SET RET 11 Y 3
			HSSFCell  ret = row.createCell(columnLast++);
			ret.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			ret.setCellValue(totalRet11y3);	
			ret.setCellStyle(cellStyleNumber);
			//Aportes OS
			HSSFCell  aportes = row.createCell(columnLast++);
			aportes.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			aportes.setCellValue(totalObraSocial);
			aportes.setCellStyle(cellStyleNumber);
			//Ret Ganancias
			HSSFCell  retencion = row.createCell(columnLast++);
			retencion.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			retencion.setCellValue(totalGanancia);
			retencion.setCellStyle(cellStyleNumber);
			//Cont 17 6.5 10
			HSSFCell  cont = row.createCell(columnLast++);
			cont.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			cont.setCellValue(totalCont176510);
			cont.setCellStyle(cellStyleNumber);			
			//Cont OS 6
			HSSFCell  contOS = row.createCell(columnLast++);
			contOS.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			contOS.setCellValue(totalContOs);
			contOS.setCellStyle(cellStyleNumber);			
			//Neto
			HSSFCell  neto = row.createCell(columnLast++);
			neto.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			neto.setCellValue(totalNeto);
			neto.setCellStyle(cellStyleNumber);
			 //celular
			HSSFCell  cel = row.createCell(columnLast++);
			cel.setCellStyle(cellStyleNumber);
			//Prepaga
			HSSFCell  prepaga = row.createCell(columnLast++);
			prepaga.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			prepaga.setCellValue(totalPrepaga);	
			prepaga.setCellStyle(cellStyleNumber);
			//Adelantos
			HSSFCell  adelantos = row.createCell(columnLast++);
			adelantos.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			adelantos.setCellValue(totalValueAdelantos);
			adelantos.setCellStyle(cellStyleNumber);
			//Reintegros
			HSSFCell  reint = row.createCell(columnLast++);
			reint.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			reint.setCellValue(totalReintegros);
			reint.setCellStyle(cellStyleNumber);
			//Embargos
			HSSFCell  embargs = row.createCell(columnLast++);
			embargs.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			embargs.setCellValue(totalEmbargos);
			embargs.setCellStyle(cellStyleNumber);
			//Cheque
			HSSFCell  cheque = row.createCell(columnLast++);
			cheque.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			cheque.setCellValue(totalCheque);
			cheque.setCellStyle(cellStyleNumber);
			//Neto a Depositar
			HSSFCell  netoDepositar = row.createCell(columnLast++);
			netoDepositar.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			netoDepositar.setCellValue(totalNetoDeposito);
			netoDepositar.setCellStyle(cellStyleNumber);
			//Fecha  a depositar
			HSSFCell  deposito = row.createCell(columnLast++);
			deposito.setCellStyle(cellStyleNumber);
			setAutosize(row, worksheet);
		}

	}

	private double setImpInasistenciasSGS(HSSFSheet worksheet, HSSFCellStyle cellStyleNumber, double totalImpInasistSGS, HSSFRow row, InformeConsolidadoDetalle detalle, int lastColumn) {
		HSSFCell cell = row.createCell(lastColumn);
		if (detalle.getImporteInasistenciasSinGoceDeSueldo() != null) {
			double value = Utils.redondear2Decimales(detalle.getImporteInasistenciasSinGoceDeSueldo());
			cell.setCellValue(value);
			totalImpInasistSGS += value;
		}
		cell.setCellStyle(cellStyleNumber);
		return totalImpInasistSGS;	
	}

	private double setImpInasistenciasI(HSSFSheet worksheet, HSSFCellStyle cellStyleNumber, double totalImpInasistI, HSSFRow row, InformeConsolidadoDetalle detalle, int lastColumn) {
		HSSFCell cell = row.createCell(lastColumn);
		if (detalle.getImporteInasistenciasInjustificadas() != null) {
			double value = Utils.redondear2Decimales(detalle.getImporteInasistenciasInjustificadas());
			cell.setCellValue(value);
			totalImpInasistI += value;
		}
		cell.setCellStyle(cellStyleNumber);
		return totalImpInasistI;	
	}

	private void setNroInasistenciasSGS(HSSFSheet worksheet, HSSFCellStyle cellStyleNumber, HSSFRow row, InformeConsolidadoDetalle detalle, int lastColumn) {
		HSSFCell cell = row.createCell(lastColumn);
		if (detalle.getNroInasistenciasSinGoceDeSueldo() != null) {
			Double value = Utils.redondear2Decimales(detalle.getNroInasistenciasSinGoceDeSueldo());
			cell.setCellValue(value);
		}
		cell.setCellStyle(cellStyleNumber);
		
	}

	private void setNroInasistenciasI(HSSFSheet worksheet, HSSFCellStyle cellStyleNumber, HSSFRow row, InformeConsolidadoDetalle detalle, int lastColumn) {
		HSSFCell cell = row.createCell(lastColumn);
		if (detalle.getNroInasistenciasInjustificadas() != null) {
			Double value = Utils.redondear2Decimales(detalle.getNroInasistenciasInjustificadas());
			cell.setCellValue(value);
		}
		cell.setCellStyle(cellStyleNumber);
	}

	private boolean showSac(Date periodo) {
		boolean showSac = false;
		if(periodo.getMonth()+1 == 6 ||periodo.getMonth()+1 == 12) {
			showSac = true;
		}
		return showSac;
	}

	private double setHE100(HSSFSheet worksheet, HSSFCellStyle cellStyleNumber,
			double totalHE100, HSSFRow row, InformeConsolidadoDetalle detalle, int lastColumn) {
		HSSFCell he100 = row.createCell(lastColumn);
		if (detalle.getValorHsExtras100() != null) {
			double valueHe100 = Utils.redondear2Decimales(detalle.getValorHsExtras100());
			he100.setCellValue(valueHe100);
			totalHE100 = totalHE100 + valueHe100;
		}
		he100.setCellStyle(cellStyleNumber);
		return totalHE100;
	}

	private double setHE50(HSSFSheet worksheet, HSSFCellStyle cellStyleNumber,
			double totalHE50, HSSFRow row, InformeConsolidadoDetalle detalle, int lastColumn) {
		HSSFCell he50 = row.createCell(lastColumn);
		if (detalle.getValorHsExtras50() != null) {
			Double hsExtras = Utils.redondear2Decimales(detalle.getValorHsExtras50());
			he50.setCellValue(hsExtras);
			totalHE50 = totalHE50 + hsExtras;
		}
		he50.setCellStyle(cellStyleNumber);
		return totalHE50;
	}

	private double setC100(HSSFSheet worksheet, HSSFCellStyle cellStyleNumber,
			double totalC100, HSSFRow row, InformeConsolidadoDetalle detalle, int lastColumn) {
		HSSFCell c100 = row.createCell(lastColumn);
		if (detalle.getHsExtras100() != null) {
			Double valueC100 = Utils.redondear2Decimales(detalle.getHsExtras100());
			c100.setCellValue(valueC100);
			totalC100 = totalC100 + valueC100;
		}
		c100.setCellStyle(cellStyleNumber);
		return totalC100;
	}

	private double setC50(HSSFSheet worksheet, HSSFCellStyle cellStyleNumber,
			double totalC50, HSSFRow row, InformeConsolidadoDetalle detalle, int lastColumn) {
		HSSFCell c50 = row.createCell(lastColumn);
		if (detalle.getHsExtras50() != null) {
			Double valuec50 = Utils.redondear2Decimales(detalle.getHsExtras50());
			c50.setCellValue(valuec50);
			totalC50 = totalC50 + valuec50;
		}
		c50.setCellStyle(cellStyleNumber);
		return totalC50;
	}

	private double setAsistenciaPuntualidad(HSSFSheet worksheet,
			HSSFCellStyle cellStyleNumber, double totalAsistenciaPuntualidad,
			HSSFRow row, InformeConsolidadoDetalle detalle, int lastColumn) {
		HSSFCell cell7 = row.createCell(lastColumn);
		if (detalle.getAsistenciaPuntualidad() != null) {
			cell7.setCellValue(detalle.getAsistenciaPuntualidad());
			totalAsistenciaPuntualidad = totalAsistenciaPuntualidad + detalle.getAsistenciaPuntualidad();
		}
		cell7.setCellStyle(cellStyleNumber);
		return totalAsistenciaPuntualidad;
	}

	private double setBasicoMes(HSSFSheet worksheet,
			HSSFCellStyle cellStyleNumber, double totalBasico, HSSFRow row,
			InformeConsolidadoDetalle detalle, int lastColumn) {
		HSSFCell cell6 = row.createCell(lastColumn);
		if (detalle.getBasicoMes() != null) {
			double basico = Utils.redondear2Decimales(detalle.getBasicoMes());
			cell6.setCellValue(basico);

			totalBasico = totalBasico + basico;
		}
		cell6.setCellStyle(cellStyleNumber);
		return totalBasico;
	}

	private void setDiasTrabajados(HSSFSheet worksheet,
			HSSFCellStyle cellStyle, HSSFRow row,
			InformeConsolidadoDetalle detalle, int lastColumn) {
		HSSFCell cell45 = row.createCell(lastColumn);
		if (detalle.getDiasTrabajados() != null) {
			cell45.setCellValue(detalle.getDiasTrabajados());
		
		}
		cell45.setCellStyle(cellStyle);
	}

	private double setDatoBasico(HSSFSheet worksheet, HSSFCellStyle cellStyle,
			HSSFCellStyle cellStyleNumber, double totalDatoBasico, HSSFRow row,
			InformeConsolidadoDetalle detalle, int lastColumn) {
		HSSFCell cell4 = row.createCell(lastColumn);
		if (detalle.getSueldoBasico() != null) {
			double sueldoBasico = Utils.redondear2Decimales(detalle.getSueldoBasico());
			cell4.setCellValue(sueldoBasico);
			
			cell4.setCellStyle(cellStyleNumber);
			totalDatoBasico = totalDatoBasico +sueldoBasico;
		}else{
			cell4.setCellStyle(cellStyle);
		}
		return totalDatoBasico;
	}

	private void setCuil(HSSFSheet worksheet, HSSFCellStyle cellStyle,
			HSSFRow row, Empleado empleado, int lastColumn) {
		HSSFCell cell3 = row.createCell(lastColumn);
		if (empleado != null
				&& !StringUtils.isEmpty(empleado.getCuil())) {
			cell3.setCellValue(empleado.getCuil());
			
		}
		cell3.setCellStyle(cellStyle);
	}

	private void setCliente(HSSFSheet worksheet, HSSFCellStyle cellStyle,
			HSSFRow row, Cliente cliente, int lastColumn) {
		HSSFCell cell2 = row.createCell(lastColumn);
		if (cliente != null
				&& !StringUtils.isEmpty(cliente.getRazonSocial())) {
			cell2.setCellValue(cliente.getRazonSocial());
			

		}
		cell2.setCellStyle(cellStyle);
	}

	private HSSFCell setLegajo(HSSFSheet worksheet, HSSFRow row, Empleado empleado,int index) {
		HSSFCell cell1 = row.createCell(index);
		if (empleado.getLegajo() != null) {
			cell1.setCellValue(empleado.getLegajo());
		}
		return cell1;
	}

	private void setNombre(HSSFSheet worksheet, HSSFCellStyle cellStyle, HSSFCell cell, Empleado empleado) {
		if (empleado != null) {
			String nombre = empleado.getNombres();
			String apellido = empleado.getApellidos();
			cell.setCellValue(apellido + ", " + nombre);

			
		}
		cell.setCellStyle(cellStyle);
	}

	private void setBeneficios(HSSFSheet worksheet,
			HSSFCellStyle cellStyleNumber,
			Map<Integer, Double> totalBeneficios, HSSFRow row,
			InformeConsolidadoDetalle detalle, List<String> beneficios) {
		if (!CollectionUtils.isEmpty(beneficios)) {
			for (String beneficio : beneficios) {
				Integer numColumna = mapBeneficiosColumna.get(beneficio);
				if (numColumna != null) {
					HSSFCell celly = row.createCell(numColumna);
					Map<String,Double> valores = mapBeneficiosValor.get(detalle.getId());
					if(valores != null && valores.get(beneficio) != null){
						double beneficioValue = Utils.redondear2Decimales(valores.get(beneficio));
						celly.setCellValue(beneficioValue);
						if(totalBeneficios.containsKey(numColumna)){
							double total = totalBeneficios.get(numColumna);
							total = total + beneficioValue;
							totalBeneficios.put(numColumna, total);
						}else{
							totalBeneficios.put(numColumna, beneficioValue);
						}
						
					}
			
					celly.setCellStyle(cellStyleNumber);
				}

			}
		}
	}
	@SuppressWarnings("deprecation")
	private boolean isVacaciones(Date periodo) {
		boolean showVacaciones = false;
		if(periodo.getMonth()+1 == mesLiquidacionVacaciones) {
			showVacaciones = true;
		}
		return showVacaciones;
	}

	private Empleado getEmpleado(InformeConsolidadoDetalle detalle) {
		Empleado empleado = null;
		if (detalle.getInformeMensualDetalle() != null
				&& detalle.getInformeMensualDetalle().getRelacionLaboral() != null
				&& detalle.getInformeMensualDetalle().getRelacionLaboral()
						.getEmpleado() != null) {
			empleado = detalle.getInformeMensualDetalle().getRelacionLaboral()
					.getEmpleado();
		}else if(detalle.getEmpleado() != null){
			empleado = detalle.getEmpleado();
		}
		return empleado;
	}

	private Cliente getCliente(InformeConsolidadoDetalle detalle) {
		Cliente cliente = null;
		if (detalle.getInformeMensualDetalle() != null
				&& detalle.getInformeMensualDetalle().getRelacionLaboral() != null
				&& detalle.getInformeMensualDetalle().getRelacionLaboral()
						.getCliente() != null) {
			cliente = detalle.getInformeMensualDetalle().getRelacionLaboral()
					.getCliente();
			
		}else if(detalle.getEmpleado() != null) {
			RelacionLaboral rl = detalle.getEmpleado().getRelacionLaboralVigente();
			if(rl != null && rl.getCliente() != null) {
				cliente = rl.getCliente();
			}
		}
		return cliente;
	}

	@Override
	public void crearEncabezadoEstadoInformesMensuales(HSSFSheet worksheet, HSSFWorkbook workbook) {
		logger.info("creando encabezados excel EstadoInformesMensuales");
		
		String headers[] = {"Periodo","Cliente","Gerente","Estado"};
		
		HSSFRow headerRow = worksheet.createRow(0);
		HSSFCellStyle cellStyle = setEstilosEncabezados(workbook);
		
		HSSFCell cell;
		for (int i = 0; i < headers.length; i++) {
			cell = headerRow.createCell(i);
			cell.setCellValue(headers[i]);
			cell.setCellStyle(cellStyle);		
		}
		
	}
	
	@Override
	public void addRegistroEstadoInformesMensuales(HSSFSheet worksheet, HSSFWorkbook workbook, String[] data, int rowIndex) {
		//logger.info("agregando registro a informe");
		
		HSSFRow dataRow = worksheet.createRow(rowIndex);
		HSSFCellStyle cellStyle = setEstilosDatos(workbook);
		
		if(data[3].equals(InformeIntermedioEstadoEnum.PENDIENTE.getNombre())) {
			HSSFFont font = workbook.createFont();
			font.setColor(HSSFColor.DARK_RED.index);
			cellStyle.setFont(font);
		}
		
		HSSFCell cell;
		for (int i = 0; i < data.length; i++) {
			cell = dataRow.createCell(i);
			cell.setCellValue(data[i]);	
			cell.setCellStyle(cellStyle);		
		}
		
		setAutosize(dataRow, worksheet);
	}
	
	/**
	 * crea el encabezado del reporte
	 * 
	 * @param worksheet
	 * @param workbook
	 * @param date
	 */
	@SuppressWarnings("deprecation")
	private void crearEncabezado(HSSFSheet worksheet, HSSFWorkbook workbook,
			Date periodo) {
		logger.info("creando encabezados excel");
		HSSFRow row0 = worksheet.createRow(0);
		HSSFCellStyle cellStyle = setEstilosEncabezados(workbook);

		HSSFCell cellA1 = row0.createCell(0);
		cellA1.setCellValue("Periodo");
		
		HSSFCell cellA2 = row0.createCell(1);
		cellA2.setCellValue(PeriodoUtils.getPeriodoFromDate(periodo));
	
		HSSFRow row1 = worksheet.createRow(2);
		int i = 0;
		
		// Apellido y Nombre
		HSSFCell cellA11 = row1.createCell(i++);
		cellA11.setCellValue("Apellido y Nombre");
		cellA11.setCellStyle(cellStyle);

		// Legajo
		HSSFCell cellB11 = row1.createCell(i++);
		cellB11.setCellValue("Legajo");
		cellB11.setCellStyle(cellStyle);
	
		// Cliente
		HSSFCell cellC11 = row1.createCell(i++);
		cellC11.setCellValue("Cliente");
		cellC11.setCellStyle(cellStyle);
		
		// CUIL
		HSSFCell cellD11 = row1.createCell(i++);
		cellD11.setCellValue("CUIL");
		cellD11.setCellStyle(cellStyle);
		
		// Dias trabajados
		HSSFCell cellE11 = row1.createCell(i++);
		cellE11.setCellValue("Dato Básico");
		cellE11.setCellStyle(cellStyle);
		
		// Sueldo Básico
		HSSFCell cellF11 = row1.createCell(i++);
		cellF11.setCellValue("Días");
		cellF11.setCellStyle(cellStyle);
	
		// Básico Mes
		HSSFCell cellG11 = row1.createCell(i++);
		cellG11.setCellValue("Básico");
		cellG11.setCellStyle(cellStyle);
		// Asistencia y Puntualidad
		HSSFCell cellH11 = row1.createCell(i++);
		cellH11.setCellValue("Asist y Punt");
		cellH11.setCellStyle(cellStyle);
		
		// Inasistencias injustificadas
		HSSFCell cellII = row1.createCell(i++);
		cellII.setCellValue("I. Injustificadas");
		cellII.setCellStyle(cellStyle);
		// Importe inasistencias injustificadas
		HSSFCell cellIII = row1.createCell(i++);
		cellIII.setCellValue("Imp. I. Injustif.");
		cellIII.setCellStyle(cellStyle);
		// Inasistencias sin goce de sueldo
		HSSFCell cellISGS = row1.createCell(i++);
		cellISGS.setCellValue("I. sin goce de Sueldo");
		cellISGS.setCellStyle(cellStyle);
		// Importe inasistencias injustificadas
		HSSFCell cellIISGS = row1.createCell(i++);
		cellIISGS.setCellValue("Imp. I. sin goce de Sueldo");
		cellIISGS.setCellStyle(cellStyle);
		
		// c50
		HSSFCell cellH211 = row1.createCell(i++);
		cellH211.setCellValue("  c50  ");
		cellH211.setCellStyle(cellStyle);
		// C100
		HSSFCell cellI11 = row1.createCell(i++);
		cellI11.setCellValue("c100");
		cellI11.setCellStyle(cellStyle);
		// HE50
		HSSFCell cell311 = row1.createCell(i++);
		cell311.setCellValue("HE50");
		cell311.setCellStyle(cellStyle);
		// HE100
		HSSFCell cell411 = row1.createCell(i++);
		cell411.setCellValue("HE100");
		cell411.setCellStyle(cellStyle);

		// Beneficios
		i = setBeneficios(worksheet, cellStyle, row1, i);
		
		//vacaciones
		boolean showVacaciones = false;
		
		i = setVacaciones(worksheet, periodo, cellStyle, row1, i,
				showVacaciones);
	
		// Con Rem Plus
		HSSFCell cellI18 = row1.createCell(i++);
		cellI18.setCellValue("Con Rem Plus");
		cellI18.setCellStyle(cellStyle);
		
		// Con No Rem
		HSSFCell cellI141 = row1.createCell(i++);
		cellI141.setCellValue("Con No Rem");
		cellI141.setCellStyle(cellStyle);	
		
		
		i = setSAC(worksheet, cellStyle, row1, cellI11, i);
		
			// Bruto
   		HSSFCell cellI211 = row1.createCell(i++);
   		cellI211.setCellValue("Bruto");
   		cellI211.setCellStyle(cellStyle);
			
		// Bruto Rem
		HSSFCell cellI121 = row1.createCell(i++);
		cellI121.setCellValue("Bruto Rem");
		cellI121.setCellStyle(cellStyle);
		// Ret(11+3)
		HSSFCell cellI181 = row1.createCell(i++);
		cellI181.setCellValue("Ret(11+3)");
		cellI181.setCellStyle(cellStyle);
		//Aportes OS
		HSSFCell cellI112 = row1.createCell(i++);
		cellI112.setCellValue("Aportes OS");
		cellI112.setCellStyle(cellStyle);
			
		//Ret Ganancias
		HSSFCell cellI118 = row1.createCell(i++);
		cellI118.setCellValue("Ret Ganancias");
		cellI118.setCellStyle(cellStyle);
		
		//Cargas sociales -> contrib 17 6.5 10 %
		HSSFCell cellcs17 = row1.createCell(i++);
		cellcs17.setCellValue("Cont 17-6.5-10%");
		cellcs17.setCellStyle(cellStyle);

		//Cargas sociales -> contrib OS 6 %
		HSSFCell cellcsOs = row1.createCell(i++);
		cellcsOs.setCellValue("Cont OS 6%");
		cellcsOs.setCellStyle(cellStyle);
		
		//Neto
		HSSFCell cellI117 = row1.createCell(i++);
		cellI117.setCellValue("Neto");
		cellI117.setCellStyle(cellStyle);
			
		//Celular
		HSSFCell cellI511 = row1.createCell(i++);
		cellI511.setCellValue("Celular");
		cellI511.setCellStyle(cellStyle);		
			
		//Prepaga
		HSSFCell cellI151 = row1.createCell(i++);
		cellI151.setCellValue("Prepaga");
		cellI151.setCellStyle(cellStyle);
		
		//Adelantos
		HSSFCell cellI115 = row1.createCell(i++);
		cellI115.setCellValue("Adelantos");
		cellI115.setCellStyle(cellStyle);		
		//Reintegros
		HSSFCell cellI19 = row1.createCell(i++);
		cellI19.setCellValue("Reintegros");
		cellI19.setCellStyle(cellStyle);	

		//eMBARGOS
		HSSFCell cellEmb = row1.createCell(i++);
		cellEmb.setCellValue("Embargos");
		cellEmb.setCellStyle(cellStyle);	
		
		//Cheque
		HSSFCell cellI171 = row1.createCell(i++);
		cellI171.setCellValue("Cheque");
		cellI171.setCellStyle(cellStyle);		

		//Neto a Depositar
		HSSFCell cellI711 = row1.createCell(i++);
		cellI711.setCellValue("Neto a Depositar");
		cellI711.setCellStyle(cellStyle);		
		
		//Fecha de Ingreso
		HSSFCell cell2I11 = row1.createCell(i++);
		cell2I11.setCellValue("Fecha de Ingreso");
		cell2I11.setCellStyle(cellStyle);		
		setAutosize(row1, worksheet);
	}
	private void setAutosize(HSSFRow row,HSSFSheet worksheet) {
		Iterator<Cell> cell = row.cellIterator();
		while(cell.hasNext()){
			worksheet.autoSizeColumn(cell.next().getColumnIndex());
		}
	}
	private int setSAC(HSSFSheet worksheet, HSSFCellStyle cellStyle,
			HSSFRow row1, HSSFCell cellI11, int i) {
		// SAC Base
		HSSFCell cellI118 = row1.createCell(i++);
		cellI118.setCellValue("SAC Base");
		cellI118.setCellStyle(cellStyle);
		// SAC Dias
		HSSFCell cellI121 = row1.createCell(i++);
		cellI121.setCellValue("SAC Dias");
		cellI121.setCellStyle(cellStyle);
		
		// SAC
		HSSFCell cellI117 = row1.createCell(i++);
		cellI117.setCellValue("SAC");
		cellI117.setCellStyle(cellStyle);
		return i;
	}
	@SuppressWarnings("deprecation")
	private int setVacaciones(HSSFSheet worksheet, Date periodo,
			HSSFCellStyle cellStyle, HSSFRow row1, int i, boolean showVacaciones) {
		if(periodo.getMonth()+1 == mesLiquidacionVacaciones) {
			showVacaciones = true;
		}
		if(showVacaciones){
			// vacaciones dias
			HSSFCell cellI12 = row1.createCell(i++);
			cellI12.setCellValue("Vac. Días");
			cellI12.setCellStyle(cellStyle);
			
			// vacaciones
			HSSFCell cellI113= row1.createCell(i++);
			cellI113.setCellValue("Vacaciones");
			cellI113.setCellStyle(cellStyle);

		}
		return i;
	}

	private int setBeneficios(HSSFSheet worksheet, HSSFCellStyle cellStyle,
			HSSFRow row1, int i) {
		Set<String> beneficios = new HashSet<String>();
		for (Long idBeneficio : mapBeneficiosNombre.keySet()) {
			// Beneficios
			String titulo = mapBeneficiosNombre.get(idBeneficio);
			beneficios.add(titulo);
		}
		countBenef = 0;
		for (String titulo : beneficios) {
			HSSFCell cellJ11 = row1.createCell(i);
			cellJ11.setCellValue(titulo);
			cellJ11.setCellStyle(cellStyle);
			mapBeneficiosColumna.put(titulo, i);
			i++;
			countBenef++;
		}
		return i;
	}

	/**
	 * crea un mapa con los beneficios de cada informe consolidado
	 * 
	 * @param informes
	 */
	private void loadBeneficiosMap(List<InformeConsolidadoDetalle> detalles) {
		if (!CollectionUtils.isEmpty(detalles)) {
			for (InformeConsolidadoDetalle detalle : detalles) {
				Map<String,Double> benef = new HashMap<String,Double>();
				List<String> beneficiosId = new ArrayList<String>();
				if (detalle.getInformeMensualDetalle() != null
						&& !CollectionUtils.isEmpty(detalle
								.getInformeMensualDetalle().getBeneficios())) {

					for (InformeConsolidadoDetalleBeneficio detalleBeneficio : detalle.getBeneficios()) {
						
						if (detalleBeneficio.getBeneficio() != null	&& detalleBeneficio.getBeneficio().getTitulo() != null) {
							mapBeneficiosNombre.put(detalleBeneficio.getId(), detalleBeneficio.getBeneficio().getTitulo());
							
							benef.put(detalleBeneficio.getBeneficio().getTitulo(), detalleBeneficio.getImporte());
							beneficiosId.add(detalleBeneficio.getBeneficio().getTitulo());
						}

					}
				}
				mapBeneficiosValor.put(detalle.getId(),benef);
				if (!CollectionUtils.isEmpty(beneficiosId)) {
					mapBeneficiosDetalle.put(detalle.getId(), beneficiosId);
				}
				
			}
		}
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

	public void setMesLiquidacionVacaciones(Integer mesLiquidacionVacaciones) {
		this.mesLiquidacionVacaciones = mesLiquidacionVacaciones;
	}
	


}
