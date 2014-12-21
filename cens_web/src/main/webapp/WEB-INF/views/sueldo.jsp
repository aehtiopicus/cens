<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
	<form:form method="POST" commandName="sueldoDto">
		<form:hidden path="sueldoId"/> 
		<form:hidden path="empleadoId"/> 
		<fieldset>
		
			<div class="tituloForm">
				<h3>Alta de Sueldo</h3>
			</div>
			<div>
				<label for="empleado">Empleado:</label>
				<form:input readonly="true" disabled="true" path="empleado" />
			</div>
				
			<div>
				<fmt:setLocale value="en_US" scope="session"/>
			 	<fmt:formatNumber type="number" pattern="###.##" maxFractionDigits="2" minFractionDigits="2"
			 	value="${sueldoDto.basico}"  var="formatNumberBasico" />
			 	
				<label for="basico">B&aacute;sico:</label>
				<form:input  path="basico" class="decimalConPunto" value="${formatNumberBasico}" />
				<form:errors path="basico" cssClass="ui-state-error"/> 
			</div>

			<fmt:setLocale value="en_US" scope="session"/>
			 	<fmt:formatNumber type="number" pattern="###.##" maxFractionDigits="2" minFractionDigits="2"
			 	value="${sueldoDto.presentismo}"  var="formatNumberPresentismo" />
			<div>
				<label for="presentismo">Presentismo:</label>
				<form:input  path="presentismo"  class="decimalConPunto"  value="${formatNumberPresentismo}"/>
				<form:errors path="presentismo" cssClass="ui-state-error"/> 
			</div>
			
			<div>
				<label for="fechaInicio">Fecha de Inicio:</label>
				<form:input  path="fechaInicio"  class="datepicker" />
				<form:errors path="fechaInicio" cssClass="ui-state-error"/> 
			</div>

		
			<div class="footerForm">
				<button class="button" type="submit" >Guardar</button>

				<button class="button" type="button" onclick="window.location='<%=request.getContextPath() %>/empleados'">Cancelar</button>
			</div>
		</fieldset>

	</form:form>

<script>
 
   
   </script>


