<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<link rel="stylesheet" href="<%=request.getContextPath() %>/css/style.css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/css/jqgrid/ui.jqgrid.css">

    <script src="js/empleados.js?v=<%= new Date().getTime()%>"></script>

<div class="centreDiv">
  
    <div class="floatLeftDiv">
    
    <div class="acciones">
    	<security:authorize ifAllGranted="ROLE_GTEOPERACION">
     		<button class="button" type="button" disabled="disabled">Nuevo Empleado</button> 
    		<button class="button" type="button" disabled="disabled">Carga Masiva</button>
    	</security:authorize>
    	<security:authorize ifNotGranted="ROLE_GTEOPERACION">
    		<button class="button" type="button" onclick="window.location='<%=request.getContextPath() %>/empleado'">Nuevo Empleado</button>
	    	<button class="button" type="button" onclick="window.location='<%=request.getContextPath() %>/adjuntarEmpleados'">Carga Masiva</button>
    	</security:authorize>
    	
    	<button class="button" type="button" onclick="exportar()">Exportar a Excel</button>
    	
    </div>
    
		<!--     Filtros -->
		<div class="filtrosDiv">
			<h3>Filtros</h3>
			
			<label for="apellidos">Apellido:</label>
			<input type="text" id="apellido"  />
			
			<label for="dni">CUIL:</label>
			<input type="text" id="cuil"/>
			
			<label for="cliente">Cliente:</label>
			<select id="cliente">
			   <option value=""/> 
		         <c:forEach items="${clienteDto}" var="cliente">
		           <option value="${cliente.id}"> ${cliente.value} </option>
		        </c:forEach>
	        </select>
	
			<label for="estado">Estado:</label>
			<select id="estado">
			     <c:forEach items="${estadoDto}" var="estado">
		           <option value="${estado.value}"> ${estado.value} </option>
		        </c:forEach>
		        <option value=""/> 
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


