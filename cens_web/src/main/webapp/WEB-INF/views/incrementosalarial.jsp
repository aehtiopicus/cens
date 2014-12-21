<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

	<form:form method="POST" commandName="incrementoSalarialDto">
<%-- 		<form:hidden path="usuarioId"/>  --%>
		<fieldset>
		
			<div class="tituloForm">
				<h3>Incremento Salarial Masivo por Cliente</h3>
			</div>
	
			<div>
				<label for="cliente">Cliente:</label>
				<form:select path="clienteId">
				    <form:options items="${clientesDto}" itemValue="id" itemLabel="value"/>
				</form:select>
				<form:errors path="clienteId" cssClass="ui-state-error"/> 
			</div>	
			
			<div>
				<label for="tipo">Tipo de Incremento:</label>
				<form:select path="tipoIncremento">
				    <form:options items="${tiposDto}" itemValue="id" itemLabel="value"/>
				</form:select>
				<form:errors path="tipoIncremento" cssClass="ui-state-error"/> 
			</div>
			
			<div>
				<fmt:setLocale value="en_US" scope="session"/>
			 	<fmt:formatNumber type="number" pattern="###.##" maxFractionDigits="2" minFractionDigits="2"
			 	value="${incrementoSalarialDto.incrementoBasico}"  var="formatNumberBasico" />
			 	
				<label for="incrementoBasico">Inc. en B&aacute;sico:</label>
				<form:input  path="incrementoBasico" class="decimalConPunto" value="${formatNumberBasico}" />
				<form:errors path="incrementoBasico" cssClass="ui-state-error"/> 
			</div>

			<fmt:setLocale value="en_US" scope="session"/>
			 	<fmt:formatNumber type="number" pattern="###.##" maxFractionDigits="2" minFractionDigits="2"
			 	value="${incrementoSalarialDto.incrementoPresentismo}"  var="formatNumberPresentismo" />
			<div>
				<label for="incrementoPresentismo">Inc. en Presentismo:</label>
				<form:input  path="incrementoPresentismo"  class="decimalConPunto"  value="${formatNumberPresentismo}"/>
				<form:errors path="incrementoPresentismo" cssClass="ui-state-error"/> 
			</div>
			
			<div>
				<label for="fechaInicio">Fecha de Inicio:</label>
				<form:input  path="fechaInicio"  class="datepicker" />
				<form:errors path="fechaInicio" cssClass="ui-state-error"/> 
				<span class=ui-state-hint>No se aplicará el incremento a los empleados que tengan una actualización de sueldo con fecha posterior a la que se indique acá.</span>
			</div>
			
			<div class="footerForm">
				<button class="button" type="submit" >Aplicar Incremento</button>
			</div>
		</fieldset>

	</form:form>
