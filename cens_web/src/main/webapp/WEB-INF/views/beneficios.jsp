<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


	<link rel="stylesheet" href="<%=request.getContextPath() %>/css/style.css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/css/jqgrid/ui.jqgrid.css">

    <script src="<%=request.getContextPath() %>/js/beneficios.js?v=<%= new Date().getTime()%>"></script>

<div class="centreDiv">
  	
  	 <div class="acciones">
 		<button class="button" type="button" onclick="window.location='<%=request.getContextPath() %>/beneficio'">Nuevo Beneficio</button>
    </div>
  	
    <div class="floatLeftDiv">
    
		<!--     Filtros -->
		<div class="filtrosDiv">

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

<div id="remBeneficio" class="dialog" title="Confirmar">
	<p>Se elimininar&aacute; el beneficio para el cliente. ¿Desea continuar?</p>
</div>


