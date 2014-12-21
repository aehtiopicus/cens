package com.aehtiopicus.cens.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Days;

import com.aehtiopicus.cens.enumeration.MesEnum;

public class PeriodoUtils {

	public static List<String> generarPeriodos(Date fechaHasta, Integer nroPeriodos){
		List<String> periodos = new ArrayList<String>();
		
		Integer mes;
		Integer anio;
		
		if(fechaHasta != null && nroPeriodos != null) {
			
			Calendar c = Calendar.getInstance();
			c.setTime(fechaHasta);
			mes = c.get(Calendar.MONTH) + 1;
			anio = c.get(Calendar.YEAR);
			

			MesEnum mesEnum;
			for (int i = 0; i < nroPeriodos; i++) {
				mesEnum = MesEnum.getMesEnumFromNumero(mes);
				
				periodos.add(mesEnum.getNombre() + " " + anio);
				
				mes --;
				if(mes.equals(0)) {
					anio --;
					mes = 12;
				}
			}
		}
		
		return periodos;
	}
	
	public static List<String> generarPeriodosSAC(Integer yearHasta, Integer nroPeriodos){
		List<String> periodos = new ArrayList<String>();
				
		if(yearHasta != null && nroPeriodos != null) {

			Integer mes = 12;
			Integer anio = yearHasta;

			MesEnum mesEnum;
			for (int i = 0; i < nroPeriodos; i++) {
				
				mesEnum = MesEnum.getMesEnumFromNumero(mes);
				periodos.add(mesEnum.getNombre() + " " + anio);
				
				mes -= 6;
				if(mes.equals(0)) {
					anio --;
					mes = 12;
				}
			}
		}
		
		return periodos;
	}
	
	public static String getPeriodoFromDate(Date fecha) {
		String periodo = "";
		if(fecha != null) {
			Calendar c = Calendar.getInstance();
			c.setTime(fecha);
			MesEnum mesEnum = MesEnum.getMesEnumFromNumero(c.get(Calendar.MONTH) + 1);
			periodo = mesEnum.getNombre() + " " + c.get(Calendar.YEAR);
		}
		return periodo;
	}

	public static Date getDateFormPeriodo(String periodoStr) {
		String[] periodoArray = periodoStr.split(" ");
		
		MesEnum mesEnum = MesEnum.getMesEnumFromNombre(periodoArray[0]);
		
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, Integer.parseInt(periodoArray[1]));
		c.set(Calendar.MONTH, mesEnum.getNumero()-1);
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		
		return c.getTime();
	}

	public static Date getNextPeriodo(Date periodo) {
		Calendar c = Calendar.getInstance();
		c.setTime(periodo);
		c.set(Calendar.MONTH, c.get(Calendar.MONTH) + 1);
		return c.getTime();
	}
	

	/**
	 * Devuelve el numero de dias habiles entre dos fechas SIN CONTAR FECHAFIN!!
	 * @param fechaInicial
	 * @param fechaFinal
	 * @return dias habiles
	 */
	public static int getDiasHabiles(Date fechaIni, Date fechaFin) {
		Calendar fechaInicial = new GregorianCalendar();
		Calendar fechaFinal = new GregorianCalendar();
		fechaInicial.setTime(fechaIni);
		fechaFinal.setTime(fechaFin);

		int diffDays = 0;

		// mientras la fecha inicial sea menor a la fecha final se cuentan los dias
		while (fechaInicial.before(fechaFinal) || fechaInicial.equals(fechaFinal)) {

			// si el dia de la semana de la fecha minima es diferente de sabado o domingo
			if (fechaInicial.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY
					&& fechaInicial.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) {
				
				fechaInicial.get(Calendar.DAY_OF_WEEK);
				// se aumentan los dias de diferencia entre min y max
				diffDays++;
			}
			// se suma 1 dia para hacer la validacion del siguiente dia.
			fechaInicial.add(Calendar.DATE, 1);

		}

		return diffDays;

    }
	
	
	public static Integer getDiasEntreFechas(Date fechaIni, Date fechaFin) {
		return Days.daysBetween(new DateTime(fechaIni), new DateTime(fechaFin)).getDays()+1;
	}
	
    public static boolean fechaEnRangoFechas(Date fecha, Date startRango, Date endRango) {
    	if(startRango.compareTo(fecha) * fecha.compareTo(endRango) >= 0) {
    		return true;
    	}else {
    		return false;
    	}
    }
    
    public static Date getFechaFinPeriodo(Date periodo){
		//Obtengo comienzo proximo periodo:
		Date proximoPeriodo = PeriodoUtils.getNextPeriodo(periodo);
		
		//Obtengo dia final del periodo
		Calendar finPeriodo = Calendar.getInstance();
		finPeriodo.setTime(proximoPeriodo);
		finPeriodo.add(Calendar.DATE, -1);
		
		return finPeriodo.getTime();
    }
    
	public static Date getFechaComienzoPeriodo(Date fecha) {
		Calendar c = Calendar.getInstance();
		c.setTime(fecha);
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		
		return c.getTime();
	}
    
}
