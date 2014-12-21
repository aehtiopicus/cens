<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

	<form:form method="POST" commandName="relacionLaboralDto">
		<form:hidden path="relacionLaboralId"/> 

		<fieldset>
		
			<div class="tituloForm">
				<h3>Asignaci&oacute;n de Empleado</h3>
			</div>
	
			<div>
				<label for="empleado">Empleado:</label>
				<form:input  path="nombreEmpleado" disabled="true"/>
			</div>
			
			<div>
				<label for="cliente">Cliente:</label>
				<form:input  path="razonSocialCliente" disabled="true"/>
			</div>

			<div>
				<label for="fechaInicio">Fecha Inicio:</label>
				<form:input  path="fechaInicio" disabled="true"/>

			</div>

			<div>
				<label for="fechaFin">Fecha Fin:</label>
				<form:input  path="fechaFin" class="datepicker"/>
				<form:errors path="fechaFin" cssClass="ui-state-error"/> 
			</div>
			
			<div class="footerForm">
				<button class="button" type="submit" >Guardar</button>

				<button class="button" type="button" onclick="window.location='<%=request.getContextPath() %>/asignacionempleados'">Cancelar</button>
			</div>
		</fieldset>

	</form:form>
