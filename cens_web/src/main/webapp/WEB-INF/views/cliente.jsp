<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

	<form:form method="POST" commandName="clienteDto">
		<form:hidden path="clienteId"/> 
		<fieldset>
		
			<div class="tituloForm">
				<c:choose>
					<c:when test="${clienteDto.clienteId == null}">
						<h3>Alta de Cliente</h3>					
					</c:when>
					<c:otherwise>
						<h3>Edici&oacute;n de Cliente</h3>					
					</c:otherwise>
				</c:choose>
			</div>
			
			<div> <!-- corregir -->
				<label for="nombre">Cliente:</label>
				<form:input  path="nombre"/>
				<form:errors path="nombre" cssClass="ui-state-error"/> 
			</div>
			
			<div>
				<label for="razon_social">Razón Social</label>
				<form:input  path="razonSocial"/>
				<form:errors path="razonSocial" cssClass="ui-state-error"/> 
			</div>
			
			<div>
				<label for="usuario">Gerente Responsable:</label>
				<form:select path="usuarioGerenteOperadorId">
				    <form:options items="${gerentesDto}" itemValue="id" itemLabel="value"/>
				</form:select>		
				<form:errors path="usuarioGerenteOperadorId" cssClass="ui-state-error"/> 		
			</div>	
			
			<div>
				<label for="usuario">Jefe de Operaci&oacute;n:</label>
				<form:select path="usuarioJefeOperadorId">
					<form:option value=""></form:option>
				    <form:options items="${jefesDto}" itemValue="id" itemLabel="value"/>
				</form:select>		
			</div>	
			
			<div>
				<label for="email">E-Mail:</label>
				<form:input  path="email"/>
				<form:errors path="email" cssClass="ui-state-error"/> 
			</div>
		
			<div>
				<label for="fecha_alta">Fecha Alta:</label>
				<form:input  path="fecha_alta" class="datepicker"/>
				<form:errors path="fecha_alta" cssClass="ui-state-error"/> 
			</div>	
			
			<div>
				<label for="fecha_baja">Fecha Baja:</label>
				<form:input  path="fecha_baja" class="datepicker"/>
				<form:errors path="fecha_baja" cssClass="ui-state-error"/> 
			</div>	
			
			<div>
				<label for="direccion">Dirección:</label>
				<form:input  path="direccion"/>
				<form:errors path="direccion" cssClass="ui-state-error"/> 
			</div>				
			
			<div>
				<label for="telefono">Teléfono:</label>
				<form:input  path="telefono" class="entero"/>
				<form:errors path="telefono" cssClass="ui-state-error"/> 
			</div>	
			
			<div>
				<label for="nombre_contacto">Nombre Contacto:</label>
				<form:input  path="nombre_contacto"/>
				<form:errors path="nombre_contacto" cssClass="ui-state-error"/> 
			</div>	
			
			<div>
				<label for="nombre_contacto">E-Mail Contacto:</label>
				<form:input  path="emailContacto"/>
				<form:errors path="emailContacto" cssClass="ui-state-error"/> 
			</div>	
			
			<div>
				<label for="hsExtrasConPresentismo">Hs extras con presentismo:</label>
				<form:checkbox  path="hsExtrasConPresentismo"/>
			</div>
			
			<div class="footerForm">
				<button class="button" type="submit" >Guardar</button>

				<button class="button" type="button" onclick="window.location='<%=request.getContextPath() %>/clientes'">Cancelar</button>
			</div>	
		</fieldset>			

	</form:form>
  <script>
    $(document).ready(function(){
        $(".entero").numeric();
        $(".decimal").numeric(","); 
    })
  </script>