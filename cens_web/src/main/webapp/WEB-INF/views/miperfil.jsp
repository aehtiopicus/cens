<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

	<form:form method="POST" commandName="miPerfilDto">
		<fieldset>
		
			<div class="tituloForm">
				<h3>Mis Datos</h3>
			</div>
	
			<div>
				<label for="username">Username:</label>
				<form:input  path="username" disabled="true"/>
			</div>

			<div>
				<label for="nombre">Nombre:</label>
				<form:input  path="nombre"/>
				<form:errors path="nombre" cssClass="ui-state-error"/> 
			</div>

			<div>
				<label for="apellido">Apellido:</label>
				<form:input  path="apellido"/>
				<form:errors path="apellido" cssClass="ui-state-error"/> 
			</div>	
			
			<div>
				<label for="email">E-Mail:</label>
				<form:input  path="email"/>
				<form:errors path="email" cssClass="ui-state-error"/> 
			</div>		
			
			
			<div class="footerForm">
				<button class="button" type="submit" >Guardar</button>
				<button class="button" type="button" onclick="javascript:history.go(-1);">Cancelar</button>
			</div>
		</fieldset>

	</form:form>
