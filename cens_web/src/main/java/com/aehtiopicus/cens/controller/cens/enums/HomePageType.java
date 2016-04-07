package com.aehtiopicus.cens.controller.cens.enums;

import com.aehtiopicus.cens.configuration.VistasConstant;
import com.aehtiopicus.cens.enumeration.cens.PerfilTrabajadorCensType;

public enum HomePageType {

	ASESOR(VistasConstant.ASESOR_DASHBOARD_LIST_VIEW,PerfilTrabajadorCensType.ASESOR),
	PROFESOR(VistasConstant.PROFESOR_ASIGNATURA_LIST_VIEW,PerfilTrabajadorCensType.PROFESOR),
	PRECEPTOR(VistasConstant.MAIN,PerfilTrabajadorCensType.PRECEPTOR),
	ALUMNO(VistasConstant.ALUMNO_DASHBOARD_LIST_VIEW,PerfilTrabajadorCensType.ALUMNO),
	ADMINISTRADOR(VistasConstant.MAIN,PerfilTrabajadorCensType.ADMINISTRADOR),
	DEFAULT(VistasConstant.MAIN,null);
	
	private String homePage;
	private PerfilTrabajadorCensType ptct;
	
	private HomePageType(String homePage,PerfilTrabajadorCensType ptct){
		this.homePage = homePage;
		this.ptct = ptct;
	}

	public String getHomePage() {
		return homePage;
	}

	public PerfilTrabajadorCensType getPtct() {
		return ptct;
	}
	
	public static HomePageType getHomePageTypeByRol(PerfilTrabajadorCensType ptct){
		HomePageType hpt = null;
		if(ptct!=null){
			for(HomePageType homePageType : HomePageType.values()){
				if(ptct.equals(homePageType.getPtct())){
					hpt = homePageType;
					break;
				}
			}
		}
		return hpt;
	}
	
}
