<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


	<form:form method="POST" commandName="parametroDto">
		<form:hidden path="entityId"/> 
		<fieldset>
		
			<div class="tituloForm">
				<c:choose>
					<c:when test="${parametroDto.entityId == null}">
						<h3>Alta de Banco</h3>					
					</c:when>
					<c:otherwise>
						<h3>Edici&oacute;n de Banco</h3>
					</c:otherwise>
				</c:choose>
			</div>
	
			<div>
				<label for="parametro">Nombre:</label>
				<form:input  path="parametro"/>
				<form:errors path="parametro" cssClass="ui-state-error"/> 
			</div>

			
			<div class="footerForm">
				<button class="button" type="submit" >Guardar</button>

				<button class="button" type="button" onclick="window.location='<%=request.getContextPath() %>/bancos'">Cancelar</button>
			</div>
		</fieldset>

	</form:form>
