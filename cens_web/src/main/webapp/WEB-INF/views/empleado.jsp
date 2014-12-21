<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

	<form:form method="POST" commandName="empleadoDto">
		<form:hidden path="empleadoId" id="empleadoId"/> 
		<fieldset>
		
			<div class="tituloForm">
				<c:choose>
					<c:when test="${empleadoDto.empleadoId == null}">
						<h3>Alta de Empleado</h3>					
					</c:when>
					<c:otherwise>
						<h3>Edici&oacute;n de Empleado</h3>					
					</c:otherwise>
				</c:choose>
			</div>
	
			<c:if test="${empleadoDto.empleadoId != null}">
			<div> 
				<label for="legajo">Legajo:</label>
				<form:input  path="legajo" disabled="true"/>
			</div>
			
			</c:if>

			<div>
				<label for="fechaIngresoNovatium">Fecha Ingreso Novatium:</label>
				<form:input class="datepicker" path="fechaIngresoNovatium" />
				<form:errors path="fechaIngresoNovatium" cssClass="ui-state-error"/> 
			</div>
	
			<div>
				<label for="fechaEgresoNovatium">Fecha Egreso Novatium:</label>
				<form:input class="datepicker"  path="fechaEgresoNovatium" />
				<form:errors path="fechaEgresoNovatium" cssClass="ui-state-error"/> 
			</div>

			<div>
				<label for="motivoBaja">Motivo Baja:</label>
				<form:select path="motivoBajaId">
				    <form:options items="${motivoBajaDto}"  itemValue="id" itemLabel="value"/>
				</form:select>
			</div>	

			<div>
				<label for="codigoContratacion">C&oacute;digo Contrataci&oacute;n:</label>
				<form:select path="codigoContratacion">
					<form:option value=""></form:option>
					<form:option value="8">8</form:option>
					<form:option value="14">14</form:option>
					<form:option value="22">22</form:option>
					<form:option value="32">32</form:option>
					<form:option value="38">38</form:option>
					<form:option value="99">99</form:option>
					<form:option value="201">201</form:option>
					<form:option value="204">204</form:option>
				</form:select>
			</div>

			<div>
				<label for="apellidos">Apellidos:</label>
				<form:input  path="apellidos"/>
				<form:errors path="apellidos" cssClass="ui-state-error"/> 
			</div>

			<div>
				<label for="nombres">Nombres:</label>
				<form:input  path="nombres"/>
				<form:errors path="nombres" cssClass="ui-state-error"/> 
			</div>	
			
			<div>
				<label for="dni">DNI:</label>
				<form:input  path="dni" class="entero"/>
				<form:errors path="dni" cssClass="ui-state-error"/> 
			</div>	
			
			<div>
				<label for="cuil">CUIL:</label>
				<form:input  path="cuil" class="entero"/>
				<form:errors path="cuil" cssClass="ui-state-error"/> 
			</div>		
			<div>
				<label for="fechaNacimiento">Fecha de Nacimiento:</label>
				<form:input class="datepicker"   path="fechaNacimiento" />
				<form:errors path="fechaNacimiento" cssClass="ui-state-error"/> 
			</div>
			<div>
				<label for="estadoCivil">Estado CivilL:</label>
				<form:select path="estadoCivil">
				    <form:options items="${estadoCivilDto}"  itemValue="value" itemLabel="value"/>
				</form:select>
			</div>	
						
			<div>
				<label for="nacionalidadId">Nacionalidad:</label>
				<form:select path="nacionalidadId">
				    <form:options items="${nacionalidadDto}" itemValue="id"  itemLabel="value"/>
				</form:select>
			</div>
			<div>
				<label for="domicilio">Domicilio:</label>
				<form:input  path="domicilio" />
				<form:errors path="domicilio" cssClass="ui-state-error"/> 
			</div>
			<div>
				<label for="telefonoFijo">Teléfono Fijo:</label>
				<form:input  path="telefonoFijo" class="entero"/>
				<form:errors path="telefonoFijo" cssClass="ui-state-error"/> 
			</div>
			<div>
				<label for="celular">Celular:</label>
				<form:input path="celular" class="entero"/>
				<form:errors path="celular" cssClass="ui-state-error"/> 
			</div>
			<div>
				<label for="telefonoUrgencias">Teléfono Urgencias:</label>
				<form:input  path="telefonoUrgencias" class="entero"/>
				<form:errors path="telefonoUrgencias" cssClass="ui-state-error"/> 
			</div>
			<div>
				<label for="contactoParaUrgencias">Contacto para Urgencias:</label>
				<form:input  path="contactoParaUrgencias"/>
				<form:errors path="contactoParaUrgencias" cssClass="ui-state-error"/> 
			</div>
			
			<div>
				<label for="emailPersonal">E-Mail Personal:</label>
				<form:input  path="emailPersonal"/>
				<form:errors path="emailPersonal" cssClass="ui-state-error"/> 
			</div>	
			<div>
				<label for="emailNovatium">E-Mail Novatium:</label>
				<form:input  path="emailNovatium"/>
				<form:errors path="emailNovatium" cssClass="ui-state-error"/> 
			</div>		
			<div>
				<label for="emailEmpresa">E-Mail Empresa:</label>
				<form:input  path="emailEmpresa"/>
				<form:errors path="emailEmpresa" cssClass="ui-state-error"/> 
			</div>		
			<div>
				<label for="obraSocial">Obra Social:</label>
				<form:select path="obraSocialId">
				    <form:options items="${obraSocialDto}" itemValue="id" itemLabel="value"/>
				</form:select>
			</div>		
			<div>
				<label for="prepaga">Prepaga:</label>
				<form:select path="prepagaId">
				    <form:options items="${prepagaDto}" itemValue="id" itemLabel="value"/>
				</form:select>
			</div>		
			<div>
				<label for="capitas">C&aacute;pitas:</label>
				<form:select path="capitas">
					<form:option value="1">1</form:option>
					<form:option value="2">2</form:option>
					<form:option value="3">3</form:option>
					<form:option value="4">4</form:option>
					<form:option value="5">5</form:option>
					<form:option value="6">6</form:option>
					<form:option value="7">7</form:option>
					<form:option value="8">8</form:option>
					<form:option value="9">9</form:option>
					<form:option value="10">10</form:option>
				</form:select>
			</div>
			
			<div>
			   <label for="chomba">Chomba:</label>
			   <form:checkbox path="chomba"  /> 				  
			</div>
			<div>
			   <label for="mochila">Mochila:</label>
			   <form:checkbox path="mochila" /> 				  
			</div>	
			<div>
				<label for="fechaPreocupacional">Fecha Preocupacional:</label>
				<form:input class="datepicker"   path="fechaPreocupacional"/>
				<form:errors path="fechaPreocupacional" cssClass="ui-state-error"/> 
			</div>
			<div>
				<label for="resultadoPreocupacional">Resultado Preocupacional:</label>
				<form:input  path="resultadoPreocupacional" />
				<form:errors path="resultadoPreocupacional" cssClass="ui-state-error"/> 
			</div>
	    	<div>
				<label for="banco">Banco:</label>
				<form:select path="bancoId">
				    <form:options items="${bancoDto}" itemValue="id" itemLabel="value"/>
				</form:select>
				<form:errors path="bancoId" cssClass="ui-state-error"/> 
			</div>
			<div>
				<label for="sucursal">Sucursal:</label>
				<form:input  path="sucursal" />
				<form:errors path="sucursal" cssClass="ui-state-error"/> 
			</div>
			<div>
				<label for="nroCuenta">Nro de Cuenta:</label>
				<form:input  path="nroCuenta"  class="entero"/>
				<form:errors path="nroCuenta" cssClass="ui-state-error"/> 
			</div>	
			<div>
				<label for="convenio">Convenio:</label>
				<form:input  path="convenio"/>
				<form:errors path="convenio" cssClass="ui-state-error"/> 
			</div>
			<div>
				<label for="cbu">CBU:</label>
				<form:input  path="cbu" class="entero"/>
				<form:errors path="cbu" cssClass="ui-state-error"/> 
			</div>	
			
			
			
	<c:choose>
		<c:when test="${empleadoId == null}">
			<div>
				<label for="cliente">Cliente:</label>
				<form:select path="clienteId" class="chosen-select">
					<form:option value=""></form:option>
				    <form:options items="${clientesDto}" itemValue="id" itemLabel="value"/>
				</form:select>
				<form:errors path="clienteId" cssClass="ui-state-error"/> 
			</div>

			<div>
				<label for="puesto">Puesto:</label>
				
			<form:select path="puestoId" class="chosen-select" >
					<form:option value=""></form:option>
				    <form:options items="${puestosDto}" itemValue="id" itemLabel="value"/>
				</form:select>
				<form:errors path="puestoId" cssClass="ui-state-error"/> 
			</div>
			<div>
				<label for="fechaInicio">Fecha Inicio:</label>
				<form:input  path="fechaInicio" class="datepicker"/>
				<form:errors path="fechaInicio" cssClass="ui-state-error"/> 
			</div>
		
			<div>
				<fmt:setLocale value="en_US" scope="session"/>
			 	<fmt:formatNumber type="number" pattern="###.##" maxFractionDigits="2" minFractionDigits="2"
			 	value="${empleadoDto.basico}"  var="formatNumberBasico" />
			 	
				<label for="basico">B&aacute;sico:</label>
				<form:input  path="basico" class="decimalConPunto" value="${formatNumberBasico}" />
				<form:errors path="basico" cssClass="ui-state-error"/> 
			</div>

			<fmt:setLocale value="en_US" scope="session"/>
			 	<fmt:formatNumber type="number" pattern="###.##" maxFractionDigits="2" minFractionDigits="2"
			 	value="${empleadoDto.presentismo}"  var="formatNumberPresentismo" />
			<div>
				<label for="presentismo">Presentismo:</label>
				<form:input  path="presentismo"  class="decimalConPunto"  value="${formatNumberPresentismo}"/>
				<form:errors path="presentismo" cssClass="ui-state-error"/> 
			</div>
		
		</c:when>
		<c:when test="${empleadoId != null}">
      	<div>
				<label for="cliente">Cliente:</label>
				<form:select path="clienteId" class="chosen-select" disabled="true">
					<form:option value=""></form:option>
				    <form:options items="${clientesDto}" itemValue="id" itemLabel="value"/>
				</form:select>
				<form:errors path="clienteId" cssClass="ui-state-error"/> 
			</div>

			<div>
				<label for="puesto">Puesto:</label>
				
			<form:select path="puestoId" class="chosen-select" disabled="true">
					<form:option value=""></form:option>
				    <form:options items="${puestosDto}" itemValue="id" itemLabel="value"/>
				</form:select>
				<form:errors path="puestoId" cssClass="ui-state-error"/> 
			</div>
			<div>
				<label for="fechaInicio">Fecha Inicio:</label>
				<form:input  path="fechaInicio" class="datepicker" disabled="true"/>
				<form:errors path="fechaInicio" cssClass="ui-state-error"/> 
			</div>
      
    	</c:when>
	</c:choose>	
					
		
			<div>
				<label for="observaciones">Observaciones:</label>
				<form:textarea  path="observaciones"/>
				<form:errors path="observaciones" cssClass="ui-state-error"/> 
			</div>	
			<div class="footerForm">
				<button class="button" type="submit" >Guardar</button>

				<button class="button" type="button" onclick="window.location='<%=request.getContextPath() %>/empleados'">Cancelar</button>
			</div>
			<div >
			 	<c:if test="${success == true}"></c:if>
		        	<label id="message" class="msgError"style="width: 100%"> ${message}</label>
		       
		        <c:if test="${success == false}">
		        	<label id="message"></label>
		        </c:if>
         </div>
		</fieldset>
	 	
	</form:form>
