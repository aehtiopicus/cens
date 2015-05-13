package com.aehtiopicus.cens.configuration;

public class UrlConstant {

	
	
	//usuarios
	public final static String USUARIO_URL = "/usuario";
	public final static String USUARIOS_URL = "/usuarios";
	public final static String USUARIOS_GRILLA_URL = "/usuariosgrilla";
	public final static String USUARIO_DELETE_URL = "/removeusuario";
	public final static String RESETEAR_PASSWORD_URL = "/reset";
	
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
	public static final String MIEMBRO_CENS_REST_PICTURE = MIEMBRO_CENS_REST+"/{id}/picture";
	
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
	public static final String ASIGNATURA_ALUMNO_CENS_ABM_MVC=ASIGNATURA_CENS_ABM_MVC+"/{asignaturaId}/alumno";	
	public static final String ASIGNATURA_ALUMNO_CENS_REST=ASIGNATURA_CENS_REST+"/{asignaturaId}/alumno";
	public static final String ASIGNATURA_INSCRIPCION_CENS_MVC=ASIGNATURA_CENS_ABM_MVC+"/{asignaturaId}/inscripcion";	
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
	/*Contacto email*/
	public static final String MIEMBRO_CONTACTO_EMAIL_REST = API+"miembro/{miembroId}/contacto/email";	
	/*Notificacion*/
	public static final String NOTIFICACION_USUARIO_REST = API+"notificacion/miembro";
	public static final String NOTIFICACION_USUARIO_MIEMBRO_REST = API+"notificacion/miembro/{miembroId}";
	public static final String NOTIFICACION_NO_LEIDAS_MIEMBRO_REST = API+"notificacion/miembro/{miembroId}/asesor";
	public static final String NOTIFICACION_NO_LEIDAS_IGNORAR_MIEMBRO_REST = API+"notificacion/{tipoId}/{tipoType}/{notificacionType}";
	/*Alumno*/
	public static final String ALUMNO_CENS_REST = API+"alumno";
	public static final String ALUMNO_CENS_DASHBOARD_MVC=MVC+"alumno/dashboard";
	/*InscripcionAlumno*/
	public static final String INSCRIPCION_ALUMNO_CENS_REST=API+"inscripcion/asignatura/{asignaturaId}";
	/*Social*/
	public static final String SOCIAL_CENS_REST_OAUTH_2=API+"social/oauth2";
	public static final String SOCIAL_CENS_REST_OAUTH_2_CALLBACK=API+"social/oauth2/{provider}/callback";
		
}

