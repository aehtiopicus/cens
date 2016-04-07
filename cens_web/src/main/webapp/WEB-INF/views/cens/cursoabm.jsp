<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script>var pagePath="<%=request.getContextPath() %>"</script>
<script src="<%=request.getContextPath() %>/js/cens/cursoabm.js"></script>


		<fieldset>
			<c:if test="${id != null}">
				<input type="hidden" id ="id" value="${id}"/>							
			</c:if>			
			<div class="tituloForm">
				<c:choose>
					<c:when test="${id == null}">
						<h3>Alta de Crusos</h3>					
					</c:when>
					<c:otherwise>
						<h3>Edici&oacute;n de Cursos</h3>					
					</c:otherwise>
				</c:choose>
			</div>			

			<div>
				<label for="nombre">Nombre de curso:</label>
				<input type="text" id="nombre" placeholder="Ingrese Nombre del Curso"/>
			</div>				
			<div>
				<label for="yearCurso">A&ntilde;o:</label>
				<input type="text" id="yearCurso" maxlength="4" class="entero"/>
			</div>
			
			<div class="footerForm">
				<button class="button" type="button" onclick="submitCurso('<%=request.getContextPath() %>/curso');" >Guardar</button>

				<button id ="cancelar" class="button" type="button" onclick="window.location='<%=request.getContextPath() %>/mvc/cursoList'">Cancelar</button>
			</div>
		</fieldset>

