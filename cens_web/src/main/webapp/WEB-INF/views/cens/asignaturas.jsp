<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


	<link rel="stylesheet" href="<%=request.getContextPath() %>/css/style.css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/css/jqgrid/ui.jqgrid.css">

    <script src="<%=request.getContextPath() %>/js/cens/asignatura.js"></script>
    <script>var pagePath="<%=request.getContextPath() %>"</script>

<div class="centreDiv">
  
    <div class="floatLeftDiv">
    
    	<div class="acciones">
    		<button class="button" type="button" onclick="window.location='<%=request.getContextPath() %>/mvc/asignaturaABM'">Nueva Asignatura</button>
    	</div>
    
		<!--     Filtros -->
		<div class="filtrosDiv">
			<h3>Filtros</h3>
			
			<label for="nombre">Nombre:</label>
			<input type="text" id="nombre"/>
			
			<label for="modalidad">Modalidad:</label>
			<input type="text" id="modalidad"/>
	        
	       	<label for="year">A&ntilde;o:</label>
			<input type="text" id="year" class="entero" maxlength="4"/>
	        
	        
	        <button class="button searchButton" type="button" onclick="gridReload()" id="submitButton">Filtrar</button>
	   </div>	

        
		<!--    Tabla a llenar  -->
        <div id="grilla">
           <table id="projectTable"></table>
            <div id="pagingDiv"></div>
        </div>
        <div class="messageDiv">
        	<label id="message"></label>
        </div>
    </div>

</div>

<div id="remAsignatura" class="dialog" title="Confirmar">
	<p>Se elimininar&aacute; la asignatura del sistema. ¿Desea continuar?</p>
</div>


