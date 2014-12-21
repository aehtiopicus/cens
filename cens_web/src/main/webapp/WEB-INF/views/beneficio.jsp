<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


	<form:form method="POST" commandName="beneficioDto">
		<form:hidden path="beneficioId"/> 
		<fieldset>
		
			<div class="tituloForm">
				<c:choose>
					<c:when test="${beneficioDto.beneficioId == null}">
						<h3>Alta de Beneficio</h3>					
					</c:when>
					<c:otherwise>
						<h3>Edici&oacute;n de Beneficio</h3>
					</c:otherwise>
				</c:choose>
			</div>
	
			<div>
				<label for="titulo">T&iacute;tulo:</label>
				<form:input  path="titulo"/>
				<form:errors path="titulo" cssClass="ui-state-error"/> 
			</div>

			<div>
				<label for="descripcion">Descripci&oacute;n:</label>
				<form:input  path="descripcion"/>
				<form:errors path="descripcion" cssClass="ui-state-error"/> 
			</div>

			<div>
				<label for="remunerativo">Remunerativo:</label>
				<form:select path="remunerativo">
					<form:option value="true">Si</form:option>
					<form:option value="false">No</form:option>
				</form:select>
			</div>
			
			<div class="footerForm">
				<button class="button" type="submit" >Guardar</button>

				<button class="button" type="button" onclick="window.location='<%=request.getContextPath() %>/beneficios'">Cancelar</button>
			</div>
		</fieldset>

	</form:form>
