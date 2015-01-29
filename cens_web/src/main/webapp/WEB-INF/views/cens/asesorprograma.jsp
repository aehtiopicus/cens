<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="<%=request.getContextPath()%>/js/jquery.fileupload.js"></script>
<script src="<%=request.getContextPath()%>/js/cens/asesorprograma.js"></script>
<script src="<%=request.getContextPath()%>/js/cens/comentarios.js"></script>
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
			
			
		<h3 class="subtitulo">Estado de Revisi&oacute;n</h3>
		
			<div>
				<label for="estado">Estado:</label>
				 <select id="estado">
			   <option value=""/> 
		         <c:forEach items="${estadosPosibles}" var="estadoRevision" >
		         	<c:if test="${estado.equals(estadoRevision) }">
		           	<option value="${estadoRevision}" selected> ${estadoRevision} </option>
		           </c:if>
		           <option value="${estadoRevision}"> ${estadoRevision} </option>
		        </c:forEach>
	        </select>
			</div>
			
			
			<div>
				<label for="visualizarPrograma" style="float: left;">Programa:</label>
				<button class="button" type="button"
					style="width: 457px;left: 3px;" id="btnVisualizar" onclick="openPrograma();" >Visualizar Programa Adjunto</button>
			</div>
		<h3 class="subtitulo">Comentarios</h3>
	
			<div id="accordion" style="height: 100%;">
				
			</div>

			<div class="footerForm">
				<button class="button" type="button" onclick="guardarPrograma();" disabled>Guardar</button>

				<button id="cancelar" class="button" type="button"
					onclick="window.location='<%=request.getContextPath()%>/mvc/profesor/asignaturaList'">Cancelar</button>
			</div>

			<input type="hidden" id="fileUploadComentarioUsed" />
			<input type="hidden" id="asesorId" />			
			<input type="hidden" id="asesorName" />
			<input type="hidden" id="btnGuardarPrograma" />
			<c:if test="${id != null}">
				<input type="hidden" id="id" value="${id}" />
			</c:if>
		
</fieldset>

<div id="estadoPrograma" class="dialog" title="Estado del Programa">
<fieldset>
	
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
				<label for="programaadjunto">Programa Adjunto:</label>
				<a id="downloadPrograma"><input type="text" id="programaadjunto"
					readonly="readonly" placeholder="No existe archivo"/></a>
			</div>
</fieldset>			
</div>



