
<archivos>
			<div id="fileUps" style="margin-top:20px">
				
				<button class="button" type="button"
					style="height: 32px; top: -1px;">
					Insertar Planilla <input id="fileSelection"
						title="Seleccionar Cartilla" type="file" class="custom-file-input"
						name="file"
						accept=".xls" />
				</button>
				<input type="text" readonly="readonly" id="fileUploadName"
					style="width: 302px; height:24px;margin-left: 7px;" />
				<a id="downloadsample" class="comments-link bold" download="cma.xls" href="/cens_web/adjuntos/cma.xls">Descargar plantilla &nbsp; &nbsp; &nbsp;&nbsp;</a>
			</div>


</archivos>
<div id="listaAlumnos">
	<p id="cmaNoData" class="comments-link bold">No Hay datos</p>
	<div id="alumnoHeader" class="ui-jqgrid-titlebar ui-widget-header ui-corner-top ui-helper-clearfix" style="margin-top: 30px; width:98%;">
		<span class="ui-jqgrid-title" style="float:left; margin-left:10px;">Datos del Alumno</span>
		<span class="ui-jqgrid-title" style="float:right; margin-right:10px;">Estado</span>
	</div>
	
	<div id="alumnoData" >
		<div style="margin: 3px;">
			<div style="display: inline-block;   margin-top: 4px;">Fabiana Micaela Cipolla, 33968270 29/08/1988</div>
			<div style="display: inline-block; float:right;">
				<div style="margin-right: 19px;" class="cmaSuccess"></div>
									
			</div>
			<div style="clear:both;"></div>
		</div>
		<div style="margin: 3px;">
			<div style="display: inline-block;   margin-top: 4px;">Fabiana Micaela Cipolla, 33968270 29/08/1988</div>
			<div style="display: inline-block; float:right;">
				<div style="margin-right: 19px;" class=cmaInfo></div>
									
			</div>
			<div style="clear:both;"></div>
		</div>
		<div style="margin: 3px;">
			<div style="display: inline-block;   margin-top: 4px;">Fabiana Micaela Cipolla, 33968270 29/08/1988</div>
			<div style="display: inline-block; float:right;">
				<div style="margin-right: 19px;" class="cmaError"></div>
									
			</div>
			<div style="clear:both;"></div>
		</div>
		
		<div style="margin: 3px;">
			<div style="display: inline-block;   margin-top: 4px;">Fabiana Micaela Cipolla, 33968270 29/08/1988</div>
			<div style="display: inline-block; float:right;">
				<div class="cmaPending"></div>
									
			</div>
			<div style="clear:both;"></div>
		</div>
		
	</div>
</div>