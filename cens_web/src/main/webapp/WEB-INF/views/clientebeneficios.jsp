<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


	<link rel="stylesheet" href="<%=request.getContextPath() %>/css/style.css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/css/jqgrid/ui.jqgrid.css">

    <script src="<%=request.getContextPath() %>/js/clientebeneficios.js?v=<%= new Date().getTime()%>"></script>

<div class="centreDiv">
  	<input type="hidden" id="clienteId" value="${clienteId}">
  	
  	<div class="acciones">
 		<button class="button" type="button" onclick="window.location='<%=request.getContextPath() %>/clientes/beneficio/${clienteId}'">Asignar Nuevo Beneficio</button>
    </div>
  	
    <div class="floatLeftDiv">
    
		<!--     Filtros -->
		<div class="filtrosDiv">
			<label for="periodo">Cliente:</label>
			<input type="text" value="${clienteDto.nombre}" disabled="disabled" />			
	   	</div>	

        
		<!--    Tabla a llenar  -->
        <div>
           <table id="projectTable"></table>
<!--             <div id="pagingDiv"></div> -->
        </div>
        <div class="messageDiv">
        	<label id="message"></label>
        </div>
        <div class="accionesRight">
			<button class="button" type="button" onclick="window.location='<%=request.getContextPath() %>/clientes'">Volver</button>
		</div>
    </div>

</div>


