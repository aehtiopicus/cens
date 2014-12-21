<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>



	<form:form method="POST" commandName="relacionLaboralDto">
		<form:hidden path="relacionLaboralId"/> 
		<form:hidden path="empleadoId"/> 
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
				<form:select path="clienteId" class="chosen-select">
					<form:option value=""></form:option>
				    <form:options items="${clientesDto}" itemValue="id" itemLabel="value"/>
				</form:select>
				<form:errors path="clienteId" cssClass="ui-state-error"/> 
			</div>

			<div>
				<label for="puesto">Puesto:</label>
				
				<form:select path="puestoId" class="chosen-select" >
					<form:option value=""></form:option>
				    <form:options items="${puestosDto}" itemValue="id" itemLabel="value"/>
				</form:select>
				<form:errors path="puestoId" cssClass="ui-state-error"/> 
			</div>

			<div>
				<label for="mailEmpresa">Mail Empresa:</label>
				<form:input  path="mailEmpresa"/>
				<form:errors path="mailEmpresa" cssClass="ui-state-error"/> 
			</div>

			<div>
				<label for="fechaInicio">Fecha Inicio:</label>
				<form:input  path="fechaInicio" class="datepicker"/>
				<form:errors path="fechaInicio" cssClass="ui-state-error"/> 
			</div>
			
			<div class="footerForm">
				<button class="button" type="submit" >Guardar</button>

				<button class="button" type="button" onclick="window.location='<%=request.getContextPath() %>/asignacionempleados'">Cancelar</button>
			</div>
		</fieldset>

	</form:form>
