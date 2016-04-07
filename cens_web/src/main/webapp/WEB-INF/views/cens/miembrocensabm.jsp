<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script>var pagePath="<%=request.getContextPath() %>"</script>
<script src="<%=request.getContextPath() %>/js/cens/miembrocensabm.js"></script>

	<form:form  commandName="miembroCensDto" onsubmit="algo(this);" action="/miembro">
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
				<input type="text" id="nombre" placeholder="Ingrese Nombre"/>
			</div>

			<div>
				<label for="apellido">Apellido:</label>
				<input type="text"  id="apellido" placeholder="Ingrese Apellido"/>
			</div>	
			
			<div>
				<label for="dni">DNI:</label>
				<form:input  path="dni" class="entero" maxlength="8" placeholder="Ingrese DNI sin puntos, ni comas"/>
			</div>
			<div>
				<label for="fechaNac">Fecha de Nacimiento:</label>
				<form:input  path="fechaNac" class="hasdatepicker" maxlength="10" placeholder ="Ingrese Fecha de Nacimiento"/>
			</div>			
			<c:choose>
			<c:when test="${id == null}">
				<div>
					<label for="usuario.username">Nombre de Usuario:</label>
					<form:input  path="usuario.username" id="username" placeholder =" Ingrese nombre de usuario"/>
				</div>											
			</c:when>
			<c:otherwise>
				<input type="hidden" id="username"/> 
			</c:otherwise>
			</c:choose>
			<div >
				<label for="">PERFILES:</label>
				
				<div style="display: inline-block;">
				<ul id="perfilList">
					<form:checkboxes items="${perfilDto}" path="usuario.perfil" id="perfilType" itemLabel="perfilType" element="li" itemValue="perfilType"/>
				</ul>
				</div>
				<input type="hidden" id="perfil"/>
			</div>
			
			
			<div class="footerForm">
				<button class="button" type="button" onclick="submitMiembro();" >Guardar</button>

				<button id ="cancelar" class="button" type="button" onclick="window.location='<%=request.getContextPath() %>/mvc/miembroList'">Cancelar</button>
			</div>
			<input type="hidden" id="profesorId"/>
		</fieldset>

	</form:form>
<div id="remAsignaturas" class="dialog" title="Confirmar">
	<p>El profesor tiene materias asignadas. ¿Desea eliminarlas?</p>
</div>