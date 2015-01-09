<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


	<link rel="stylesheet" href="<%=request.getContextPath() %>/css/style.css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/css/jqgrid/ui.jqgrid.css">

    <script src="js/usuarios.js?v=<%= new Date().getTime()%>"></script>

<div class="centreDiv">
  
    <div class="floatLeftDiv">
    
    	<div class="acciones">
    		<button class="button" type="button" onclick="window.location='<%=request.getContextPath() %>/usuario'">Nuevo Usuario</button>
    	</div>
    
		<!--     Filtros -->
		<div class="filtrosDiv">
			<h3>Filtros</h3>
			
			<label for="perfil">Perfil:</label>
			<select id="perfil">
			   <option value=""/> 
		         <c:forEach items="${perfilDto}" var="perfil">
		           <option value="${perfil.perfilType}"> ${perfil.perfilType} </option>
		        </c:forEach>
	        </select>
	        
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

<div id="remUser" class="dialog" title="Confirmar">
	<p>Se elimininar&aacute; el usuario del sistema. ¿Desea continuar?</p>
</div>


