 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html>
	<head>
		<title>Catalogo de Filtros - Detalle Filtro</title>
		<jsp:include page="includes/includes.jsp"></jsp:include>

		<script src="<%=request.getContextPath()%>/resources/js/v2/tablas/tablasDetalleFiltro.js"></script>
				
		<script> 
			var idFiltro = ${filtro.data[0].id};
			tituloEncabezado = '${filtro.data[0].codigoCorto} - ${filtro.data[0].marca}';
			precioFormated = Math.round(${filtro.data[0].precioBase} * 100) / 100;
		</script>
	</head>
	<body>
		<!-- Agrego el header a la pagina -->
		<jsp:include page="includes/header.jsp"></jsp:include>
		
		<div id="content" class="detalle-filtro">
			<div class="top">
				<div class="left">
					<ul>
						<li>
							<label class="nom-datos-filtro codigo">Código:</label>
							<label class="val-nom-datos-filtro">${filtro.data[0].codigoCorto}</label>
						</li>
						<li>
							<label class="nom-datos-filtro">Marca:</label>
							<label class="val-nom-datos-filtro">${filtro.data[0].marca}</label>
						</li>
						<li>
							<label class="nom-datos-filtro">Descripción:</label>
							<label class="val-nom-datos-filtro">${filtro.data[0].descripcion}</label>
						</li>
						<li>
							<label class="nom-datos-filtro">Tipo:</label>
							<label class="val-nom-datos-filtro">${filtro.data[0].tipo}</label>
						</li>
						<li>
							<label class="nom-datos-filtro">Subtipo:</label>
							<label class="val-nom-datos-filtro">${filtro.data[0].subTipo}</label>
						</li>
						<li>
							<label class="nom-datos-filtro">Largo/Ancho/Alto:</label>
							<label class="val-nom-datos-filtro">${filtro.data[0].largoFiltro}/${filtro.data[0].anchoFiltro}/${filtro.data[0].alturaFiltro}</label>
						</li>
						<li>
							<label class="nom-datos-filtro">Rosca - Díametro Interno:</label>
							<label class="val-nom-datos-filtro">${filtro.data[0].roscaFiltro}</label>
						</li>
						<li>
							<label class="nom-datos-filtro">Rosca Sensor:</label>
							<label class="val-nom-datos-filtro">${filtro.data[0].roscaSensorFiltro}</label>
						</li>
						<li>
							<label class="nom-datos-filtro">Precio Público:</label>
							<label class="val-nom-datos-filtro precio"> - </label>
						</li>
					</ul>
				</div>
				<div class="right">
					<ul>
						<li>
							<div class="image-wrapper">
								<img id="filtro-image" src="../img/${filtro.data[0].foto}" alt="Imagen Filtro">
<!-- 								height="230" width="272" -->
							</div>
						</li>
						<li class=center-li>
							<label>Cantidad:</label>
							<input type="number" id="input-cantidad" min="0" value="0" >
							<button class="buttonSinTexto ui-icon-filtros-add" type="button" onclick="addFiltrosToPedido()" id="addFiltrosButton">
								<span class="ui-icon"></span>
							</button>
							<button class="buttonSinTexto ui-icon-filtros-delete" type="button" onclick="removeFiltrosToPedido()" id="deleteFiltrosButton">
								<span class="ui-icon"></span>
							</button>
						</li>
						<li id="filtros-agregados" class=center-li>
							<label>Filtros Agregados:</label>
							<label id="cantidad-filtros"> - </label>
						</li>
					</ul>	
				</div>	
			</div>
			<div class="bottom">
				<div class="left">
					<table id="aplicaciones-table"></table>
					<div id="aplicaciones-table-paging"></div> 
				</div>
				<div class="right">
					<table id="reemplazos-table"></table> 
					<div id="reemplazos-table-paging"></div>
				</div>
			</div>			
		</div>	
		<!-- Agrego el footer a la pagina -->
		<jsp:include page="includes/footer.jsp"></jsp:include>
	</body>
	<script>
		$(document).ready(function() {
		    cargarCantidad();
			$(".val-nom-datos-filtro.precio").text(precioFormated);

			$('#input-cantidad').on('keyup', function(e) {
			    if (e.which == 13) {
			    	console.log("El usuario presiono la tecla enter....");
			    	addFiltrosToPedido();
			    }
			});
		});
		
		function cargarCantidad(){
			$.ajax({
                type: "GET",
                url: "../pedido/cantidad?filtroId="+idFiltro,
                success: function(data){
                	if(data.success == true){
                    	$('#cantidad-filtros').text(data.data[0].cantidad);
                	} else {
                    	$('#cantidad-filtros').text(" - ");                		
                	}
                },
                error: function(e){

                }
            });
		};
		
		function addFiltrosToPedido(){
			cantidad = parseInt($('#cantidad-filtros').text()) + parseInt($('#input-cantidad').val());
			var jsonString = 	"{\"id\":${filtro.data[0].id},"
							+"\"codigo\":\"${filtro.data[0].codigoCorto}\","
							+"\"desc\":\"Agregamos filtros al pedido\","
							+"\"cantidad\":"+cantidad+","
							+"\"precio\":0," // Campo no utilizado
							+"\"precioUnitario\":0}"; // Campo no utilizado
							
			$.ajax({
			type: "POST",
            url: "../pedido/agregar",
			data: jsonString,
			contentType: "application/json",
			dataType: "json",
			success: function(data){
				cargarCantidad();
				$('#input-cantidad').val(0);
				$('#cantidad-filtros').text(cantidad);
			}
			});
		};
						
		function removeFiltrosToPedido(){
			var jsonString = 	"{\"id\":${filtro.data[0].id},"
								+"\"codigo\":\"${filtro.data[0].codigoCorto}\","
								+"\"desc\":\"Eliminar filtro del detalle\","
								+"\"cantidad\":0,"
								+"\"precio\":0," // no interesa 
								+"\"precioUnitario\":0}"; // no interesa 
			$.ajax({
                type: "POST",
                url: "../pedido/eliminarDetalle",
                data: jsonString,
                contentType: "application/json",
                dataType: "json",
                success: function(data){
                	cargarCantidad();
                	$('#input-cantidad').val(0);
                	$('#cantidad-filtros').text("0");
                }
            });
		};
		
	</script>
</html>