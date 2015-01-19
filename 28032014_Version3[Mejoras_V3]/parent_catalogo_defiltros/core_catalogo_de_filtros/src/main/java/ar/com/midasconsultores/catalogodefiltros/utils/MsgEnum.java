package ar.com.midasconsultores.catalogodefiltros.utils;

public enum MsgEnum {

	ERROR_ID_DE_FILTRO_INEXISTENTE("El id de filtro indicado no existe en la base de datos."),
	ERROR_ID_DE_VEHICULO_INEXISTENTE("El id de veh&iacute;culo indicado no existe en la base de datos.");
	
//	MSGADDOK("El registro fue almacenado correctamente."),
//	MSGUPDOK("El registro fue actualizado correctamente."),
//	MSGDELASK("Desea eliminar los registros seleccionados?"),
//	MSGDELOK("Los registros fueron eliminados correctamente."),
//	MSGADDFAIL("Ocurri&oacute; un error al intentar almacenar el registro."),
//	MSGADDFAILID("Ocurri&oacute; un error al intentar almacenar el registro."),
//	MSGUPDFAIL("Ocurri&oacute; un error al intentar actualizar el registro."),
//	MSGDELFAIL("Ocurri&oacute; un error al intentar eliminar los registros."),
//	MSGDELFAILREF("No se puede eliminar los registros seleccionados porque tienen relaciones con otros registros. Por favor, elimine las relaciones y vuelva a intentarlo."),
//	MSGFILEUPDWAIT("El archivo est&aacute; siendo cargado."),
//	MSGFILEUPDOK("La carga del archivo finaliz&oacute; satisfactoriamente."),
//	BTNADD("Agregar"),
//	BTNDEL("Eliminar"),
//	BTNSAVE("Guardar"),
//	BTNOK("Aceptar"),
//	BTNCANCEL("Cancelar"),
//	BTNLOGO("Ver logo..."),
//	BTNPICT("Ver foto..."),
//	BTNLOADLOGO("Cargar logo..."),
//	BTNLOADPICT("Cargar foto..."),
//	BTNLOADLABEL("Cargar etiqueta..."),
//	BTNLOADBOTTLE("Cargar botella..."),
//	BTNLOADTECHSHEET("Cargar ficha t&eacute;cnica..."),
//	BTNSELECTFILE("Seleccionar archivo"),
//	LBLFILE("Archivo"),
//	TTLSUCCESS("Confirmaci&oacute;n"),
//	TTLWARNING("Advertencia"),
//	TTLDELETE("Eliminar"),
//	TTLLOGO("Ver logo..."),
//	TTLPICT("Ver foto..."),
//	TTLLOADLOGO("Cargar logo..."),
//	TTLLOADPICT("Cargar foto..."),
//	TTLLOADLABEL("Cargar etiqueta..."),
//	TTLLOADBOTTLE("Cargar botella..."),
//	TTLLOADTECHSHEET("Cargar ficha t&eacute;cnica...");
	
	private String mensaje;
	
	private MsgEnum(String mensaje){
		this.mensaje = mensaje;
	}
	
	public String getMsg(){
		return this.mensaje;
	}
}
