<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

	<form:form method="POST" commandName="informeMensualHeaderNuevoDto">
<%-- 		<form:hidden path="usuarioId"/>  --%>
		<fieldset>
		
			<div class="tituloForm">
				<h3>Alta Informe Mensual</h3>
			</div>
	
			<div>
				<label for="periodo">Periodo:</label>
				<form:select path="periodo">
				    <form:options items="${periodosDto}"/>
				</form:select>
				<form:errors path="periodo" cssClass="ui-state-error"/> 
			</div>	
			
			<div>
				<label for="cliente">Cliente:</label>
				<form:select path="clienteId">
				    <form:options items="${clientesDto}" itemValue="id" itemLabel="value"/>
				</form:select>
				<form:errors path="clienteId" cssClass="ui-state-error"/> 
			</div>	
			<div>
			   <label for="usarInformeAnterior">Usar informe anterior:</label>
			   <form:checkbox path="usarInformeAnterior" /> 				  
			</div>
			
			<div class="footerForm">
				<button class="button" type="submit" >Continuar</button>

				<button class="button" type="button" onclick="window.location='<%=request.getContextPath() %>/operacion/informesmensuales'">Cancelar</button>
			</div>
		</fieldset>

	</form:form>
