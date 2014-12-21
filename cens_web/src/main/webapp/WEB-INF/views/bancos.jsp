<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


	<link rel="stylesheet" href="<%=request.getContextPath() %>/css/style.css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/css/jqgrid/ui.jqgrid.css">

    <script src="<%=request.getContextPath() %>/js/bancos.js?v=<%= new Date().getTime()%>"></script>

<div class="centreDiv">
  	
  	 <div class="acciones">
 		<button class="button" type="button" onclick="window.location='<%=request.getContextPath() %>/banco'">Nuevo Banco</button>
    </div>
  	
    <div class="floatLeftDiv">
    
		<!--     Filtros -->
		<div class="filtrosDiv">
			<label for="nombre">Nombre:</label>
			<input type="text" id="nombre"  />
			
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

<div id="remBanco" class="dialog" title="Confirmar">
	<p>Se elimininar&aacute; el banco del sistema. ¿Desea continuar?</p>
</div>


