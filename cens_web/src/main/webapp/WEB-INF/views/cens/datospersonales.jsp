<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="<%=request.getContextPath()%>/js/jquery.fileupload.js"></script>
<script src="<%=request.getContextPath()%>/js/cens/asignaturarevision.js"></script>
<script>
var pagePath="<%=request.getContextPath()%>";
</script>

<fieldset>
	<div class="tituloForm">

		<h3 id="titulo">Datos Personales<span class="cursoFont"> ${asignatura}</span></h3>	
	</div>
	<div class="censaccordion3" style=" height: 100%;">
		<h3 class="subtitulo">Datos del Miembro</h3>
		<div>
			<div>
				<label for="nombre">Nombre:</label> <input type="text" id="nombre"
					placeholder="Nombre" />
			</div>
			<div>
				<label for="apellido" >Apellido:</label>
				<input type="text" id="apellido"
					placeholder="Apellido" />
			</div>
			<div>
				<label for="dni" >DNI:</label>
				<input type="text" id="dni"
					placeholder="N&uacute;mero de documento" />
			</div>
		</div>
		<div>
		<h3 class="subtitulo">Datos del Usuario</h3>
			
			<div>
				<label for="cantCartillas">Cantidad de Cartillas:</label> <input
					type="text" id="cantCartillas" maxlength="2" class="entero" />
			</div>
			<div>
				<label for="programaadjunto">Programa Adjunto:</label>
				<button class="button" type="button"
					style="height: 32px; top: -1px;" id="btnEliminarPrograma">Eliminar
					Archivo</button>
				<a id="downloadPrograma"><input type="text" id="programaadjunto"
					readonly="readonly" placeholder="No existe archivo"
					style="width: 302px;" /></a>
			</div>
			<archivos>
			<div id="fileUp">
				<label for="fileupload">Adjuntar Programa:</label>
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
		</div>
		<h3 class="subtitulo">Datos del Material Did&aacute;ctico</h3>
		<div></div>
	</div>
	<h3 class="subtitulo">Datos del Programa</h3>
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
			<div>
				<label for="programaadjunto">Programa Adjunto:</label>
				<button class="button" type="button"
					style="height: 32px; top: -1px;" id="btnEliminarPrograma">Eliminar
					Archivo</button>
				<a id="downloadPrograma"><input type="text" id="programaadjunto"
					readonly="readonly" placeholder="No existe archivo"
					style="width: 302px;" /></a>
			</div>
			<archivos>
			<div id="fileUp">
				<label for="fileupload">Adjuntar Programa:</label>
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
			<c:if test="${id != null}">
				<input type="hidden" id="id" value="${id}" />
			</c:if>
		
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
