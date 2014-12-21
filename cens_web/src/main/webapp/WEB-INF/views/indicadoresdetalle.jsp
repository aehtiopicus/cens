<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


	<link rel="stylesheet" href="<%=request.getContextPath() %>/css/style.css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/css/jqgrid/ui.jqgrid.css">

    <script src="<%=request.getContextPath() %>/js/indicadoresdetalles.js?v=<%= new Date().getTime()%>"></script>

 	
	<div class="filtrosDiv">
			<label>Cliente:</label>	<input type="text"  disabled="true" value="${cliente}"/>
			<label>Periodo 1:</label>	<input type="text"  disabled="true" value="${periodo1}"/>
			<label>Periodo 2:</label>	<input type="text"  disabled="true" value="${periodo2}"/>
		</div>
	
   <div class="centreDiv">
  	  	<input type="hidden" id="clienteId" value="${clienteId}">
  	  	<input type="hidden" id="periodo1" value="${periodo1}">
  	  	<input type="hidden" id="periodo2" value="${periodo2}">
    <div class="floatLeftDiv">
           
		<!--    Tabla a llenar  -->
        <div>
           <table id="projectTable"></table>
        </div>
        <div class="messageDiv">
        	<label id="message"></label>
        </div>
        
        <div class="accionesRight">
			<button class="button" type="button" onclick="window.location='<%=request.getContextPath() %>/indicadores'">Volver</button>
		</div>
    </div>

</div>




