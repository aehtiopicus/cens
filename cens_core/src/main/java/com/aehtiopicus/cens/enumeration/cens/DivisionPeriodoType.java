package com.aehtiopicus.cens.enumeration.cens;

import org.apache.commons.lang.StringUtils;

public enum DivisionPeriodoType {
	
	SEMESTRE_1("SEMESTRE","PRIMER SEMESTRE"),
	SEMESTRE_2("SEMESTRE","SEGUNDO SEMESTRE"),
	CUATRIMESTRE_1("CUATRIMESTRE","PRIMER CUATRIMESTRE"),
	CUATRIMESTRE_2("CUATRIMESTRE","SEGUNDO CUATRIMESTRE"),
	CUATRIMESTRE_3("CUATRIMESTRE","TERCER CUATRIMESTRE"),
	TRIMESTRE_1("TRIMESTRE","PRIMER TRIMESTRE"),
	TRIMESTRE_2("TRIMESTRE","SEGUNDO TRIMESTRE"),
	TRIMESTRE_3("TRIMESTRE","TERCER TRIMESTRE"),
	TRIMESTRE_4("TRIMESTRE","CUARTO TRIMESTRE"),
	BIMESTRE_1("BIMESTRE","PRIMER BIMESTRE"),
	BIMESTRE_2("BIMESTRE","SEGUNDO BIMESTRE"),
	BIMESTRE_3("BIMESTRE","TERCER BIMESTRE"),
	BIMESTRE_4("BIMESTRE","CUARTO BIMESTRE"),
	BIMESTRE_5("BIMESTRE","QUINTO BIMESTRE"),
	BIMESTRE_6("BIMESTRE","SEXTO BIMESTRE");

	private String periodoName;
	private String periodoBase;

	private DivisionPeriodoType(String periodoBase,String periodoName) {
		this.periodoName = periodoName;
		this.periodoBase = periodoBase;
	}

	public String getPeriodoName() {
		return periodoName;
	}
		

	public String getPeriodoBase() {
		return periodoBase;
	}

	public static DivisionPeriodoType getDivisionByPeriodoName(String periodoName){
		if(StringUtils.isNotEmpty(periodoName)){
			for(DivisionPeriodoType dpt : DivisionPeriodoType.values()){
				if(dpt.getPeriodoName().equalsIgnoreCase(periodoName)){
					return dpt;
				}
			}
		}
		return null;
	}

}
