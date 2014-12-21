<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

	<form:form method="POST" commandName="resetPasswordDto">
		<form:hidden path="usuarioId"/> 

		<fieldset>
			<div class="tituloForm">
				<h3>Resetear Contraseña</h3>
			</div>
	
			<div>
				<label for="username">Username:</label>
				<form:input  path="username" disabled="true"/>
			</div>

			<div>
				<label for="passwordNuevo">Nueva contraseña:</label>
				<form:password  path="passwordNuevo"/>
				<form:errors path="passwordNuevo" cssClass="ui-state-error"/> 
			</div>
			
			<div>
				<label for="passwordNuevoConfirmation">Confirmar contraseña:</label>
				<form:password  path="passwordNuevoConfirmation"/>
				<form:errors path="passwordNuevoConfirmation" cssClass="ui-state-error"/> 
			</div>
			
			<div class="footerForm">
				<button class="button" type="submit" >Guardar</button>
				<button class="button" type="button" onclick="javascript:history.go(-1);">Cancelar</button>
			</div>
		</fieldset>

	</form:form>
