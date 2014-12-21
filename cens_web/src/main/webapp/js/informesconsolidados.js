var url = "informesconsolidadosgrilla";
var cookiePage = "";
var cookieRegsXPage = "";

jQuery(document).ready(function () {

	restoreState();
	var filtros = getFiltros();
	
    jQuery("#projectTable").jqGrid({
    	 	url: url + filtros,
            datatype: "json",
            contentType :'application/json',
            jsonReader: {repeatitems: false, id:"informeConsolidadoId"},
            colNames:['Periodo', 'Estado','Tipo', 'Acci&oacute;n','Finalizar','Generar Archivos','<span class="ui-icon ui-icon-trash"/>'],
            colModel:[
                {name:'periodo',index:'periodo', sortable: false},
                {name:'estado',index:'estado',sortable: false},
                {name:'tipo',index:'tipo', width:30, align:"center", sortable: false},
                { 	
        			name: 'informeConsolidadoId',   
        			width: 60,
        			align:"center",
        			editable: false, 
        			sortable: false,
        			fixed: true,
        			formatter: editCurrencyFmatter
        		},
                { 	
        			name: 'informeConsolidadoId',
        			width: 80,
        			align:"center",
        			editable: false, 
        			sortable: false,
        			fixed: true,
        			formatter: consolidarCurrencyFmatter
        		},
                { 	
        			name: 'informeConsolidadoId',
        			width: 100,
        			align:"center",
        			editable: false, 
        			sortable: false,
        			fixed: true,
        			formatter: generarArchivosCurrencyFmatter
        		},
                { 	
                	align: 'center',
        			name: 'informeConsolidadoId',  
        			width: 16,
        			editable: false, 
        			sortable: false,
        			fixed: true,
        			formatter: deleteCurrencyFmatter
        		}
            ],
            rowNum:cookieRegsXPage,
            rowList:[5,10,50],
            pager: "#pagingDiv",
            page:cookiePage,
            viewrecords: true,
            caption: "Informes Consolidados",
            loadComplete: function (data) {
            	$(".ui-pg-selbox").val(cookieRegsXPage);
            	$(".ui-pg-input").val(data.page);
            	setCookie('icPage', $('.ui-pg-input').val());
            	cookiePage = $('.ui-pg-input').val();
            },
            onPaging: function(page){
            	setCookie('icRegXPage', $(".ui-pg-selbox").val());
            	cookieRegsXPage = $(".ui-pg-selbox").val();
            }
        });
        
        $(window).bind('resize', function() {
            $("#projectTable").setGridWidth($(window).width()-marginWidthGrid);
            $("#projectTable").setGridHeight($(window).height()-marginHeightGridFull-20);
        }).trigger('resize');
        
        $( "#consolidarDialog" ).dialog({
    		autoOpen: false,
    		width: 400,
    		buttons: [
    			{
    				text: "Ok",
    				click: function() {
    					$( this ).dialog( "close" );
    					consolidar();
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
        
        $( "#eliminarDialog" ).dialog({
    		autoOpen: false,
    		width: 400,
    		buttons: [
    			{
    				text: "Ok",
    				click: function() {
    					$( this ).dialog( "close" );
    					eliminar();
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
    });
 

 function editCurrencyFmatter (cellvalue, options, rowObject) {
	var template = "<a href='informeconsolidado/{INFORME_ID}' title='{TOOLTIP_TEXT}'>{ACCION}</a>"; 
	var link = template.replace("{INFORME_ID}",cellvalue);
	
	if(rowObject.estado == "Borrador"){		
		link = link.replace("{TOOLTIP_TEXT}","Editar");
		link = link.replace("{ACCION}","<span class='ui-icon ui-icon-pencil' style='margin-left: 38%;'/>");
	}else{
		link = link.replace("{TOOLTIP_TEXT}","Ver");
		link = link.replace("{ACCION}","<span class='ui-icon ui-icon-zoomin' style='margin-left: 38%;'/>");				
	}

	 return link;
 }
 
function consolidarCurrencyFmatter (cellvalue, options, rowObject) {
	 if(rowObject.estado == "Borrador"){
		return "<a href='javascript:consolidar_dialog("+cellvalue+")' title='Finalizar Informe'><span class='ui-icon ui-icon-check' style='margin-left: 38%;'/></a>";
	}else{
		return "";
	}
}
function generarArchivosCurrencyFmatter (cellvalue, options, rowObject) {
	 if(rowObject.estado == "Consolidado"){
		var periodo = rowObject.periodo;
		return "<a href='pago?periodo="+periodo+"' title='Generar Archivos'><span class='ui-icon ui-icon-folder-collapsed' style='margin-left: 38%;'/></a>";
	}else{
		return "";
	}
	 
	 if(rowObject.estado == "Borrador"){

			return "<a href='javascript:enviar_dialog("+cellvalue+")' title='Enviar Informe'><span class='ui-icon ui-icon-mail-closed' style='margin-left: 38%;'/></a>";
		}else{
			return "";
		}
} 
 

function deleteCurrencyFmatter (cellvalue, options, rowObject)
{
	var template = "<a href='javascript:deleteInforme_dialog({ENTITY_ID})' title='Eliminar'><span class='ui-icon ui-icon-trash'/></a>";
	
	if(rowObject.estado == "Borrador"){
		return template.replace("{ENTITY_ID}",cellvalue);
	}else{
		return "";
	}
}

function saveState(){
	 setCookie('icF1', jQuery("#periodo").val());
}

function restoreState(){
	if(isAValidCookie('icF1')){
		jQuery("#periodo").val(getCookie('icF1'));
	}
	
	if(getCookie('icRegXPage') != ""){
		$(".ui-pg-selbox").val(getCookie('icRegXPage'));
		cookieRegsXPage = getCookie('icRegXPage');
	}else{
		cookieRegsXPage = 5;
	}
	if(getCookie('icPage') != ""){
		$('.ui-pg-input').val(getCookie('icPage'));
		cookiePage = getCookie('icPage');
	}else{
		cookiePage = 1;
	}
}
 function gridReload(currentPage){

	var filtros = getFiltros();
	
	var pageNro = 1; 
	if(currentPage != null && currentPage > 1){
		pageNro = currentPage;
	}
	
	jQuery("#projectTable").jqGrid('setGridParam',{
		url: url + filtros,
        gridview:true,
        contentType :'application/json',
      	dataType: "json",
      	page:pageNro}).trigger("reloadGrid");
	
	saveState();
 }
 
 function getFiltros(){
	 var filtros = "?";
	 filtros += "periodo=" + $("#periodo").val();
	 return filtros;
 }
 
 var informeId = null;
 function consolidar_dialog(informeConsolidadoId){
	 informeId = informeConsolidadoId;
 	$("#consolidarDialog").dialog("open");
 }
 
function consolidar(){
	 var currentPage = $('.ui-pg-input').val();
	
	 $.ajax({
		url : "finalizarinformeconsolidado",
		type : "POST",
		data : {
			informeConsolidadoId : informeId
		},
		success : function(data) {

			if (data.success) {
				gridReload(currentPage);
			}

		},
		error : function(data) {
			alert("error: (" + data.status + ") " + data.statusText);
		}
	 });
}

function deleteInforme_dialog(informeConsolidadoId){
	informeId = informeConsolidadoId;
	$("#eliminarDialog").dialog("open");
}
function eliminar(){
	 var currentPage = calculatePageToLoadAfterDelete();
	 $('#spiner').show();
	 $.ajax({
		url : "eliminarinformeconsolidado",
		type : "POST",
		data : {
			informeConsolidadoId : informeId
		},
		success : function(data) {
			if(data.success){
				//refresco datos enla grilla
				gridReload(currentPage);
				$('#message').addClass('msgSuccess');
			}else{
				$('#message').addClass('msgError');
			}
			//cargo mensaje en pantalla
			$('#spiner').hide();
			$('#message').text(data.message)
			setTimeout("$('#message').text('')", 5000);

		},
		error : function(data) {
			 $('#spiner').hide();
			alert("error: (" + data.status + ") " + data.statusText);
		}
	 });
}
function calculatePageToLoadAfterDelete(){
	var nroRegistrosInPage = $('.jqgrow ').size();
	var currentPage = $('.ui-pg-input').val();
	
	if(currentPage > 1 && nroRegistrosInPage == 1){
		return currentPage-1;
	}
	return currentPage;
}