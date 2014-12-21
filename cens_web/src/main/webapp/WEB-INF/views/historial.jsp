<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


	<link rel="stylesheet" href="<%=request.getContextPath() %>/css/style.css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/css/jqgrid/ui.jqgrid.css">

    <script src="<%=request.getContextPath() %>/js/historial.js?v=<%= new Date().getTime()%>"></script>

<div class="centreDiv">
	<input type="hidden" value="${empleadoId}" id="empleadoId">
	
	<div class="acciones">
    </div>
	
	<div class="floatLeftDiv">
	
		<!--     Filtros -->
		<div class="filtrosDiv">
				<label>Empleado:</label>	
				<input type="text"  disabled="disabled" value="${empleado}"/>
		</div>
		<!--    Tabla a llenar  -->
		<div>
			<table id="projectTable"></table>
		</div>
		
		<div class="accionesRight">
			<button class="button" type="button" onclick="javascript:history.go(-1);">Volver</button>
		</div>
	</div>
</div>


