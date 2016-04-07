package ar.com.midasconsultores.catalogodefiltros.utils;



public enum TipoFiltroEnum {

	Aire(new String[] { 
		"FILTRO AIRE", 
		"AIRE SECUNDARIO", 
		"AIRE FRENOS",
		"ACCESORIOS PARA AIRE", 
		"PREFILTROS DE AIRE" 
	}), 
	
	Combustible(new String[] { 
		"FILTRO GAS OIL", 
		"FILTRO NAFTA" 
	}), 
	
	Aceite(new String[] {
		"FILTRO ACEITE MOTOR", 
		"ACEITE HIDRAULICO", 
		"ACEITE DE CAJA CAMBIO", 
		"ASPIRADORES Y RECOLECTORES DE ACEITE", 
		"EQUIPOS PARA ACEITES" 
	}), 
	
	Otros(new String[] { 
		"", 
		"CARCASAS", 
		"CARCASAS FILTROS DE AIRE",
		"BELLEZA AUTOMOTOR", 
		"REPUESTOS VARIOS ",
		"FILTRO SISTEMA REFRIGERANTE", 
		"LUBRICANTES", 
		"TAMBORES",
		"PRECINTOS", 
		"SACA-FILTROS", 
		"EQUIPOS Y ACCESORIOS PARA GRASA",
		"FILTRO TRAMPAS DE AGUA", 
		"BOMBA DE TRASVASE", 
		"LUBRICANTE MOTO",
		"CABEZALES", 
		"BOMBINES CEBADORES",
		"SEPARADOR AIRE / ACEITE", 
		"FILTROS COMPLETOS",
		"ELETROBOMBAS,ACCESORIOS P/DIESEL Y AGUA",
		"ENROLLADORES DE MANGERA", 
		"ANTICONGELANTES",
		"MERCHANDISING", 
		"ANTICONGELANTES",
		"HERRAMIENTAS PARA AIRE COMPRIMIDO", 
		"FLETES VARIOS",
		"GENERAL", 
		"EQUIPOS PARA TALLER", 
		"ACCESORIOS P/ACEITERAS",
		"GRASA DE LITIO CON MOLIBDENO",
		"COMBOS",
		"SENSORES DE FILTROS",
		"MERCHANDISING",
		"KIT MOVIL PARA ACEITE"
	}), 
	
	Habitaculo(	new String[] { 
		"AIRE HABITACULO" 
	});


	private String[] tipoFiltro;


	private TipoFiltroEnum(String[] tipoFiltro) {
		this.tipoFiltro = tipoFiltro;
	}

	public String[] getValoresTipoFiltro() {
		String[] result = new String[tipoFiltro.length];
		int i = 0;

		for (String s : tipoFiltro) {
			
			result[i] = s;			
			i++;
		}
		
		return result;		
	}


	
}
