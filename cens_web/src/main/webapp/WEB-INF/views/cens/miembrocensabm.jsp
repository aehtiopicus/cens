<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script>var pagePath="<%=request.getContextPath() %>"</script>
<script src="<%=request.getContextPath() %>/js/cens/miembrocensabm.js"></script>

	<form:form  commandName="miembroCensDto" onsubmit="algo(this);" action="/miembro">
<%-- 		<form:hidden path="usuarioId"/>  --%>
		<fieldset>
			<c:if test="${id != null}">
				<form:hidden path="id" id="id"/>
				<form:hidden path="usuario.id" id="usuarioid"/>
			</c:if>			
			<div class="tituloForm">
				<c:choose>
					<c:when test="${id == null}">
						<h3>Alta de Miembro Cens</h3>					
					</c:when>
					<c:otherwise>
						<h3>Edici&oacute;n de Miembro Cens</h3>					
					</c:otherwise>
				</c:choose>
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
				<label for="dni">DNI:</label>
				<form:input  path="dni" class="entero" maxlength="8"/>
				<form:errors path="dni" cssClass="ui-state-error"/> 
			</div>
			<div>
				<label for="fechaNac">Fecha de Nacimiento:</label>
				<form:input  path="fechaNac" class="hasdatepicker"/>
				<form:errors path="fechaNac" cssClass="ui-state-error"/> 
			</div>			
			<c:choose>
			<c:when test="${id == null}">
				<div>
					<label for="usuario.username">Nombre de Usuario:</label>
					<form:input  path="usuario.username" id="username"/>
					<form:errors path="usuario.username" cssClass="ui-state-error"/> 
				</div>
				
			
				<div>
					<label for="usuario.password">Contraseña:</label>
					<form:password  path="usuario.password" id="password"/>
					<form:errors path="usuario.password" cssClass="ui-state-error"/> 
				</div>
				
				<div>
					<label for="usuario.passwordConfirm">Confirmar Contraseña:</label>
					<form:password  path="usuario.passwordConfirm" id="passwordConfirm"/>
					<form:errors path="usuario.passwordConfirm" cssClass="ui-state-error"/> 
				</div>
			</c:when>
			<c:otherwise>
				<form:hidden  path="usuario.username" id="username"/>	
				<form:hidden  path="usuario.password" id="password"/>		
			</c:otherwise>
			</c:choose>
			<div >
				<label for="">PERFILES:</label>
				
				<div style="display: inline-block;">
				<ul id="perfilList">
					<form:checkboxes items="${perfilDto}" path="usuario.perfil" id="perfilType" itemLabel="perfilType" element="li" itemValue="perfilType"/>
				</ul>
				</div>
			</div>
			
			
			<div class="footerForm">
				<button class="button" type="button" onclick="submitMiembro('<%=request.getContextPath() %>/miembro');" >Guardar</button>

				<button id ="cancelar" class="button" type="button" onclick="window.location='<%=request.getContextPath() %>/miembroList'">Cancelar</button>
			</div>
		</fieldset>

	</form:form>
