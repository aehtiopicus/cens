<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


	<link rel="stylesheet" href="<%=request.getContextPath() %>/css/style.css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/css/jqgrid/ui.jqgrid.css">

    <script src="<%=request.getContextPath() %>/js/informesmensualesnoconsolidados.js?v=<%= new Date().getTime()%>"></script>

<div class="centreDiv">
  
    <div class="floatLeftDiv">
    
     	<div class="acciones">
    		<button class="button" type="button" onclick="crearInforme()">Generar Informe Consolidado</button>
    		<button class="button" type="button" onclick="exportar()">Exportar a Excel</button>
    	</div>
    	      
		<!--     Filtros -->
		<div class="filtrosDiv">
			<h3>Filtros</h3>

			<label for="periodo">Periodo:</label>
			<select id="periodo">
		         <c:forEach items="${periodosDto}" var="item">
		           <option value="${item}"> ${item} </option>
		        </c:forEach>
	        </select>
			
			<label for="cliente">Cliente:</label>
			<select id="cliente">
			   <option value=""/> 
		         <c:forEach items="${clientesDto}" var="item">
		           <option value="${item.id}"> ${item.value} </option>
		        </c:forEach>
	        </select>
	        
	        <label for="estado">Estado:</label>
			<select id="estado">
			   <option value=""/> 
		         <c:forEach items="${estadosDto}" var="item">
		           <option value="${item.value}"> ${item.value} </option>
		        </c:forEach>
	        </select>
	        
	        
	        <button class="button searchButton" type="button" onclick="gridReload()" id="submitButton">Filtrar</button>
	   </div>	

        
		<!--    Tabla a llenar  -->
        <div>
           <table id="projectTable"></table>
           
        </div>
        <div class="messageDiv">
        	<label id="message"></label>
        </div>
    </div>

</div>

<div id="advertenciaDialog" class="dialog" title="Confirmar">
	<p>Los informes en estado pendiente no se incluir&aacute;n en el informe consolidado. ¿Desea continuar?</p>
</div>



