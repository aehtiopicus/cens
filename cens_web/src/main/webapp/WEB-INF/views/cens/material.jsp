<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


	<link rel="stylesheet" href="<%=request.getContextPath() %>/css/style.css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/css/jqgrid/ui.jqgrid.css">

    <script src="<%=request.getContextPath() %>/js/cens/material.js"></script>
    <script>
    	var pagePath="<%=request.getContextPath() %>";
    	var asignatura="${asignatura}";
    	var programaId=${programaId};
    </script>

<div class="centreDiv">
  	
    <div class="floatLeftDiv">
    <h3 >Material Did&aacute;ctico de la asignatura ${asignatura}</h3>
    	<div class="acciones" style="padding-bottom: 10px;">
    		<button class="button" type="button" onclick="crearMaterialDidacticto('<%=request.getContextPath() %>/mvc/programa/${programaId}/materialABM?asignatura=${asignatura}')">Nuevo Material Did&aacute;ctico</button>
    	</div>
    	<div></div>
		<!--    Tabla a llenar  -->
        <div>
           <table id="projectTable"></table>
            <div id="pagingDiv"></div>
        </div>
        <div class="messageDiv">
        	<label id="message"></label>
        </div>
         <div style="clear: both"></div>
    </div>

</div>

<div id="remMaterial" class="dialog" title="Confirmar">
	<p>Se elimininar&aacute; el Material Did&aacute;ctico del sistema. ¿Desea continuar?</p>
</div>


