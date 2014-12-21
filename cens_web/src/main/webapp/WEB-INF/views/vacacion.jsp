<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

	<form:form method="POST" commandName="vacacionesDto">
		<form:hidden path="vacacionesId"/> 
		<form:hidden path="empleadoId"/> 
		<fieldset>
		
			<div class="tituloForm">
				<c:choose>
					<c:when test="${vacacionesDto.vacacionesId == null}">
						<h3>Alta de Vacaciones</h3>					
					</c:when>
					<c:otherwise>
						<h3>Edici&oacute;n de Vacaciones</h3>					
					</c:otherwise>
				</c:choose>
			</div>
			<div>
				<label for="fechaInicio">Fecha de Inicio:</label>
				<form:input  path="fechaInicio"  class="datepicker_vac" />
				<form:errors path="fechaInicio" cssClass="ui-state-error"/> 
			</div>
			<div>
				<label for="fechaFin">Fecha de Fin:</label>
				<form:input  path="fechaFin"  class="datepicker_vac" />
				<form:errors path="fechaFin" cssClass="ui-state-error"/> 
			</div>

			<div>
				<label for="observaciones">Observaciones:</label>
				<form:textarea  path="observaciones"/>
				<form:errors path="observaciones" cssClass="ui-state-error"/> 
			</div>	
			<div class="footerForm">
				<button class="button" type="submit" >Guardar</button>
          
				<button class="button" type="button" onclick="window.location='<%=request.getContextPath() %>/empleados/vacaciones/${vacacionesDto.empleadoId}'">Cancelar</button>
			</div>
		</fieldset>

	</form:form>

<script>
    $(document).ready(function(){
        $(".entero").numeric();
    
        $(".datepicker_vac").datepicker({
			inline: true,
			yearRange: "-15:+3",
			changeMonth: true,
		    changeYear: true
		});
   });
   function getVacaciones(){
    	var empleadoId = $('#empleadoId').val();
   		var url = window.location;
   		url.replace("?")
    	window.location =window.location.href+'vacaciones/'+empleadoId;
    	
    }
  </script>
