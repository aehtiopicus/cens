<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
	<script>var pagePath="<%=request.getContextPath() %>"</script>
	<script>var asignaturaId="${asignaturaId}"</script>
    <script src="<%=request.getContextPath() %>/js/cens/alumno.js"></script>
    <script src="<%=request.getContextPath() %>/js/cens/asignaturainscripcion.js"></script>
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
	
	<div id="alumnoHeader" class="ui-jqgrid-titlebar ui-widget-header ui-corner-top ui-helper-clearfix" style="margin-top: 30px; width:98%;">
		<span class="ui-jqgrid-title" style="float:left; margin-left:10px;">Datos del Alumno</span>
		<span class="ui-jqgrid-title" style="float:right; margin-right:10px;">Estado</span>
	</div>
	
	<div id="alumnoData" >
		<div style="margin: 3px;">
			<div style="display: inline-block;   margin-top: 4px;">Fabiana Micaela Cipolla, 33968270 29/08/1988</div>
			<div style="display: inline-block; float:right;">
				<div style="margin-right: 19px;" class="cmaSuccess"></div>
									
			</div>
			<div style="clear:both;"></div>
		</div>
		<div style="margin: 3px;">
			<div style="display: inline-block;   margin-top: 4px;">Fabiana Micaela Cipolla, 33968270 29/08/1988</div>
			<div style="display: inline-block; float:right;">
				<div style="margin-right: 19px;" class=cmaInfo></div>
									
			</div>
			<div style="clear:both;"></div>
		</div>
		<div style="margin: 3px;">
			<div style="display: inline-block;   margin-top: 4px;">Fabiana Micaela Cipolla, 33968270 29/08/1988</div>
			<div style="display: inline-block; float:right;">
				<div style="margin-right: 19px;" class="cmaError"></div>
									
			</div>
			<div style="clear:both;"></div>
		</div>
		
		<div style="margin: 3px;">
			<div style="display: inline-block;   margin-top: 4px;">Fabiana Micaela Cipolla, 33968270 29/08/1988</div>
			<div style="display: inline-block; float:right;">
				<div class="cmaPending"></div>
									
			</div>
			<div style="clear:both;"></div>
		</div>
		
	</div>
</div>

<div class="footerForm">
				<button class="button" type="button" onclick="submitCurso('<%=request.getContextPath() %>/curso');" >Guardar</button>

				<button id ="cancelar" class="button" type="button" onclick="window.location='<%=request.getContextPath() %>/mvc/asignaturaABM/${asignaturaId}/alumno'">Cancelar</button>
</div>

</fieldset>