var indice = {};
var inicial = true;
var columnasATotalizar = {};
var anchoGrilla = 0;
var altoGrilla = 0;
var minAnchoGrilla = 600;
var minAltoGrilla = 200;
var codigosContratacion = null;

$(document).ready(function () {

  
    $( "#finalizarDialog" ).dialog({
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
    
	
    $( "#adicionalesDialog" ).dialog({
		autoOpen: false,
		width: 400,
		buttons: [
			{
				text: "Ok",
				click: function() {
					$( this ).dialog( "close" );
				}
			}
		]
	});
    

	//llamo a get data en un hilo de manera que el browser no se bloquee mientras se carga
    //la info del informe. En la UI se mostrara un gif indicando que se esta cargando el informe.
    //Concurrent.Thread.create(getData); 
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

function save_dialog(){
	$('#finalizarDialog').dialog("open");
}

var descriptionRenderer = function(instance, td, row, col, prop,value, cellProperties) {
	var escaped = Handsontable.helper.stringify(value);
	escaped = strip_tags(escaped, '<em><b><a>'); // be sure you only allow certain HTML tags to avoid XSS threats (you should also remove unwanted HTML attributes)
	td.innerHTML = escaped;
	return td;
};

function strip_tags(input, allowed) {
	// +   original by: Kevin van Zonneveld (http://kevin.vanzonneveld.net)
	allowed = (((allowed || "") + "").toLowerCase().match(/<[a-z][a-z0-9]*>/g) || []).join(''); // making sure the allowed arg is a string containing only tags in lowercase (<a><b><c>)
	var tags = /<\/?([a-z][a-z0-9]*)\b[^>]*>/gi,
	commentsAndPhpTags = /<!--[\s\S]*?-->|<\?(?:php)?[\s\S]*?\?>/gi;
	return input.replace(commentsAndPhpTags, '').replace(tags, function ($0, $1) {
		return allowed.indexOf('<' + $1.toLowerCase() + '>') > -1 ? $0 : '';
	});
}


function mostrarInforme(colNames, colTypes, colData) {

	$(".excelGridContainer").handsontable({
		data : colData,
		colHeaders : colNames,
		columns : colTypes,
		fixedColumnsLeft: 2,
		currentRowClassName: 'currentRow',
		search: true,
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
				updateValores(data[0][0]);
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

function finalizar_dialog(){
	$('#finalizarDialog').dialog("open");
}

function adicionalesDetalle(detalleId){	
	$.ajax({
		url: "../obtenerdetalleadicionales",
		type: "POST",
		data:{
			detalleInformeId : detalleId
		},
		success: function(data){	
			if(data.success){
				var template = "<p><label id='adName'>{AD_NAME}:</label> <label id='adVal' style='float:right;'>{AD_VALUE}</label></p>";
				
				$("#adicionalesDialog").html("");
				var adicional;
				for(var i = 0; i < data.adicionales.length; i++){
					adicional = template.replace("{AD_NAME}",data.adicionales[i].beneficio);
					adicional = adicional.replace("{AD_VALUE}",data.adicionales[i].valor.toFixed(2));
					$("#adicionalesDialog").append(adicional);
				}
				
				
				$("#adicionalesDialog").dialog("open");
			}else{
				$("#adicionalesDialog").html("Error");
				$("#adicionalesDialog").dialog("open");
			}
			
		},
		error: function(data){
			alert("error: (" + data.status + ") " + data.statusText);
		}
	});
	
}

/*
 * recupera las filas actualizadas y se guardan
 */
function save(accion){
	var registros = []
	var template = "b_{J}_Valor";
	var myVar;
	var registro;
	var regBen;
	
	for (key in indice){
		registro = $(".excelGridContainer").data("handsontable").getData()[key]		
		
		//Actualizo los valores de los beneficios
		for(keyBen in registro.beneficios){
			regBen = registro.beneficios[keyBen];
			myVar = template.replace("{J}", regBen.beneficioId);
			regBen.valor = registro[myVar];
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
				$('#message').text(data.message);
				setTimeout("$('#message').text('')", 5000);				
			
			}else if(accion == 1){ //selecciono el  boton guardar y salir
				//redireccionar a la grilla de informes
				toInformesGrid();	
				
			}else if(accion == 2){ //selecicono el boton guardar y enviar
				//cambiar estado del informe
				finalizar();
			}

		},
		error: function(data){
			alert("error: (" + data.status + ") " + data.statusText);
		}
	});
}	


function finalizar(){
	var infId = $('#idInforme').val();
	$.ajax({
		url: "../finalizarinformeconsolidado",
		type: "POST",
		data:{
			informeConsolidadoId : infId
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
	window.location='../informeconsolidadoexcelexport?idInforme='+infId;
}

function toInformesGrid(){
	window.location = "../informesconsolidados";	
}


function manejarResultadoSuccessAjax(cHeaders, cTypes, cData){
	//guardo las columnas a totalizar
	for(var i = 0; i < cTypes.length; i++){
		if(cTypes[i].showTotal){
			columnasATotalizar[cTypes[i].data]= true;
		}
	}
	
	//agrego los beneficios
	var template = "b_{J}_Valor";
	var myVar;
	for(var i = 0; i < cData.length; i++){
		for(var j = 0; j < cData[i].beneficios.length; j++){
			myVar = template.replace("{J}", cData[i].beneficios[j].beneficioId);
			cData[i][myVar]=cData[i].beneficios[j].valor;
		}
	}
	
	cData.push(new Object());
	cData[cData.length-1]['usarCheque']=null;
	mostrarInforme(cHeaders, cTypes, cData);
	
	//Concurrent.Thread.create(mostrarInforme, cHeaders, cTypes, cData); 
	
	$('#spiner').hide();
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
		url : "../informeconsolidadoexceldata",
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
			
			manejarResultadoSuccessAjax(cHeaders, cTypes, cData);

		},
		error : function(data) {
			$('#spiner').hide();
			alert("error: (" + data.status + ") " + data.statusText);
			clearInterval(hilo);
		}
	});
		 
}

function botonVolver(){
	if(document.referrer.indexOf("informesporconsolidar") > 0){
		toInformesGrid();
	}else{
		history.go(-1);
	}
}


function applyFilter(){
	var value = ('' + $('#txtFiltro').val());
	
	if(value === ''){
		$('.excelGridContainer').handsontable("selectCell", 0, 0);
	
	}else{
		var source = $(".excelGridContainer").handsontable("getInstance");
		var result = source.search.query(
				value,
				Handsontable.Search.global.getDefaultCallback(),
				Handsontable.Search.global.getDefaultQueryMethod(),
				[0,1]
			);
		
		if(result.length == 0){
			$('.excelGridContainer').handsontable("selectCell", 0, 0);
		}else{
			$('.excelGridContainer').handsontable("selectCell", result[0].row, result[0].col);			
		}
	}	
	$('.excelGridContainer').handsontable('deselectCell');
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
		//source.setDataAtRowProp(posicionTotal, prop, Math.round(total*100)/100);
		totales[prop] = total;
	}
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

/**FUNCIONES QUE ACTUALIZAN LOS TOTALES DE LA FILA LUEGO DE QUE EL USUARIO MODIFICA ALGUN VALOR**/
function updateValores(rowIndex){
	var registro = $(".excelGridContainer").data("handsontable").getData()[rowIndex];
	
	if(registro.id != null && registro.id != ""){
		updateBasicoMes(registro);
		updateInasistencias(registro);
		updateHExtras(registro);
		updateVacaciones(registro)
		updateSAC(registro)
		updateSueldoBruto(registro);
		updateSueldoBrutoRemunerativo(registro);
		updateRetenciones(registro);
		updateNeto(registro);
		updateNetoADepositar(registro);	
		
		updateCargasSociales(registro);
		updateTotales();
	}
	
	//$(".excelGridContainer").data("handsontable").render();
}

function updateBasicoMes(registro){
	var diasMes = $("#diasPeriodo").val();
	if(registro.diasTrabajados != null){
		registro.basicoMes = Math.round( (registro.sueldoBasico / diasMes * registro.diasTrabajados) *100)/100;
	}
}

function updateInasistencias(registro){
	if(registro.nroInasistenciasInjustificadas == null){
		registro.importeInasistenciasInjustificadas = null
	}else{
		registro.importeInasistenciasInjustificadas = Math.round( (registro.sueldoBasico / 30 * registro.nroInasistenciasInjustificadas) * 100)/100;
	}
	
	if(registro.nroInasistenciasSinGoceSueldo == null){
		registro.importeInasistenciasSinGoceSueldo = null
	}else{
		registro.importeInasistenciasSinGoceSueldo = Math.round( (registro.sueldoBasico / 30 * registro.nroInasistenciasSinGoceSueldo) * 100)/100;
	}
	
}

function updateHExtras(registro){
	var hsMes = $("#horasPeriodo").val();
	if(registro.hsExtras50 != null){
		if(registro.hsExtrasConPresentismo){
			registro.valorHsExtras50 = Math.round( ((registro.sueldoBasico + registro.asistenciaPuntualidad)/hsMes * 1.5 * registro.hsExtras50) *100)/100;
		}else{
			registro.valorHsExtras50 = Math.round( (registro.sueldoBasico/hsMes * 1.5 * registro.hsExtras50) *100)/100;
		}
	}
	if(registro.hsExtras100 != null){
		if(registro.hsExtrasConPresentismo){
			registro.valorHsExtras100 = Math.round( ((registro.sueldoBasico + registro.asistenciaPuntualidad)/hsMes * 2 * registro.hsExtras100) *100)/100;
		}else{
			registro.valorHsExtras100 = Math.round( (registro.sueldoBasico/hsMes * 2 * registro.hsExtras100) *100)/100;
		}		
	}

}

function calcularTotalBeneficios(registro){
	var template = "b_{J}_Valor";
	var myVar;
	var regBen;
	var totalBeneficios = 0;
	var val;
	for(var i=0; i < registro.beneficios.length; i++){
		regBen = registro.beneficios[i];
		myVar = template.replace("{J}", regBen.beneficioId);
		val = registro[myVar];
		if(val != null && val != undefined){
			totalBeneficios += val;
		}
	}

	return totalBeneficios;
}

function updateVacaciones(registro){
	var divisorVacaciones = Number($('#divisorVacaciones').val());
	var diasMes = Number($("#diasPeriodo").val());
	
	var vacaciones = 0;
	if(registro.vacacionesDias != null && registro.vacacionesDias != ""){
		vacaciones = Math.round( (registro.sueldoBasico / divisorVacaciones * registro.vacacionesDias) *100)/100;
		vacaciones -= Math.round( (registro.sueldoBasico / diasMes * registro.vacacionesDias) *100)/100;
		registro.vacacionesValor = vacaciones;
	}else{
		registro.vacacionesValor = null;
	}
}

function updateSAC(registro){
	if(registro.sacDias != null && registro.sacDias != "" && registro.sacBase != null && registro.sacBase != ""){
		registro.sacValor = Math.round( (registro.sacBase/360 * registro.sacDias) *100)/100;
	}else{
		registro.sacValor = null;
	}
}

function updateSueldoBruto(registro){
	
	var bruto = 0;
	
	if(registro.basicoMes != null){ bruto += Math.round(registro.basicoMes*100)/100};
	if(registro.asistenciaPuntualidad != null){ bruto += Math.round(registro.asistenciaPuntualidad*100)/100};
	
	if(registro.importeInasistenciasInjustificadas != null){ bruto -= Math.round(registro.importeInasistenciasInjustificadas*100/100)};
	if(registro.importeInasistenciasSinGoceSueldo!= null){ bruto -= Math.round(registro.importeInasistenciasSinGoceSueldo*100/100)};
	
	if(registro.valorHsExtras50 != null){ bruto += Math.round(registro.valorHsExtras50*100)/100};
	if(registro.valorHsExtras100 != null){ bruto += Math.round(registro.valorHsExtras100*100)/100};
	
	bruto += Math.round(calcularTotalBeneficios(registro)*100)/100;
	
	if(registro.conceptoRemurativoPlus != null){bruto += Math.round(registro.conceptoRemurativoPlus*100)/100;}
	if(registro.conceptoNoRemurativo != null){bruto += Math.round(registro.conceptoNoRemurativo*100)/100;}
	
	if(registro.vacacionesValor != null){bruto += Math.round(registro.vacacionesValor*100)/100;}
	if(registro.sacValor != null){bruto += Math.round(registro.sacValor*100)/100;}
	
	registro.sueldoBruto = bruto;
	
}

function updateSueldoBrutoRemunerativo(registro){
	
	if(registro.sueldoBruto != null){
		registro.sueldoBrutoRemunerativo = registro.sueldoBruto;
		if(registro.conceptoNoRemurativo != null){
			registro.sueldoBrutoRemunerativo -=  Math.round(registro.conceptoNoRemurativo*100)/100;
		}
	}
}

function updateRetenciones(registro){
	var tope = Number($('#topeRetencion').val());
	var retOsPorc = Number($('#retOSPorc').val())/100;
	var ret11Porc = Number($('#ret11Porc').val())/100;
	var ret3Porc = Number($('#ret3Porc').val())/100;
	var myVal;
	
	if(registro.sueldoBrutoRemunerativo > tope){
		myVal = tope;
	}else{
		myVal = registro.sueldoBrutoRemunerativo;
	}
	
	registro.ret11y3 = Math.round(myVal * ret11Porc *100)/100 + Math.round(myVal * ret3Porc *100)/100;
	registro.retObraSocial = Math.round(myVal * retOsPorc * 100)/100;
}

function updateNeto(registro){
	var neto = registro.sueldoBruto;
	if(registro.ret11y3 != null){neto -= registro.ret11y3;}
	if(registro.retObraSocial != null){neto -= registro.retObraSocial;}
	if(registro.retGanancia != null){neto -= registro.retGanancia;}
	
	registro.neto = Math.round(neto *100)/100;
}

function updateNetoADepositar(registro){
	var netoADepositar = registro.neto;
	if(registro.celular != null){netoADepositar -= registro.celular;}
	if(registro.prepaga != null){netoADepositar -= registro.prepaga;}
	if(registro.adelantos != null){netoADepositar -= registro.adelantos;}
	if(registro.reintegros != null){netoADepositar -= registro.reintegros;}
	if(registro.embargos != null){netoADepositar -= registro.embargos;}

	if(registro.usarCheque){
		registro.cheque = Math.round(netoADepositar *100)/100;
		registro.netoADepositar = null;
	}else{
		registro.netoADepositar = Math.round(netoADepositar *100)/100;
		registro.cheque = null;
	}
}

function updateCargasSociales(registro){
	if(registro.codigoContratacionEmpleado == null || registro.codigoContratacionEmpleado == undefined){
		registro.contOS = null;
		registro.cont176510 = null;
		return;
	}
	var contOSPorc = Number($('#contOsPorc').val())/100;
	getContCodigosMap();
	
	registro.contOS = Math.round(registro.sueldoBrutoRemunerativo  * contOSPorc * 100)/100;
	registro.cont176510 = Math.round(registro.sueldoBrutoRemunerativo * codigosContratacion[registro.codigoContratacionEmpleado] * 100)/100;
}

function getContCodigosMap(){
	if(codigosContratacion == null){
		codigosContratacion = {};
		var codigos = ($('#contCodigos').val()).split(";");
		var codigo;
		for ( var int = 0; int < codigos.length; int++) {
			codigo = codigos[int].split("-");
			codigosContratacion[Number(codigo[0])] = Number(codigo[1])/100;
		}
	}
}
/** ------------------------------------------------------------------------------------------- **/