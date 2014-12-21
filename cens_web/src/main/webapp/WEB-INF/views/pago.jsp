<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
    <script src="<%=request.getContextPath() %>/js/pago.js?v=<%= new Date().getTime()%>"></script>
	

		<fieldset>
			<input type="hidden" id="periodo" value="${periodo}">
			<div class="tituloForm">
				<h3>Pagos</h3>
			</div>
			<div>
				<label>Fecha de Acreditaci�n:</label>
				<input  class="datepicker" id="fecha"  />
			</div>
			<div>
				<label>Concepto:</label>
				<input type="text" id="concepto"/>
			</div>
			
			<div class="footerForm">
				<button class="button" type="button" onclick="exportar()" >Descargar Archivos</button>
				
				<button class="button" type="button" onclick="window.location='<%=request.getContextPath() %>/administracion/informesconsolidados'">Cancelar</button>
			</div>
	
		<div class="messageDiv">
        	<label id="message" style="width:100%"></label>
        </div>
			
		</fieldset>

<div id="empleadoSinBancoDialog" class="dialog" title="Atenci�n">
	<p>Los siguientes empleados no tienen un banco asignado.</p> 
	<div id="empleadoSinBanco"></div>
	<p>Si contin�a no se generar� archivo de pago para los empleados listados arriba. �Desea continuar?</p>
</div>

<div id="spiner">
	<div class="spiner_image"></div>
	<span class="spiner_text">Verificando informaci�n...</span>
	<span class="spiner_text2">Finalizado, Volviendo a pantalla principal.</span>
</div>