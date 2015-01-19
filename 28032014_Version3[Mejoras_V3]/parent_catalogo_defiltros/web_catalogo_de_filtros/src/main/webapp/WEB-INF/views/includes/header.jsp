<%-- <%@ page language="java" contentType="text/html; charset=ISO-8859-1" --%>
<%--     pageEncoding="ISO-8859-1"%> --%>
<script src="<%=request.getContextPath()%>/resources/js/v2/update/updateType.js"></script>
		<div id=div_header>
			<div class=left>
				<button class="ui-icon-filtros-volver" type="button" onclick="javascript:history.back()" id="backButton">
					Volver
				</button>
				<button class="buttonSinTexto ui-icon-filtros-home" type="button"  onclick="goToHome()" id="homeButton">
					<span class="ui-icon"></span>
				</button>
			</div>
			
			<div class=center>
				<span class=title id="title"></span>
			</div>
			
			<div class=right>
				<button class="buttonSinTexto ui-icon-filtros-percent" type="button" onclick="cargarPorcentajeVenta()" id="percentButton">
					<span class="ui-icon"></span>
				</button>

				<button class="buttonSinTexto ui-icon-filtros-refresh" type="button" onclick="abrirDialogoActualizaciones()" id="updateButton">
					<span class="ui-icon"></span>
				</button>
				<button class="buttonSinTexto ui-icon-filtros-shop" type="button" onclick="goToPedido()" id="shopButton">
					<span class="ui-icon"></span>
				</button>
			</div>
			
			<div id="spiner">
			       <div class="loading ui-state-default ui-state-active loadingpopupactualizacion">Actualizando...</div> 
<!-- 				<div class="spiner_image"></div> -->
<!--  				<span class="spiner_text">Actualizando..</span> -->
<!--  				<span class="spiner_text2">Finalizado, Volviendo a pantalla principal.</span> -->
			</div>
			
			<div id="porcentajeVenta" class="dialog" title="Porcentaje Precio Venta P&uacute;blico">
				<span>Porcentaje de Ajuste</span>
				<input type="text" name="porcentajeInput" class="decimalConPunto" id="porcentajeInput"></input>
				<span>%</span>
			</div>
			<div id="porcentajeVentaSuccess" class="dialog" title="Informaci&oacute;n">
				<span>Se modific&oacute; el porcentaje.</span>
			</div>
			<div id="actualizarDialog" class="dialog" title="Actualizaci&oacute;n del Sistema">
                            <div id=actualizacionBotones class=div-actualizacion>
					<button id="updateManual" class=button onclick="actualizarManual()">Manual</button>
					<button id="updateAutomatico" class=button onclick="actualizarAutomatico()">Autom&aacute;tica</button>
				</div>		
                           <!-- <div id=updateLeft class=filtrosPanel style="display: inline">
                            <div class="filtro" id="updateType">
                                
                                <label for="updateType" class="labelForSelect">Tipo Actualizaci&oacute;n</label>
                                    <select class="chosen-select" id="updateTypeSelect">  
                                        <option value=""></option>
                                        <option value="1">Base de datos</option>
                                        <option value="2">Sistema</option>
                                    </select>                                
                                <div id="updateType_loading" class="combo_loading"></div>
                            </div>
                            </div>
                           -->
                            
                           <div id="messageDiv">
                               <span id="message"></span>
                               <div id="updateTypeDiv">                                   
                               </div>
                               <span id="updateTypeWarning" style="color: chocolate; font-weight: 900;font-size: 10px;"></span>
                           </div>
				
				<div style="display:none;">
		 			<input id="updateSistemaFileupload" type="file" name="files[]" data-url="<%=request.getContextPath() %>/fileUpload/upload/dump" title="Seleccione un archivo" accept=".zip" />
				</div>
				<div id="actualizarDialog_progressBar" class="field" style="display: none">
                                    <br></br>	
                                    <label>Progreso de Carga:</label>
                                        <br></br>
					<div>
						<div id="error" style="display: none;">
							<span class="error"> </span>
						</div>
						<div class="ui-progress-bar ui-container transition"
							id="progress_bar" style="width: 100%; float: left;">
							<div class="ui-progress" style="width: 0%; display: none;">
								<span id="progressBarLabel" class="ui-label" style="display: none;">Procesando Información. Aguarde por favor.</span>
							</div>
						</div>
					</div>
                                        
				</div>
			</div>
		</div>
	