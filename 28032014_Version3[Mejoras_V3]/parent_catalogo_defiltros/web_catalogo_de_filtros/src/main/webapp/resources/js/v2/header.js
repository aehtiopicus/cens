var tituloEncabezado;
var applicationPath;

function startSpinner(){
     $('.spiner_text').html("Actualizando");
	$('#spiner').show();
}

function stopSpinner(){
	$('#spiner').hide();
}

function loadHeaderOnStart(){
	
	if(getCookie("empresa") == "true"){
		$('#percentButton').hide();
	}else{
		$('#percentButton').show();		
	}
	
	applicationPath = getCookie("appPath");
	
	if(tituloEncabezado != null){
		$('#title').text(tituloEncabezado);
	}else{
		$('#title').text("Catálogo de Filtros");		
	}
	
	$(".decimalConPunto").numeric("."); 
	
	$( "#porcentajeVenta" ).dialog({
			autoOpen: false,
			width: 400,
			modal: true,
			buttons: [
				{
					text: "Modificar",
					click: function() {
						$( this ).dialog( "close" );
						confirmarNuevoPorcentaje();
					}
				}
			]
		});
	 
	 $( "#porcentajeVentaSuccess" ).dialog({
			autoOpen: false,
			width: 200,
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
	 
	 $( "#actualizarDialog" ).dialog({
			autoOpen: false,
			width: 500,
			modal: true,                        
                        close : function(){
                          limpiarDatosUpdate();
                        },
			buttons: [
			
				{
					text: "Actualizar",
					disabled: true,
                                        id:"actualizarButton",
					click: function() {
						actualizar($('#actualizarDialog_progressBar').is(":visible") || ftpdown ? 1 :2);
					}
				}
			]
	 });
	 
	 if(location.pathname.indexOf("/index") > 0){
		 $('#backButton').hide();
	 }else{
		 $('#backButton').show();		 
	 }
         if(getCookie("actualizar")==="true"){
            $('#requiredUpdate').text("Actualizaciones Disponibles");
         }else{
             $('#requiredUpdate').text("");
         }
}

function inicializaDialogoActualizacion(){
    $( "#actualizarDialog" ).dialog({
			autoOpen: false,
			width: 500,
			modal: true                       
                        
	 });
}
function cargarPorcentajeVenta(){
	
	$.ajax({
		type:"GET",
		url: applicationPath + "precio_venta",
		success: function(result){
			if(result.success){
				//porcentaje = result.data.porcentaje;
				
				$('#porcentajeInput').val(result.data.porcentaje);
				$("#porcentajeVenta").dialog("open");
				
			}
		}
		
	});
}


function confirmarNuevoPorcentaje(){
	porcentaje = $('#porcentajeInput').val();
	
	$.ajax({
		type:"POST",
		url: applicationPath + "precio_venta_guardar",
		data: {
			porcentaje:porcentaje
		},
		success: function(result){
			if(result.success){
				$("#porcentajeVentaSuccess").dialog("open");
				
				//verifico si existe la funcion para actualizar los precios en la tabla de filtros del index
				//si esta definida la funcion la ejecuto
				if(typeof actualizarPreciosEnFiltrosTable == 'function'){
					actualizarPreciosEnFiltrosTable();
				}
			}
		}
		
	});
	
}


function goToPedido(){
	location.href= applicationPath + "pedido";
}

function goToHome(){
	location.href= applicationPath;
}