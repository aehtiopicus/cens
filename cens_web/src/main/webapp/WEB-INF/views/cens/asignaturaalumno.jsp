<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


	<link rel="stylesheet" href="<%=request.getContextPath() %>/css/style.css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/css/jqgrid/ui.jqgrid.css">

	<script>
		var pagePath="<%=request.getContextPath() %>"
		var asignaturaId="${asignaturaId}";
	</script>
    <script src="<%=request.getContextPath() %>/js/cens/asignaturaalumno.js"></script>
    

<div class="centreDiv">
  
    <div class="floatLeftDiv">
    
    	<div class="acciones">
    		<button class="button" type="button" onclick="window.location='<%=request.getContextPath() %>/mvc/asignaturaABM/${asignaturaId}/inscripcion'">Inscribir Alumno</button>
    	</div>
    
		<!--     Filtros -->
		<div class="filtrosDiv">
			<h3>Filtros</h3>					
	        	      
			<label for="apellidos">Apellido:</label>
			<input type="text" id="apellido"  />
	        
	        
	        <button class="button searchButton" type="button" onclick="gridReload()" id="submitButton">Filtrar</button>
	   </div>	

        
		<!--    Tabla a llenar  -->
        <div>
           <table id="projectTable"></table>
            <div id="pagingDiv"></div>
        </div>
        <div class="messageDiv">
        	<label id="message"></label>
        </div>
    </div>

</div>

<div id="desincribirAlumno" class="dialog" title="Confirmar">
	<p>Se elimininar&aacute; la inscripci&oacute;n del Alumno. ¿Desea continuar?</p>
</div>
