var indice = {};
var inicial = true;
var columnasATotalizar = {};
var anchoGrilla = 0;
var altoGrilla = 0;
var minAnchoGrilla = 600;
var minAltoGrilla = 200;

$(document).ready(function () {

  
    $( "#enviarDialog" ).dialog({
		autoOpen: false,
		width: 400,
		buttons: [
			{
				text: "Ok",
				click: function() {
					$( this ).dialog( "close" );
					save(2);
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
    
	getData();

	$(window).bind('resize', function() {
		
    	anchoGrilla = $(window).width()-marginWidthGrid;
    	altoGrilla = $(window).height()-marginHeightGrid+80;
    	
    	$(".excelGridContainer").handsontable('render');
    	
    	if(anchoGrilla < minAnchoGrilla){
    		$('.htCore').width(minAnchoGrilla);
    	}else{
        	$('.htCore').width(anchoGrilla);		
    	}

    }).trigger('resize');
});

function mostrarInforme(colNames, colTypes, colData) {
	
	$(".excelGridContainer").handsontable({
		data : colData,
		colHeaders : colNames,
		columns : colTypes,
		fixedColumnsLeft: 1,
		currentRowClassName: 'currentRow',
		width: function() {
			if (anchoGrilla == 0) {
				return $(window).width()-marginWidthGrid;
			} else if(anchoGrilla < minAnchoGrilla){
				return minAnchoGrilla;
			}
			return anchoGrilla;
		},
			
		height: function(){
			if(altoGrilla == 0){
				return $(window).height()-marginHeightGrid+80;
			} else if(altoGrilla < minAltoGrilla){
				return minAltoGrilla;
			}
			return altoGrilla;
		},
		
		onChange : function(data) {
			if (inicial == false) {
				indice[data[0][0]] = data[0][0];
				updateColumnTotal(data[0][1]);
			} else {
				inicial = false;
			}

		},
		afterInit: function(){
			updateTotales();
		}
	});
	
	$('.htCore').width($(window).width()-marginWidthGrid);
}

function save_dialog(){
	$('#enviarDialog').dialog("open");
}

/*
 * recupera las filas actualizadas y se guardan
 */
function save(accion){
	var registros = []
	var template = "b{J}Valor";
	var myVar;
	var registro;
	
	for (key in indice){
		registro = $(".excelGridContainer").data("handsontable").getData()[key]
		
		//actualizo los valores de los beneficios 
		for(var j = 0; j < registro.beneficios.length; j++){
			myVar = template.replace("{J}", registro.beneficios[j].beneficioId);
			registro.beneficios[j].valor = registro[myVar];
		}			
		registros.push(registro);	
	}
	
	
	$.ajax({
		url: "update",
		type: "POST",
		data: 
			"{ \"registros\" :"+JSON.stringify(registros)+"}"		
		,
		contentType :'application/json',
		dataType: "json",
		success: function(data){
			indice = {}; 
			
			if(accion == null){	//selecciono el  boton guardar
				if(data.success){
					$('#message').addClass('msgSuccess');				
				}
				//cargo mensaje en pantalla
				$('#message').text(data.message)
				setTimeout("$('#message').text('')", 5000);				
			
			}else if(accion == 1){ //selecciono el  boton guardar y salir
				//redireccionar a la grilla de informes
				toInformesGrid();	
				
			}else if(accion == 2){ //selecicono el boton guardar y enviar
				//cambiar estado del informe
				enviar();
			}

		},
		error: function(data){
			alert("error: (" + data.status + ") " + data.statusText);
		}
	});
}	


function enviar(){
	var infId = $('#idInforme').val();
	$.ajax({
		url: "../enviarinformemensual",
		type: "POST",
		data:{
			informeMensualId : infId
		},
		success: function(data){
			
			if(data.success){
				toInformesGrid();		
			}
			
		},
		error: function(data){
			alert("error: (" + data.status + ") " + data.statusText);
		}
	});
}

function exportar(){
	var infId = $('#idInforme').val();
	window.location='../informemensualexcelexport?idInforme='+infId;
}


function toInformesGrid(){
	window.location = "../informesmensuales";	
}


/**
 * Obtiene los datos necesarios para llenar la grilla excel :)
 * 
 */
function getData() {
	var idInforme = $("#idInforme").val();
	$('#spiner').show();
	
	$.ajax({
		cache: false,
		url : "../informemensualexceldata",
		type : "GET",
		data : {
			idInforme : idInforme
		},
		dataType : "text",
		success : function(data) {

			var oData = JSON.parse(data);

			var cHeaders = oData.colHeaders;
			var cTypes = oData.colTypes;
			var cData = oData.colData;

			
			//guardo las columnas a totalizar
			for(var i = 0; i < cTypes.length; i++){
				if(cTypes[i].showTotal){
					columnasATotalizar[cTypes[i].data]= true;
				}
			}
			
			var template = "b{J}Valor";
			var myVar;
			for(var i = 0; i < cData.length; i++){
				for(var j = 0; j < cData[i].beneficios.length; j++){
					myVar = template.replace("{J}", cData[i].beneficios[j].beneficioId);
					cData[i][myVar]=cData[i].beneficios[j].valor;
				}
				
				
			}
			
			//agrego una linea mas al informe (para los totales)
			cData.push(new Object());
			
			mostrarInforme(cHeaders, cTypes, cData);
			$('#spiner').hide();
		},
		error : function(data) {
			$('#spiner').hide();
			alert("error: (" + data.status + ") " + data.statusText);
		}
	});
		 
}

function botonVolver(){
	if(document.referrer.indexOf("nuevoinforme") > 0){
		window.location = "../informesmensuales";	
	}else{
		history.go(-1);
	}
}

/**FUNCIONES QUE ACTUALIZAN LOS TOTALES DE LAS COLUMNAS LUEGO DE QUE EL USUARIO MODIFICA ALGUN VALOR O CUANDO LA GRILLA ES RENDERIZADA**/
function updateTotales(){
	var source = $(".excelGridContainer").handsontable("getInstance");
	
	var array;
	var total;
	
	var posicionTotal =source.countRows()-1;
	var totales = source.getData()[posicionTotal];
	
	for(var prop in columnasATotalizar){
		array = source.getDataAtProp(prop).slice(0, source.getDataAtProp(prop).length-1);
		total = Math.round(obtenerSumatoriaFromArray(array)*100)/100;
		totales[prop] = total;
	}
	source.render();

	
}

function updateColumnTotal(columnName){
	var source = $(".excelGridContainer").handsontable("getInstance");
	
	var posicionTotal =source.countRows()-1;
	var totales = source.getData()[posicionTotal];
	
	var	array = source.getDataAtProp(columnName).slice(0, source.getDataAtProp(columnName).length-1);
	var	total = Math.round(obtenerSumatoriaFromArray(array)*100)/100;
		
	totales[columnName] = total;
	source.render();
}

function obtenerSumatoriaFromArray(array){
	var strSumatoria = array.join("_");
	
	strSumatoria = strSumatoria.replace(/__/g,"_0_");
	strSumatoria = strSumatoria.replace(/__/g,"_0_");
	strSumatoria = strSumatoria.replace(/_0/g,"");
	
	var start = 0;
	var end = strSumatoria.length;
	if((/^_/).test(strSumatoria)){start++;}
	if(/_$/.test(strSumatoria)){end--;}
	
	strSumatoria = strSumatoria.slice(start, end);
	strSumatoria = strSumatoria.replace(/_/g,"+");
	if(strSumatoria.length > 0){
		return eval(strSumatoria);		
	}else{
		return 0;
	}
}

