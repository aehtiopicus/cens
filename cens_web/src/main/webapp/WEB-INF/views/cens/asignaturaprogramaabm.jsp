<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="<%=request.getContextPath()%>/js/jquery.fileupload.js"></script>
<script src="<%=request.getContextPath()%>/js/cens/asignaturaprogramaabm.js"></script>

<script src="<%=request.getContextPath()%>/js/cens/comentarios.js"></script>
<script src="<%=request.getContextPath() %>/js/jquery.timeago.js"></script>
<script src="<%=request.getContextPath() %>/js/jquery.autogrow-textarea.js"></script>
<script src="<%=request.getContextPath() %>/js/jquery.comment.js"></script>   
    	
<script>
var pagePath="<%=request.getContextPath()%>";
var profesorId = ${profesorId};
var asignaturaId = ${asignaturaId};
</script>

<fieldset>
	<div class="tituloForm">
	<c:choose>
		<c:when test="${disabled!=null && disabled == 'true'}">
			<h3 ><span style="color: rgb(218, 77, 44);"> PROGAMA NO EDITABLE</span></h3>
			<h3 id="titulo">Planificaci&oacute;n de Programa <span class="cursoFont"> ${asignatura}</span></h3>
		</c:when>
		<c:otherwise>
			<h3 id="titulo">Planificaci&oacute;n de Programa <span class="cursoFont"> ${asignatura}</span></h3>
		</c:otherwise>
	</c:choose>	
	</div>
	<c:choose>
		<c:when test="${disabled!=null && disabled == 'true'}">
			<div>
				<label for="nombre">Nombre:</label> <input type="text" id="nombre"
					placeholder="Nombre del programa" readonly/>
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
			<div id="programaAdjuntado" style="display:none;">
				<label for="downloadPrograma">Programa Adjunto:</label>
				<div style="display: inline-flex; width: 458px;">
					<a id="downloadPrograma" class="comments-link bold">No existe un programa adjunto</a>					
				</div>
			</div>
			

			<div class="footerForm">
				<button class="button" type="button" onclick="guardarPrograma();" disabled>Guardar</button>

				<button id="cancelar" class="button" type="button"
					onclick="window.location='<%=request.getContextPath()%>/mvc/profesor/asignaturaList'">Cancelar</button>
			</div>

			<input type="hidden" id="fileUploadUsed" />
			<input type="hidden" id="btnGuardarPrograma" />
			<input type="hidden" id="fileUploadComentarioUsed"/>
			<c:if test="${id != null}">
				<input type="hidden" id="id" value="${id}" />
			</c:if>
		</c:when>
		<c:otherwise>
			<div>
				<label for="nombre">Nombre:</label> <input type="text" id="nombre"
					placeholder="Nombre del programa" />
			</div>
			<div>
				<label for="descripcion" style="float: left;">Descripci&oacute;n:</label>
				<textarea rows="4" cols="50" id="descripcion"
					style="margin-left: 3px;" placeholder="Breve descripci&oacute;n"></textarea>
			</div>
			<div>
				<label for="cantCartillas">Cantidad de Cartillas:</label> <input
					type="text" id="cantCartillas" maxlength="2" class="entero" />
			</div>
			<div id="programaAdjuntado" style="display:none;">
				<label for="downloadPrograma">Programa Adjunto:</label>
				<div style="display: inline-flex; width: 458px;">
					<a id="downloadPrograma" class="comments-link bold">No existe un programa adjunto</a>
					<label id="eliminarProgramaAdjunto" class='eliminar-archivo' style='display:none;'></label>
				</div>
			</div>
			<archivos>
			<div id="fileUp">
				<label for="fileupload">Programa Adjunto:</label>
				<button class="button" type="button"
					style="height: 32px; top: -1px;">
					Insertar Archivo <input id="fileupload"
						title="Seleccionar Programa" type="file" class="custom-file-input"
						name="file"
						accept=".pttx,.ppt,.xlsx,.xls,.doc,.docx,.pps,.ppsx,.pdf" />
				</button>
				<input type="text" readonly="readonly" id="fileUploadName"
					style="width: 302px; margin-left: 7px;" />
			</div>


			</archivos>

			<div class="footerForm">
				<button class="button" type="button" onclick="guardarPrograma();">Guardar</button>

				<button id="cancelar" class="button" type="button"
					onclick="window.location='<%=request.getContextPath()%>/mvc/profesor/asignaturaList'">Cancelar</button>
			</div>

			<input type="hidden" id="fileUploadUsed" />
			<input type="hidden" id="btnGuardarPrograma" />
			<input type="hidden" id="fileUploadComentarioUsed"/>
			<c:if test="${id != null}">
				<input type="hidden" id="id" value="${id}" />
			</c:if>
		</c:otherwise>
	</c:choose>
	
	<h3 class="subtitulo" id="comentariosTitulo" style="display: none;">Comentarios</h3>
	
			<div id="accordion" style="height: 100%;">
				
			</div>
</fieldset>

<div id="guardarPrograma" class="dialog" title="Confirmar">
	<p>Se adjuntar&aacute; un programa a la asignatura. ¿Desea
		continuar?</p>
	<div>
		<div id="progressbar" style="border: none;">
			<div class="progress-label"></div>
			<input type="hidden" id="fileUploadFailure" />
		</div>
	</div>
</div>

<div id="borrarPrograma" class="dialog" title="Confirmar">
	<p>Se eliminar&aacute; el programa adjunto. ¿Desea continuar?</p>

</div>
