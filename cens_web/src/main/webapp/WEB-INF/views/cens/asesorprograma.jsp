<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="<%=request.getContextPath()%>/js/jquery.fileupload.js"></script>
<script src="<%=request.getContextPath()%>/js/cens/asesorprograma.js"></script>
		<script src="<%=request.getContextPath()%>/js/cens/comentarios.js"></script>
        <script src="<%=request.getContextPath() %>/js/jquery.timeago.js"></script>
   		 <script src="<%=request.getContextPath() %>/js/jquery.autogrow-textarea.js"></script>
    	<script src="<%=request.getContextPath() %>/js/jquery.comment.js"></script>       
<script>
var pagePath="<%=request.getContextPath()%>";
var programaId = ${programaId};
var asesorId = ${asesorId};
var asignaturaId = ${asignaturaId};
var profesorId;
</script>

<fieldset>
	<div class="tituloForm">
			<h3 id="titulo">Revisi&oacute;n de Programa <span class="cursoFont"> ${asignatura}</span></h3>
	</div>
			
			<h3 class="subtitulo">Datos del Programa</h3>
			<div>
				<label for="nombre">Nombre:</label> <input type="text" id="nombre"
					placeholder="Nombre del programa" readonly/>
			</div>
			<div>
				<label for="profesor">Profesor:</label> <input type="text" id="profesor"
					placeholder="Nombre del profesor" readonly/>
			</div>
			<div>
				<label for="descripcion" style="float: left;">Descripci&oacute;n:</label>
				<textarea rows="4" cols="50" id="descripcion"
					style="margin-left: 3px;" placeholder="Breve descripci&oacute;n" readonly></textarea>
			</div>
			<div>
				<label for="cantCartillas">Cantidad de Cartillas:</label> <input
					type="text" id="cantCartillas" maxlength="2" class="entero" readonly/>
			</div>
			<div>
				<label for="downloadPrograma">Programa Adjunto:</label>
				<a id="downloadPrograma" class="comments-link bold"></a>
			</div>
		<h3 class="subtitulo">Estado de Revisi&oacute;n</h3>
		
			<div>
				<label for="estado">Estado:</label>
				 <select id="estado">			   
		         <c:forEach items="${estadosPosibles}" var="estadoRevision" >
		         	<c:choose>
		         		<c:when test="${estado.equals(estadoRevision) }">
		         		<option value="${estadoRevision}" selected> ${estadoRevision} </option>
		         		</c:when>
		         		<c:otherwise>
		         		<option value="${estadoRevision}"> ${estadoRevision} </option>
		         		</c:otherwise>
		         	</c:choose>		         	
		           
		        </c:forEach>
	        </select>
			</div>

			
			<div class="footerForm">
				<button class="button" id="btnGuardar" type="button" onclick="cambiarEstado();" disabled="disabled">Guardar</button>

				<button id="cancelar" class="button" type="button"
					onclick="window.location='<%=request.getContextPath()%>/mvc/asesor/dashboard'">Cancelar</button>
			</div>
			
		<h3 class="subtitulo">Comentarios</h3>
	
			<div id="accordion" style="height: 100%;">
				
			</div>

			

			<input type="hidden" id="fileUploadComentarioUsed" />
			<input type="hidden" id="asesorId" />			
			<input type="hidden" id="asesorName" />
			<input type="hidden" id="btnGuardarPrograma" />
			<input type="hidden" id="estadoActual" value="${estado}">
			<c:if test="${id != null}">
				<input type="hidden" id="id" value="${id}" />
			</c:if>
		
</fieldset>

<div id="cambiarEstado" class="dialog" title="Confirmar">
	<p id="cambiarEstadoValue"></p>
</div>




