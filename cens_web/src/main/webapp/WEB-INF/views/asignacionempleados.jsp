<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


	<link rel="stylesheet" href="<%=request.getContextPath() %>/css/style.css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/css/jqgrid/ui.jqgrid.css">

    <script src="js/asignacionempleados.js?v=<%= new Date().getTime()%>"></script>

<div class="centreDiv">
  
    <div class="floatLeftDiv">
    
    	<div class="acciones">
<%--     		<button class="button" type="button" onclick="window.location='<%=request.getContextPath() %>/asignarempleado'">Nueva Asignaci&oacute;n</button> --%>
    	</div>
    
		<!--     Filtros -->
		<div class="filtrosDiv">
			<h3>Filtros</h3>
			
			<label for="apellido">Apellido:</label>
			<input type="text" id="apellido"  />

			<label for="nombre">Nombre:</label>
			<input type="text" id="nombre"  />
			
			<label for="cliente">Cliente:</label>
			<select id="cliente">
			   <option value=""/> 
		         <c:forEach items="${clientesDto}" var="item">
		           <option value="${item.id}"> ${item.value} </option>
		        </c:forEach>
	        </select>
	        
	        
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



