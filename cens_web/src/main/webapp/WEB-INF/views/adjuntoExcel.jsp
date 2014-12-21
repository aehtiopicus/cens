<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

	<form:form method="POST"  enctype="multipart/form-data"  commandName="empleadoExcelDto">
	
		<fieldset>
		
			
			<div> 
				<input   type="file"  name="archivo"/>
				<form:errors path="archivo" cssClass="ui-state-error"/> 
			</div>
				
			<div class="footerForm">
			
			 <c:if test="${empleadoExcelDto.error != null}">
			    <button  onclick="window.location.href='adjuntos/errores.txt'" type="button" class="button" title="Ver Informe">Ver Informe</button>
			 </c:if>
			 
			 
				<button class="button" type="submit" >Subir</button>

				<button class="button" type="button" onclick="window.location='<%=request.getContextPath() %>/empleados'">Cancelar</button>
				
				
			</div>
			
			
			
		</fieldset>			

	</form:form>
  