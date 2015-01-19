function filterAplicaciones(idFiltro) {

	var store = Ext.getStore("Vehiculos");

	if (store) {

		store.currentPage = 1;

		store.getProxy().setExtraParams({
			idFiltro : idFiltro,
		});

		store.load({
			currentPage : 1,
		});

	}

}

function obtenerCantidadPedida(idFiltro) {
	var store = Ext.getStore("PedidoRequest");

	if (store) {

		store.currentPage = 1;

		store.getProxy().setExtraParams({
			filtroId : idFiltro,
		});

		store.load({
			currentPage : 1,
		});

	}
}

function filterReemplazos(idFiltro) {
	var filtrosStore = Ext.getStore("Reemplazos");

	if (filtrosStore) {

		filtrosStore.currentPage = 1;

		filtrosStore.getProxy().setExtraParams({
			idFiltro : idFiltro,
		});

		filtrosStore.load({
			currentPage : 1,
		});

	}

}

function filterFiltrosParaAplicacion(idVehiculo, filtro) {

	var filtrosStore = Ext.getStore("FiltrosAplicacion" + filtro);

	if (filtrosStore) {

		filtrosStore.currentPage = 1;

		filtrosStore.getProxy().setExtraParams({
			idAplicacion : idVehiculo,
			filtro : filtro,
		});

		filtrosStore.load({
			currentPage : 1,
		});

	}

}

function resizeVehiculoDetalles() {
	var detailpanel = Ext.ComponentQuery.query('vehiculodetail');
	for ( var i = 0; i < detailpanel.length; i++) {
		if (detailpanel[i]) {
			$('#' + detailpanel[i].id)
					.width(
							window.innerWidth
									+ (10 - ((window.innerWidth % 100) % 10)));
		}
	}
}

function hideCombosAndResetStore(nombreList, nombreStore) {
	// hide out floating list

	var myList = Ext.Viewport.down(nombreList);

	if (myList) {
		myList.setHidden(true);
	}

	// remove all data from the store
	var store = Ext.getStore(nombreStore);

	if (store) {
		store.removeAll();
	}

}

function keyUpNumerico(field) {

	if (field.value != field.value.replace(/[^0-9\.]/g, '')) {
		field.value = field.value.replace(/[^0-9\.]/g, '');
	}
	;

};

function dataNoDisponible(record) {
	$.each(record.data, function(a) {
		if (record.data[a] == null || record.data[a].length == 0) {
			record.data[a] = " - ";
		}
	});
}

function calcularPrecio(precio) {

	var value = parseFloat(1 + parseFloat(ajuste) / 100);
	return '$ ' + (parseFloat(precio) * (isNaN(value) ? 1 : value)).toFixed(2);

}

function actualizarDatosPrecio() {
	$.ajax({
		type : "POST",
		url : 'precio_venta_guardar/'
				+ $(
						'#'
								+ $('#' + Ext.getCmp('porcentajeAjuste').id
										+ ' :input')[0].id).val(),
		success : function(value) {
			if (value.success) {
				ajuste = $(
						'#'
								+ $('#' + Ext.getCmp('porcentajeAjuste').id
										+ ' :input')[0].id).val();

				Ext.Viewport.getComponent(Ext.getCmp('pedidoPanel')).hide();

			}
		}
	});

}

function getPorcentaje() {
	return $('#' + $('#' + Ext.getCmp('porcentajeAjuste').id + ' :input')[0].id)
			.val();
}
function doSomething(keyStroke) {
	var result = false;
	var inp = String.fromCharCode(keyStroke);
	if (/[a-zA-Z0-9-_ ]/.test(inp)) {
		result = true;
	}
	return result;
}
function agregarPedido(){
	var cantidad=document.getElementsByName("txtCantidad")[document.getElementsByName("txtCantidad").length-1].value;
	if(cantidad ==null || cantidad.length == 0){
		showError("Debe indicar una Cantidad");
	}else{
		cantidad = parseInt(cantidad) +cantidadActual();
		$.ajax({
		  type: "POST",
		  url:  'pedido/agregar',
		  data: "{\"id\":"+Ext.getStore("Vehiculos")._proxy._extraParams.idFiltro+", \"codigo\": \"cod\", \"desc\":\"3\",\"cantidad\":"+cantidad+",\"precio\":0,\"precioUnitario\":0  }",
		  dataType: "json",
		  contentType :'application/json',
		  success :  function(value){
			  if(value.success){
				  showSuccess(value,'Pedido Guardado');
			  }else{
				showError('Error Al Guardar el Pedido');
			  }
		  }
		});
	}
}
function cantidadActual(){
	
	var labelText=document.getElementsByName("cantidadAgregadaLabel")[document.getElementsByName("cantidadAgregadaLabel").length-1].innerHTML;
	return parseInt(labelText.substring(labelText.indexOf(':')+1,labelText.length));
}
function borrarPedido(){

	if(cantidadActual() != 0){
		
		$.ajax({
		  type: "POST",
		  url:  'pedido/eliminarDetalle',
		  data: "{\"id\":"+Ext.getStore("Vehiculos")._proxy._extraParams.idFiltro+", \"codigo\": \"cod\", \"desc\":\"3\",\"cantidad\":0,\"precio\":0,\"precioUnitario\":0  }",
		  dataType: "json",
		  contentType :'application/json',
		  success :  function(value){
			  if(value.success){
				  showSuccess(value,'Filtro eliminado');
			  }else{
				showError('Error al Eliminar el Filtro');
			  }
		  }
		});
	}
}

function showSuccess(value,message){
	  Ext.Msg.show({

		    html : message,
		    buttons : [{
		        xtype: 'button',
		        text: 'Aceptar',
		        align: 'right',
		        style :'margin : 20px'
		    }],
		     border: 0,
		    style: 'border-color: blue; border-style: solid; background:rgb(243, 243, 243);text-align: center '
		});
	  if(value!=null){
		  document.getElementsByName("cantidadAgregadaLabel")[document.getElementsByName("cantidadAgregadaLabel").length-1].innerHTML= "Filtros Agregados: "+value.cantidad;
	  	document.getElementsByName("txtCantidad")[document.getElementsByName("txtCantidad").length-1].value= "";
	  }
}

function showError(value){
	  Ext.Msg.show({

		    html : value,
		    buttons : [{
		        xtype: 'button',
		        text: 'Aceptar',
		        align: 'right',
		        style :'margin : 20px'
		    }],
		     border: 0,
		    style: 'border-color: blue; border-style: solid; background:rgb(243, 243, 243);text-align: center '
		});
}
function keyUpCantidadFiltros(){

	keyUpNumerico(document.getElementsByName("txtCantidad")[document.getElementsByName("txtCantidad").length-1]);
		    
};