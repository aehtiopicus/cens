<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

	<form:form method="POST" commandName="usuarioDto">
		<form:hidden path="usuarioId"/> 
		<fieldset>
		
			<div class="tituloForm">
				<c:choose>
					<c:when test="${usuarioDto.usuarioId == null}">
						<h3>Alta de Usuario</h3>					
					</c:when>
					<c:otherwise>
						<h3>Edici&oacute;n de Usuario</h3>					
					</c:otherwise>
				</c:choose>
			</div>
	
			<div>
				<label for="username">Username:</label>
				<form:input  path="username"/>
				<form:errors path="username" cssClass="ui-state-error"/> 
			</div>
				
			<c:if test="${usuarioDto.usuarioId == null}">
				<div>
					<label for="password">Password:</label>
					<form:password  path="password"/>
					<form:errors path="password" cssClass="ui-state-error"/> 
				</div>
				
				<div>
					<label for="passwordConfirm">Confirmar Password:</label>
					<form:password  path="passwordConfirmation"/>
					<form:errors path="passwordConfirmation" cssClass="ui-state-error"/> 
				</div>
			</c:if>

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
			
			<div>
				<label for="perfil">Perfil:</label>
				<form:select path="perfilId">
					<option></option>
				    <form:options items="${perfilesDto}" itemValue="id" itemLabel="value"/>
				</form:select>
				<form:errors path="perfilId" cssClass="ui-state-error"/> 
			</div>		
			
			<div class="footerForm">
				<button class="button" type="submit" >Guardar</button>

				<button class="button" type="button" onclick="window.location='<%=request.getContextPath() %>/usuarios'">Cancelar</button>
			</div>
		</fieldset>

	</form:form>
