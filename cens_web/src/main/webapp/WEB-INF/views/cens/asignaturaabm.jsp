<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script>var pagePath="<%=request.getContextPath() %>"</script>
<script src="<%=request.getContextPath() %>/js/cens/asignaturaabm.js"></script>


		<fieldset>
			<c:if test="${id != null}">
				<input type="hidden" id ="id" value="${id}"/>							
			</c:if>			
			<div class="tituloForm">
				<c:choose>
					<c:when test="${id == null}">
						<h3>Alta de Asignaturas</h3>					
					</c:when>
					<c:otherwise>
						<h3>Edici&oacute;n de Asignaturas</h3>					
					</c:otherwise>
				</c:choose>
			</div>			

			<div>
				<label for="nombre">Nombre:</label>
				<input type="text" id="nombre"/>
			</div>
			<div>
				<label for="modalidad">Modalidad:</label>
				<input type="text" id="modalidad"/>
			</div>
			<div>
				<label for="horarios">Horarios:</label>
				<input type="text" id="horarios"/>
			</div>					
			<div>
				<label for="profesor">Profesor:</label>
				<input type="text" id="profesor"/>
			</div>	
			<div>
				<label for="profesorsuplente">Profesor Suplente:</label>
				<input type="text" id="profesorsuplente"/>
			</div>	
			
			<div>
				<label for="curso">Curso:</label>
				<input type="text" id="curso"/>
			</div>
			<input type="hidden" id ="profesorId" />
			<input type="hidden" id ="profesorSuplenteId" />
			<input type="hidden" id ="cursoId" />
			<input type="hidden" id ="profesorName" />
			<input type="hidden" id ="profesorSuplenteName" />
			<input type="hidden" id ="cursoName" />
			<div class="footerForm">
				<button class="button" type="button" onclick="submitCurso('<%=request.getContextPath() %>/asignatura');" >Guardar</button>

				<button id ="cancelar" class="button" type="button" onclick="window.location='<%=request.getContextPath() %>/asignaturaList'">Cancelar</button>
			</div>
		</fieldset>

