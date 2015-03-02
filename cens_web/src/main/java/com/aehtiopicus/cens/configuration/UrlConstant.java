package com.aehtiopicus.cens.configuration;

public class UrlConstant {

	
	
	//usuarios
	public final static String USUARIO_URL = "/usuario";
	public final static String USUARIOS_URL = "/usuarios";
	public final static String USUARIOS_GRILLA_URL = "/usuariosgrilla";
	public final static String USUARIO_DELETE_URL = "/removeusuario";
	public final static String RESETEAR_PASSWORD_URL = "/reset";

	//mis datos
	public final static String MI_PERFIL_URL = "/perfil";
	public final static String CAMBIAR_PASSWORD_URL = MI_PERFIL_URL +"/cambiarclave";

	//empleados
	public final static String EMPLEADO_URL = "/empleado";
	public final static String EMPLEADOS_URL = "/empleados";
	public final static String EMPLEADOS_GRILLA_URL = "/empleadosgrilla";
	public final static String EMPLEADO_EXCEL =EMPLEADOS_URL+"/exportar";
	public final static String EMPLEADO_SUELDO = EMPLEADOS_URL+"/sueldo";
	public final static String EMPLEADO_VACACION = EMPLEADOS_URL+"/vacaciones";
	public final static String EMPLEADO_HISTORIAL_GRILLA_URL = EMPLEADOS_URL+"/historialgrilla";
	public final static String EMPLEADO_HISTORIAL_URL = EMPLEADOS_URL+"/historial";
	public final static String EMPLEADO_VACACIONES_GRILLA = EMPLEADO_VACACION+"/vacacionesgrilla";
	public final static String VACACIONES_REMOVE_URL = EMPLEADO_VACACION + "/removevacaciones";
	public final static String EMPLEADO_VACACION_URL ="vacaciones"; 
	public final static String EMPLEADO_VACACION_FORM_URL =EMPLEADOS_URL+"/vacacion"; 
	
	//beneficios
	public final static String BENEFICIOS_URL = "/beneficios";
	public final static String BENEFICIO_URL = "/beneficio";
	public final static String BENEFICIOS_GRILLA_URL = "/beneficiosgrilla";
	public final static String BENEFICIO_DELETE_URL = "/removebeneficio";
	
	//clientes
	public final static String CLIENTE_URL = "/cliente";
	public final static String CLIENTES_URL = "/clientes";
	public final static String CLIENTES_GRILLA_URL = "/clientesgrilla";

	//beneficios-cliente
	public final static String CLIENTE_BENEFICIOS_URL = CLIENTES_URL + "/beneficios";
	public final static String CLIENTE_BENEFICIO_URL = CLIENTES_URL + "/beneficio";
	public final static String CLIENTE_BENEFICIOS_GRILLA_URL = CLIENTES_URL + "/beneficiosgrilla";
	public final static String CLIENTE_BENEFICIO_DELETE_URL = CLIENTES_URL + "/removebeneficiocliente";
	public final static String CLIENTE_BENEFICIO_CHANGE_ESTADO_URL = CLIENTE_BENEFICIOS_URL + "/changeestado";

	//asignacion empleados (empleado - cliente)
	public final static String ASIGNACION_EMPLEADOS_URL = "/asignacionempleados";
	public final static String ASIGNACION_EMPLEADO_NEW_URL = "/nuevaasignacion";
	public final static String ASIGNACION_EMPLEADO_EDIT_URL = "/editarasignacion";
	public final static String ASIGNACION_PUESTO_URL = "/cambiarpuesto";
	public final static String ASIGNACION_EMPLEADOS_GRILLA_URL = "/asignacionempleadosgrilla";
	public final static String INCREMENTO_MASIVO_POR_CLIENTE_NEW_URL = "/incrementosalarial";
	public final static String INCREMENTO_MASIVO_POR_CLIENTE_RESULT_URL = "/incrementosalarialresult";
	public final static String INCREMENTO_MASIVO_POR_CLIENTE_RESULT_GRILLA_URL = "/incrementosalarialresultgrilla";
		

	//informes mensuales
	public final static String GTE_OPERACIONES = "operacion";
	public final static String INFORMES_MENSUALES_URL = GTE_OPERACIONES + "/informesmensuales";
	public final static String INFORMES_MENSUALES_GRILLA_URL = GTE_OPERACIONES + "/informesmensualesgrilla";
	public final static String INFORME_MENSUAL_NEW_URL = GTE_OPERACIONES + "/nuevoinforme";
	public final static String INFORME_MENSUAL_EDIT_URL = GTE_OPERACIONES + "/informemensual";
	public final static String INFORME_MENSUAL_EXCEL_URL = GTE_OPERACIONES + "/informemensualexceldata";
	public final static String INFORME_MENSUAL_EXCEL_UPDATE_URL = INFORME_MENSUAL_EDIT_URL + "/update";
	public final static String INFORME_MENSUAL_ENVIAR_URL = GTE_OPERACIONES + "/enviarinformemensual";
	public final static String INFORME_MENSUAL_INTERMEDIO_URL = GTE_OPERACIONES + "/informeintermediomensual";
	public final static String INFORME_MENSUAL_DELETE_URL = GTE_OPERACIONES + "/eliminarinformemensual";
	public final static String INFORME_MENSUAL_EXCEL_EXPORT_URL = GTE_OPERACIONES + "/informemensualexcelexport";																			 
	
	
	//informes consolidados
	public final static String ADMINISTRACION = "administracion";
	public final static String INFORME_MENSUAL_NO_CONSOLIDADO_URL = ADMINISTRACION + "/informesporconsolidar";
	public final static String INFORME_MENSUAL_NO_CONSOLIDADO_GRILLA_URL = ADMINISTRACION + "/informesporconsolidargrilla";
	public final static String INFORME_MENSUAL_NO_CONSOLIDADO_EXPORT = INFORME_MENSUAL_NO_CONSOLIDADO_URL + "/exportar";
	public final static String INFORMES_CONSOLIDADOS_URL = ADMINISTRACION + "/informesconsolidados";
	public final static String INFORMES_CONSOLIDADOS_GRILLA_URL = ADMINISTRACION + "/informesconsolidadosgrilla";
	public final static String INFORME_CONSOLIDADO_NEW_URL = ADMINISTRACION + "/nuevoinforme";
	public final static String INFORME_CONSOLIDADO_EDIT_URL = ADMINISTRACION + "/informeconsolidado";
	public final static String INFORME_CONSOLIDADO_EXCEL_URL = ADMINISTRACION + "/informeconsolidadoexceldata";
	public final static String INFORME_CONSOLIDADO_EXCEL_UPDATE_URL = INFORME_CONSOLIDADO_EDIT_URL + "/update";
	public final static String INFORME_CONSOLIDADO_VERIFICAR_EXISTENCIA = ADMINISTRACION + "/verificarexistencia";
	public final static String INFORME_CONSOLIDADO_GET_DETALLE_ADICIONALES = ADMINISTRACION + "/obtenerdetalleadicionales";
	public final static String INFORME_CONSOLIDADO_FINALIZAR_URL = ADMINISTRACION + "/finalizarinformeconsolidado";
	public final static String INFORME_CONSOLIDADO_DELETE_URL = ADMINISTRACION + "/eliminarinformeconsolidado";
	public final static String INFORME_MENSUAL_RECHAZAR_URL = ADMINISTRACION + "/rechazarinformemensual";
	public final static String INFORME_CONSOLIDADO_EXCEL_EXPORT_URL = ADMINISTRACION + "/informeconsolidadoexcelexport";																			 

	//informes consolidados SAC
	public final static String INFORME_SAC_NEW_URL = ADMINISTRACION + "/nuevoinformesac";
	public final static String INFORME_SAC_EDIT_URL = ADMINISTRACION + "/informesac";
	
	//indicadores
	public final static String INDICADORES = "/indicadores";
	public final static String INDICADORES_GRILLA_URL = "/indicadoresgrilla";
	public final static String INDICADORES_DETALLE_GRILLA_URL = "/indicadoresdetallegrilla";
	public final static String INDICADORES_DETALLE = "/indicadoresdetalle";
	
	//pagos
	public final static String PAGO_URL = ADMINISTRACION+"/pago";
	public final static String CREAR_PAGO_URL = ADMINISTRACION+"/crearpago";
	public final static String PAGO_VALIDACION_URL = ADMINISTRACION+"/validarpago";
	
	public final static String ADJUNTO_URL = "/adjuntarEmpleados";
	
	//parametrizaciones
	public final static String OBRAS_SOCIALES_URL = "/obrassociales";
	public final static String OBRA_SOCIAL_URL = "/obrasocial";
	public final static String OBRAS_SOCIALES_GRILLA_URL = "/obrassocialesgrilla";
	public final static String OBRA_SOCIAL_DELETE_URL = "/removeobrasocial";
	
	public final static String PREPAGAS_URL = "/prepagas";
	public final static String PREPAGA_URL = "/prepaga";
	public final static String PREPAGAS_GRILLA_URL = "/prepagasgrilla";
	public final static String PREPAGA_DELETE_URL = "/removeprepaga";

	public final static String BANCOS_URL = "/bancos";
	public final static String BANCO_URL = "/banco";
	public final static String BANCOS_GRILLA_URL = "/bancosgrilla";
	public final static String BANCO_DELETE_URL = "/removebanco";
	
	public static final String MOTIVOS_BAJA_URL = "/motivosbaja";
	public static final String MOTIVO_BAJA_URL = "/motivobaja";
	public final static String MOTIVOS_BAJA_GRILLA_URL = "/motivosbajagrilla";
	public final static String MOTIVO_BAJA_DELETE_URL = "/removemotivobaja";
	
	/****/	
		 
	/*API*/
	private static final String API="/api/";	
	/*MVC*/
	private static final String MVC="/mvc/";
	
	public final static String MAIN_URL = MVC+"main";
	public final static String UNAUTHORIZED_URL = "/unauthorized";
	
	/*Perfil*/
	public static final String ROL =API+"rol";
	
	public static final String MIEMBRO_CENS_REST =API+"miembro";
	public static final String MIEMBRO_CENS =MVC+"miembroList";
	public static final String MIEMBRO_CENS_ABM=MVC+"miembroABM";
	
	/*Usuario*/
	public static final String USUARIO_CENS_REST =API+"usuario";
	public static final String USUARIO_CENS_REST_PASSWORD =USUARIO_CENS_REST+"/{id}/reset";
	public static final String USUARIO_CENS_REST_CHANGE_PASSWORD = USUARIO_CENS_REST+"/{id}/changepass";
	public static final String USUARIO_CENS_REST_CHANGE_USERNAME = USUARIO_CENS_REST+"/{id}/changeusername";
	public static final String USUARIO_CENS_REST_CHANGE_PICTURE = USUARIO_CENS_REST+"/{id}/changepicture";
	public static final String USUARIO_CENS_REST_PICTURE = USUARIO_CENS_REST+"/{id}/picture";
	public final static String RESET_PASSWORD_MVC = MVC+"resetpassword";
	/*Curso*/
	public static final String CURSO_CENS_MVC=MVC+"cursoList";
	public static final String CURSO_CENS_ABM_MVC=MVC+"cursoABM";
	public static final String CURSO_CENS_REST = API+"curso";
	/*Asignatura*/
	public static final String ASIGNATURA_CENS_REST =API+"asignatura";	
	public static final String ASIGNATURA_CENS_MVC=MVC+"asignaturaList";
	public static final String ASIGNATURA_CENS_ABM_MVC=MVC+"asignaturaABM";	
	/*Profesor*/
	public static final String PROFESOR_CENS_REST=API+"profesor";
	public static final String PROFESOR_CENS_REMOVE_ASIGNATURAS_REST=PROFESOR_CENS_REST+"/{id}/removerasignaturas";
	public static final String PROFESOR_CENS_ASIGNATURAS_MVC=MVC+"profesor/asignaturaList";
	public static final String PROFESOR_CENS_CURSO_ASIGNATURAS_REST=PROFESOR_CENS_REST+"/{id}/curso/asignatura";
	/*Programa*/
	public static final String PROGRAMA_CENS_MVC=MVC+"asignatura/{id}/programa";
	public static final String PROGRAMA_CENS_REST =API+"asignatura/{id}/programa";
	public static final String PROGRAMA_CENS_NO_FILE_REST =API+"asignatura/{id}/programanf";
	public static final String PROGRAMA_CENS_FILE_REST =API+"asignatura/{id}/programa/{programaId}/archivo";	
	/*Aesor*/
	public static final String ASESOR_CENS_REST=API+"asesor";
	public static final String ASESOR_CENS_CURSO_ASIGNATURAS_REST=ASESOR_CENS_REST+"/{id}/dashboard";
	public static final String ASESOR_CENS_DASHBOARD_MVC=MVC+"asesor/dashboard";
	public static final String ASESOR_CENS_ASIGNATURA_MVC=MVC+"asesor/{id}/asignatura/{asignaturaId}/programa/{programaId}";	
	public static final String ASESOR_CENS_MATERIAL_MVC=MVC+"asesor/{id}/asignatura/{asignaturaId}/programa/{programaId}/material/{materialId}";
	/*Comentarios*/
	public static final String COMENTARIO_CENS_REST=API+"comentario/comments/list";
	public static final String COMENTARIO_CENS_NO_FILE_REST=API+"comentario/comments/listnf";
	/*MaterialDidactico*/
	public static final String MATERIAL_DIDACTICO_CENS_MVC=MVC+"programa/{id}/material";
	public static final String MATERIAL_DIDACTICO_CENS_ABM_MVC=MVC+"programa/{programaId}/materialABM";
	public static final String MATERIAL_DIDACTICO_CENS_REST =API+"programa/{id}/material";	
	public static final String MATERIAL_DIDACTICO_CENS_NO_FILE_REST =API+"programa/{id}/materialnf";
	public static final String MATERIAL_DIDACTICO_CENS_FILE_REST =API+"programa/{id}/material/{materialId}/archivo";
	/*Perfil*/
	public static final String PERFIL_CENS_MVC = MVC+"perfil";
	public static final String CONTACTO_CENS_MVC = MVC+"contacto";	
		
}
