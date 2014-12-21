<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


	<link rel="stylesheet" href="<%=request.getContextPath() %>/css/style.css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/css/jqgrid/ui.jqgrid.css">

    <script src="../../js/vacaciones.js?v=<%= new Date().getTime()%>"></script>

<div class="centreDiv">
  
   
  <div class="acciones">
    <c:choose>
		    <c:when test="${estado == 'Actual'}">
				      <button class="button" type="button" onclick="window.location='<%=request.getContextPath() %>/empleados/vacacion?empleadoId=${empleadoId}'">Cargar Vacaciones</button>
			</c:when>
		
	</c:choose>		
        	
    </div>
    <div class="floatLeftDiv">
    
     <input type="hidden"  id="empleadoId" value="${empleadoId}"/>
    
    	<!--     Filtros -->
		<div class="filtrosDiv">
			<label>Empleado:</label>	<input type="text"  disabled="true" value="${empleado}"/>
		</div>
		
		<!--    Tabla a llenar  -->
        <div>
           <table id="projectTable"></table>
            <div id="pagingDiv"></div>
        </div>
        <div class="messageDiv">
        	<label id="message"></label>
        </div>
        <div class="accionesRight">
			<button class="button" style="margin-top:-7px" type="button"
				onclick="window.location='<%=request.getContextPath() %>/empleados'">Volver</button>
		</div>
    </div>
    

</div>

<div id="remVacaciones" class="dialog" title="Confirmar">
	<p>Se elimininar&aacute;  del sistema. ¿Desea continuar?</p>
</div>


