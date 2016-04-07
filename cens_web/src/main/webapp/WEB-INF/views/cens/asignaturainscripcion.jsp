<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
	<script>var pagePath="<%=request.getContextPath() %>"</script>
	
    <script src="<%=request.getContextPath() %>/js/cens/alumno.js"></script>
    <script src="<%=request.getContextPath() %>/js/cens/asignaturainscripcion.js"></script>
    
    <script>
    data ={
    		asignaturaId:"${asignaturaId}"
    };
   	 var alumnoinscripcion = new alumnos.as.inscripcion(data);
    </script>
    <fieldset>
    <div class="tituloForm">
    	<h3 style="text-align: left;">Inscripci&oacute;n de Alumnos</h3>
    </div>
	<div>
		<div>
				<label for="alumno">Alumno:</label>
				<input type="text" id="alumno" placeholder="Ingrese Alumno" style="width: 340px;"/>
				<button class="button agregarAlumno" type="button" id="agregar">Agregar</button>
		</div>	
	</div>

<div id="listaAlumnos">
	<p id="cmaNoData" class="comments-link bold">No Hay datos</p>
	<div id="alumnoHeader" class="ui-jqgrid-titlebar ui-widget-header ui-corner-top ui-helper-clearfix" style="margin-top: 30px; width:98%;">
		<span class="ui-jqgrid-title" style="float:left; margin-left:10px;">Datos del Alumno</span>
		<span class="ui-jqgrid-title" style="float:right; margin-right:10px;">Estado</span>
	</div>
	
	<div id="alumnoData" >		
		
	</div>
</div>

<div class="footerForm">
				<button id="btnGuardar" class="button" type="button">Guardar</button>

				<button id ="cancelar" class="button" type="button" onclick="window.location='<%=request.getContextPath() %>/mvc/asignaturaABM/${asignaturaId}/alumno'">Cancelar</button>
</div>

</fieldset>