<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<link rel="stylesheet" href="<%=request.getContextPath() %>/css/style.css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/css/jqgrid/ui.jqgrid.css">

    <script src="<%=request.getContextPath() %>/js/incrementoSalarial.js?v=<%= new Date().getTime()%>"></script>


<div class="centreDiv">
	<input type="hidden" value="${incrementoSalarialDto.clienteId}" id="clienteId">
	<input type="hidden" value="${datetime}" id="dateTime">
		
	<div class="acciones">
    </div>
	
	<div class="floatLeftDiv">
	
		<!--     Filtros -->
		<div class="filtrosDiv">
			<label>Cliente:</label>	
			<input type="text"  disabled="disabled" value="${cliente}"/>
			<label>Tipo Incremento:</label>	
			<input type="text"  disabled="disabled" value="${incrementoSalarialDto.tipoIncremento }"/>
			<label>Inc. Básico:</label>	
			<input type="text"  disabled="disabled" value="${incrementoSalarialDto.incrementoBasico}"/>
			<label>Inc. Presentismo:</label>	
			<input type="text"  disabled="disabled" value="${incrementoSalarialDto.incrementoPresentismo}"/>
			<label>Fecha Inicio:</label>	
			<input type="text"  disabled="disabled" value="${incrementoSalarialDto.fechaInicio}"/>
		</div>
		<!--    Tabla a llenar  -->
		<div>
			<table id="projectTable"></table>
		</div>
		
		<div class="accionesRight">
			<button class="button" type="button" onclick="window.location='<%=request.getContextPath() %>'">Continuar</button>
		</div>
	</div>
</div>


