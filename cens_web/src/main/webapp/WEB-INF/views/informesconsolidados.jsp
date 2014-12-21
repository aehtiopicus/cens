<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


	<link rel="stylesheet" href="<%=request.getContextPath() %>/css/style.css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/css/jqgrid/ui.jqgrid.css">

    <script src="<%=request.getContextPath() %>/js/informesconsolidados.js?v=<%= new Date().getTime()%>"></script>

<div class="centreDiv">
  
    <div class="floatLeftDiv">
    
    	<div class="acciones">
<%--     		<button class="button" type="button" onclick="window.location='<%=request.getContextPath() %>/administracion/nuevoinforme'">Nuevo Informe</button> --%>
    		<button class="button" type="button" onclick="window.location='<%=request.getContextPath() %>/administracion/informesporconsolidar'">Nuevo Informe Consolidado</button>
    		<button class="button" type="button" onclick="window.location='<%=request.getContextPath() %>/administracion/nuevoinformesac'">Nuevo Informe SAC</button>

    	</div>
    
		<!--     Filtros -->
		<div class="filtrosDiv">
			<h3>Filtros</h3>

			<label for="periodo">Periodo:</label>
			<select id="periodo">
			   <option value=""/> 
		         <c:forEach items="${periodosDto}" var="item">
		           <option value="${item}"> ${item} </option>
		        </c:forEach>
	        </select>
			
			
<!-- 			<label for="cliente">Estado:</label> -->
<!-- 			<select id="cliente"> -->
<!-- 			   <option value=""/>  -->
<%-- 		         <c:forEach items="${clientesDto}" var="item"> --%>
<%-- 		           <option value="${item.id}"> ${item.value} </option> --%>
<%-- 		        </c:forEach> --%>
<!-- 	        </select> -->
	        
	        
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

<div id="consolidarDialog" class="dialog" title="Confirmar">
	<p>El informe no podrá ser editado en el futuro. ¿Desea continuar?</p>
</div>
<div id="eliminarDialog" class="dialog" title="Confirmar">
	<p>Se elimininar&aacute; el informe del sistema. ¿Desea continuar?</p>
</div>
<div id="spiner">
	<div class="spiner_image"></div>
	<span class="spiner_text_informe">Borrando Informe...</span>
</div>