 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html>
	<head>
		<title>Catalogo de Filtros - Pedido Page</title>
		<jsp:include page="includes/includes.jsp"></jsp:include>

		<script> tituloEncabezado = 'Formulario de Pedidos'; </script>
		<script src="<%=request.getContextPath()%>/resources/js/v2/tablas/tablaPedido.js"></script>
		<script>
			var vendedor = ${vendedor};
			var codigoCliente = '${codigoCliente}';
			$(document).ready(function() {
				if(vendedor){
	 				$("#codigo-cliente-label").remove();
					if(getCookie('codigoCliente') != ""){ 
						$("#codigo-cliente-input").val(getCookie('codigoCliente'));
					}else{
						$("#codigo-cliente-input").val(codigoCliente);	
					}	 				
	 				$("#codigo-cliente-input-div").show(codigoCliente);
				} else {
	 				$("#codigo-cliente-input-div").remove();
	 				$("#codigo-cliente-label").show();
				}
			});						
		  	$(function() {
			    $( "#agregar-button" )
			      .button()
			      .click(function( event ) {
			        event.preventDefault();
			      });
			  	$( "#codigo-filtro-input" ).autocomplete({
			  	//define callback to format results  
		            source: function(req, add){  
		                //pass request to server  
		                $.getJSON("./filtros/pedidos/combo", req, function(data) {  			                              
		                    //create array for response objects  
		                    var suggestions = [];  		                              
		                    //process response    
		                    for ( var i = 0; i < data.total; i++ ) {                   
		                    	suggestions.push(data.data[i]);  
		                    }	     
			                if(suggestions.length == 0){
			                	suggestions.push(" - Sin Resultados - ");
			                } 
			                add(suggestions);	
	            		});  
		        	},  
			        minLength: 2,
			        select: function( event, ui ) {
						$("#codigo-filtro-input-hidden").val(ui.item.valor);
			        }
		      	});	
			  				  	
			  	$( "#codigo-cliente-input" ).autocomplete({
			  		//define callback to format results  
		            source: function(req, add){  
		                //pass request to server  
		                $.getJSON("./pedido/clientes/combo", req, function(data) {  			                              
		                    //create array for response objects  
		                    var suggestions = [];  		                              
		                    //process response    
		                    for ( var i = 0; i < data.total; i++ ) {                   
		                    	suggestions.push(data.data[i]);  
		                    }	     
			                if(suggestions.length == 0){
			                	suggestions.push(" - Sin Resultados - ");
			                } 
			                add(suggestions);	
	            		});  
		        	},  
			        minLength: 1,
			        select: function( event, ui ) {
			        	if(ui.item.valor){
			        		codigoCliente = ui.item.value;
			        		setCookie('codigoCliente', codigoCliente);
				        	gridReload();	
			        	}			        	
			        }
		      	});	
			  	
			  	$( "#confirmacion-dialog" ).dialog({
					autoOpen: false,
					width: 400,
					modal: true,
					buttons: [
						{
							text: "Aceptar",
							click: function() {
								limpiarPedido();
								$( this ).dialog( "close" );
							}
						},
						{
							text: "Cancelar",
							click: function() {
								$( this ).dialog( "close" );
							}
						}
					]
			 });
			  	
		  	$( "#confirmacion-pedido-dialog" ).dialog({
				autoOpen: false,
				width: 400,
				modal: true,
				buttons: [
					{
						text: "Aceptar",
						click: function() {
							descargarPedido();
							$( this ).dialog( "close" );
						}
					},
					{
						text: "Cancelar",
						click: function() {
							$( this ).dialog( "close" );
						}
					}
				]
			 });
		  	
		  	$( "#info-dialog" ).dialog({
				autoOpen: false,
				width: 300,
				modal: true,
				buttons: [
					{
						text: "Ok",
						click: function() {
							$( this ).dialog( "close" );
						}
					}
				]
			 });
			  	
		 	});
  		</script>
	</head>
	<body>
		<div id=pedido_container>
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
					<span class=title>Formulario de Pedidos</span>
				</div>
			</div>
			<div id="top">
				<div id=div_pedido_header>
					<span class=title>Agregar Filtro</span>
				</div>
				<div id=div_content>
<!-- 					<ul> -->
<!-- 						<li> -->
							<div class="filtro">
								<label>Ingrese Código:</label>
								<div class="ui-widget" style="display: inline;">
									<input id="codigo-filtro-input" />
									<input type=hidden id="codigo-filtro-input-hidden" />
								</div>
								<div class="cantidad-input-div">
									<label>Cantidad:</label>
									<input type="number" id="input-cantidad" min="1" value="1">
								</div>		
								<button id="agregar-button" type="button" onclick="addFiltrosToPedido()">Agregar</button>						
							</div>
							
<!-- 						</li> -->
<!-- 						<li id="filtros-agregados">							 -->
							
<!-- 						</li> -->
<!-- 					</ul>	 -->
				</div>
			</div>
			<div id="bottom">
				<div id="div_pedido_header">
					<span class=title>Pedido Actual</span>
				</div>
				<div id=div_content>
					<div id="table-header">
						<div class="left">
							<label>Código de Cliente:</label>
							<div id="codigo-cliente-input-div" class="ui-widget" style="display: none;">
								<input id="codigo-cliente-input" />
							</div>
							<label id="codigo-cliente-label" style="display:none">${codigoCliente}</label>
						</div>
						<div class="right">
							<div id=descargar-pedido-button>
								<a class="pedido-button" href="javascript:confirmPedidoDialogOpen()">
									<span id=descargar-pedido-image></span>
									<span id=descargar-pedido-text>Descargar Pedido</span>
								</a>
							</div>
							<div id=limpiar-pedido-button>
								<a class="pedido-button" href="javascript:confirmDialogOpen()">
									<span id=limpiar-pedido-image></span>
									<span id=limpiar-pedido-text>Limpiar</span>
								</a>
							</div>							
						</div>
					</div>
					<div class="table-pedido">
						<table id="pedido-table"></table>
						<div id="pedido-table-paging"></div> 
					</div>
					<div id="div_pedido_header" class="footer">
						<span class="title total-row">Total: <span id="total-pedido"> - </span></span>
					</div>
				</div>
			</div>
		</div>
		<div id="confirmacion-dialog" class="dialog" title="Gestión de Pedido">
			<span>¿Desea Eliminar todos los detalles del Pedido?</span>
		</div>
		<div id="info-dialog" class="dialog" title="Error">
			<span>Ingrese un valor en 'Código de Cliente'.</span>
		</div>
		<div id="confirmacion-pedido-dialog" class="dialog" title="Gestión de Pedido">
			<span>¿Desea Realizar el Pedido?</span>
		</div>		
	</body>
	<script>
		function confirmDialogOpen(){
			if($( ".pedido-button" ).hasClass("disable")){
				return false;
			}
			$( "#confirmacion-dialog" ).dialog( "open" );			
		};
		
		function confirmPedidoDialogOpen(){
			if($( ".pedido-button" ).hasClass("disable")){
				return false;
			}
			$( "#confirmacion-pedido-dialog" ).dialog( "open" );			
		};
		
		function addFiltrosToPedido(){
			cantidad = $('#input-cantidad').val();
			var jsonString = 	"{\"id\":"+$("#codigo-filtro-input-hidden").val()+","
							+"\"codigo\":\""+$("#codigo-filtro-input").val()+"\","
							+"\"desc\":\"Agregamos filtros al pedido\","
							+"\"cantidad\":"+cantidad+","
							+"\"precio\":0," // Campo no utilizado
							+"\"precioUnitario\":0}"; // Campo no utilizado
							
			$.ajax({
				type: "POST",
	            url: "./pedido/agregar",
				data: jsonString,
				contentType: "application/json",
				dataType: "json",
				success: function(data){
					$('#input-cantidad').val(cantidad);
					$("#codigo-filtro-input").val("");
					gridReload();
// 					jQuery("#pedido-table").trigger("reloadGrid");
				}
			});
		};
		
		function descargarPedido(){
			var codigoCliente = $("#codigo-cliente-input").val();
			if(codigoCliente){
				var gridBody =  $("#pedido-table").children("tbody");
				var firstRow = gridBody.children("tr.jqgfirstrow");
				gridBody.empty().append(firstRow);
				$(".pedido-button").addClass("disable");
            	calculateTotal();
            	download(codigoCliente);
			} else {
				$( "#info-dialog" ).dialog( "open" );			
			}			
		}
		
		function download(codigoCliente){
        	method = "get"; // Set method to post by default if not specified.
            // The rest of this code assumes you are not using a library.
            // It can be made less wordy if you use one.
            var form = document.createElement("form");
            form.setAttribute("method", method);
            form.setAttribute("action", "./pedido/generar");
            
            form.appendChild(createElement("input", {type: "hidden", name: "codigo", value: codigoCliente}));
            
            document.body.appendChild(form);
            form.submit();
        }
		
		 var createElement = (function()
         		{
         		    // Detect IE using conditional compilation
         		    if (/*@cc_on @*//*@if (@_win32)!/*@end @*/false) {
         		        // Translations for attribute names which IE would otherwise choke on
         		        var attrTranslations = {"class": "className", "for": "htmlFor"};
         		        var setAttribute = function(element, attr, value) {
         		            if (attrTranslations.hasOwnProperty(attr)){
         		                element[attrTranslations[attr]] = value;
         		            } else if (attr == "style"){
         		                element.style.cssText = value;
         		            } else {
         		                element.setAttribute(attr, value);
         		            }
         		        };
         		        return function(tagName, attributes){
         		            attributes = attributes || {};
         		            // See http://channel9.msdn.com/Wiki/InternetExplorerProgrammingBugs
         		            if (attributes.hasOwnProperty("name") ||
         		                attributes.hasOwnProperty("checked") ||
         		                attributes.hasOwnProperty("multiple"))
         		            {
         		                var tagParts = ["<" + tagName];
         		                if (attributes.hasOwnProperty("name")){
         		                    tagParts[tagParts.length] =
         		                        ' name="' + attributes.name + '"';
         		                    delete attributes.name;
         		                }
         		                if (attributes.hasOwnProperty("checked") &&
         		                    	"" + attributes.checked == "true"){
         		                    tagParts[tagParts.length] = " checked";
         		                    delete attributes.checked;
         		                }
         		                if (attributes.hasOwnProperty("multiple") &&
         		                    	"" + attributes.multiple == "true"){
         		                    tagParts[tagParts.length] = " multiple";
         		                    delete attributes.multiple;
         		                }
         		                tagParts[tagParts.length] = ">";
         		                var element = document.createElement(tagParts.join(""));
         		            } else {
         		                var element = document.createElement(tagName);
         		            }
         		            for (var attr in attributes){
         		                if (attributes.hasOwnProperty(attr)){
         		                    setAttribute(element, attr, attributes[attr]);
         		                }
         		            }
         		            return element;
         		        };
         		    }
         		    // All other browsers
         		    else
         		    {
         		        return function(tagName, attributes)
         		        {
         		            attributes = attributes || {};
         		            var element = document.createElement(tagName);
         		            for (var attr in attributes)
         		            {
         		                if (attributes.hasOwnProperty(attr))
         		                {
         		                    element.setAttribute(attr, attributes[attr]);
         		                }
         		            }
         		            return element;
         		        };
         		    }
         		})();
		 
		function limpiarPedido(){
			var emptyJsonString = 	"{\"pedidoList\": []}"; 
			$.ajax({
				type: "POST",
				url: "./pedido/guardar",
				data: emptyJsonString,
				contentType: "application/json",
				dataType: "json",
				success: function(data){
					gridReload();
// 					jQuery("#pedido-table").trigger("reloadGrid");
				}
			});
		}
		
		function removeFiltroOfPedido(idFiltro){
			var jsonString = 	"{\"id\":"+idFiltro+","
								+"\"codigo\":\"\","
								+"\"desc\":\"Eliminar filtro del detalle\","
								+"\"cantidad\":0,"
								+"\"precio\":0," // no interesa 
								+"\"precioUnitario\":0}"; // no interesa 
			$.ajax({
                type: "POST",
                url: "./pedido/eliminarDetalle",
                data: jsonString,
                contentType: "application/json",
                dataType: "json",
                success: function(data){
                	gridReload();
// 					jQuery("#pedido-table").trigger("reloadGrid");
                }
            });
		}
	</script>
</html>