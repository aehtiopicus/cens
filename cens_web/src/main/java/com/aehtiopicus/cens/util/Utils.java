package com.aehtiopicus.cens.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

import com.aehtiopicus.cens.configuration.UrlConstant;

public class Utils {
	
	public final static String DD_MM_YYYY = "dd/MM/yyyy";
	
	public static SimpleDateFormat sdf = new SimpleDateFormat(DD_MM_YYYY);
	
	 
	    public static int getNumberOfPages(int numberPerPage, Integer cant){
	    
	    	double totalPages = 0;
	    	if(cant > 0){
	    		totalPages = Double.valueOf(cant) / numberPerPage;
	    	}
	    	return (int) Math.ceil(totalPages);
	    }
	    
	    public static boolean  esCUILValido(String inputValor) {
		    String inputString = inputValor.toString();
		    if (inputString.length() == 11) {
		    	String chartA = String.valueOf((inputString.charAt(0)));
		    	String chartB = String.valueOf((inputString.charAt(1)));
		    	
		        String caracters_1_2 = chartA + chartB;
		        if (caracters_1_2.equals("20") || caracters_1_2.equals("23") || caracters_1_2.equals("24") || caracters_1_2.equals("27") || caracters_1_2.equals("30") || caracters_1_2.equals("33") || caracters_1_2.equals("34")) {
		            int count = inputString.charAt(0) * 5 + inputString.charAt(1) * 4 + inputString.charAt(2) * 3 + inputString.charAt(3) * 2 + inputString.charAt(4) * 7 + inputString.charAt(5) * 6 + inputString.charAt(6) * 5 + inputString.charAt(7) * 4 + inputString.charAt(8) * 3 + inputString.charAt(9) * 2 + inputString.charAt(10) * 1;
		            double division = count / 11;
		            if (division == Math.floor(division)) {
		                return true;
		            }
		        }
		    }
		    return false;
		}
	    
	    public static boolean validarCuit(long numero) {
	        long sexo = numero / (long) Math.pow(10, 9);
	        long dni = numero / 10 - sexo * (long) Math.pow(10, 8);
	        long verificador = numero % 10;
	 
	        String serie = "2345672345";
	        String numero2 = String.valueOf(sexo) + String.valueOf(dni);
	        long suma = 0;
	        for (int i = 0; i < 10; i++) {
	                suma += (numero2.charAt(i)- '0') * (serie.charAt(9 - i) - '0');
	        }
	        long digito = 11 - suma % 11;
	        digito = digito == 11 ? 0 : digito;
	        digito = digito == 10 ? 9 : digito;
	        return verificador == digito;
	}
	    
	public  static String getParameterFromRequest(String paramenterName, HttpServletRequest request) {
		String parameter;
		try {
			parameter = request.getParameter(paramenterName);
		} catch (Exception e) {
			parameter = null;
		}
		return parameter;
	}
	
	//
	public static ModelAndView getUnauthorizedActionModelAndView() {
		return new ModelAndView("redirect:" + UrlConstant.UNAUTHORIZED_URL);
	}
	
	//
	public static Map<String, Object> getUnauthorizedActionMap(){
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("sucess", false);
		data.put("message", "No tiene permisos para realizar la accion.");
   		return data;
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
