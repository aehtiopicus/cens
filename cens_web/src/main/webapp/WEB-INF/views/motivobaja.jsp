<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


	<form:form method="POST" commandName="motivoBajaDto">
		<form:hidden path="motivoBajaId"/> 
		<fieldset>
		
			<div class="tituloForm">
				<c:choose>
					<c:when test="${motivoBajaDto.motivoBajaId == null}">
						<h3>Alta de Motivo de Baja</h3>					
					</c:when>
					<c:otherwise>
						<h3>Edici&oacute;n de Motivo de Baja</h3>
					</c:otherwise>
				</c:choose>
			</div>
	
			<div>
				<label for="motivo">Motivo:</label>
				<form:input  path="motivo"/>
				<form:errors path="motivo" cssClass="ui-state-error"/> 
			</div>
			<div>
				<label for="articuloLct">Nro Art&iacute;culo LCT:</label>
				<form:input  path="articuloLct"/>
				<form:errors path="articuloLct" cssClass="ui-state-error"/> 
			</div>

			
			<div class="footerForm">
				<button class="button" type="submit" >Guardar</button>

				<button class="button" type="button" onclick="window.location='<%=request.getContextPath() %>/motivosbaja'">Cancelar</button>
			</div>
		</fieldset>

	</form:form>
