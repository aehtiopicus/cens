var url = "informesporconsolidargrilla";
var periodoSeleccionado;
jQuery(document).ready(function () {
	
	restoreState();
	var filtros = getFiltros();
	
    jQuery("#projectTable").jqGrid({
            url: url + filtros,
            datatype: "json",
            contentType :'application/json',
            jsonReader: {repeatitems: false, id:"informeMensualId"},
            colNames:['Periodo', 'Cliente ','Gerente de Operaciones', 'Estado', 'Ver','Rechazar'],
            colModel:[
                {name:'periodo',index:'periodo', sortable: false},
                {name:'clienteNombre',index:'clienteNombre',sortable: false},
                {name:'gerenteNombre',index:'gerenteNombre',sortable: false},
                {name:'estado',index:'estado',sortable: false},
                { 	
        			name: 'informeId',   
        			width: 40,
        			editable: false, 
        			sortable: false,
        			fixed: true,
        			formatter: verCurrencyFmatter
        		},
                { 	
        			name: 'informeId',
        			width: 65,
        			editable: false, 
        			sortable: false,
        			fixed: true,
        			formatter: rechazarCurrencyFmatter
        		}
            ],
            viewrecords: true,
            rowNum: 0,
            caption: "Informes Mensuales"
        });
        
        $(window).bind('resize', function() {
            $("#projectTable").setGridWidth($(window).width()-marginWidthGrid);
            $("#projectTable").setGridHeight($(window).height()-marginHeightGridFull);
        }).trigger('resize');
        
        $("#advertenciaDialog").dialog({
        	autoOpen : false,
        	width : 400,
        	buttons : [ {
        		text : "Ok",
        		click : function() {
        			$(this).dialog("close");
        			verificarExistencia();
        		}
        	}, {
        		text : "Cancelar",
        		click : function() {
        			$(this).dialog("close");
        		}
        	} ]
        });
        
        periodoSeleccionado = $("#periodo").val();
    });
 

 function verCurrencyFmatter (cellvalue, options, rowObject) {
	if(rowObject.estado == "Recibido"){
		var template1 = "<a href='../operacion/informemensual/{INFORME_MENUSAL_ID}' title='Ver'><span class='ui-icon ui-icon-search' style='margin-left: 38%;'/></a>"; 
	    return template1.replace("{INFORME_MENUSAL_ID}",cellvalue);	
	}else{
		 return "";
	 }
	
 }
 function rechazarCurrencyFmatter (cellvalue, options, rowObject) {
	 if(rowObject.estado == "Recibido"){
		 var template1 = "<a href='javascript:rechazar({INFORME_MENUSAL_ID})' title='Rechazar'><span class='ui-icon ui-icon-closethick' style='margin-left: 38%;'/></a>"; 
		 return template1.replace("{INFORME_MENUSAL_ID}",cellvalue);	 
	 }else{
		 return "";
	 }
	 
		 
}
 
function saveState(){
	 setCookie('imncF1', jQuery("#cliente").val());
	 setCookie('imncF2', jQuery("#estado").val());
	 setCookie('imncF3', jQuery("#periodo").val());
}

function restoreState(){
	if(isAValidCookie('imncF1')){ 
		jQuery("#cliente").val(getCookie('imncF1'));
	}
	if(isAValidCookie('imncF2')){
		jQuery("#estado").val(getCookie('imncF2'));
	}
	if(isAValidCookie('imncF3')){
		jQuery("#periodo").val(getCookie('imncF3'));
	}
}

 function gridReload(currentPage){

	var filtros = getFiltros();
    periodoSeleccionado = $("#periodo").val();

	var pageNro = 1; 
	if(currentPage != null && currentPage > 1){
		pageNro = currentPage;
	}
	
	jQuery("#projectTable").jqGrid(
           'setGridParam',
           {
           	url: url + filtros,
            gridview:true,
            contentType :'application/json',
      		dataType: "json",
      		page:pageNro})
      		.trigger("reloadGrid");
	
	saveState();
}
 

 function getFiltros() {
	var filtros = "?";
	filtros += "cliente=" + $("#cliente").val() + "&";
	filtros += "estado=" + $("#estado").val() + "&";
	filtros += "periodo=" + $("#periodo").val();
	return filtros;
}
 
function crearInforme(){
	if($('td[title=Pendiente]').size() > 0){
		$("#advertenciaDialog").dialog("open");
	}else{
		verificarExistencia();
	}
}

function verificarExistencia() {
	var beneficioId = $("#beneficio").val();
	var clienteId = $("#clienteId").val();

	$.ajax({
		type : "POST",
		url : "verificarexistencia",
		data : {
			periodo : periodoSeleccionado
		},
		success : function(data) {
			if(data.existe){
				$('#message').addClass('msgError');
				$('#message').text("El informe para " + periodoSeleccionado + " ha sido creado previamente.")
				setTimeout("$('#message').text('')", 5000);		
				
			}else{
				window.location = "nuevoinforme?periodo=" + periodoSeleccionado;
			}
			
		},
		error : function(rs, e) {
			alert(rs.responseText);
			return false;
		}
	});
}

function rechazar(informeMensualId) {
	$.ajax({
		url : "rechazarinformemensual",
		type : "POST",
		data : {
			informeMensualId : informeMensualId
		},
		success : function(data) {
			if (data.success) {
				gridReload();
			}

		},
		error : function(data) {
			alert("error: (" + data.status + ") " + data.statusText);
		}
	});
}

function exportar(){
	var url = '/exportar' + getFiltros();
	window.location= window.location.href+url;	
}