<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

	<form:form method="POST" commandName="beneficioClienteDto">
		<form:hidden path="clienteId"/> 
		<form:hidden path="beneficioClienteId"/> 
		<form:hidden path="habilitado"/> 
		
		<fieldset>
		
			<div class="tituloForm">
				<h3>Asignaci&oacute;n de Beneficio</h3>
			</div>
	
			<div>
				<label for="beneficio">Beneficio:</label>
				<form:select path="beneficioId">
				    <form:options items="${beneficiosDto}" itemValue="id" itemLabel="value"/>
				</form:select>
				<form:errors path="beneficioId" cssClass="ui-state-error"/> 
			</div>
			
			<div>
				<label for="tipo">Tipo:</label>
				<form:select path="tipo">
				    <form:options items="${tiposDto}" itemValue="id" itemLabel="value"/>
				</form:select>
				<form:errors path="tipo" cssClass="ui-state-error"/> 
			</div>		
	
			<div>
				<fmt:setLocale value="en_US" scope="session"/>
			 	<fmt:formatNumber type="number" pattern="###.##" maxFractionDigits="2" minFractionDigits="2"
			 	value="${beneficioClienteDto.valor}"  var="formatNumberValor" />
			
				<label for="valor">Valor:</label>
				<form:input  path="valor" cssClass='decimalConPunto' value="${formatNumberValor}"/>
				<form:errors path="valor" cssClass="ui-state-error"/> 
			</div>	
			
			<div class="footerForm">
				<button class="button" type="submit" >Guardar</button>

				<button class="button" type="button" onclick="window.location='<%=request.getContextPath() %>/clientes/beneficios/${beneficioClienteDto.clienteId}'">Cancelar</button>
			</div>
		</fieldset>

	</form:form>
