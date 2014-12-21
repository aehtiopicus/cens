<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<link rel="stylesheet" href="<%=request.getContextPath() %>/css/style.css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/css/jqgrid/ui.jqgrid.css">

    <script src="js/clientes.js?v=<%= new Date().getTime()%>"></script>

<div class="centreDiv">
  
    <div class="floatLeftDiv">
    
       	<div class="acciones">
	    	<security:authorize ifAnyGranted="ROLE_GTEOPERACION, ROLE_RRHH">
	    		<button class="button" type="button" disabled="disabled">Nuevo Cliente</button>
	    	</security:authorize>
	    	<security:authorize ifAnyGranted="ROLE_ADMINISTRACION, ROLE_ADMINISTRADOR">
	    		<button class="button" type="button" onclick="window.location='<%=request.getContextPath() %>/cliente'">Nuevo Cliente</button>
	    	</security:authorize>
    	</div>
    	
		<!--     Filtros -->
		<div class="filtrosDiv">
			<h3>Filtros</h3>
			<label for="nombre">Nombre:</label>
			<input type="text" id="nombre"  />
			<label for="estado">Estado:</label>
			<select id="estado">
			   <option value=""/> 
		         <c:forEach items="${estadosDto}" var="estado">
		           <option value="${estado.value}"> ${estado.value} </option>
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

<div id="remUser" class="dialog" title="Confirmar">
	<p>Se elimininar&aacute; el cliente del sistema. ¿Desea continuar?</p>
</div>


