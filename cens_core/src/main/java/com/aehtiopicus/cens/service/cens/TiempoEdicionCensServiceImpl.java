package com.aehtiopicus.cens.service.cens;

import org.springframework.beans.factory.annotation.Autowired;

public class TiempoEdicionCensServiceImpl implements TiempoEdicionCensService{

	
	/**
	 * Paso 1 buscar asginaturas sin programas pero con profesor que lleven mas de n tiempo
	 * Paso 2 buscar asignaturas con programas cuyo estado lleve mas de lleven mas de n tiempo
	 * Paso 3 buscar asignaturas Sin material cuyo programa este en estado aprobado
	 * Paso 4 buscar asignaturas con programas 
	 */
	
	@Autowired
	private AsignaturaCensService asignaturaCensService;
	
	public void buscarAsignaturasSinPrograma(){
//		asignaturaCensService.countAsignaturasActivasByProfesor(null)
	}
}
