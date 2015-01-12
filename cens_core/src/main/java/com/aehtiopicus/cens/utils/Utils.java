package com.aehtiopicus.cens.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.aehtiopicus.cens.domain.entities.Perfil;
import com.aehtiopicus.cens.enumeration.cens.PerfilTrabajadorCensType;

public class Utils {

	public static Double redondear2Decimales(Double value) {
		if(value == null) {
			return null;
		}
		BigDecimal bigDecimal = new BigDecimal(value);
		BigDecimal bigDecimalRedondeado = bigDecimal.setScale(2, RoundingMode.HALF_UP); 
		
		return bigDecimalRedondeado.doubleValue();
	}
	
	public static boolean checkIsCensMiembro(List<Perfil> perfil, PerfilTrabajadorCensType perfilType){
		if(CollectionUtils.isEmpty(perfil)){
			return false;
		}
		for(Perfil p : perfil){
			if(p.getPerfilType().equals(perfilType)){
				return true;
			}
		}
		return false;
	}
	
	 /**
     * Retorna una nueva pagina con el especificado tipo de objeto
     * @param pageIndex Numero de pagina que se quiere obtener
     * @return
     */
    public static Pageable constructPageSpecification(int pageIndex, int row) {
        Pageable pageSpecification = new PageRequest(pageIndex, row, sortByUsernameAsc());
     
        return pageSpecification;
    }
    
    /**
     * Retorna  Sort object que ordena usuarios acorde al nombre asociado 
     * @return
     */
    public static  Sort sortByUsernameAsc() {
        return new Sort(Sort.Direction.ASC, "usuario.username");
    }
    
}
